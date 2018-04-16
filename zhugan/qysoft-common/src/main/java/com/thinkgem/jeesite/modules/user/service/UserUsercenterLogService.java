/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.util.List;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.UserUsercenterLog;
import com.thinkgem.jeesite.modules.user.dao.UserUsercenterLogDao;

import javax.annotation.Resource;

/**
 * 报单中心审核Service
 * @author cai
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserUsercenterLogService extends CrudService<UserUsercenterLogDao, UserUsercenterLog> {
	
	@Resource
	private UserUserinfoService userUserinfoService;

	public UserUsercenterLog get(String id) {
		return super.get(id);
	}
	
	public List<UserUsercenterLog> findList(UserUsercenterLog userUsercenterLog) {
		return super.findList(userUsercenterLog);
	}
	
	public Page<UserUsercenterLog> findPage(Page<UserUsercenterLog> page, UserUsercenterLog userUsercenterLog) {
		return super.findPage(page, userUsercenterLog);
	}
	

	public void save(UserUsercenterLog userUsercenterLog) throws ValidationException {
		super.save(userUsercenterLog);
	}
	

	public void updateCenterLogStatus(UserUsercenterLog userUsercenterLog,String status,String message) throws ValidationException {


		if(!userUsercenterLog.getStatus().equals(EnumUtil.CheckType.uncheck.toString())){
			throw new ValidationException("不可重复审核!");
		}

		userUsercenterLog.setStatus(status);
		userUsercenterLog.setCommt(message);

		if("0".equals(status)){
			userUsercenterLog.setStatus("2");
		}
		super.save(userUsercenterLog);
		userUserinfoService.setUserCenter(userUsercenterLog.getUserName(),status,userUsercenterLog.getLevel(),userUsercenterLog.getCenterAddress());
	}
	
	

	public void delete(UserUsercenterLog userUsercenterLog)throws ValidationException {
		super.delete(userUsercenterLog);
	}
	
}