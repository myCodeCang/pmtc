/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.modules.sys.utils.annotations.RecordLog;
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
import com.thinkgem.jeesite.modules.user.entity.UserLevel;
import com.thinkgem.jeesite.modules.user.service.UserLevelService;

/**
 * 用户等级表Controller
 * @author cai
 * @version 2017-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userLevel")
public class UserLevelController extends BaseController {

	@Resource
	private UserLevelService userLevelService;
	
	@ModelAttribute
	public UserLevel get(@RequestParam(required=false) String id) {
		UserLevel entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userLevelService.get(id);
		}
		if (entity == null){
			entity = new UserLevel();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userLevel:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserLevel userLevel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserLevel> page = userLevelService.findPage(new Page<UserLevel>(request, response), userLevel); 
		model.addAttribute("page", page);
		return "modules/user/userLevelList";
	}

	@RequiresPermissions("user:userLevel:view")
	@RequestMapping(value = "form")
	public String form(UserLevel userLevel, Model model) {
		model.addAttribute("userLevel", userLevel);
		return "modules/user/userLevelForm";
	}

	@RecordLog(logTitle = "用户管理-等级管理-等级添加-新增/修改")
	@RequiresPermissions("user:userLevel:edit")
	@RequestMapping(value = "save")
	public String save(UserLevel userLevel, Model model, RedirectAttributes redirectAttributes) {
		try {
			userLevelService.save(userLevel);
			addMessage(redirectAttributes, "保存用户等级成功");
			return "redirect:"+Global.getAdminPath()+"/user/userLevel/?repage";

		} catch (ValidationException e) {
			addMessage(model, e.getMessage());
			return form(userLevel, model);
		} catch (Exception e) {
			addMessage(model, "失败!");
			return form(userLevel, model);
		}

	}

	@RecordLog(logTitle = "用户管理-等级管理-等级删除")
	@RequiresPermissions("user:userLevel:edit")
	@RequestMapping(value = "delete")
	public String delete(UserLevel userLevel, RedirectAttributes redirectAttributes) throws ValidationException {
		try {
			userLevelService.delete(userLevel);
			addMessage(redirectAttributes, "删除用户等级成功");

		} catch (ValidationException e) {
			addMessage(redirectAttributes, "失败:"+e.getMessage());
		} catch (Exception e) {
			addMessage(redirectAttributes,"失败!");
		}
		return "redirect:"+Global.getAdminPath()+"/user/userLevel/?repage";

	}

}