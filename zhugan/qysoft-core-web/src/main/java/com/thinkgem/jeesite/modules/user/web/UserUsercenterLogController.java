/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

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
import com.thinkgem.jeesite.modules.user.entity.UserUsercenterLog;
import com.thinkgem.jeesite.modules.user.service.UserUsercenterLogService;

/**
 * 报单中心审核Controller
 * @author cai
 * @version 2017-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userUsercenterLog")
public class UserUsercenterLogController extends BaseController {

	@Resource
	private UserUsercenterLogService userUsercenterLogService;
	
	@ModelAttribute
	public UserUsercenterLog get(@RequestParam(required=false) String id) {
		UserUsercenterLog entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userUsercenterLogService.get(id);
		}
		if (entity == null){
			entity = new UserUsercenterLog();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userUsercenterLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserUsercenterLog userUsercenterLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserUsercenterLog> page = userUsercenterLogService.findPage(new Page<UserUsercenterLog>(request, response), userUsercenterLog); 
		model.addAttribute("page", page);
		return "modules/user/userUsercenterLogList";
	}
	
	

	@RequestMapping(value = "updetstatus") 
	public String updetstatus(UserUsercenterLog userUsercenterLog,String promValue,String promMsg, Model model, RedirectAttributes redirectAttributes) {

		try {
			userUsercenterLogService.updateCenterLogStatus(userUsercenterLog,promValue,promMsg);
			addMessage(redirectAttributes, "审核成功");
			return "redirect:"+Global.getAdminPath()+"/user/userUsercenterLog/?repage";

		} catch (ValidationException e) {
			addMessage(redirectAttributes, "失败:"+e.getMessage());
		} catch (Exception e) {
			addMessage(redirectAttributes, "失败");
		}

		return "redirect:"+Global.getAdminPath()+"/user/userUsercenterLog/?repage";

	}
	
	

}