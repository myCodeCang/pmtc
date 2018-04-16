/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.user.dao.UserCalculationTempDao;
import com.thinkgem.jeesite.modules.user.entity.UserCalculationTemp;
import com.thinkgem.jeesite.modules.user.service.UserCalculationTempService;
import com.thinkgem.jeesite.modules.user.service.UserReportService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 董事奖发放Controller
 * @author kevin
 * @version 2017-06-22
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userCalculationTemp")
public class UserCalculationTempController extends BaseController {

	@Autowired
	private UserCalculationTempService userCalculationTempService;
	@Resource
	private UserCalculationTempDao userCalculationTempDao;

	@Autowired
	private UserUserinfoService userUserinfoService;
	@Autowired
	private UserReportService userReportService;
	
	@ModelAttribute
	public UserCalculationTemp get(@RequestParam(required=false) String id) {
		UserCalculationTemp entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userCalculationTempService.get(id);
		}
		if (entity == null){
			entity = new UserCalculationTemp();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userCalculationTemp:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserCalculationTemp userCalculationTemp, HttpServletRequest request, HttpServletResponse response, Model model) {

		String moneyNum = userCalculationTemp.getMoneyNum(); //基数

		userCalculationTempService.countCalculationTemp(moneyNum);//计算插表

		Page<UserCalculationTemp> page = userCalculationTempService.findPage(new Page<UserCalculationTemp>(request, response), userCalculationTemp);
		model.addAttribute("page", page);
		return "modules/user/userCalculationTempList";
	}

	@RequiresPermissions("user:userCalculationTemp:view")
	@RequestMapping(value = "form")
	public String form(UserCalculationTemp userCalculationTemp, Model model) {
		model.addAttribute("userCalculationTemp", userCalculationTemp);
		return "modules/user/userCalculationTempForm";
	}

	@RequiresPermissions("user:userCalculationTemp:edit")
	@RequestMapping(value = "save")
	public String save(UserCalculationTemp userCalculationTemp, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userCalculationTemp)){
			return form(userCalculationTemp, model);
		}
		userCalculationTempService.save(userCalculationTemp);
		addMessage(redirectAttributes, "保存董事奖发放成功");
		return "redirect:"+Global.getAdminPath()+"/user/userCalculationTemp/?repage";
	}
	
	@RequiresPermissions("user:userCalculationTemp:edit")
	@RequestMapping(value = "delete")
	public String delete(UserCalculationTemp userCalculationTemp, RedirectAttributes redirectAttributes) {
		userCalculationTempService.delete(userCalculationTemp);
		addMessage(redirectAttributes, "删除董事奖发放成功");
		return "redirect:"+Global.getAdminPath()+"/user/userCalculationTemp/?repage";
	}

	@RequiresPermissions("user:userCalculationTemp:view")
	@RequestMapping(value = {"calculate"})
	public String calculate(UserCalculationTemp userCalculationTemp, HttpServletRequest request, HttpServletResponse response, Model model) {
		userCalculationTempService.calculateUserReport();
		Page<UserCalculationTemp> page = userCalculationTempService.findPage(new Page<UserCalculationTemp>(request, response), userCalculationTemp);
		model.addAttribute("page", page);
		return "modules/user/userCalculationTempList";
	}

}