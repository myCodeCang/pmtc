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
import com.thinkgem.jeesite.modules.user.entity.TranscodePriceDaylog;
import com.thinkgem.jeesite.modules.user.service.TranscodePriceDaylogService;

/**
 * k线走势Controller
 * @author wyxiang
 * @version 2018-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/user/transcodePriceDaylog")
public class TranscodePriceDaylogController extends BaseController {

	@Autowired
	private TranscodePriceDaylogService transcodePriceDaylogService;
	
	@ModelAttribute
	public TranscodePriceDaylog get(@RequestParam(required=false) String id) {
		TranscodePriceDaylog entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = transcodePriceDaylogService.get(id);
		}
		if (entity == null){
			entity = new TranscodePriceDaylog();
		}
		return entity;
	}
	
	@RequiresPermissions("user:transcodePriceDaylog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TranscodePriceDaylog transcodePriceDaylog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TranscodePriceDaylog> page = transcodePriceDaylogService.findPage(new Page<TranscodePriceDaylog>(request, response), transcodePriceDaylog); 
		model.addAttribute("page", page);
		return "modules/user/transcodePriceDaylogList";
	}

	@RequiresPermissions("user:transcodePriceDaylog:view")
	@RequestMapping(value = "form")
	public String form(TranscodePriceDaylog transcodePriceDaylog, Model model) {
		model.addAttribute("transcodePriceDaylog", transcodePriceDaylog);
		return "modules/user/transcodePriceDaylogForm";
	}

	@RequiresPermissions("user:transcodePriceDaylog:edit")
	@RequestMapping(value = "save")
	public String save(TranscodePriceDaylog transcodePriceDaylog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, transcodePriceDaylog)){
			return form(transcodePriceDaylog, model);
		}
		try {
			transcodePriceDaylogService.save(transcodePriceDaylog);
		} catch (ValidationException e) {
			addMessage(redirectAttributes,"失败"+e.getMessage());
			return "redirect:"+Global.getAdminPath()+"/user/transcodePriceDaylog/?repage";
		}
		addMessage(redirectAttributes, "保存k线走势成功");
		return "redirect:"+Global.getAdminPath()+"/user/transcodePriceDaylog/?repage";
	}
	
	@RequiresPermissions("user:transcodePriceDaylog:edit")
	@RequestMapping(value = "delete")
	public String delete(TranscodePriceDaylog transcodePriceDaylog, RedirectAttributes redirectAttributes) {
		transcodePriceDaylogService.delete(transcodePriceDaylog);
		addMessage(redirectAttributes, "删除k线走势成功");
		return "redirect:"+Global.getAdminPath()+"/user/transcodePriceDaylog/?repage";
	}

}