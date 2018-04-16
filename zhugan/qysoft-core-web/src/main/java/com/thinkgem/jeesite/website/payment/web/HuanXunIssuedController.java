package com.thinkgem.jeesite.website.payment.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.payment.utils.Verify;
import com.thinkgem.jeesite.modules.user.entity.UserOptions;
import com.thinkgem.jeesite.modules.user.entity.UserWithdraw;
import com.thinkgem.jeesite.modules.user.service.UserOptionsService;
import com.thinkgem.jeesite.modules.user.service.UserWithdrawService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/28.
 */
@Controller
@RequestMapping(value = "/payment/huanxunPay/issued")
public class HuanXunIssuedController extends BaseController {
    @Autowired
    private UserOptionsService optionService;
    @Resource
    private UserWithdrawService userWithdrawService;


    @RequestMapping(value = "issueds")
    public String issued(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        String userWithdrowId = request.getParameter("MerBillno");
        UserWithdraw userWithdraw = userWithdrawService.get(userWithdrowId);
        if (null == userWithdraw) {
            addMessage(redirectAttributes, "委托打款失败");
            return "redirect:" + Global.getAdminPath() + "/user/userWithdraw/?repage";
        }

        if (!userWithdraw.getStatus().equals(UserWithdraw.editStatus)) {
            addMessage(redirectAttributes, "提现审核未通过,打款信息无法提交!");
            return "redirect:" + Global.getAdminPath() + "/user/userWithdraw/?repage";
        }
        if (!userWithdraw.getBatchNo().equals("0")) {
            addMessage(redirectAttributes, "请不要重复提交!");
            return "redirect:" + Global.getAdminPath() + "/user/userWithdraw/?repage";
        }
        try {
            Map<String, String> huanxunPayConfig = getHuanxunPayConfigInfo();
            //ws 地址
            String wsUrl = huanxunPayConfig.get("wsUrl");
            //String qwsUrl = huanxunPayConfig.get("qwsUrl");
            //证书
            String directStr = huanxunPayConfig.get("md5_cert");
            //3des
            String desKey = huanxunPayConfig.get("desiv");//秘钥
            String desIv = huanxunPayConfig.get("deskey");//向量
            // 委托付款
            String result = issued(request, directStr, wsUrl, desKey, desIv,userWithdraw);
            logger.error(result);
            String status = getIssuedBatchStatus(result);//  0：未处理，8：待审核，9：失败
            String msg = "";
            if (StringUtils2.isNotBlank(getIssuedErrorMsg(result))) {
                msg = getIssuedErrorMsg(result);
            } else {
                msg = getIssuedRspMsg(result);
            }
            if ("0".equals(status)) {
               msg="未处理";
            }
            if(status==null){
                msg="请求失败!";
            }
            addMessage(redirectAttributes, msg);
            if ("8".equals(status)) {
                try {
                    String BatchNo = getBatchBillno(result);
                    userWithdraw.setRemittanceStatus("1");
                    userWithdraw.setBatchNo(BatchNo);
                    userWithdrawService.updateRemittanceStatus(userWithdraw);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            addMessage(redirectAttributes, "系统异常");
            return "redirect:" + Global.getAdminPath() + "/user/userWithdraw/?repage";
        }
        return "redirect:" + Global.getAdminPath() + "/user/userWithdraw/?repage";
    }

    /**
     * 委托付款请求
     *
     * @param request
     * @param directStr
     * @param wsUrl
     * @return
     */
    private String issued(HttpServletRequest request, String directStr, String wsUrl, String key, String iv, UserWithdraw userWithdraw ) {
        // 获取参数
        Map<String, String> huanxunPayConfig = getHuanxunPayConfigInfo();
        String merCode = huanxunPayConfig.get("merchant_num");//商户号
        String merName = huanxunPayConfig.get("MerName");//商户名
        String Account = huanxunPayConfig.get("transaction_account");//商户账号
        String BizId = huanxunPayConfig.get("BizId");//下发类型
        String currency = huanxunPayConfig.get("currency");//币种
        String channel = huanxunPayConfig.get("channel");//渠道类型
        String version = huanxunPayConfig.get("version");//固定版本号
        //（<Detail>为重复节点,可多次出现）
        String detail =
                "<MerBillNo>" + request.getParameter("MerBillno") + "</MerBillNo>" +
                        "<AccountName><![CDATA[" + request.getParameter("AccountName") + "]]></AccountName>" +
                        "<AccountNumber>" + request.getParameter("AccountNumber") + "</AccountNumber>" +
                        "<BankName><![CDATA[" + request.getParameter("BankName") + "]]></BankName>" +
                        "<BranchBankName><![CDATA[" + request.getParameter("BranchBankName") + "]]></BranchBankName>" +
                        "<BankCity><![CDATA[" + request.getParameter("BankCity") + "]]></BankCity>" +
                        "<BankProvince><![CDATA[" + request.getParameter("BankProvince") + "]]></BankProvince>" +
                        "<BillAmount>" + request.getParameter("BillAmount") + "</BillAmount>" +
                        "<IdCard>" + request.getParameter("IdCard") + "</IdCard>" +
                        "<MobilePhone>" + request.getParameter("mobile") + "</MobilePhone>"
                        +
                        "<Remark>" +"用户"+userWithdraw.getUserName()+  "</Remark>";
        String detailDes = null;
        try {
            //对Detail内容用3des加密
            detailDes = encrypt3DES(detail, key, iv);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        // body前部分
        String bodyXmlh = "<Body>" +
                "<BizId>" + BizId + "</BizId>" +
                "<ChannelId>" + channel + "</ChannelId>" +
                "<Currency>" + currency + "</Currency>" +
                "<Date>" + date + "</Date>" +
                "<Attach><![CDATA[" + "单号" + request.getParameter("MerBillno")+"用户"+userWithdraw.getUserName()+"]]></Attach>" +
                "<IssuedDetails>" + "<Detail>";
        //body尾部
        String bodyXmlf = "</Detail>" + "</IssuedDetails>" + "</Body>";
        //拼接完整<Body></Body>
        String bodyXml = bodyXmlh + detailDes + bodyXmlf;
        //System.out.println("bodyXml:" + bodyXml);
        // 利用body+数字证书做MD5签名
        String sign = DigestUtils.md5Hex(Verify.getBytes(bodyXml + directStr, "UTF-8"));
        //System.out.println("sign:" + sign);
        // 拼接发送ips完整的xml报文
        String xml =
                "<Req>" +
                        "<Head>" +
                        "<Version>" + version + "</Version>" +
                        "<MerCode>" + merCode + "</MerCode>" +
                        "<MerName>" + merName + "</MerName>" +
                        "<Account>" + Account + "</Account>" +
                        "<MsgId>" + request.getParameter("MerBillno") + "</MsgId>" +
                        "<ReqDate>" + date + "</ReqDate>" +
                        "<Signature>" + sign + "</Signature>" +
                        "</Head>" +
                        bodyXml +
                        "</Req>";
        logger.error(">>>>> 委托付款 请求报文: " + xml);
        //拼接webservice  url地址
        String wsUrlSdl = wsUrl + "issued?wsdl";
        //代理方式调用webservice
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsUrlSdl);
        Object[] result = null;
        try {
            result = client.invoke("issued", xml);//调用webservice
            //调用ws接口  获取响应报文
            logger.error("返回报文---------------------------------------");
            logger.error("非客户端模式" + (String) result[0]);
        } catch (Exception e) {
            logger.error("请求失败");
            return "请求失败";
        }
        String resultXml = (String) result[0];
        // 1、返回报文验签
        if (!checkSign(resultXml, merCode, directStr)) {
            logger.error("验签失败");
            return "验签失败";
        }
        //2、 验签通过，判断IPS返回状态码
        if (!getRspCode(resultXml).equals("000000")) {
            logger.error("交易响应不成功");
            return resultXml;
        }
        logger.error(resultXml);
        return resultXml;
    }

    //3des加密
    public static String encrypt3DES(String encryptString, String encryptKey, String iv) throws Exception {
        byte encryptedData[];
        IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes("UTF-8"));
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("UTF-8"), "DESede");
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(1, key, zeroIv);
        encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));
        String is3DesString = new String(org.apache.commons.codec.binary.Base64.encodeBase64(encryptedData));
        return is3DesString;
    }

    /**
     * 验签
     *
     * @param xml
     * @return
     */
    public boolean checkSign(String xml, String merCode, String directStr) {

        if (xml == null) {
            return false;
        }
        String OldSign = getSign(xml); // 返回签名
        String text = getBodyXml(xml); // body
        System.out.println("MD5验签，验签文：" + text + "\n待比较签名值:" + OldSign);

        String result = DigestUtils.md5Hex(Verify.getBytes(text + directStr, "UTF-8"));
        System.out.println("签名值:" + result);
        if (OldSign == null || result == null || !OldSign.equals(result)) {
            return false;
        }
        return true;
    }

    /**
     * 获取报文中<rspcode></rspcode>部分
     *
     * @param xml
     * @return
     */
    public String getRspCode(String xml) {
        int s_index = xml.indexOf("<RspCode>");
        int e_index = xml.indexOf("</RspCode>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index+9, e_index);
        }
        return sign;
    }

    /**
     * 获取报文中<BatchStatus></BatchStatus>部分
     *
     * @param xml
     * @return
     */
    public String getIssuedBatchStatus(String xml) {
        int s_index = xml.indexOf("<BatchStatus>");
        int e_index = xml.indexOf("</BatchStatus>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 13, e_index);
        }
        return sign;
    }

    /**
     * 获取报文中<BatchStatus></BatchStatus>部分
     *
     * @param xml
     * @return
     */
    public String getIssuedRspMsg(String xml) {
        int s_index = xml.indexOf("<RspMsg><![CDATA[");
        int e_index = xml.indexOf("]]></RspMsg>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 17, e_index);
        }
        return sign;

    }

    /**
     * 获取报文中<BatchNo></BatchNo>部分
     *
     * @param xml
     * @return
     */
    public String getBatchBillno(String xml) {
        int s_index = xml.indexOf("<BatchBillno>");
        int e_index = xml.indexOf("</BatchBillno>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 13, e_index);
        }
        return sign;
    }

    /**
     * 获取报文中<ErrorMsg></ErrorMsg>部分
     *
     * @param xml
     * @return
     */
    public String getIssuedErrorMsg(String xml) {
        int s_index = xml.indexOf("<ErrorMsg>");
        int e_index = xml.indexOf("</ErrorMsg>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 10, e_index);
        }
        return sign;
    }

    /**
     * 获取报文中<Signature></Signature>部分
     *
     * @param xml
     * @return
     */
    public String getSign(String xml) {
        int s_index = xml.indexOf("<Signature>");
        int e_index = xml.indexOf("</Signature>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 11, e_index);
        }
        return sign;
    }

    /**
     * 获取body部分
     *
     * @param xml
     * @return
     */
    public String getBodyXml(String xml) {
        int s_index = xml.indexOf("<Body>");
        int e_index = xml.indexOf("</Body>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index, e_index + 7);
        }
        System.out.println("返回body：" + sign);
        return sign;
    }

    /**
     * 获取报文中<ErrorCode></ErrorCode>部分
     *
     * @param xml
     * @return
     */
    public String getIssuedErrorCode(String xml) {
        int s_index = xml.indexOf("<ErrorCode>");
        int e_index = xml.indexOf("</ErrorCode>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 14, e_index);
        }
        return sign;
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
}
