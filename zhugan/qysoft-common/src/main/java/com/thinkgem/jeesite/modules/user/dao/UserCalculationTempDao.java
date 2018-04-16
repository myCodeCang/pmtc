/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserCalculationTemp;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 董事奖发放DAO接口
 * @author kevin
 * @version 2017-06-22
 */
@MyBatisDao
public interface UserCalculationTempDao extends CrudDao<UserCalculationTemp> {

    public void clearUserCalculationTemp();

    public void updateCalculationTempByType(@Param ("userName") String userName , @Param("reportType") String reportType, @Param ("money") BigDecimal money);

}