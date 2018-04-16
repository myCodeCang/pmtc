/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.util.List;

import com.thinkgem.jeesite.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.UserAddress;
import com.thinkgem.jeesite.modules.user.dao.UserAddressDao;

/**
 * 用户地址表Service
 * @author luojie
 * @version 2017-05-08
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserAddressService extends CrudService<UserAddressDao, UserAddress> {

	public UserAddress get(String id) {
		return super.get(id);
	}
	
	public List<UserAddress> findList(UserAddress userAddress) {
		return super.findList(userAddress);
	}
	
	public Page<UserAddress> findPage(Page<UserAddress> page, UserAddress userAddress) {
		return super.findPage(page, userAddress);
	}
	

	public void save(UserAddress userAddress) throws ValidationException {
		super.save(userAddress);
	}
	

	public void delete(UserAddress userAddress)throws ValidationException {
		super.delete(userAddress);
	}
	
}