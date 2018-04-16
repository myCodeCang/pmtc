/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.TranscodePriceDaylog;
import com.thinkgem.jeesite.modules.user.service.TranscodePriceDaylogService;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


import java.util.Date;

/**
 * k线走势DAO接口
 * @author wyxiang
 * @version 2018-03-20
 */
@MyBatisDao
public interface TranscodePriceDaylogDao extends CrudDao<TranscodePriceDaylog> {

    TranscodePriceDaylog getNowLog();
	List<TranscodePriceDaylog> findWeekData();

    List<TranscodePriceDaylog> findMonthData();
    void updateAmount(@Param("id") String id, @Param("amount") BigDecimal amount);

    List<TranscodePriceDaylog> findByCreateDate(Date date);
}