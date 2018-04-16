package com.thinkgem.jeesite.website.payment.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.user.entity.UserOptions;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.UserOptionsService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yankai on 2017/6/7.
 */
@Controller
@RequestMapping(value = "${frontPath}/payment/pay")
public class PayController extends BaseController {
    //请求网关地址
    //public static String GATEWAY_URL = "https://openapi.alipay.com/gateway.do";
    public static String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";

    @Resource
    private UserOptionsService userOptionsService;
    @Resource
    private UserUserinfoService userUserinfoService;

    @RequestMapping(value = "recharge")
    public String recharge(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<UserOptions> payGroup = userOptionsService.getByGroupName("pay_group");
        if (Collections3.isEmpty(payGroup)) {
            return success("成功!!", response, model);
        }

        String userName = request.getParameter("userName");
        if (StringUtils2.isBlank(userName)) {
            return error("失败!!", response, model);
        }

        UserUserinfo userInfo = userUserinfoService.getByName(userName);
        if (null == userInfo) {
            return error("失败!!", response, model);
        }

        Map<String, Object> retValue = payGroup.stream()
                .collect(Collectors.toMap(UserOptions::getOptionName, (userOptions) -> JSONUtils.parse(userOptions.getOptionValue())));

        StringBuilder notifyUrl = new StringBuilder();
        notifyUrl.append(request.getScheme()).append("://").append(request.getServerName())
                .append(":").append(request.getServerPort()).append("/payment/aliPay/notify");

        StringBuilder wxnotifyUrl = new StringBuilder();
        wxnotifyUrl.append(request.getScheme()).append("://").append(request.getServerName())
                .append(":").append(request.getServerPort()).append("/payment/weiXPay/payNotify");

        model.addAttribute("options", retValue);
        model.addAttribute("userMoney", userInfo.getMoney());
        model.addAttribute("userName", userName);
        model.addAttribute("notifyUrl", notifyUrl.toString());
        model.addAttribute("wxnotifyUrl",wxnotifyUrl.toString());

        //model.addAttribute("notifyUrl", "http://sushan.ngrok.cc/payment/aliPay/notify");

        return "website/themes/basic_app/payment/recharge";
    }

    @RequestMapping(value = "alipayWeb1")
    public String alipayWeb(HttpServletRequest request, HttpServletResponse response, Model model) {
        String amount = request.getParameter("amount");
        if (StringUtils2.isBlank(amount)) {
            return error("失败: 请输入正确的金额", response, model);
        }

        String tradeNo = request.getParameter("tradeNo");
        if (StringUtils2.isBlank(tradeNo)) {
            return error("失败: 交易订单不能为空", response, model);
        }

        String subject = request.getParameter("subject");
        if (StringUtils2.isBlank(subject)) {
            return error("失败: Subject不能为空", response, model);
        }

        String body = request.getParameter("body");
        model.addAttribute("amount", amount);
        model.addAttribute("tradeNo", tradeNo);
        model.addAttribute("subject", subject);
        model.addAttribute("body", body);

        return "website/themes/basic_web/payment/alipay/submitPay";
    }

    @RequestMapping(value = "alipayWeb2", method = RequestMethod.POST)
    public void alipayWeb(HttpServletRequest request, HttpServletResponse response) {
        String amount = request.getParameter("amount");
        String tradeNo = request.getParameter("tradeNo");
        String body = request.getParameter("body");
        String subject = request.getParameter("subject");
        String productCode = "QUICK_WAP_PAY";

        StringBuilder notifyUrl = new StringBuilder();
        notifyUrl.append(request.getScheme()).append("://").append(request.getServerName())
                .append(":").append(request.getServerPort()).append("/payment/aliPay/notify");

        try {
            String appID = Global.getOption("system_zhifb", "zfb_appid");
            String publicKey = Global.getOption("system_zhifb", "webdev_public_key");
            String privateKey = Global.getOption("system_zhifb", "web_private_key");
            String redirectUrl = Global.getOption("system_zhifb", "redirect_url");

            //调用RSA签名方式
            AlipayClient client = new DefaultAlipayClient(GATEWAY_URL, appID, privateKey, FORMAT, CHARSET, publicKey, "RSA");
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

            // 封装请求支付信息
            AlipayTradeWapPayModel payModel = new AlipayTradeWapPayModel();
            payModel.setOutTradeNo(tradeNo);
            payModel.setSubject(subject);
            payModel.setTotalAmount(amount);
            payModel.setBody(body);
            payModel.setProductCode(productCode);
            alipayRequest.setBizModel(payModel);

            // 设置异步通知地址
            alipayRequest.setNotifyUrl(notifyUrl.toString());
            // 设置同步地址
            alipayRequest.setReturnUrl(redirectUrl);

            // 生成Form表单
            String form = client.pageExecute(alipayRequest).getBody();
            response.setContentType("text/html;charset=" + CHARSET);
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        }
    }

