package com.thinkgem.jeesite.modules.wechat.handler;

import java.util.Map;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.modules.wechat.entity.SysUserWx;
import com.thinkgem.jeesite.modules.wechat.service.SysUserWxService;
import com.thinkgem.jeesite.modules.wechat.service.WeixinService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {

    @Autowired
    private SysUserWxService userWxService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService wxMpService,
            WxSessionManager sessionManager) throws WxErrorException {
        String openId = wxMessage.getFromUser();
        this.logger.info("取消关注用户 OPENID: " + openId);
        WeixinService weixinService = (WeixinService) wxMpService;

        // 获取微信用户基本信息
        WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);
        if (userWxInfo != null) {
            SysUserWx sysUserWx = userWxService.findByOpenId(openId);
            if (sysUserWx != null) {
                try {
                    sysUserWx.setIsSubscribe("0");
                    userWxService.changeSubscribeStatus(sysUserWx);
                } catch (ValidationException ex) {
                    logger.error(ex.getMessage());
                }
            }
        }
        return null;
    }

}
