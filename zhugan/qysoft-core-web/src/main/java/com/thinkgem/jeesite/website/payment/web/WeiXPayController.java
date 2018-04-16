package com.thinkgem.jeesite.website.payment.web;
import com.github.binarywang.wxpay.bean.WxPayOrderNotifyResponse;
import com.github.binarywang.wxpay.bean.result.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.payment.entity.ThirdPartPayCallbackEntity;
import com.thinkgem.jeesite.modules.payment.service.WeixpayCallbackService;
import com.thinkgem.jeesite.modules.wechat.service.WeixinService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2017/7/25.
 */
@Controller
@RequestMapping("/payment/weiXPay")
public class WeiXPayController extends BaseController {
    private static final String RETURN_SUCCESS = "SUCCESS";
    private static final String RETURN_FAILED = "FAIL";

    @Autowired(required = false)
    private WeixpayCallbackService wxpayCallbackService;

    @Autowired
    private WeixinService wexinService;

    @ResponseBody
    @RequestMapping(value = "payNotify")
    public String payNotify(HttpServletRequest request, HttpServletResponse response) {
        WxPayService payService = wexinService.getPayService();
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WxPayOrderNotifyResult result = payService.getOrderNotifyResult(xmlResult);
            if (!RETURN_SUCCESS.equals(result.getReturnCode())) {
                return RETURN_FAILED;
            }
            //调用业务处理接口
            if (wxpayCallbackService.callback(result.toMap())) {
                return WxPayOrderNotifyResponse.success("处理成功!");
            }
            return WxPayOrderNotifyResponse.fail("处理失败!");
        } catch (Exception e) {
            logger.error("微信回调结果异常,异常原因{}", e.getMessage());
            return WxPayOrderNotifyResponse.fail(e.getMessage());
        }
    }

}
