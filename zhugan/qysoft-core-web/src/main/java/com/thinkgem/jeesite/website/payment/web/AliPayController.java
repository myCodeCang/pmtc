package com.thinkgem.jeesite.website.payment.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.alipay.api.internal.util.AlipaySignature;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.payment.entity.PaymentCallback;
import com.thinkgem.jeesite.modules.user.entity.UserOptions;
import com.thinkgem.jeesite.modules.user.service.UserOptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yankai on 2017/6/7.
 */
@Controller
@RequestMapping(value = "/payment/aliPay")
public class AliPayController extends BaseController {
    @Resource
    private UserOptionsService optionService;

    private static final String TRADE_STATUS_SUCCESS = "TRADE_SUCCESS";

    private static final String RETURN_SUCCESS = "success";

    private static final String RETURN_FAILED = "failed";

    @Autowired(required = false)
    @Qualifier("alipayCallback")
    private PaymentCallback paymentCallback;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @RequestMapping(value = "notify")
    public String notify(HttpServletRequest request, HttpServletResponse response, Model model) {
        String tradeStatus = request.getParameter("trade_status");
        if (!TRADE_STATUS_SUCCESS.equals(tradeStatus)) {
            return RETURN_FAILED;
        }

        if (!verify(request)) {
            return RETURN_FAILED;
        }

        if (paymentCallback == null) {
            return RETURN_FAILED;
        }

        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> resultMap = new HashMap<>();

            parameterMap.forEach((key, value) -> resultMap.putIfAbsent(key, value[0]));
            if (paymentCallback.callback(resultMap)) {
                return RETURN_SUCCESS;
            }

            return RETURN_FAILED;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return RETURN_FAILED;
    }

    protected boolean verify(HttpServletRequest request) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            UserOptions userOption = optionService.getByOptionName("system_zhifb");
            Map<String, String> parse = (Map<String, String>) JSONUtils.parse(userOption.getOptionValue());
            return AlipaySignature.rsaCheckV1(params, parse.get("public_key_zhifb"), "UTF-8", "RSA");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }
}
