package com.thinkgem.jeesite.website.wechat;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.wechat.service.WeixinService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by kevin on 2017/7/30.
 */
@Controller
@RequestMapping(value = "/wechat/core")
public class WechatController extends BaseController {

    @Autowired
    private WeixinService wexinService;

    @ResponseBody
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        // 接收微信服务器发送请求时传递过来的4个参数
        String signature = request.getParameter("signature");//微信加密签名signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String timestamp = request.getParameter("timestamp");//时间戳
        String nonce = request.getParameter("nonce");//随机数
        String echostr = request.getParameter("echostr");//随机字符串

        if (StringUtils2.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (wexinService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";

    }

    @ResponseBody
    @RequestMapping(value = "index", method = RequestMethod.POST, produces = "application/xml; charset=UTF-8")
    public String index(@RequestBody String requestBody, @RequestParam("signature") String signature,
                        @RequestParam(value = "encrypt_type", required = false) String encType,
                        @RequestParam(value = "msg_signature", required = false) String msgSignature,
                        @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, HttpServletRequest request) {
        if (StringUtils2.isAnyBlank(signature, timestamp, nonce)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (!wexinService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            request.getSession().setAttribute("wx_openid", inMessage.getOpenId());
            WxMpXmlOutMessage outMessage = wexinService.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toXml();
        } else if ("aes".equals(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody,
                    wexinService.getWxMpConfigStorage(), timestamp, nonce, msgSignature);
            logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = wexinService.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toEncryptedXml(wexinService.getWxMpConfigStorage());
        }
        return out;
    }

    @RequestMapping(value = "wxLogin", method = RequestMethod.GET)
    public String wxLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
        String code = request.getParameter("code");
        if (StringUtils2.isBlank(code)) {
            return "error/400";
        }

        String openId = StringUtils2.EMPTY;
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wexinService.oauth2getAccessToken(code);
            if (wxMpOAuth2AccessToken == null) {
                return "error/400";
            }

            WxMpUser wxMpUser = wexinService.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");
            if (wxMpUser == null) {
                return "error/400";
            }

            openId = wxMpUser.getOpenId();
            if (StringUtils2.isBlank(openId)) {
                return "error/400";
            }

            request.getSession().setAttribute("wx_openid", openId);
        } catch (WxErrorException e) {
            logger.error(e.getLocalizedMessage());
            return "error/400";
        }

        StringBuilder targetUrl = new StringBuilder();
        targetUrl.append(request.getScheme()).append("://").append(request.getServerName())
                .append(":").append(request.getServerPort()).append("/wap/index.html?openId=" + openId);
        return "redirect:" + targetUrl;
    }
}
