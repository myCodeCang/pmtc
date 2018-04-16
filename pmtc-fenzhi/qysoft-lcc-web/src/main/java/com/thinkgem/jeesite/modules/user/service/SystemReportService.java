/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.user.entity.UserReport;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.SystemReport;
import com.thinkgem.jeesite.modules.user.dao.SystemReportDao;

/**
 * 平台统计Service
 * @author luo
 * @version 2018-04-02
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class SystemReportService extends CrudService<SystemReportDao, SystemReport> {

	public SystemReport get(String id) {
		return super.get(id);
	}
	
	public List<SystemReport> findList(SystemReport systemReport) {
		return super.findList(systemReport);
	}
	
	public Page<SystemReport> findPage(Page<SystemReport> page, SystemReport systemReport) {
		return super.findPage(page, systemReport);
	}
	

	public void save(SystemReport systemReport) {
		super.save(systemReport);
	}
	

	public void delete(SystemReport systemReport) {
		super.delete(systemReport);
	}

	public void updateSystemReportByType(String reportType, BigDecimal money) {
		SystemReport systemReport = new SystemReport();
		systemReport.setCreateDate(new Date());
		List<SystemReport> reports = this.findList(systemReport);
		if(reports.size() == 0){
			this.save(systemReport);
			reports = this.findList(systemReport);
		}
		dao.updateSystemReportByType(reports.get(0).getId(), reportType, money);
	}

}