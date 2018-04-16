/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.exception.ValidationException;
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
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.user.entity.UserMailmessage;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.UserMailmessageService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;

/**
 * 站内信Controller
 * @author cais
 * @version 2017-04-26
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userMailmessage")
public class UserMailmessageController extends BaseController {

	@Resource
	private UserMailmessageService userMailmessageService;
	@Resource
	private UserUserinfoService userUserinfoService;
	
	@ModelAttribute
	public UserMailmessage get(@RequestParam(required=false) String id) {
		UserMailmessage entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userMailmessageService.get(id);
		}
		if (entity == null){
			entity = new UserMailmessage();
		}
		return entity;
	}
	

	
	@RequiresPermissions("user:userMailmessage:view")
	@RequestMapping(value = "send")
	public String sendlist(UserMailmessage userMailmessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		userMailmessage.setSendName("admin");
		Page<UserMailmessage> page = userMailmessageService.findPage(new Page<UserMailmessage>(request, response), userMailmessage); 
		model.addAttribute("page", page);
		return "modules/user/userMailmessageSendList";
	}
	
	@RequiresPermissions("user:userMailmessage:view")
	@RequestMapping(value = "rece")
	public String recelist(UserMailmessage userMailmessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		userMailmessage.setReceName(UserUtils.getUser().getName());
		Page<UserMailmessage> page = userMailmessageService.findPage(new Page<UserMailmessage>(request, response), userMailmessage); 
		model.addAttribute("page", page);
		return "modules/user/userMailmessageReceList";
	}
	
	@RequiresPermissions("user:userMailmessage:view")
	@RequestMapping(value = "sendform")
	public String sendform(UserMailmessage userMailmessage, Model model) {
		model.addAttribute("userMailmessage", userMailmessage);
		return "modules/user/userMailmessageSendForm";
	}
	
	@RequiresPermissions("user:userMailmessage:view")
	@RequestMapping(value = "receform")
	public String receform(UserMailmessage userMailmessage, Model model) {
		model.addAttribute("userMailmessage", userMailmessage);
		return "modules/user/userMailmessageReceForm";
	}
	
	@RequiresPermissions("user:userMailmessage:edit")
	@RequestMapping(value = "save") 
	public String save(UserMailmessage userMailmessage, Model model, RedirectAttributes redirectAttributes) throws ValidationException {
		if (!beanValidator(model, userMailmessage)){
			return sendform(userMailmessage, model);
		}
		UserUserinfo userinfo = null;
		userinfo = userUserinfoService.getByName(userMailmessage.getToMemberName());
		if(userinfo==null){
			addMessage(redirectAttributes, "信件发送失败,没有找到该收件人");
			redirectAttributes.addFlashAttribute("toMemberName", userMailmessage.getToMemberName());
			redirectAttributes.addFlashAttribute("title", userMailmessage.getMessageTitle());
			redirectAttributes.addFlashAttribute("messageBody", userMailmessage.getMessageBody());
			return "redirect:"+Global.getAdminPath()+"/user/userMailmessage/sendform?repage"
					+ "";
		}
		if(UserUtils.getUser ().getName ().equals (userMailmessage.getToMemberName ())){
			addMessage(redirectAttributes, "信件发送失败,收件人不可以填自己");
			return "redirect:"+Global.getAdminPath()+"/user/userMailmessage/sendform?repage"
					+ "";
		}
		userMailmessage.setAddtime(new Date());
		userMailmessageService.save(userMailmessage);
		addMessage(redirectAttributes, "信件发送成功");
		return "redirect:"+Global.getAdminPath()+"/user/userMailmessage/send?repage";
	}
		
	
	@RequiresPermissions("user:userMailmessage:edit")
	@RequestMapping(value = "recedelete")
	public String recedelete(UserMailmessage userMailmessage, RedirectAttributes redirectAttributes) {
		try {
			userMailmessageService.delete(userMailmessage);
			addMessage(redirectAttributes, "删除信件成功");

		} catch (ValidationException e) {
			addMessage(redirectAttributes, "失败:"+e.getMessage());
		} catch (Exception e) {
			addMessage(redirectAttributes, "失败:"+e.getMessage());
		}

		return "redirect:"+Global.getAdminPath()+"/user/userMailmessage/rece?repage";
	}
	
	@RequiresPermissions("user:userMailmessage:edit")
	@RequestMapping(value = "senddelete")
	public String senddelete(UserMailmessage userMailmessage, RedirectAttributes redirectAttributes) {
		try {

			userMailmessageService.delete(userMailmessage);
			addMessage(redirectAttributes, "删除信件成功");
		} catch (ValidationException e) {
			addMessage(redirectAttributes, "失败:"+e.getMessage());
		} catch (Exception e) {
			addMessage(redirectAttributes, "失败:"+e.getMessage());
		}

		return "redirect:"+Global.getAdminPath()+"/user/userMailmessage/send?repage";

	}

}