    @RequestMapping(value = "order")
    public String order(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<UserOptions> payGroup = userOptionsService.getByGroupName("pay_group");
        if (Collections3.isEmpty(payGroup)) {
            return success("成功!!", response, model);
        }

        String money = request.getParameter("money");
        if (StringUtils2.isBlank(money)) {
            return error("失败:支付金额必须大于０", response, model);
        }

        try {
            BigDecimal dMoney = new BigDecimal(money);
            if (BigDecimal.ZERO.compareTo(dMoney) >= 0) {
                return error("失败:支付金额必须大于０", response, model);
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return error("失败:支付金额必须大于０", response, model);
        }

        String userName = request.getParameter("userName");
        if (StringUtils2.isBlank(userName)) {
            return error("失败!!", response, model);
        }

        String tradeNo = request.getParameter("tradeNo");
        if (StringUtils2.isBlank(tradeNo)) {
            return error("失败: 订单号不能为空!!", response, model);
        }

        Map<String, Object> retValue = payGroup.stream()
                .collect(Collectors.toMap(UserOptions::getOptionName, (userOptions) -> JSONUtils.parse(userOptions.getOptionValue())));

        StringBuilder notifyUrl = new StringBuilder();
        notifyUrl.append(request.getScheme()).append("://").append(request.getServerName())
                .append(":").append(request.getServerPort()).append("/payment/aliPay/notify");
        model.addAttribute("options", retValue);
        //model.addAttribute("userMoney", userUserinfoService.getByNameLock(userName).getMoney());
        model.addAttribute("userName", userName);
        model.addAttribute("notifyUrl", notifyUrl.toString());

        return "website/themes/basic_app/payment/order";
    }

    @RequestMapping(value = "huanxunPay")
    public String huanxunPay(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        String money = request.getParameter("money");
        String userName = request.getParameter("userName");
        String isOrder = request.getParameter("isOrder");
        boolean isWeb = StringUtils2.isBlank(request.getParameter("isWeb")) ? false : true;

        String tradeNo = StringUtils2.EMPTY;
        if ("true".equals(isOrder)) {
            tradeNo = request.getParameter("tradeNo");
            if (StringUtils2.isBlank(tradeNo)) {
                model.addAttribute("errMsg", "订单号不能为空");
                model.addAttribute("userName", userName);
                if (isWeb) {
                    return "website/themes/basic_app/payment/order";
                } else {
                    historBack(response);
                    return StringUtils2.EMPTY;
                }
            }

            if (StringUtils2.isBlank(money)) {
                model.addAttribute("errMsg", "支付金额必须大于0");
                model.addAttribute("userName", userName);
                if (isWeb) {
                    historBack(response);
                    return StringUtils2.EMPTY;
                } else {
                    return "website/themes/basic_app/payment/order";
                }
            }

            try {
                BigDecimal dMoney = new BigDecimal(money);
                if (BigDecimal.ZERO.compareTo(dMoney) >= 0) {
                    model.addAttribute("errMsg", "支付金额必须大于0");
                    model.addAttribute("userName", userName);
                    if (isWeb) {
                        historBack(response);
                        return StringUtils2.EMPTY;
                    } else {
                        return "website/themes/basic_app/payment/order";
                    }
                }
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                model.addAttribute("errMsg", "支付金额必须大于0");
                model.addAttribute("userName", userName);
                if (isWeb) {
                    historBack(response);
                    return StringUtils2.EMPTY;
                } else {
                    return "website/themes/basic_app/payment/order";
                }
            }
        } else {
            if (StringUtils2.isBlank(money)) {
                model.addAttribute("errMsg", "入金金额必须是大于0的整数");
                model.addAttribute("userName", userName);
                if (isWeb) {
                    historBack(response);
                    return StringUtils2.EMPTY;
                } else {
                    return "website/themes/basic_app/payment/recharge";
                }
            }
        }

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");

            List<UserOptions> payGroup = userOptionsService.getByGroupName("pay_group");
            if (!Collections3.isEmpty(payGroup)) {
                Map<String, Object> retValue = payGroup.stream()
                        .collect(Collectors.toMap(UserOptions::getOptionName, (userOptions) -> JSONUtils.parse(userOptions.getOptionValue())));

                model.addAttribute("options", retValue);
            }

            //获取环迅支付配置信息
            Map<String, String> payConfig = getHuanxunPayConfigInfo();
            if (payConfig == null || payConfig.isEmpty()) {
                model.addAttribute("errMsg", "支付失败！");
                model.addAttribute("userName", userName);
                if (isWeb) {
                    historBack(response);
                    return StringUtils2.EMPTY;
                } else {
                    if ("true".equals(isOrder)) {
                        return "website/themes/basic_app/payment/order";
                    } else {
                        return "website/themes/basic_app/payment/recharge";
                    }
                }
            }

            //构造请求Body
            StringBuffer bodyXml = buildBodyData(request, money, userName, isOrder, tradeNo, isWeb);
            //构造请求数据
            StringBuffer resultXml = buildRequestData(payConfig, bodyXml);

            model.addAttribute("formAction", payConfig.get("request_url"));
            model.addAttribute("requestParam", payConfig.get("request_param"));
            model.addAttribute("paramValue", resultXml.toString());
            return "website/themes/basic_app/payment/huanxun/submitPay";
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            model.addAttribute("errMsg", "支付失败！");
            model.addAttribute("userName", userName);
            if (isWeb) {
                historBack(response);
                return StringUtils2.EMPTY;
            } else {
                if ("true".equals(isOrder)) {
                    return "website/themes/basic_app/payment/order";
                } else {
                    return "website/themes/basic_app/payment/recharge";
                }
            }
        }
    }

