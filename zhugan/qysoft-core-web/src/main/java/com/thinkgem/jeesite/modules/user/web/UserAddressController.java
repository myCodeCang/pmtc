/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.sys.utils.annotations.RecordLog;
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
import com.thinkgem.jeesite.modules.user.entity.UserAddress;
import com.thinkgem.jeesite.modules.user.service.UserAddressService;

/**
 * 用户地址表Controller
 * @author luojie
 * @version 2017-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userAddress")
public class UserAddressController extends BaseController {

	@Resource
	private UserAddressService userAddressService;
	
	@ModelAttribute
	public UserAddress get(@RequestParam(required=false) String id) {
		UserAddress entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userAddressService.get(id);
		}
		if (entity == null){
			entity = new UserAddress();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userAddress:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserAddress userAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserAddress> page = userAddressService.findPage(new Page<UserAddress>(request, response), userAddress); 
		model.addAttribute("page", page);
		return "modules/user/userAddressList";
	}

	@RequiresPermissions("user:userAddress:view")
	@RequestMapping(value = "form")
	public String form(UserAddress userAddress, Model model) {
		model.addAttribute("userAddress", userAddress);
		return "modules/user/userAddressForm";
	}

	@RecordLog(logTitle = "用户管理-会员管理-地址-新增/修改")
	@RequiresPermissions("user:userAddress:edit")
	@RequestMapping(value = "save")
	public String save(UserAddress userAddress, Model model, RedirectAttributes redirectAttributes) {


		try {
			userAddressService.save(userAddress);
			addMessage(redirectAttributes, "保存用户地址成功");
			return "redirect:"+Global.getAdminPath()+"/user/userAddress?userName="+userAddress.getUserName();
		} catch (ValidationException e) {
			addMessage(model, "失败:"+e.getMessage());
			return form(userAddress, model);
		} catch (Exception e) {
			addMessage(model,"失败");
			return form(userAddress, model);
		}


	}

	@RecordLog(logTitle = "用户管理-会员管理-地址删除")
	@RequiresPermissions("user:userAddress:edit")
	@RequestMapping(value = "delete")
	public String delete(UserAddress userAddress, RedirectAttributes redirectAttributes)  {

		try {

			userAddressService.delete(userAddress);
			addMessage(redirectAttributes, "删除用户地址成功");
			return "redirect:"+Global.getAdminPath()+"/user/userAddress/?repage";
		} catch (ValidationException e) {
			addMessage(redirectAttributes, "失败:"+e.getMessage());
			return "redirect:"+Global.getAdminPath()+"/user/userAddress/?repage";
		} catch (Exception e) {
			addMessage(redirectAttributes,"失败");
			return "redirect:"+Global.getAdminPath()+"/user/userAddress/?repage";
		}

	}
}