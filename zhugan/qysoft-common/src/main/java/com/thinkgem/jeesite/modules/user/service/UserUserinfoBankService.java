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
import com.thinkgem.jeesite.modules.user.entity.UserUserinfoBank;
import com.thinkgem.jeesite.modules.user.dao.UserUserinfoBankDao;

/**
 * 个人银行卡Service
 * @author luo
 * @version 2017-05-12
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserUserinfoBankService extends CrudService<UserUserinfoBankDao, UserUserinfoBank> {

	public UserUserinfoBank get(String id) {
		return super.get(id);
	}
	
	public List<UserUserinfoBank> findList(UserUserinfoBank userUserinfoBank) {
		return super.findList(userUserinfoBank);
	}
	
	public Page<UserUserinfoBank> findPage(Page<UserUserinfoBank> page, UserUserinfoBank userUserinfoBank) {
		return super.findPage(page, userUserinfoBank);
	}
	

	public void save(UserUserinfoBank userUserinfoBank) throws ValidationException {
		UserUserinfoBank selectBank = this.getByBankUserNum(userUserinfoBank.getUserName(),userUserinfoBank.getBankUserNum());
		if(selectBank !=null) {
			throw new ValidationException("请不要绑定相同的银行卡");
		}
		super.save(userUserinfoBank);
	}
//	public void byBankIdUpdateBankInfo(UserUserinfoBank userUserinfoBank) throws ValidationException {
//		UserUserinfoBank selectBank = this.get(userUserinfoBank.getId());
//		if(selectBank ==null) {
//			throw new ValidationException("服务器正忙,请稍后再试!");
//		}
//		dao.byBankIdUpdateBankInfo(userUserinfoBank);
//	}
	public UserUserinfoBank getByBankUserNum(String userName, String bankUserNum) {
		return dao.getByBankUserNum(userName,bankUserNum);
	}

	public void delete(UserUserinfoBank userUserinfoBank) throws ValidationException{
		super.delete(userUserinfoBank);
	}


	public UserUserinfoBank getByUserName(String userName) {
		return dao.getByUserName(userName);
	}

}