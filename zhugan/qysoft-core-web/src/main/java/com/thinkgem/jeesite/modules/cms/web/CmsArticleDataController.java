/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.web;

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
import com.thinkgem.jeesite.modules.cms.entity.CmsArticleData;
import com.thinkgem.jeesite.modules.cms.service.CmsArticleDataService;

/**
 * 文章详情Controller
 * @author luo
 * @version 2017-05-19
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsArticleData")
public class CmsArticleDataController extends BaseController {

	@Autowired
	private CmsArticleDataService cmsArticleDataService;
	
	@ModelAttribute
	public CmsArticleData get(@RequestParam(required=false) String id) {
		CmsArticleData entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = cmsArticleDataService.get(id);
		}
		if (entity == null){
			entity = new CmsArticleData();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:cmsArticleData:view")
	@RequestMapping(value = {"list", ""})
	public String list(CmsArticleData cmsArticleData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsArticleData> page = cmsArticleDataService.findPage(new Page<CmsArticleData>(request, response), cmsArticleData); 
		model.addAttribute("page", page);
		return "modules/cms/cmsArticleDataList";
	}

	@RequiresPermissions("cms:cmsArticleData:view")
	@RequestMapping(value = "form")
	public String form(CmsArticleData cmsArticleData, Model model) {
		model.addAttribute("cmsArticleData", cmsArticleData);
		return "modules/cms/cmsArticleDataForm";
	}

	@RequiresPermissions("cms:cmsArticleData:edit")
	@RequestMapping(value = "save")
	public String save(CmsArticleData cmsArticleData, Model model, RedirectAttributes redirectAttributes) throws ValidationException {
		if (!beanValidator(model, cmsArticleData)){
			return form(cmsArticleData, model);
		}
		cmsArticleDataService.save(cmsArticleData);
		addMessage(redirectAttributes, "保存文章成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsArticleData/?repage";
	}
	
	@RequiresPermissions("cms:cmsArticleData:edit")
	@RequestMapping(value = "delete")
	public String delete(CmsArticleData cmsArticleData, RedirectAttributes redirectAttributes) throws ValidationException{
		cmsArticleDataService.delete(cmsArticleData);
		addMessage(redirectAttributes, "删除文章成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsArticleData/?repage";
	}

}