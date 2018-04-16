/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.TransPriceDaylog;

import java.util.List;

/**
 * 物品价格记录表DAO接口
 * @author luo
 * @version 2018-02-10
 */
@MyBatisDao
public interface TransPriceDaylogDao extends CrudDao<TransPriceDaylog> {

    List<TransPriceDaylog> findWeekData();

    List<TransPriceDaylog> findMonthData();
}