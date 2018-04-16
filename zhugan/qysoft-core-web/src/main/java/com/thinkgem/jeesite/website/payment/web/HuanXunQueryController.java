package com.thinkgem.jeesite.website.payment.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.payment.utils.CxfCommunication;
import com.thinkgem.jeesite.modules.payment.utils.Verify;
import com.thinkgem.jeesite.modules.payment.webservice.huanxun.TradeQueryService;
import com.thinkgem.jeesite.modules.user.entity.UserOptions;
import com.thinkgem.jeesite.modules.user.entity.UserWithdraw;
import com.thinkgem.jeesite.modules.user.service.UserOptionsService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import com.thinkgem.jeesite.modules.user.service.UserWithdrawService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/10.
 */
@Controller
@RequestMapping(value = "/payment/huanxunPay/query")
public class HuanXunQueryController extends BaseController {
    @Autowired
    private UserOptionsService optionService;
    @Resource
    private UserWithdrawService userWithdrawService;
    @Resource
    private UserUserinfoService userUserinfoService;

    @RequestMapping(value = "querys")
    public String query(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Map<String, String> huanxunPayConfig = getHuanxunPayConfigInfo();
            //ws 地址
            String qwsUrl = huanxunPayConfig.get("qwsUrl");
            String userWithdrowId = request.getParameter("MerBillNo");
            UserWithdraw userWithdraw = userWithdrawService.get(userWithdrowId);
            userWithdraw.setUserinfo(userUserinfoService.getByName(userWithdraw.getUserName()));
            String BatchNo = userWithdraw.getBatchNo();
            model.addAttribute("userWithdraw", userWithdraw);
            //证书
            String directStr = huanxunPayConfig.get("md5_cert");
            // 获取请求uri
            String httpInfo = request.getRequestURI();
            String result = null;
            /*if (httpInfo.contains("query")) {*/
                //委托付款查询
                result = queryReports(request, directStr, qwsUrl, BatchNo);
            //}
//          出款状态getTrdStatus订单状态getOrdStatus交易金额getTrdAmt批次流水号getBatchNo
            String TrdStatus = getTrdStatus(result);
            String OrdStatus = getOrdStatus(result);
            String TrdAmt = getTrdAmt(result);
            String reBatchNo = getBatchNo(result);
            model.addAttribute("TrdStatus", TrdStatus);
            model.addAttribute("OrdStatus", OrdStatus);
            model.addAttribute("TrdAmt", TrdAmt);
            model.addAttribute("reBatchNo", reBatchNo);
//            model.addAttribute("result", result);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "系统异常!");
//            request.setAttribute("result", "系统异常");
        }
        return "modules/user/userWithdrawQyery";
    }

    /**
     * 委托付款查询(生产环境用，生产环境用，生产环境用)
     *
     * @param request
     * @param directStr
     * @param wsUrl
     * @return
     */
    private String queryReports(HttpServletRequest request, String directStr, String wsUrl, String BatchNo) {

        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //获取参数
        Map<String, String> huanxunPayConfig = getHuanxunPayConfigInfo();
        String merCode = huanxunPayConfig.get("merchant_num");//商户号
        String merName = huanxunPayConfig.get("MerName");//商户名
        String Account = huanxunPayConfig.get("transaction_account");//商户账号
        String version = huanxunPayConfig.get("version");//固定版本号
        // body （<Param>为重复节点）
        String bodyXml =
                "<body>" +
                        "<BatchNo>" + BatchNo + "</BatchNo>" +
                        "<MerBillNo>" + request.getParameter("MerBillNo") + "</MerBillNo>" + "</body>";

        // MD5签名
        String sign = DigestUtils
                .md5Hex(Verify.getBytes(bodyXml + merCode + directStr,
                        "UTF-8"));
        // 发送给ipsxml
        String xml = "<Ips>" +
                "<IssuedTradeReq>" +
                "<head>" +
                "<Version>" + version + "</Version>" +
                "<MerCode>" + merCode + "</MerCode>" +
                "<MerName>" + merName + "</MerName>" +
                "<Account>" + Account + "</Account>" +
                "<ReqDate>" + date + "</ReqDate>" +
                "<Signature>" + sign + "</Signature>" +
                "</head>" +
                bodyXml +
                "</IssuedTradeReq>" +
                "</Ips>";
        //System.out.println(">>>>> 委托付款查询 请求报文: " + xml);
        String qwsUrlSdl = wsUrl + "trade?wsdl";
        //System.out.println("qwsUrlSdl:" + qwsUrlSdl);

        //获取webservice service
        TradeQueryService query = (TradeQueryService) CxfCommunication.getWsPort(qwsUrlSdl, TradeQueryService.class);
        //调用ws接口  获取响应报文
        String resultXml = null;
        try {
            resultXml = query.getIssuedByBillNo(xml);
            //调用ws接口  获取响应报文
            //System.out.println("返回报文---------------------------------------");
            //System.out.println("客户端模式" + (String) resultXml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(">>>>> response xml: " + resultXml);
        /**
         * TODO
         */
        // 1、验签
        if (!checkSign(resultXml, merCode, directStr)) {
            //TODO重点
            //System.out.println("验签失败");
            return "验签失败";
        }

//        2、 验签通过，判断IPS返回状态码RspCode
        if (!getRspCode(resultXml).equals("000000")) {
            //System.out.println("请求响应不成功");
            return resultXml;
        }


        //3、 判断查询结果
        /**
         * TODO
         * 重点
         * OrdStatus	订单状态		订单状态_订单状态 默认：0  处理中：8     失败：9  成功：10
         * TrdStatus	出款状态      0：正常  4：退票   其中“退票“是指银行返回的最终结果，一般最长为T+3工作日，具体以银行反馈的时间为准。
         * 1.OrdStatus状态为0或者8继续查询
         * 2.当状态为OrdStatus为9为失败。
         * 3.当OrdStatus为10，TrdStatus为4时为退票，做退票处理
         * 4.当OrdStatus为10，TrdStatus为0，占时显示成功，并非一定成功，之后可能出现退票（注意点）
         *
         */

        //3、商户自己业务逻辑处理
        /**
         * TODO
         */
        return resultXml;
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
        String text = getBodyXml(xml); // 获取body
        //System.out.println("MD5验签，验签文：" + text + "\n待比较签名值:" + OldSign);

        String result = DigestUtils
                .md5Hex(Verify.getBytes(text + merCode + directStr,
                        "UTF-8"));
        //System.out.println("签名值:" + result);
        if (OldSign == null || result == null || !OldSign.equals(result)) {
            return false;
        }
        return true;
    }

    /**
     * 获取报文中<RspCode></RspCode>部分
     *
     * @param xml
     * @return
     */
    public String getRspCode(String xml) {
        int s_index = xml.indexOf("<RspCode>");
        int e_index = xml.indexOf("</RspCode>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 9, e_index);
        }
        return sign;
    }

    /**
     * 出款状态
     * 获取报文中<TrdStatus></TrdStatus>部分
     *
     * @param xml
     * @return
     */
    public String getTrdStatus(String xml) {
        int s_index = xml.indexOf("<TrdStatus>");
        int e_index = xml.indexOf("</TrdStatus>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 11, e_index);
        }
        String TrdStatus = "";
        if ("0".equals(sign)) {
            TrdStatus = "正常";
        } else {
            TrdStatus = "退票,指银行返回的最终结\n" +
                    "果，一般最长为 T+3 工作日，具体以\n" +
                    "银行反馈的时间为准。";
        }
        return TrdStatus;
    }

    /**
     * 订单状态
     * 获取报文中<OrdStatus></OrdStatus>部分
     *
     * @param xml
     * @return
     */
    public String getOrdStatus(String xml) {
        int s_index = xml.indexOf("<OrdStatus>");
        int e_index = xml.indexOf("</OrdStatus>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 11, e_index);
        }
        String OrdStatus = "";
        if ("8".equals(sign)) {
            OrdStatus = "处理中";
        }
        if ("10".equals(sign)) {
            OrdStatus = "成功";
        }
        if ("9".equals(sign)) {
            OrdStatus = "失败";
        }
        if ("0".equals(sign)) {
            OrdStatus = "待审核";
        }
        return OrdStatus;
    }

    /**
     * 交易金额
     * 获取报文中<OrdStatus></OrdStatus>部分
     *
     * @param xml
     * @return
     */
    public String getTrdAmt(String xml) {
        int s_index = xml.indexOf("<TrdAmt>");
        int e_index = xml.indexOf("</TrdAmt>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 8, e_index);
        }
        return sign;
    }

    /**
     * 批次流水号
     * 获取报文中<BatchNo></BatchNo>部分
     *
     * @param xml
     * @return
     */
    public String getBatchNo(String xml) {
        int s_index = xml.indexOf("<BatchNo>");
        int e_index = xml.indexOf("</BatchNo>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 9, e_index);
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
        int s_index = xml.indexOf("<body>");
        int e_index = xml.indexOf("</body>");
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
     * 获取报文中<ErrMsg></ErrMsg>部分
     *
     * @param xml
     * @return
     */
    public String getErrMsg(String xml) {
        int s_index = xml.indexOf("<ErrMsg>");
        int e_index = xml.indexOf("</ErrMsg>");
        String sign = null;
        if (s_index > 0) {
            sign = xml.substring(s_index + 8, e_index);
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
