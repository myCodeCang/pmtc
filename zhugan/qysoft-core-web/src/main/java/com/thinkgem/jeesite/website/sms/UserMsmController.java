/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.website.sms;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.user.entity.UserMsm;
import com.thinkgem.jeesite.modules.user.service.UserMsmService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 保存用户短信Controller
 *
 * @author yankai
 * @version 2017-05-28
 */
@Controller
@RequestMapping(value = "/msm/lkMsm")
public class UserMsmController extends BaseController {

    @Resource
    private UserMsmService userMsmService;

    @Resource
    private UserUserinfoService userUserinfoService;

    @ModelAttribute
    public UserMsm get(@RequestParam(required = false) String id) {
        UserMsm entity = null;
        if (StringUtils2.isNotBlank(id)) {
            entity = userMsmService.get(id);
        }
        if (entity == null) {
            entity = new UserMsm();
        }
        return entity;
    }


    @RequestMapping(value = "sendVerifyCode")
    @ResponseBody
    public String sendVerifyCode(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws ValidationException {
        String mobile = request.getParameter("mobile");
        String type = request.getParameter("type");

        if (StringUtils2.isEmpty(mobile)) {
            return error ("手机号码不能为空!", response, model);
        }
        try {
            model.addAttribute("info",userMsmService.choceSendType(mobile,type));
            return success ("短信发送成功!", response, model);
        } catch (ValidationException e) {
            return error (e.getMessage(), response, model);
        } catch (Exception ex2) {
            return error ("发送失败", response, model);
        }
    }

    @RequestMapping(value = "checkVerifyCode")
    @ResponseBody
    public boolean checkVerifyCode(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws ValidationException {
        String mobile = request.getParameter("mobile");
        if (StringUtils2.isEmpty(mobile)) {
            return false;
        }

        String verifyCode = request.getParameter("verifyCode");
        if (StringUtils2.isEmpty(verifyCode)) {
            return false;
        }

        return userMsmService.checkVerifyCode(mobile, verifyCode);
    }
}