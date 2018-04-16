package com.thinkgem.jeesite.modules.wechat.handler;

import java.util.Map;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import com.thinkgem.jeesite.modules.wechat.builder.TextBuilder;
import com.thinkgem.jeesite.modules.wechat.entity.SysUserWx;
import com.thinkgem.jeesite.modules.wechat.service.SysUserWxService;
import com.thinkgem.jeesite.modules.wechat.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Binary Wang
 */
@Component
public class SubscribeHandler extends AbstractHandler {

    @Autowired
    private SysUserWxService userWxService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());
        WeixinService weixinService = (WeixinService) wxMpService;

        // 获取微信用户基本信息
        WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);

        if (userWxInfo != null) {
            String openId = userWxInfo.getOpenId();
            SysUserWx sysUserWx = userWxService.findByOpenId(openId);
            //添加新用戶
            try {
                if (sysUserWx == null) {

                    sysUserWx = new SysUserWx();
                    sysUserWx.setOpenId(userWxInfo.getOpenId());
                    sysUserWx.setUnionId(userWxInfo.getUnionId());
                    sysUserWx.setWxNickname(userWxInfo.getNickname());
                    sysUserWx.setWxImg(userWxInfo.getHeadImgUrl());
                }
                sysUserWx.setIsSubscribe(userWxInfo.getSubscribe() ? "1" : "0");
                userWxService.save(sysUserWx);
            } catch (ValidationException ex) {
                logger.error(ex.getMessage());
            }
        }

        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            String welcome = Global.getOption("wechat_config", "welcome");
            if (StringUtils2.isBlank(welcome)) {
                welcome = "感谢关注";
            }
            return new TextBuilder().build(welcome, wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    protected WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) throws Exception {
        //TODO
        return null;
    }

}
