/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.user.entity.UserReport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.UserMoneyReport;
import com.thinkgem.jeesite.modules.user.dao.UserMoneyReportDao;

/**
 * 舆情分析Service
 * @author luo
 * @version 2018-03-13
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserMoneyReportService extends CrudService<UserMoneyReportDao, UserMoneyReport> {

	public UserMoneyReport get(String id) {
		return super.get(id);
	}
	
	public List<UserMoneyReport> findList(UserMoneyReport userMoneyReport) {
		return super.findList(userMoneyReport);
	}
	
	public Page<UserMoneyReport> findPage(Page<UserMoneyReport> page, UserMoneyReport userMoneyReport) {
		return super.findPage(page, userMoneyReport);
	}
	

	public void save(UserMoneyReport userMoneyReport) {
		super.save(userMoneyReport);
	}
	

	public void delete(UserMoneyReport userMoneyReport) {
		super.delete(userMoneyReport);
	}

	public void updateUserMoneyReport(String userName, String reportType, BigDecimal money) {
		UserMoneyReport userReport = new UserMoneyReport();
		userReport.setUserName(userName);
		List<UserMoneyReport> reportList = this.findList(userReport);
		if (reportList.size() == 0) {
			UserMoneyReport currReport = new UserMoneyReport();
			currReport.setUserName(userName);
			this.save(currReport);
			reportList = this.findList(userReport);
		}
		dao.updateMoneyReportByType(reportList.get(0).getId(), reportType, money);
	}
	
}