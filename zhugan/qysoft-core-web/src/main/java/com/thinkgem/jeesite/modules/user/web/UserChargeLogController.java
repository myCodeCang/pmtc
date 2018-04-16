/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.DateUtils2;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.sys.utils.annotations.RecordLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.user.entity.UserChargeLog;
import com.thinkgem.jeesite.modules.user.service.UserChargeLogService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 会员充值Controller
 * @author liweijia
 * @version 2017-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userChargeLog")
public class UserChargeLogController extends BaseController {

	@Resource
	private UserChargeLogService userChargeLogService;
	
	@ModelAttribute
	public UserChargeLog get(@RequestParam(required=false) String id) {
		UserChargeLog entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userChargeLogService.get(id);
		}
		if (entity == null){
			entity = new UserChargeLog();
		}




		return entity;
	}
	
	@RequiresPermissions("user:userChargeLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserChargeLog userChargeLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserChargeLog> page = userChargeLogService.findPage(new Page<UserChargeLog>(request, response), userChargeLog);
		model.addAttribute("page", page);
		return "modules/user/userChargeLogList";
	}

	@RequiresPermissions("user:userChargeLog:view")
	@RequestMapping(value = "form")
	public String form(UserChargeLog userChargeLog, Model model) {
		model.addAttribute("userChargeLog", userChargeLog);
		return "modules/user/userAdminChargeForm";
	}

	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RecordLog(logTitle = "系统设置-用户管理-导出用户数据")
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "userChargeLogExport", method= RequestMethod.POST)
	public String userChargeLogExport(UserChargeLog user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据"+ DateUtils2.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<UserChargeLog> page = userChargeLogService.findPage(new Page<UserChargeLog>(request, response, -1), user);
			new ExportExcel("用户数据", UserChargeLog.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/userUserinfoList";
	}
}