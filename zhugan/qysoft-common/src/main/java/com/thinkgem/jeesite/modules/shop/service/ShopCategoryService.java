/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.service;

import java.util.List;

import com.thinkgem.jeesite.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shop.entity.ShopCategory;
import com.thinkgem.jeesite.modules.shop.dao.ShopCategoryDao;

/**
 * 商品分类Service
 * @author wyxiang
 * @version 2017-05-04
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class ShopCategoryService extends CrudService<ShopCategoryDao, ShopCategory> {

	public ShopCategory get(String id) {
		return super.get(id);
	}
	
	public List<ShopCategory> findList(ShopCategory shopCategory) {
		return super.findList(shopCategory);
	}
	
	public Page<ShopCategory> findPage(Page<ShopCategory> page, ShopCategory shopCategory) {
		return super.findPage(page, shopCategory);
	}
	

	public void save(ShopCategory shopCategory) throws ValidationException {
		super.save(shopCategory);
	}
	

	public void delete(ShopCategory shopCategory) throws ValidationException{
		super.delete(shopCategory);
	}
	
}