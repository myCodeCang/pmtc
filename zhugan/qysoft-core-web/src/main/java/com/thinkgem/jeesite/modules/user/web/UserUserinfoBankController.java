/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
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
import com.thinkgem.jeesite.modules.user.entity.UserUserinfoBank;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoBankService;

/**
 * 个人银行卡Controller
 * @author luo
 * @version 2017-05-12
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userUserinfoBank")
public class UserUserinfoBankController extends BaseController {

	@Resource
	private UserUserinfoBankService userUserinfoBankService;
	
	@ModelAttribute
	public UserUserinfoBank get(@RequestParam(required=false) String id) {
		UserUserinfoBank entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userUserinfoBankService.get(id);
		}
		if (entity == null){
			entity = new UserUserinfoBank();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userUserinfoBank:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserUserinfoBank userUserinfoBank, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserUserinfoBank> page = userUserinfoBankService.findPage(new Page<UserUserinfoBank>(request, response), userUserinfoBank); 
		model.addAttribute("page", page);
		return "modules/user/userUserinfoBankList";
	}

	@RequiresPermissions("user:userUserinfoBank:view")
	@RequestMapping(value = "form")
	public String form(UserUserinfoBank userUserinfoBank, Model model) {
		model.addAttribute("userUserinfoBank", userUserinfoBank);
		return "modules/user/userUserinfoBankForm";
	}

	@RequiresPermissions("user:userUserinfoBank:edit")
	@RequestMapping(value = "save")
	public String save(UserUserinfoBank userUserinfoBank, Model model, RedirectAttributes redirectAttributes)  {
		try {
			userUserinfoBankService.save(userUserinfoBank);
			addMessage(redirectAttributes, "保存银行卡成功");
			return "redirect:"+Global.getAdminPath()+"/user/userUserinfoBank/?repage";

		} catch (ValidationException e) {
			addMessage(model, "失败:"+e.getMessage());
			return form(userUserinfoBank, model);
		} catch (Exception e) {
			addMessage(model, "失败");
			return form(userUserinfoBank, model);
		}


	}
	
	@RequiresPermissions("user:userUserinfoBank:edit")
	@RequestMapping(value = "delete")
	public String delete(UserUserinfoBank userUserinfoBank, RedirectAttributes redirectAttributes){

		try {
			userUserinfoBankService.delete(userUserinfoBank);
			addMessage(redirectAttributes, "删除银行卡成功");

		} catch (ValidationException e) {
			addMessage(redirectAttributes, "失败:"+e.getMessage());
		} catch (Exception e) {
			addMessage(redirectAttributes, "失败");
		}

		return "redirect:"+Global.getAdminPath()+"/user/userUserinfoBank/?repage";
	}
}