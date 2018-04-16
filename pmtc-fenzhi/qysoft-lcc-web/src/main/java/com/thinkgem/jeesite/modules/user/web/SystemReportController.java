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
import com.thinkgem.jeesite.modules.user.entity.SystemReport;
import com.thinkgem.jeesite.modules.user.service.SystemReportService;

/**
 * 平台统计Controller
 * @author luo
 * @version 2018-04-02
 */
@Controller
@RequestMapping(value = "${adminPath}/user/systemReport")
public class SystemReportController extends BaseController {

	@Autowired
	private SystemReportService systemReportService;
	
	@ModelAttribute
	public SystemReport get(@RequestParam(required=false) String id) {
		SystemReport entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = systemReportService.get(id);
		}
		if (entity == null){
			entity = new SystemReport();
		}
		return entity;
	}
	
	@RequiresPermissions("user:systemReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(SystemReport systemReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SystemReport> page = systemReportService.findPage(new Page<SystemReport>(request, response), systemReport); 
		model.addAttribute("page", page);
		return "modules/user/systemReportList";
	}

	@RequiresPermissions("user:systemReport:view")
	@RequestMapping(value = "form")
	public String form(SystemReport systemReport, Model model) {
		model.addAttribute("systemReport", systemReport);
		return "modules/user/systemReportForm";
	}

	@RequiresPermissions("user:systemReport:edit")
	@RequestMapping(value = "save")
	public String save(SystemReport systemReport, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, systemReport)){
			return form(systemReport, model);
		}
		systemReportService.save(systemReport);
		addMessage(redirectAttributes, "保存平台统计成功");
		return "redirect:"+Global.getAdminPath()+"/user/systemReport/?repage";
	}
	
	@RequiresPermissions("user:systemReport:edit")
	@RequestMapping(value = "delete")
	public String delete(SystemReport systemReport, RedirectAttributes redirectAttributes) {
		systemReportService.delete(systemReport);
		addMessage(redirectAttributes, "删除平台统计成功");
		return "redirect:"+Global.getAdminPath()+"/user/systemReport/?repage";
	}

}