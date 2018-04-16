/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.math.BigDecimal;
import java.util.List;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.UserCharge;
import com.thinkgem.jeesite.modules.user.dao.UserChargeDao;

import javax.annotation.Resource;

/**
 * 汇款审核Service
 * @author wyxiang
 * @version 2017-04-30
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserChargeService extends CrudService<UserChargeDao, UserCharge> {

	@Resource
	private UserUserinfoService userUserinfoService;

	public UserCharge get(String id) {
		return super.get(id);
	}
	
	public List<UserCharge> findList(UserCharge userCharge) {
		return super.findList(userCharge);
	}
	
	public Page<UserCharge> findPage(Page<UserCharge> page, UserCharge userCharge) {
		return super.findPage(page, userCharge);
	}
	

	public void save(UserCharge userCharge) throws ValidationException {
		super.save(userCharge);
	}
	


    public void updateStatus(UserCharge userCharge, String status, String message) throws ValidationException {

		if(!userCharge.getStatus ().equals (EnumUtil.CheckType.uncheck.toString())){
			throw new ValidationException ("已审核过,不要重复审核");
		}
		userCharge.setCommt(message);
		if(status.equals(EnumUtil.YesNO.NO.toString())){
			userCharge.setStatus(EnumUtil.CheckType.failed.toString());
		}
		else{
			userCharge.setStatus(EnumUtil.CheckType.success.toString());
		}
		dao.updateStatus(userCharge);
		if( status.equals(EnumUtil.CheckType.success.toString())){
			userUserinfoService.updateUserMoney(userCharge.getUserName(), new BigDecimal(userCharge.getChangeMoney()), message, EnumUtil.MoneyChangeType.WIRE_TRANSFERS);
		}

    }
}