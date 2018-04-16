/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.user.entity.UserMoneyReport;
import com.thinkgem.jeesite.modules.user.service.UserMoneyReportService;

/**
 * 舆情分析Controller
 * @author luo
 * @version 2018-03-13
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userMoneyReport")
public class UserMoneyReportController extends BaseController {

	@Autowired
	private UserMoneyReportService userMoneyReportService;
	
	@ModelAttribute
	public UserMoneyReport get(@RequestParam(required=false) String id) {
		UserMoneyReport entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userMoneyReportService.get(id);
		}
		if (entity == null){
			entity = new UserMoneyReport();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userMoneyReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserMoneyReport userMoneyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserMoneyReport> page = userMoneyReportService.findPage(new Page<UserMoneyReport>(request, response), userMoneyReport); 
		model.addAttribute("page", page);
		return "modules/user/userMoneyReportList";
	}

	@RequiresPermissions("user:userMoneyReport:view")
	@RequestMapping(value = "form")
	public String form(UserMoneyReport userMoneyReport, Model model) {
		model.addAttribute("userMoneyReport", userMoneyReport);
		return "modules/user/userMoneyReportForm";
	}

	@RequiresPermissions("user:userMoneyReport:edit")
	@RequestMapping(value = "save")
	public String save(UserMoneyReport userMoneyReport, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userMoneyReport)){
			return form(userMoneyReport, model);
		}
		userMoneyReportService.save(userMoneyReport);
		addMessage(redirectAttributes, "保存舆情分析成功");
		return "redirect:"+Global.getAdminPath()+"/user/userMoneyReport/?repage";
	}
	
	@RequiresPermissions("user:userMoneyReport:edit")
	@RequestMapping(value = "delete")
	public String delete(UserMoneyReport userMoneyReport, RedirectAttributes redirectAttributes) {
		userMoneyReportService.delete(userMoneyReport);
		addMessage(redirectAttributes, "删除舆情分析成功");
		return "redirect:"+Global.getAdminPath()+"/user/userMoneyReport/?repage";
	}

}