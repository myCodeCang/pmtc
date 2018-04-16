package com.thinkgem.jeesite.website.payment.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.payment.entity.ThirdPartPayCallbackEntity;
import com.thinkgem.jeesite.modules.payment.service.HuanxunPayService;
import com.thinkgem.jeesite.modules.user.entity.UserOptions;
import com.thinkgem.jeesite.modules.user.service.UserOptionsService;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.bouncycastle.openssl.PEMReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Map;

/**
 * Created by yankai on 2017/6/25.
 */
@Controller
@RequestMapping(value = "/payment/huanxunPay")
public class HuanXunPayController extends BaseController {
    private static final String RETURN_OK = "ipscheckok";
    private static final String RETURN_FAIL = "ipscheckfail";
    @Autowired
    private UserOptionsService optionService;

    @Autowired(required = false)
    @Qualifier("huanxunCallback")
    private HuanxunPayService huanxunCllback;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 支付成功
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/finish")
    public String payFinish(HttpServletRequest request, Model model) {
        String isweb = request.getParameter("isweb");
        if (StringUtils2.isBlank(isweb)) {
            return "website/themes/basic_app/payment/finish";
        } else {
            Map<String, String> huanxunPayConfig = getHuanxunPayConfigInfo();
            model.addAttribute("redirect", huanxunPayConfig.get("success_url"));
            return "website/themes/basic_web/payment/finish";
        }
    }

