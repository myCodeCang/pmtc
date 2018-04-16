/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.modules.sys.utils.annotations.RecordLog;
import com.thinkgem.jeesite.modules.user.entity.UserBankCommon;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.user.entity.UserBankWithdraw;
import com.thinkgem.jeesite.modules.user.service.UserBankWithdrawService;

/**
 * 提款银行Controller
 *
 * @author cai
 * @version 2017-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userBankWithdraw")
public class UserBankWithdrawController extends BaseController {

    @Resource
    private UserBankWithdrawService userBankWithdrawService;

    @ModelAttribute
    public UserBankWithdraw get(@RequestParam(required = false) String id) {
        UserBankWithdraw entity = null;
        if (StringUtils2.isNotBlank(id)) {
            entity = userBankWithdrawService.get(id);
        }
        if (entity == null) {
            entity = new UserBankWithdraw();
        }
        return entity;
    }

    @RequiresPermissions("user:userBankWithdraw:view")
    @RequestMapping(value = {"list", ""})
    public String list(UserBankWithdraw userBankWithdraw, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserBankWithdraw> page = userBankWithdrawService.findPage(new Page<UserBankWithdraw>(request, response), userBankWithdraw);
        model.addAttribute("page", page);
        return "modules/user/userBankWithdrawList";
    }

    @RequiresPermissions("user:userBankWithdraw:view")
    @RequestMapping(value = "form")
    public String form(UserBankWithdraw userBankWithdraw, Model model) {
        model.addAttribute("userBankWithdraw", userBankWithdraw);
        return "modules/user/userBankWithdrawForm";
    }

    @RecordLog(logTitle = "常规配置-提款银行管理-提款银行设置添加-新增/修改")
    @RequiresPermissions("user:userBankWithdraw:edit")
    @RequestMapping(value = "save")
    public String save(UserBankWithdraw userBankWithdraw, Model model, RedirectAttributes redirectAttributes) {

        try {
            userBankWithdrawService.save(userBankWithdraw);
            addMessage(redirectAttributes, "保存提款银行设置成功");
            return "redirect:" + Global.getAdminPath() + "/user/userBankWithdraw/?repage";

        } catch (ValidationException e) {
            addMessage(model, "失败:" + e.getMessage());
            return form(userBankWithdraw, model);
        } catch (Exception e) {
            addMessage(model, "失败!");
            return form(userBankWithdraw, model);
        }



    }

    @RecordLog(logTitle = "常规配置-提款银行管理-提款银行设置删除")
    @RequiresPermissions("user:userBankWithdraw:edit")
    @RequestMapping(value = "delete")
    public String delete(UserBankWithdraw userBankWithdraw, RedirectAttributes redirectAttributes) {
        try {
            userBankWithdrawService.delete(userBankWithdraw);
            addMessage(redirectAttributes, "删除提款银行设置成功");
        } catch (ValidationException e) {
            addMessage(redirectAttributes, "失败:"+e.getMessage());
        } catch (Exception e) {
            addMessage(redirectAttributes,"失败!");
        }
        return "redirect:" + Global.getAdminPath() + "/user/userBankWithdraw/?repage";
    }

}