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
import com.thinkgem.jeesite.modules.user.entity.TransPriceDaylog;
import com.thinkgem.jeesite.modules.user.service.TransPriceDaylogService;

/**
 * 物品价格记录表Controller
 * @author luo
 * @version 2018-02-10
 */
@Controller
@RequestMapping(value = "${adminPath}/user/transPriceDaylog")
public class TransPriceDaylogController extends BaseController {

	@Autowired
	private TransPriceDaylogService transPriceDaylogService;
	
	@ModelAttribute
	public TransPriceDaylog get(@RequestParam(required=false) String id) {
		TransPriceDaylog entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = transPriceDaylogService.get(id);
		}
		if (entity == null){
			entity = new TransPriceDaylog();
		}
		return entity;
	}
	
	@RequiresPermissions("user:transPriceDaylog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TransPriceDaylog transPriceDaylog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TransPriceDaylog> page = transPriceDaylogService.findPage(new Page<TransPriceDaylog>(request, response), transPriceDaylog); 
		model.addAttribute("page", page);
		return "modules/user/transPriceDaylogList";
	}

	@RequiresPermissions("user:transPriceDaylog:view")
	@RequestMapping(value = "form")
	public String form(TransPriceDaylog transPriceDaylog, Model model) {
		model.addAttribute("transPriceDaylog", transPriceDaylog);
		return "modules/user/transPriceDaylogForm";
	}

	@RequiresPermissions("user:transPriceDaylog:edit")
	@RequestMapping(value = "save")
	public String save(TransPriceDaylog transPriceDaylog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, transPriceDaylog)){
			return form(transPriceDaylog, model);
		}
		transPriceDaylogService.save(transPriceDaylog);
		addMessage(redirectAttributes, "保存物品价格记录表成功");
		return "redirect:"+Global.getAdminPath()+"/user/transPriceDaylog/?repage";
	}
	
	@RequiresPermissions("user:transPriceDaylog:edit")
	@RequestMapping(value = "delete")
	public String delete(TransPriceDaylog transPriceDaylog, RedirectAttributes redirectAttributes) {
		transPriceDaylogService.delete(transPriceDaylog);
		addMessage(redirectAttributes, "删除物品价格记录表成功");
		return "redirect:"+Global.getAdminPath()+"/user/transPriceDaylog/?repage";
	}

}