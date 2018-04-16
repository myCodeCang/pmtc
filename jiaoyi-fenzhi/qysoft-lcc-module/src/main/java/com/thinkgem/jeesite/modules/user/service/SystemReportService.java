/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.dao.UserAccountchangeDao;
import com.thinkgem.jeesite.modules.user.dao.SystemReportDao;
import com.thinkgem.jeesite.modules.user.entity.UserAccountchange;
import com.thinkgem.jeesite.modules.user.entity.SystemReport;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户奖金Service
 *
 * @author xueyuliang
 * @version 2017-04-25
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackForClassName = {"RuntimeException", "Exception", "ValidationException"})
public class SystemReportService extends CrudService<SystemReportDao, SystemReport> {

    @Resource
    private UserUserinfoService userUserinfoService;

    @Resource
    private UserAccountchangeDao userAccountchangeDao;

    public SystemReport get(String id) {
        return super.get(id);
    }

    public List<SystemReport> findList(SystemReport userReport) {
        return super.findList(userReport);
    }


    public Page<SystemReport> findPage(Page<SystemReport> page, SystemReport userReport) {
        return super.findPage(page, userReport);
    }


    public void save(SystemReport userReport) throws ValidationException {
        super.save(userReport);
    }


    public void delete(SystemReport userReport) throws ValidationException {
        super.delete(userReport);
    }

    public void updateSystemReportByType(String userName, String reportType, BigDecimal money) {
        SystemReport userReport = new SystemReport();
        userReport.setCreateDate(new Date());
        userReport.setUserName(userName);
        List<SystemReport> reportList = this.findList(userReport);
        if (reportList.size() == 0) {
            SystemReport currReport = new SystemReport();
            currReport.setUserName(userName);
            this.save(currReport);
            reportList = this.findList(userReport);
        }
        dao.updateSystemReportByType(reportList.get(0).getId(), reportType, money);
    }

    public void  replaceSystemReportByType(String userName, String reportType, BigDecimal money) {
        SystemReport userReport = new SystemReport();
        userReport.setCreateDate(new Date());
        userReport.setUserName(userName);
        List<SystemReport> reportList = this.findList(userReport);
        if (reportList.size() == 0) {
            SystemReport currReport = new SystemReport();
            currReport.setUserName(userName);
            this.save(currReport);
            reportList = this.findList(userReport);
        }
        dao.replaceSystemReportByType(reportList.get(0).getId(), reportType, money);
    }


    public SystemReport getOrganReport(SystemReport userReport) {

        return dao.getOrganReport(userReport);
    }

    public SystemReport getTeamReport(SystemReport userReport) {
        return dao.getTeamReport(userReport);
    }


    public SystemReport sumSystemReport(SystemReport userReport) {

        return dao.sumSystemReport(userReport);
    }

    public List<SystemReport> findQuarterList(String order, int topLimit, Date startDate, Date lastDate) {
        return dao.findQuarterList(order,topLimit,startDate,lastDate);

    }

    public List<SystemReport> findListSum(SystemReport userReport) {

        return dao.findListSum(userReport);
    }

    //孵化统计每天产币
    public Page<SystemReport> sumFuHuaMoney(Page<SystemReport> page, SystemReport userReport) {
        userReport.setPage(page);
        page.setList(dao.sumFuHuaMoney(userReport));
        return page;
    }
}