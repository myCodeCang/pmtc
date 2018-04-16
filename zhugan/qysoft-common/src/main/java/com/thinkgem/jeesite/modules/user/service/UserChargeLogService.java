/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.dao.UserChargeLogDao;
import com.thinkgem.jeesite.modules.user.entity.UserAccountchange;
import com.thinkgem.jeesite.modules.user.entity.UserChargeLog;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;

/**
 * 会员充值Service
 * @author liweijia
 * @version 2017-03-23
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserChargeLogService extends CrudService<UserChargeLogDao, UserChargeLog> {

	
	public UserChargeLog get(String id) {
		return super.get(id);
	}
	
	public List<UserChargeLog> findList(UserChargeLog userCharge) {
		return super.findList(userCharge);
	}
	
	public Page<UserChargeLog> findPage(Page<UserChargeLog> page, UserChargeLog userCharge) {
		return super.findPage(page, userCharge);
	}

	public UserChargeLog getByRecordCode(String code) {
		return dao.getByRecordCode(code);
	}
}