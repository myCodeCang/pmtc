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
import com.thinkgem.jeesite.modules.user.entity.UserBankCharge;
import com.thinkgem.jeesite.modules.user.dao.UserBankChargeDao;

/**
 * 充值银行信息Service
 * @author cai
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserBankChargeService extends CrudService<UserBankChargeDao, UserBankCharge> {

	public UserBankCharge get(String id) {
		return super.get(id);
	}
	
	public List<UserBankCharge> findList(UserBankCharge userBankCharge) {
		return super.findList(userBankCharge);
	}
	
	public Page<UserBankCharge> findPage(Page<UserBankCharge> page, UserBankCharge userBankCharge) {
		return super.findPage(page, userBankCharge);
	}
	

	public void save(UserBankCharge userBankCharge) throws ValidationException {
		UserBankCharge bankCharge = dao.getByBankCode (userBankCharge.getBankCode ());
		if(bankCharge!=null){
			if(!bankCharge.getId ().equals (userBankCharge.getId ())){
				throw new ValidationException("该银行代码已绑定,请勿重复绑定");
			}
		}
		super.save(userBankCharge);
	}
	

	public void delete(UserBankCharge userBankCharge) throws ValidationException{
		super.delete(userBankCharge);
	}

	
}