/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.dao.UserLevelLogDao;
import com.thinkgem.jeesite.modules.user.entity.UserLevelLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户升级明细表Service
 * @author luojie
 * @version 2017-04-25
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserLevelLogService extends CrudService<UserLevelLogDao, UserLevelLog> {
	@Autowired
	private UserLevelService userLevelService;

	public UserLevelLog get(String id) {
		return super.get(id);
	}
	
	public List<UserLevelLog> findList(UserLevelLog userLevelLog) {
		return super.findList(userLevelLog);
	}

	public Page<UserLevelLog> findPage(Page<UserLevelLog> page, UserLevelLog userLevelLog) {

		return super.findPage(page, userLevelLog);
	}
	public UserLevelLog contPerformance(UserLevelLog userLevelLog) {

		return dao.contPerformance(userLevelLog);
	}
}