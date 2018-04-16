/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.util.List;

import com.thinkgem.jeesite.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.UserMailmessage;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.dao.UserMailmessageDao;

/**
 * 站内信Service
 * @author cais
 * @version 2017-04-26
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserMailmessageService extends CrudService<UserMailmessageDao, UserMailmessage> {
	
	
	public UserMailmessage get(String id) {
		return super.get(id);
	}
	
	public List<UserMailmessage> findList(UserMailmessage userMailmessage) {
		return super.findList(userMailmessage);
	}
	
	public Page<UserMailmessage> findPage(Page<UserMailmessage> page, UserMailmessage userMailmessage) {
		return super.findPage(page, userMailmessage);
	}
	

	public void save(UserMailmessage userMailmessage) throws ValidationException {
		
		super.save(userMailmessage);
	
	}
	

	public void delete(UserMailmessage userMailmessage) throws ValidationException{
		super.delete(userMailmessage);
	}
	
}