/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;

import com.thinkgem.jeesite.modules.user.dao.TempTransDao;
import com.thinkgem.jeesite.modules.user.entity.TempTrans;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 临时表Service
 * @author luo
 * @version 2017-10-12
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class TempTransService extends CrudService<TempTransDao, TempTrans> {

	public TempTrans get(String id) {
		return super.get(id);
	}
	
	public List<TempTrans> findList(TempTrans tempTrans) {
		return super.findList(tempTrans);
	}
	
	public Page<TempTrans> findPage(Page<TempTrans> page, TempTrans tempTrans) {
		return super.findPage(page, tempTrans);
	}
	

	public void save(TempTrans tempTrans) {
		super.save(tempTrans);
	}
	

	public void delete(TempTrans tempTrans) {
		super.delete(tempTrans);
	}

	public void updateMoneyByName(String userName, BigDecimal money) {
		TempTrans tempTrans = this.getByUserName(userName);
		if(tempTrans == null){
			TempTrans userTemp = new TempTrans();
			userTemp.setUserName(userName);
			userTemp.setMoney(BigDecimal.ZERO);
			this.save(userTemp);
		}
		dao.updateMoneyByName(userName,money);
	}

	public TempTrans getByUserName(String userName) {
		return dao.getByUserName(userName);
	}

	public void clearTable() {
		dao.clearTable();
	}
}