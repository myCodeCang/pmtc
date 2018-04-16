/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wechat.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.wechat.entity.SysUserWx;
import com.thinkgem.jeesite.modules.wechat.service.SysUserWxService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信用户关联Controller
 * @author kevin
 * @version 2017-07-30
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/sysUserWx")
public class SysUserWxController extends BaseController {

	@Autowired
	private SysUserWxService sysUserWxService;
	
	@ModelAttribute
	public SysUserWx get(@RequestParam(required=false) String id) {
		SysUserWx entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = sysUserWxService.get(id);
		}
		if (entity == null){
			entity = new SysUserWx();
		}
		return entity;
	}
	
	@RequiresPermissions("wechat:sysUserWx:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysUserWx sysUserWx, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUserWx> page = sysUserWxService.findPage(new Page<SysUserWx>(request, response), sysUserWx); 
		model.addAttribute("page", page);
		return "modules/wechat/sysUserWxList";
	}

	@RequiresPermissions("wechat:sysUserWx:view")
	@RequestMapping(value = "form")
	public String form(SysUserWx sysUserWx, Model model) {
		model.addAttribute("sysUserWx", sysUserWx);
		return "modules/wechat/sysUserWxForm";
	}

	@RequiresPermissions("wechat:sysUserWx:edit")
	@RequestMapping(value = "save")
	public String save(SysUserWx sysUserWx, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysUserWx)){
			return form(sysUserWx, model);
		}
		sysUserWxService.save(sysUserWx);
		addMessage(redirectAttributes, "保存微信用户关联成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/sysUserWx/?repage";
	}
	
	@RequiresPermissions("wechat:sysUserWx:edit")
	@RequestMapping(value = "delete")
	public String delete(SysUserWx sysUserWx, RedirectAttributes redirectAttributes) {
		sysUserWxService.delete(sysUserWx);
		addMessage(redirectAttributes, "删除微信用户关联成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/sysUserWx/?repage";
	}

}