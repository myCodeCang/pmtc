/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.shop.entity.ShopCategory;
import com.thinkgem.jeesite.modules.shop.service.ShopCategoryService;

import java.util.List;

/**
 * 商品分类Controller
 * @author wyxiang
 * @version 2017-05-04
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopCategory")
public class ShopCategoryController extends BaseController {

	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@ModelAttribute
	public ShopCategory get(@RequestParam(required=false) String id) {
		ShopCategory entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = shopCategoryService.get(id);
		}
		if (entity == null){
			entity = new ShopCategory();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ShopCategory shopCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ShopCategory> shopCategoryList = shopCategoryService.findAllList(shopCategory);
		model.addAttribute("shopCategoryList", shopCategoryList);




		return "modules/shop/shopCategoryList";
	}

	@RequestMapping(value = "form")
	public String form(ShopCategory shopCategory, Model model) {
		model.addAttribute("shopCategory", shopCategory);
		return "modules/shop/shopCategoryForm";
	}

	@RequestMapping(value = "save")
	public String save(ShopCategory shopCategory, Model model, RedirectAttributes redirectAttributes) throws ValidationException {
		if (!beanValidator(model, shopCategory)){
			return form(shopCategory, model);
		}
		shopCategoryService.save(shopCategory);
		addMessage(redirectAttributes, "保存商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopCategory/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ShopCategory shopCategory, RedirectAttributes redirectAttributes) throws ValidationException{
		shopCategoryService.delete(shopCategory);
		addMessage(redirectAttributes, "删除商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopCategory/?repage";
	}

}