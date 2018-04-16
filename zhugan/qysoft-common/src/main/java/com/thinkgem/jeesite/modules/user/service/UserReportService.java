/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils2;
import com.thinkgem.jeesite.modules.user.dao.UserAccountchangeDao;
import com.thinkgem.jeesite.modules.user.dao.UserReportDao;
import com.thinkgem.jeesite.modules.user.entity.UserAccountchange;
import com.thinkgem.jeesite.modules.user.entity.UserReport;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * 用户奖金Service
 *
 * @author xueyuliang
 * @version 2017-04-25
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackForClassName = {"RuntimeException", "Exception", "ValidationException"})
public class UserReportService extends CrudService<UserReportDao, UserReport> {

    @Resource
    private UserUserinfoService userUserinfoService;

    @Resource
    private UserAccountchangeDao userAccountchangeDao;

    public UserReport get(String id) {
        return super.get(id);
    }

    public List<UserReport> findList(UserReport userReport) {
        return super.findList(userReport);
    }


    public Page<UserReport> findPage(Page<UserReport> page, UserReport userReport) {
        return super.findPage(page, userReport);
    }


    public void save(UserReport userReport) throws ValidationException {
        super.save(userReport);
    }


    public void delete(UserReport userReport) throws ValidationException {
        super.delete(userReport);
    }

    public void updateUserReportByType(String userName, String reportType, BigDecimal money) {
        UserReport userReport = new UserReport();
        userReport.setCreateDate(new Date());
        userReport.setUserName(userName);
        List<UserReport> reportList = this.findList(userReport);
        if (reportList.size() == 0) {
            UserReport currReport = new UserReport();
            currReport.setUserName(userName);
            this.save(currReport);
            reportList = this.findList(userReport);
        }
        dao.updateUserReportByType(reportList.get(0).getId(), reportType, money);
    }

    /*public void updateUserReportToDate(Map<String, List<UserAccountchange>> accountChanges) {
        accountChanges.forEach((userName, accountChangeList) -> {      //按用户名对帐变分组
            Map<String, List<UserAccountchange>> accountChangesByDate = accountChangeList.stream().collect(groupingBy(accountChange -> DateUtils2.formatDate(accountChange.getCreateDate(), "\"yyyy-MM-dd\"")));
            accountChangesByDate.forEach((dateStr, acListByDate) -> {   //按日期对帐变分组
                Map<String, List<UserAccountchange>> accountChangesByType = acListByDate.stream().collect(groupingBy(UserAccountchange::getChangeType));
                UserReport userReport = new UserReport();
                userReport.setUserName(userName);
                accountChangesByType.forEach((type, acByType) -> {      //按帐变类型分组
                    if (type.equals(EnumUtil.MoneyChangeType.charget.toString())) {

                    }
                    acByType.forEach(ac -> {
                        userReport.setCreateDate(ac.getCreateDate());

                    });
                });
            });
        });
    }*/

    public void updateUserReportToDate(String userName, String reportType, BigDecimal money, Date date) {
        if (money.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        //UserUserinfo userUserinfo = userUserinfoService.getByNameLock(userName);
        UserReport userReport = new UserReport();
        userReport.setCreateDate(date);
        userReport.setUserName(userName);
        List<UserReport> reportList = this.findList(userReport);
        if (reportList.size() == 0) {
            UserReport currReport = new UserReport();
            currReport.setUserName(userName);
            currReport.setCreateDate(date);
            this.save(currReport);
            reportList = this.findList(userReport);
        }
        dao.updateUserReportByType(reportList.get(0).getId(), reportType, money);
    }


    public void updateUserReportByDate(String userName, String reportType, BigDecimal money) {
        this.updateUserReportByDateAndLog(userName, reportType, money, "", EnumUtil.MoneyChangeType.none_log, EnumUtil.MoneyType.money.toString());
    }

    /**
     * 根据时间插入报表,并且插入用户日志
     *
     * @param userName
     * @param reportType
     * @param money
     */
    public void updateUserReportByDateAndLog(String userName, String reportType, BigDecimal money, String message, EnumUtil.MoneyChangeType changeType, String moneyType) {
        if (money.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        UserUserinfo userUserinfo = userUserinfoService.getByNameLock(userName);
        UserReport userReport = new UserReport();
        userReport.setCreateDateByDate(new Date());
        userReport.setUserName(userName);
        List<UserReport> reportList = this.findList(userReport);
        if (reportList.size() == 0) {
            UserReport currReport = new UserReport();
            currReport.setUserName(userName);
            currReport.setStatus("0");
            this.save(currReport);
            reportList = this.findList(userReport);
        }
        dao.updateUserReportByType(reportList.get(0).getId(), reportType, money);

        if (EnumUtil.MoneyChangeType.none_log != changeType) {
            //插入用户帐变
            UserAccountchange userAccountchange = new UserAccountchange();
            userAccountchange.setUserName(userUserinfo.getUserName());
            userAccountchange.setChangeMoney(money.toString());
            userAccountchange.setBeforeMoney(new BigDecimal(0));
            userAccountchange.setAfterMoney("0");
            userAccountchange.setCommt(message);
            userAccountchange.setStatus("0");
            userAccountchange.setChangeType(changeType.toString());
            userAccountchange.setMoneyType(moneyType);
            userAccountchange.preInsert();

            userAccountchangeDao.insert(userAccountchange);
        }

    }

    public UserReport getOrganReport(UserReport userReport) {

        return dao.getOrganReport(userReport);
    }

    public UserReport getTeamReport(UserReport userReport) {
        return dao.getTeamReport(userReport);
    }

    public UserReport countBonusReport(UserReport report) {
        report.setBuyBonus(report.getDividendEight().multiply(new BigDecimal(Global.getOption("system_trans", "buy_goods_bonus"))));
        report.setApplyBonus(report.getDividendSix().multiply(new BigDecimal(Global.getOption("system_trans", "apply_goods_bonus"))));
        report.setProcedureBonus(report.getDividendSeven().multiply(new BigDecimal(Global.getOption("system_trans", "procedure_money_bonus"))));
        report.setShopBonus((report.getDividendTen().add(report.getDividendNine())).multiply(new BigDecimal(Global.getOption("system_trans", "shop_bonus"))));
        return report;
    }

    public UserReport sumUserReport(UserReport userReport) {

        return dao.sumUserReport(userReport);
    }

    public List<UserReport> findQuarterList(String order, int topLimit, Date startDate, Date lastDate) {
        return dao.findQuarterList(order,topLimit,startDate,lastDate);

    }

    public List<UserReport> findListSum(UserReport userReport) {

        return dao.findListSum(userReport);
    }

    //孵化统计每天产币
    public Page<UserReport> sumFuHuaMoney(Page<UserReport> page, UserReport userReport) {
        userReport.setPage(page);
        page.setList(dao.sumFuHuaMoney(userReport));
        return page;
    }
}