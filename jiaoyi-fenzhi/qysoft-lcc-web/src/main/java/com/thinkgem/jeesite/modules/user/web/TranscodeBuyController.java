/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.exception.ValidationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.user.entity.TranscodeBuy;
import com.thinkgem.jeesite.modules.user.service.TranscodeBuyService;

import java.math.BigDecimal;

/**
 * 交易表Controller
 * @author wyxiang
 * @version 2018-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/user/transcodeBuy")
public class TranscodeBuyController extends BaseController {

	@Autowired
	private TranscodeBuyService transcodeBuyService;
	
	@ModelAttribute
	public TranscodeBuy get(@RequestParam(required=false) String id) {
		TranscodeBuy entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = transcodeBuyService.get(id);
		}
		if (entity == null){
			entity = new TranscodeBuy();
		}
		return entity;
	}
	
	@RequiresPermissions("user:transcodeBuy:view")
	@RequestMapping(value = {"list", ""})
	public String list(TranscodeBuy transcodeBuy, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TranscodeBuy> page = transcodeBuyService.findPage(new Page<TranscodeBuy>(request, response), transcodeBuy); 
		model.addAttribute("page", page);
		return "modules/user/transcodeBuyList";
	}

	@RequiresPermissions("user:transcodeBuy:view")
	@RequestMapping(value = "form")
	public String form(TranscodeBuy transcodeBuy, Model model) {
		model.addAttribute("transcodeBuy", transcodeBuy);
		return "modules/user/transcodeBuyForm";
	}

	@RequiresPermissions("user:transcodeBuy:edit")
	@RequestMapping(value = "save")
	public String save(TranscodeBuy transcodeBuy, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, transcodeBuy)){
			return form(transcodeBuy, model);
		}
		transcodeBuyService.save(transcodeBuy);
		addMessage(redirectAttributes, "保存交易成功");
		return "redirect:"+Global.getAdminPath()+"/user/transcodeBuy/?repage";
	}
	
	@RequiresPermissions("user:transcodeBuy:edit")
	@RequestMapping(value = "delete")
	public String delete(TranscodeBuy transcodeBuy, RedirectAttributes redirectAttributes) {
		transcodeBuyService.delete(transcodeBuy);
		addMessage(redirectAttributes, "删除交易成功");
		return "redirect:"+Global.getAdminPath()+"/user/transcodeBuy/?repage";
	}


	@RequestMapping(value = "adminPushBuy", method = RequestMethod.GET)
	public String adminPushBuy( Model model) {
		return "modules/user/adminPushBuy";
	}

	@RequestMapping(value = "adminPushBuy", method = RequestMethod.POST)
	public String adminPushBuy(Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String userName = request.getParameter("userName");
		String num = request.getParameter("sellNum");
		try {
			transcodeBuyService.adminPushBuy(userName,new BigDecimal(num));
			addMessage(redirectAttributes, "发布成功");
		} catch (ValidationException e) {
			addMessage(redirectAttributes, "发布失败: "+e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/user/transcodeBuy/adminPushBuy?repage";
	}
}