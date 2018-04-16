/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.user.entity.UserTeamLevel;
import com.thinkgem.jeesite.modules.user.service.UserTeamLevelService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 团队等级Controller
 * @author 薛玉良
 * @version 2017-08-11
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userTeamLevel")
public class UserTeamLevelController extends BaseController {

	@Autowired
	private UserTeamLevelService userTeamLevelService;
	
	@ModelAttribute
	public UserTeamLevel get(@RequestParam(required=false) String id) {
		UserTeamLevel entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userTeamLevelService.get(id);
		}
		if (entity == null){
			entity = new UserTeamLevel();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userTeamLevel:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserTeamLevel userTeamLevel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserTeamLevel> page = userTeamLevelService.findPage(new Page<UserTeamLevel>(request, response), userTeamLevel);
		model.addAttribute("page", page);
		return "modules/user/userTeamLevelList";
	}

	@RequiresPermissions("user:userTeamLevel:view")
	@RequestMapping(value = "form")
	public String form(UserTeamLevel userTeamLevel, Model model) {
		model.addAttribute("userTeamLevel", userTeamLevel);
		return "modules/user/userTeamLevelForm";
	}

	@RequiresPermissions("user:userTeamLevel:edit")
	@RequestMapping(value = "save")
	public String save(UserTeamLevel userTeamLevel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userTeamLevel)){
			return form(userTeamLevel, model);
		}
		userTeamLevelService.save(userTeamLevel);
		addMessage(redirectAttributes, "保存团队等级成功");
		return "redirect:"+ Global.getAdminPath()+"/user/userTeamLevel/?repage";
	}
	
	@RequiresPermissions("user:userTeamLevel:edit")
	@RequestMapping(value = "delete")
	public String delete(UserTeamLevel userTeamLevel, RedirectAttributes redirectAttributes) {
		userTeamLevelService.delete(userTeamLevel);
		addMessage(redirectAttributes, "删除团队等级成功");
		return "redirect:"+ Global.getAdminPath()+"/user/userTeamLevel/?repage";
	}

}