/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.util.List;

import com.thinkgem.jeesite.modules.user.entity.ShopCommOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.dao.ShopCommOrderDao;
import com.thinkgem.jeesite.modules.user.dao.ShopCommOrderDao;

/**
 * 通信记录Service
 * @author xueyuliang
 * @version 2017-06-08
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class ShopCommOrderService extends CrudService<ShopCommOrderDao, ShopCommOrder> {

	public ShopCommOrder get(String id) {
		return super.get(id);
	}
	
	public List<ShopCommOrder> findList(ShopCommOrder shopCommOrder) {
		return super.findList(shopCommOrder);
	}
	
	public Page<ShopCommOrder> findPage(Page<ShopCommOrder> page, ShopCommOrder shopCommOrder) {
		return super.findPage(page, shopCommOrder);
	}
	

	public void save(ShopCommOrder shopCommOrder) {
		super.save(shopCommOrder);
	}
	

	public void delete(ShopCommOrder shopCommOrder) {
		super.delete(shopCommOrder);
	}
	
}