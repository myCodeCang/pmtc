/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
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
import com.thinkgem.jeesite.modules.user.entity.TranscodeBuyLog;
import com.thinkgem.jeesite.modules.user.service.TranscodeBuyLogService;

/**
 * 撮合成功记录Controller
 * @author wyxiang
 * @version 2018-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/user/transcodeBuyLog")
public class TranscodeBuyLogController extends BaseController {

	@Autowired
	private TranscodeBuyLogService transcodeBuyLogService;
	
	@ModelAttribute
	public TranscodeBuyLog get(@RequestParam(required=false) String id) {
		TranscodeBuyLog entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = transcodeBuyLogService.get(id);
		}
		if (entity == null){
			entity = new TranscodeBuyLog();
		}
		return entity;
	}
	
	@RequiresPermissions("user:transcodeBuyLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TranscodeBuyLog transcodeBuyLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TranscodeBuyLog> page = transcodeBuyLogService.findPage(new Page<TranscodeBuyLog>(request, response), transcodeBuyLog); 
		model.addAttribute("page", page);
		return "modules/user/transcodeBuyLogList";
	}

	@RequiresPermissions("user:transcodeBuyLog:view")
	@RequestMapping(value = "form")
	public String form(TranscodeBuyLog transcodeBuyLog, Model model) {
		model.addAttribute("transcodeBuyLog", transcodeBuyLog);
		return "modules/user/transcodeBuyLogForm";
	}

	@RequiresPermissions("user:transcodeBuyLog:edit")
	@RequestMapping(value = "save")
	public String save(TranscodeBuyLog transcodeBuyLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, transcodeBuyLog)){
			return form(transcodeBuyLog, model);
		}
		transcodeBuyLogService.save(transcodeBuyLog);
		addMessage(redirectAttributes, "保存撮合成功记录成功");
		return "redirect:"+Global.getAdminPath()+"/user/transcodeBuyLog/?repage";
	}
	
	@RequiresPermissions("user:transcodeBuyLog:edit")
	@RequestMapping(value = "delete")
	public String delete(TranscodeBuyLog transcodeBuyLog, RedirectAttributes redirectAttributes) {
		transcodeBuyLogService.delete(transcodeBuyLog);
		addMessage(redirectAttributes, "删除撮合成功记录成功");
		return "redirect:"+Global.getAdminPath()+"/user/transcodeBuyLog/?repage";
	}


	@RequiresPermissions("user:transcodeBuyLog:edit")
	@RequestMapping(value = "buyUnconfirmed")
	public String buyUnconfirmed(TranscodeBuyLog transcodeBuyLog,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String id =request.getParameter("id");
		try {
			transcodeBuyLogService.buyUnconfirmed(id);
			addMessage(redirectAttributes, "操作订单成功");
		} catch (ValidationException e) {
			addMessage(redirectAttributes, "操作订单失败:"+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/transcodeBuyLog/?repage&status=0";
	}

	@RequiresPermissions("user:transcodeBuyLog:edit")
	@RequestMapping(value = "sellUnconfirmed")
	public String sellUnconfirmed(TranscodeBuyLog transcodeBuyLog,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String id =request.getParameter("id");
		try {
			transcodeBuyLogService.sellUnconfirmed(id);
			addMessage(redirectAttributes, "操作订单成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "操作订单失败:"+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/transcodeBuyLog/?repage&status=1";
	}
}