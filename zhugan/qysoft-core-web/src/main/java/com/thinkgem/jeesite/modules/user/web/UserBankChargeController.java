/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.user.entity.UserBankCharge;
import com.thinkgem.jeesite.modules.user.service.UserBankChargeService;

/**
 * 充值银行信息Controller
 * @author cai
 * @version 2017-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userBankCharge")
public class UserBankChargeController extends BaseController {

	@Resource
	private UserBankChargeService userBankChargeService;
	
	@ModelAttribute
	public UserBankCharge get(@RequestParam(required=false) String id) {
		UserBankCharge entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userBankChargeService.get(id);
		}
		if (entity == null){
			entity = new UserBankCharge();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userBankCharge:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserBankCharge userBankCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserBankCharge> page = userBankChargeService.findPage(new Page<UserBankCharge>(request, response), userBankCharge); 
		model.addAttribute("page", page);
		return "modules/user/userBankChargeList";
	}

	@RequiresPermissions("user:userBankCharge:view")
	@RequestMapping(value = "form")
	public String form(UserBankCharge userBankCharge, Model model) {
		model.addAttribute("userBankCharge", userBankCharge);
		return "modules/user/userBankChargeForm";
	}

	@RecordLog(logTitle = "常规配置-充值银行管理-充值银行信息添加-新增/修改")
	@RequiresPermissions("user:userBankCharge:edit")
	@RequestMapping(value = "save")
	public String save(UserBankCharge userBankCharge, Model model, RedirectAttributes redirectAttributes) {

		try {
			userBankChargeService.save(userBankCharge);
			addMessage(redirectAttributes, "保存充值银行信息成功");
			return "redirect:"+Global.getAdminPath()+"/user/userBankCharge/?repage";

		} catch (ValidationException e) {
			addMessage(model, "失败:"+e.getMessage());
			return form(userBankCharge, model);
		} catch (Exception e) {
			addMessage(model, "失败");
			return form(userBankCharge, model);
		}



	}

	@RecordLog(logTitle = "常规配置-充值银行管理-充值银行信息删除")
	@RequiresPermissions("user:userBankCharge:edit")
	@RequestMapping(value = "delete")
	public String delete(UserBankCharge userBankCharge, RedirectAttributes redirectAttributes) {
		try {
			userBankChargeService.delete(userBankCharge);
			addMessage(redirectAttributes, "删除充值银行信息成功");
			return "redirect:"+Global.getAdminPath()+"/user/userBankCharge/?repage";

		} catch (ValidationException e) {
			addMessage(redirectAttributes, "失败:"+e.getMessage());
			return "redirect:"+Global.getAdminPath()+"/user/userBankCharge/?repage";
		} catch (Exception e) {
			addMessage(redirectAttributes, "失败");
			return "redirect:"+Global.getAdminPath()+"/user/userBankCharge/?repage";
		}


	}

}