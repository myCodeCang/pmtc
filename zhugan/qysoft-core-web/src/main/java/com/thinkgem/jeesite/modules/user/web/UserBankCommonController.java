/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.modules.sys.utils.annotations.RecordLog;
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
import com.thinkgem.jeesite.modules.user.entity.UserBankCommon;
import com.thinkgem.jeesite.modules.user.service.UserBankCommonService;

/**
 * 银行信息Controller
 * @author kevin
 * @version 2017-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userBankCommon")
public class UserBankCommonController extends BaseController {

	@Resource
	private UserBankCommonService userBankCommonService;
	
	@ModelAttribute
	public UserBankCommon get(@RequestParam(required=false) String id) {
		UserBankCommon entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userBankCommonService.get(id);
		}
		if (entity == null){
			entity = new UserBankCommon();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userBankCommon:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserBankCommon userBankCommon, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserBankCommon> page = userBankCommonService.findPage(new Page<UserBankCommon>(request, response), userBankCommon); 
		model.addAttribute("page", page);
		return "modules/user/userBankCommonList";
	}

	@RequiresPermissions("user:userBankCommon:view")
	@RequestMapping(value = "form")
	public String form(UserBankCommon userBankCommon, Model model) {
		model.addAttribute("userBankCommon", userBankCommon);
		return "modules/user/userBankCommonForm";
	}

	@RecordLog(logTitle = "常规配置-银行配置-银行添加-新增/修改")
	@RequiresPermissions("user:userBankCommon:edit")
	@RequestMapping(value = "save")
	public String save(UserBankCommon userBankCommon, Model model, RedirectAttributes redirectAttributes) {
		try {
			userBankCommonService.save(userBankCommon);
			addMessage(redirectAttributes, "保存银行成功");
			return "redirect:"+Global.getAdminPath()+"/user/userBankCommon/?repage";

		} catch (ValidationException e) {
			addMessage(model, "失败:"+e.getMessage());
			return form(userBankCommon, model);
		} catch (Exception e) {
			addMessage(model,"失败!");
			return form(userBankCommon, model);
		}


	}

	@RecordLog(logTitle = "常规配置-银行配置-银行删除")
	@RequiresPermissions("user:userBankCommon:edit")
	@RequestMapping(value = "delete")
	public String delete(UserBankCommon userBankCommon, RedirectAttributes redirectAttributes) {

		try {
			userBankCommonService.delete(userBankCommon);
			addMessage(redirectAttributes, "删除银行成功");
			return "redirect:"+Global.getAdminPath()+"/user/userBankCommon/?repage";
		} catch (ValidationException e) {
			addMessage(redirectAttributes, "失败:"+e.getMessage());
			return "redirect:"+Global.getAdminPath()+"/user/userBankCommon/?repage";
		} catch (Exception e) {
			addMessage(redirectAttributes,"失败!");
			return "redirect:"+Global.getAdminPath()+"/user/userBankCommon/?repage";
		}
	}

	/**
	 * 查询默认银行列表弹窗
	 * @param userBankCommon
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectBankCommen")
	public String selectBankCommen(UserBankCommon userBankCommon, HttpServletRequest request, HttpServletResponse response, Model model) {
	 	userBankCommon.setStatus (EnumUtil.YesNO.YES.toString ());
		Page<UserBankCommon> page = userBankCommonService.findPage(new Page<UserBankCommon>(request, response), userBankCommon);
		model.addAttribute("page", page);
		return "modules/user/selectBankCommen";
	}

}