    private StringBuffer buildRequestData(Map<String, String> payConfig, StringBuffer bodyXml) throws UnsupportedEncodingException {
        String md5Cert = payConfig.get("md5_cert");                 //MD5证书
        String merCode = payConfig.get("merchant_num");             //商户号
        String merAccount = payConfig.get("transaction_account");   //交易账号

        String sign = DigestUtils.md5Hex((bodyXml.toString() + merCode + md5Cert).getBytes("UTF-8"));
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        //构造支付请求结果数据
        StringBuffer resultXml = new StringBuffer();
        resultXml.append("<Ips>");
        resultXml.append("<GateWayReq>");
        resultXml.append("<head>");
        resultXml.append("<Version>").append("v1.0.0").append("</Version>");
        resultXml.append("<MerCode>").append(merCode).append("</MerCode>");
        resultXml.append("<Account>").append(merAccount).append("</Account>");
        resultXml.append("<ReqDate>").append(nowTime).append("</ReqDate>");
        resultXml.append("<Signature>").append(sign).append("</Signature>");
        resultXml.append("</head>");
        resultXml.append(bodyXml.toString());
        resultXml.append("</GateWayReq>");
        resultXml.append("</Ips>");
        return resultXml;
    }

    private StringBuffer buildBodyData(HttpServletRequest request, String money, String userName, String isOrder, String tradeNo, boolean isWeb) {
        String merBillNo;
        if ("true".equals(isOrder)) {
            merBillNo = tradeNo;
        } else {
            merBillNo = "HUANXUPAY" + IdGen.randomNum(8);
        }
        DateTimeFormatter fromatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nowDate = LocalDateTime.now().format(fromatter);

        StringBuilder notifyUrl = new StringBuilder();
        notifyUrl.append(request.getScheme()).append("://").append(request.getServerName())
                .append(":").append(request.getServerPort()).append("/payment/huanxunPay/success");

        /*String isClustered = Global.getConfig("isClustered");
        if ("TRUE".equalsIgnoreCase(isClustered)) {
            notifyUrl.append(Global.getConfig("pay.callback.huanxun"));
        } else {
            notifyUrl.append(request.getScheme()).append("://").append(request.getServerName())
                    .append(":").append(request.getServerPort()).append("/payment/huanxunPay/success");
        }*/

        //构造<Body>数据
        StringBuffer bodyXml = new StringBuffer();
        bodyXml.append("<body>");
        bodyXml.append("<MerBillNo>").append(userName + "_" + merBillNo).append("</MerBillNo>");
        bodyXml.append("<GatewayType>").append("01").append("</GatewayType>");
        bodyXml.append("<Date>").append(nowDate).append("</Date>");
        bodyXml.append("<CurrencyType>").append("156").append("</CurrencyType>");
        bodyXml.append("<Amount>").append(money).append("</Amount>");
        bodyXml.append("<Merchanturl><![CDATA[").append(StringUtils2.EMPTY).append("]]></Merchanturl>");
        bodyXml.append("<OrderEncodeType>").append("5").append("</OrderEncodeType>");
        bodyXml.append("<RetEncodeType>").append("17").append("</RetEncodeType>");
        bodyXml.append("<RetType>").append("1").append("</RetType>");
        bodyXml.append("<ServerUrl><![CDATA[").append(notifyUrl.toString()).append("]]></ServerUrl>");
        //bodyXml.append("<ServerUrl><![CDATA[").append(payConfig.get("success_url")).append("]]></ServerUrl>");
        if ("true".equals(isOrder)) {
            bodyXml.append("<GoodsName>").append("环迅支付").append("</GoodsName>");
        } else {
            bodyXml.append("<GoodsName>").append("环迅充值").append("</GoodsName>");
        }
        bodyXml.append("<Attach>").append(isWeb ? "WEB" : "APP").append("</Attach>");
        bodyXml.append("</body>");
        return bodyXml;
    }

    private void historBack(HttpServletResponse response) throws IOException {
        ServletOutputStream output = response.getOutputStream();
        output.flush();
        output.println("<script>");
        output.println("history.back()");
        output.println("</script>");
    }

    /**
     * 获取环迅支付配置信息
     *
     * @return
     */
    private Map<String, String> getHuanxunPayConfigInfo() {
        UserOptions huanxunConfigValue = userOptionsService.getByOptionName("system_huanxun");
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

    /**
     * 线下转账
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/xxzz", method = RequestMethod.GET)
    public String xxzz(HttpServletRequest request, Model model) {
        return "website/themes/basic_app/payment/offlineTransfer";
    }
}
