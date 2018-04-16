/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.util.List;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.modules.user.dao.UserBankChargeDao;
import com.thinkgem.jeesite.modules.user.dao.UserBankWithdrawDao;
import com.thinkgem.jeesite.modules.user.entity.UserBankCharge;
import com.thinkgem.jeesite.modules.user.entity.UserBankWithdraw;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.UserBankCommon;
import com.thinkgem.jeesite.modules.user.dao.UserBankCommonDao;

import javax.annotation.Resource;

/**
 * 银行信息Service
 * @author kevin
 * @version 2017-03-23
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserBankCommonService extends CrudService<UserBankCommonDao, UserBankCommon> {
	@Resource
	private UserBankWithdrawDao userBankWithdrawDao;
	@Resource
	private UserBankChargeDao userBankChargeDao;

	public UserBankCommon get(String id) {
		return super.get(id);
	}
	
	public List<UserBankCommon> findList(UserBankCommon userBankCommon) {
		return super.findList(userBankCommon);
	}
	
	public Page<UserBankCommon> findPage(Page<UserBankCommon> page, UserBankCommon userBankCommon) {
		return super.findPage(page, userBankCommon);
	}
	

	public void save(UserBankCommon userBankCommon) throws ValidationException {
		UserBankCommon bankCommon = getByBankCode (userBankCommon.getBankCode ());
		if(bankCommon !=null){
			if(!bankCommon.getId ().equals (userBankCommon.getId ())){
				throw new ValidationException("该银行代码已绑定,请勿重复绑定");
			}
		}
		super.save(userBankCommon);
	}
	

	public void delete(UserBankCommon userBankCommon) throws ValidationException{
		UserBankWithdraw userBankWithdraw = userBankWithdrawDao.getByBankCode(userBankCommon.getBankCode());
		UserBankCharge userBankCharge = userBankChargeDao.getByBankCode(userBankCommon.getBankCode());
		if(userBankWithdraw != null || userBankCharge != null){
			throw new ValidationException("该银行已被其他银行绑定,不能删除");
		}
		super.delete(userBankCommon);
	}

	/**
	 * 通过bankcode获取银行信息
	 * @param bankCode
	 * @return
	 */
    public  UserBankCommon getByBankCode(String bankCode) {
		return  dao.getByBankCode(bankCode);
    }
}