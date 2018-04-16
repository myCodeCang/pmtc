/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.user.entity.UserLevelLog;
import com.thinkgem.jeesite.modules.user.service.UserLevelLogService;

/**
 * 用户升级明细表Controller
 * @author luojie
 * @version 2017-04-25
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userLevelLog")
public class UserLevelLogController extends BaseController {

	@Resource
	private UserLevelLogService userLevelLogService;
	
	@ModelAttribute
	public UserLevelLog get(@RequestParam(required=false) String id) {
		UserLevelLog entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userLevelLogService.get(id);
		}
		if (entity == null){
			entity = new UserLevelLog();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userLevelLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserLevelLog userLevelLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<UserLevelLog> page = userLevelLogService.findPage(new Page<UserLevelLog>(request, response), userLevelLog); 
		model.addAttribute("page", page);
		return "modules/user/userLevelLogList";
	}

}