    @ResponseBody
    @RequestMapping(value = "success")
    public String successNotify(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");

            // 获取xml
            String resultXml = request.getParameter("paymentResult");
            if (StringUtils2.isBlank(resultXml)) {
                logger.error("resultXml is Null");
                return RETURN_FAIL;
            }

            //获取环迅支付配置信息
            Map<String, String> payConfig = getHuanxunPayConfigInfo();
            if (payConfig == null || payConfig.isEmpty()) {
                logger.error("payConfig is Null");
                return RETURN_FAIL;
            }

            String merCode = payConfig.get("merchant_num");             //商户号
            String md5Cert = payConfig.get("md5_cert");                 //MD5证书
            String rsaKey = payConfig.get("rsa_key");                   //RSA公钥

            // 获取请求uri
            String httpInfo = request.getRequestURI();
            if (httpInfo.contains("/huanxunPay/success")) {
                String result = s2sNotify(resultXml, merCode, md5Cert, rsaKey);
                if (RETURN_OK.equals(result)) {
                    String domain = Global.getOption("system_sys", "domain");
                    String successUrl = domain + "/payment/huanxunPay/finish";
                    if (!StringUtils2.isBlank(successUrl) && StringUtils2.startsWithIgnoreCase(successUrl, "http")) {
                        ServletOutputStream output = response.getOutputStream();
                        String attach = getAttach(resultXml);
                        if ("<![CDATA[WEB]]>".equals(attach)) {
                            successUrl = successUrl + "?isweb=1";
                        }
                        output.flush();
                        output.println(RETURN_OK);
                        output.println("<script>");
                        output.println("location.href='" + successUrl + "'");
                        output.println("</script>");
                    }
                }
                return result;
            }
            return RETURN_OK;
        } catch (Exception ex) {
            logger.error("Callback error:------ " + ex.getLocalizedMessage());
            return RETURN_FAIL;
        }
    }

    private String s2sNotify(String resultXml, String merCode, String directStr, String ipsRsaPub) {
        // 1、获取签名方式 验签
        if (!checkSign(resultXml, merCode, directStr, ipsRsaPub)) {
            logger.error("签名验证失败:" + resultXml);
            return RETURN_FAIL;
        }
        //2、验签通过，判断IPS返回状态码
        if (!getRspCode(resultXml).equals("000000")) {
            logger.error("请求响应不成功:" + resultXml);
            return RETURN_FAIL;
        }

        //3、IPS返回成功  根据交易状态做相应处理
        String status = getStatus(resultXml);
        if (status.equals("Y")) {   //交易成功
            //Object huanxunPayService = SpringContextHolder.getBean("huanxunPayService");
            if (huanxunCllback == null) {
                return RETURN_OK;
            }

            //构造请求返回参数
            String merBillNo = getMerBillNo(resultXml);
            if (StringUtils2.isBlank(merBillNo)) {
                logger.error("merBillNo is Null");
                return RETURN_FAIL;
            }

            String[] split = merBillNo.split("_");
            if (split == null || split.length < 2) {
                logger.error("split is Null");
                return RETURN_FAIL;
            }

            String userName = split[0];
            String merBillNum = split[1];
            ThirdPartPayCallbackEntity entity = new ThirdPartPayCallbackEntity();
            entity.setAmount(getAmount(resultXml));
            entity.setOrderNo(merBillNum);
            entity.setUserName(userName);
            entity.setTradeDate(getDate(resultXml));
            entity.setBillNo(getIpsBillNo(resultXml));

            if (!huanxunCllback.doPayBusiness(entity)) {
                logger.error("Do business error");
                return RETURN_FAIL;
            }

        } else if (status.equals("N")) {
            logger.error("交易失败:" + resultXml);
            return RETURN_FAIL;
        } else if (status.equals("P")) {
            logger.error("交易处理中:" + resultXml);
            return RETURN_FAIL;
        }

        return RETURN_OK;
    }

    /**
     * 获取报文中<IpsBillNo></IpsBillNo>部分
     *
     * @param xml
     * @return
     */
    private String getIpsBillNo(String xml) {
        int s_index = xml.indexOf("<IpsBillNo>");
        int e_index = xml.indexOf("</IpsBillNo>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 11, e_index);
        }
        return sign;
    }

    /**
     * 获取报文中<MerBillNo></MerBillNo>部分
     *
     * @param xml
     * @return
     */
    private String getMerBillNo(String xml) {
        int s_index = xml.indexOf("<MerBillNo>");
        int e_index = xml.indexOf("</MerBillNo>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 11, e_index);
        }
        return sign;
    }

    /**
     * 获取报文中<RspCode></RspCode>部分
     *
     * @param xml
     * @return
     */
    private String getRspCode(String xml) {
        int s_index = xml.indexOf("<RspCode>");
        int e_index = xml.indexOf("</RspCode>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 9, e_index);
        }
        return sign;
    }

    /**
     * 获取报文中<Status></Status>部分
     *
     * @param xml
     * @return
     */
    private String getStatus(String xml) {
        int s_index = xml.indexOf("<Status>");
        int e_index = xml.indexOf("</Status>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 8, e_index);
        }
        return sign;
    }

    /**
     * 获取报文中<RetEncodeType></RetEncodeType>部分
     *
     * @param xml
     * @return
     */
    private String getRetEncodeType(String xml) {
        int s_index = xml.indexOf("<RetEncodeType>");
        int e_index = xml.indexOf("</RetEncodeType>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 15, e_index);
        }
        return sign;
    }

    /**
     * 获取body部分
     *
     * @param xml
     * @return
     */
    private String getBodyXml(String xml) {
        int s_index = xml.indexOf("<body>");
        int e_index = xml.indexOf("</body>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index, e_index + 7);
        }
        return sign;
    }

    /**
     * 获取报文中<Signature></Signature>部分
     *
     * @param xml
     * @return
     */
    private String getSign(String xml) {
        int s_index = xml.indexOf("<Signature>");
        int e_index = xml.indexOf("</Signature>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 11, e_index);
        }
        return sign;
    }

    /**
     * 验证签名
     *
     * @param xml
     * @return
     */
    private boolean checkSign(String xml, String merCode, String directStr, String ipsRsaPub) {

        if (xml == null) {
            return false;
        }
        String OldSign = getSign(xml); // 返回签名
        String text = getBodyXml(xml); // body
        String retEncodeType = getRetEncodeType(xml); //加密方式
        String result = null;
        if (OldSign == null || retEncodeType == null) {
            return false;
        }
        // 根据验签方式进行验签
        if (retEncodeType.equals("16")) {
            return verifyMD5withRSA(ipsRsaPub, text + merCode, OldSign);
        } else if (retEncodeType.equals("17")) {
            result = DigestUtils
                    .md5Hex(getBytes(text + merCode + directStr,
                            "UTF-8"));
        } else {
            return false;
        }
        if (result == null || !OldSign.equals(result)) {
            return false;
        }
        return true;
    }

    /**
     * 获取报文中<Attach></Attach>部分
     *
     * @param xml
     * @return
     */
    private String getAttach(String xml) {
        int s_index = xml.indexOf("<Attach>");
        int e_index = xml.indexOf("</Attach>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 8, e_index);
        }
        return sign;
    }

    /**
     * 获取报文中<Amount></Amount>部分
     *
     * @param xml
     * @return
     */
    private String getAmount(String xml) {
        int s_index = xml.indexOf("<Amount>");
        int e_index = xml.indexOf("</Amount>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 8, e_index);
        }
        return sign;
    }

    /**
     * 获取报文中<Date></Date>部分
     *
     * @param xml
     * @return
     */
    private String getDate(String xml) {
        int s_index = xml.indexOf("<Date>");
        int e_index = xml.indexOf("</Date>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 6, e_index);
        }
        return sign;
    }

    public byte[] getBytes(String content, String charset) {
        if (StringUtils2.isBlank(content)) {
            content = "";
        }
        if (StringUtils2.isBlank(charset))
            throw new IllegalArgumentException("charset can not null");
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
        }
        throw new RuntimeException("charset is not valid,charset is:" + charset);
    }

    /**
     * 获取环迅支付配置信息
     *
     * @return
     */
    private Map<String, String> getHuanxunPayConfigInfo() {
        UserOptions huanxunConfigValue = optionService.getByOptionName("system_huanxun");
        if (null == huanxunConfigValue) {
            return Maps.newHashMap();
        }

        String optionValue = huanxunConfigValue.getOptionValue();
        if (optionValue == null) {
            return Maps.newHashMap();
        }

        Map<String, String> result = (Map<String, String>) JSONUtils.parse(optionValue);
        if (MapUtils.isEmpty(result)) {
            return Maps.newHashMap();
        }

        return result;
    }

    private boolean verifyMD5withRSA(String publicKey, String data, String sign, String charset) {
        try {
            PublicKey publicKeyObj = loadPublicKey(publicKey);
            return verify(publicKeyObj, getBytes(data, charset), Hex.decodeHex(sign.toCharArray()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean verifyMD5withRSA(String publicKey, String data, String sign) {
        return verifyMD5withRSA(publicKey, data, sign, "UTF-8");
    }

    private PublicKey loadPublicKey(String publicKey) {
        try {
            PEMReader reader = new PEMReader(new StringReader(publicKey));
            return (PublicKey) reader.readObject();
        } catch (Exception ex) {
            throw new RuntimeException("load publicKey error:", ex);
        }
    }

    private boolean verify(PublicKey publicKey, byte[] data, byte[] sign) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(sign);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
