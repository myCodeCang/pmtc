/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
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
import com.thinkgem.jeesite.modules.user.entity.UserCharge;
import com.thinkgem.jeesite.modules.user.service.UserChargeService;

/**
 * 汇款审核Controller
 * @author wyxiang
 * @version 2017-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userCharge")
public class UserChargeController extends BaseController {

	@Resource
	private UserChargeService userChargeService;
	
	@ModelAttribute
	public UserCharge get(@RequestParam(required=false) String id) {
		UserCharge entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userChargeService.get(id);
		}
		if (entity == null){
			entity = new UserCharge();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userCharge:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserCharge userCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserCharge> page = userChargeService.findPage(new Page<UserCharge>(request, response), userCharge); 
		model.addAttribute("page", page);
		return "modules/user/userChargeList";
	}


	@RecordLog(logTitle = "财务管理-汇款审核-操作-同意/驳回")
	@RequestMapping(value = "updetstatus")
	public String updetstatus(UserCharge userCharge,String promValue,String promMsg, Model model, RedirectAttributes redirectAttributes) {
		try {
			userChargeService.updateStatus(userCharge,promValue,promMsg);
			addMessage(model, "成功");
		} catch (ValidationException e) {
			addMessage(model, "失败:"+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userCharge/?repage";
	}
}