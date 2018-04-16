/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.annotations.RecordLog;
import com.thinkgem.jeesite.modules.user.entity.UserSequence;
import com.thinkgem.jeesite.modules.user.service.UserSequenceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * 序列管理Controller
 * @author hh
 * @version 2017-05-04
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userSequence")
public class UserSequenceController extends BaseController {

	@Resource
	private UserSequenceService userSequenceService;
	
	@ModelAttribute
	public UserSequence get(@RequestParam(required=false) String id) {
		UserSequence entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userSequenceService.get(id);
		}
		if (entity == null){
			entity = new UserSequence();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userSequence:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserSequence userSequence, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserSequence> page = userSequenceService.findPage(new Page<UserSequence>(request, response), userSequence); 
		model.addAttribute("page", page);
		return "modules/user/userSequenceList";
	}

	@RequiresPermissions("user:userSequence:view")
	@RequestMapping(value = "form")
	public String form(UserSequence userSequence, Model model) {
		model.addAttribute("userSequence", userSequence);
		return "modules/user/userSequenceForm";
	}

	@RecordLog(logTitle = "系统设置-系统序列管理-序列管理添加-新增/修改")
	@RequiresPermissions("user:userSequence:edit")
	@RequestMapping(value = "save")
	public String save(UserSequence userSequence, Model model, RedirectAttributes redirectAttributes) throws ValidationException {
		if (!beanValidator(model, userSequence)){
			return form(userSequence, model);
		}
		userSequenceService.save(userSequence);
		addMessage(redirectAttributes, "保存序列管理成功");
		return "redirect:"+Global.getAdminPath()+"/user/userSequence/?repage";
	}

	@RecordLog(logTitle = "系统设置-系统序列管理-序列删除")
	@RequiresPermissions("user:userSequence:edit")
	@RequestMapping(value = "delete")
	public String delete(UserSequence userSequence, RedirectAttributes redirectAttributes)throws ValidationException {
		userSequenceService.delete(userSequence);
		addMessage(redirectAttributes, "删除序列管理成功");
		return "redirect:"+Global.getAdminPath()+"/user/userSequence/?repage";
	}
}