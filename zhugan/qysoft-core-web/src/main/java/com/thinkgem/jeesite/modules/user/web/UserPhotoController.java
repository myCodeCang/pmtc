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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.user.entity.UserPhoto;
import com.thinkgem.jeesite.modules.user.service.UserPhotoService;

/**
 * 图片Controller
 * @author luojie
 * @version 2017-06-27
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userPhoto")
public class UserPhotoController extends BaseController {

	@Autowired
	private UserPhotoService userPhotoService;
	
	@ModelAttribute
	public UserPhoto get(@RequestParam(required=false) String id) {
		UserPhoto entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userPhotoService.get(id);
		}
		if (entity == null){
			entity = new UserPhoto();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userPhoto:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserPhoto userPhoto, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserPhoto> page = userPhotoService.findPage(new Page<UserPhoto>(request, response), userPhoto); 
		model.addAttribute("page", page);
		return "modules/user/userPhotoList";
	}

	@RequiresPermissions("user:userPhoto:view")
	@RequestMapping(value = "form")
	public String form(UserPhoto userPhoto, Model model) {
		model.addAttribute("userPhoto", userPhoto);
		return "modules/user/userPhotoForm";
	}

	@RequiresPermissions("user:userPhoto:edit")
	@RequestMapping(value = "save")
	public String save(UserPhoto userPhoto, Model model, RedirectAttributes redirectAttributes) throws ValidationException{

		try {
			userPhotoService.save(userPhoto);
		} catch (ValidationException e) {
			addMessage(model, "失败:"+e.getMessage());
			return form(userPhoto, model);
		}
		addMessage(redirectAttributes, "保存图片成功");
		return "redirect:"+Global.getAdminPath()+"/user/userPhoto/?repage";
	}
	
	@RequiresPermissions("user:userPhoto:edit")
	@RequestMapping(value = "delete")
	public String delete(UserPhoto userPhoto, RedirectAttributes redirectAttributes) {
		userPhotoService.delete(userPhoto);
		addMessage(redirectAttributes, "删除图片成功");
		return "redirect:"+Global.getAdminPath()+"/user/userPhoto/?repage";
	}

}