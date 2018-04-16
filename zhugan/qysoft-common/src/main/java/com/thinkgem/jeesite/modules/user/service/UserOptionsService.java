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
import com.thinkgem.jeesite.modules.user.entity.UserOptions;
import com.thinkgem.jeesite.modules.user.dao.UserOptionsDao;

/**
 * 常规配置Service
 * @author wyxiang
 * @version 2017-04-26
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserOptionsService extends CrudService<UserOptionsDao, UserOptions> {

	public UserOptions get(String id) {
		return super.get(id);
	}
	
	public UserOptions getByOptionName(String optName){
		return dao.getByOptionName(optName);
	}
	
	public List<UserOptions> getByGroupName(String groupName){
		return dao.getByGroupName(groupName);
	}
	
	public List<UserOptions> findList(UserOptions userOptions) {
		return super.findList(userOptions);
	}
	
	public Page<UserOptions> findPage(Page<UserOptions> page, UserOptions userOptions) {
		return super.findPage(page, userOptions);
	}
	

	public void save(UserOptions userOptions) throws ValidationException {
		super.save(userOptions);
	}
	

	public void delete(UserOptions userOptions) throws ValidationException{
		super.delete(userOptions);
	}
	
}