package com.thinkgem.jeesite.modules.wechat.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.wechat.entity.SysUserWx;
import com.thinkgem.jeesite.modules.wechat.entity.SysWxConsts;
import com.thinkgem.jeesite.modules.wechat.service.SysUserWxService;
import com.thinkgem.jeesite.modules.wechat.service.WeixinService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kevin on 2017/7/30.
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/msgSend")
public class SysWxMsgSendController extends BaseController {
    @Autowired
    private WeixinService wexinService;

    @Autowired
    private SysUserWxService userWxService;

    @RequestMapping(value = {"sendText"})
    public String sendText(@RequestParam("text_content") String textContent,
                           @RequestParam("groupName") String groupName, @RequestParam("optLabel") String optLabel,
                           HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
        massMessage.setMsgType(WxConsts.MASS_MSG_TEXT);
        massMessage.setContent(textContent);

        List<SysUserWx> wxUsers = userWxService.findList(new SysUserWx());
        if (!Collections3.isEmpty(wxUsers)) {
            List<String> openIdS = wxUsers.stream().map(SysUserWx::getOpenId).collect(Collectors.toList());
            massMessage.getToUsers().addAll(openIdS);

            try {
                WxMpMassSendResult massResult = wexinService.massOpenIdsMessageSend(massMessage);
                if (null == massResult || StringUtils2.isBlank(massResult.getMsgId())) {
                    addMessage(redirectAttributes, "失败: 消息发送失败");
                } else {
                    addMessage(redirectAttributes, "消息发送成功");
                }
            } catch (WxErrorException e) {
                logger.error(e.getLocalizedMessage());
                addMessage(redirectAttributes, "失败: 消息发送失败");
            }
        }

        return "redirect:" + Global.getAdminPath() + "/user/userOptions/wxMsgSend?groupName=" + groupName + "&optLabel=" + optLabel;
    }

    @RequestMapping(value = {"sendPic"})
    public String sendPic(@RequestParam("picUrl") String picUrl, @RequestParam("groupName") String groupName, @RequestParam("optLabel") String optLabel,
                           HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try (InputStream resourceAsStream = request.getSession().getServletContext().getResourceAsStream(picUrl)) {
            List<SysUserWx> wxUsers = userWxService.findList(new SysUserWx());
            if (!Collections3.isEmpty(wxUsers)) {
                WxMediaUploadResult uploadMediaRes = wexinService.getMaterialService().mediaUpload(WxConsts.MEDIA_IMAGE, SysWxConsts.FILE_JPG, resourceAsStream);
                WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
                massMessage.setMsgType(WxConsts.MASS_MSG_IMAGE);
                massMessage.setMediaId(uploadMediaRes.getMediaId());

                List<String> openIdS = wxUsers.stream().map(SysUserWx::getOpenId).collect(Collectors.toList());
                massMessage.getToUsers().addAll(openIdS);
                WxMpMassSendResult massResult = wexinService.massOpenIdsMessageSend(massMessage);

                if (null == massResult || StringUtils2.isBlank(massResult.getMsgId())) {
                    addMessage(redirectAttributes, "失败: 消息发送失败");
                } else {
                    addMessage(redirectAttributes, "消息发送成功");
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            addMessage(redirectAttributes, "失败: 消息发送失败");
        }

        return "redirect:" + Global.getAdminPath() + "/user/userOptions/wxMsgSend?groupName=" + groupName + "&optLabel=" + optLabel;
    }
}
