/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.TransPriceDaylog;
import com.thinkgem.jeesite.modules.user.dao.TransPriceDaylogDao;

/**
 * 物品价格记录表Service
 * @author luo
 * @version 2018-02-10
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class TransPriceDaylogService extends CrudService<TransPriceDaylogDao, TransPriceDaylog> {

	public TransPriceDaylog get(String id) {
		return super.get(id);
	}
	
	public List<TransPriceDaylog> findList(TransPriceDaylog transPriceDaylog) {
		return super.findList(transPriceDaylog);
	}
	
	public Page<TransPriceDaylog> findPage(Page<TransPriceDaylog> page, TransPriceDaylog transPriceDaylog) {
		return super.findPage(page, transPriceDaylog);
	}
	

	public void save(TransPriceDaylog transPriceDaylog) {
		super.save(transPriceDaylog);
	}
	

	public void delete(TransPriceDaylog transPriceDaylog) {
		super.delete(transPriceDaylog);
	}

	public List<TransPriceDaylog> findWeekData(){
		return dao.findWeekData();
	}

	public List<TransPriceDaylog> findMonthData(){
		return dao.findMonthData();
	}
	
}