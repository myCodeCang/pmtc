/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.SystemReport;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 平台统计DAO接口
 * @author luo
 * @version 2018-04-02
 */
@MyBatisDao
public interface SystemReportDao extends CrudDao<SystemReport> {

    void updateSystemReportByType(@Param("id") String id,@Param("reportType") String reportType,@Param("money") BigDecimal money);
}