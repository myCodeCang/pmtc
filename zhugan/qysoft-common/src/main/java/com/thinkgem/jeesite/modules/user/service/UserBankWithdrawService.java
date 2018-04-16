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
import com.thinkgem.jeesite.modules.user.entity.UserBankWithdraw;
import com.thinkgem.jeesite.modules.user.dao.UserBankWithdrawDao;

/**
 * 提款银行Service
 * @author cai
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserBankWithdrawService extends CrudService<UserBankWithdrawDao, UserBankWithdraw> {

	public UserBankWithdraw get(String id) {
		return super.get(id);
	}
	
	public List<UserBankWithdraw> findList(UserBankWithdraw userBankWithdraw) {
		return super.findList(userBankWithdraw);
	}
	
	public Page<UserBankWithdraw> findPage(Page<UserBankWithdraw> page, UserBankWithdraw userBankWithdraw) {
		return super.findPage(page, userBankWithdraw);
	}
	

	public void save(UserBankWithdraw userBankWithdraw) throws ValidationException {
		UserBankWithdraw withdraw = getByBankCode(userBankWithdraw.getBankCode ());
		if(withdraw!=null){
			if(!withdraw.getId ().equals (userBankWithdraw.getId ())){
					throw new ValidationException("该银行代码已绑定,请勿重复绑定");
			}
		}
		super.save(userBankWithdraw);
	}
	

	public void delete(UserBankWithdraw userBankWithdraw) throws ValidationException {
		super.delete(userBankWithdraw);
	}

    public UserBankWithdraw getByBankCode(String bankCode) {
		return  dao.getByBankCode(bankCode);
    }
}