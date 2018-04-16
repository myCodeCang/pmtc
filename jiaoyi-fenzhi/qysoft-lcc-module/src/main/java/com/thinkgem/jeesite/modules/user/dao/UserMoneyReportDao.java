/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserMoneyReport;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 舆情分析DAO接口
 * @author luo
 * @version 2018-03-13
 */
@MyBatisDao
public interface UserMoneyReportDao extends CrudDao<UserMoneyReport> {

    void updateMoneyReportByType(@Param("id") String id,@Param("reportType") String reportType,@Param("money") BigDecimal money);
}