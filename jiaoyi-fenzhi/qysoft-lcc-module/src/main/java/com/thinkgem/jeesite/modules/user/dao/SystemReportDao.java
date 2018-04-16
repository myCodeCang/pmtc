/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.SystemReport;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户奖金DAO接口
 * @author xueyuliang
 * @version 2017-04-25
 */
@MyBatisDao
public interface SystemReportDao extends CrudDao<SystemReport> {

     void updateSystemReportByType(@Param("id") String id, @Param("reportType") String reportType, @Param("money") BigDecimal money);

     SystemReport getOrganReport(SystemReport systemReport);

     SystemReport getTeamReport(SystemReport systemReport);

     SystemReport sumSystemReport(SystemReport systemReport);

    List<SystemReport> findQuarterList(@Param("order") String order, @Param("topLimit") int topLimit, @Param("startDate") Date startDate, @Param("lastDate") Date lastDate);

    List<SystemReport> findListSum(SystemReport systemReport);

    List<SystemReport> sumFuHuaMoney(SystemReport systemReport);

    void replaceSystemReportByType(@Param("id") String id, @Param("reportType") String reportType, @Param("money") BigDecimal money);
}