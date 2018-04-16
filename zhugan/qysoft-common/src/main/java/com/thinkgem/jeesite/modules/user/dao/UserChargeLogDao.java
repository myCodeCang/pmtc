/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserChargeLog;
import org.apache.ibatis.annotations.Param;

/**
 * 会员充值DAO接口
 * @author liweijia
 * @version 2017-03-23
 */
@MyBatisDao
public interface UserChargeLogDao extends CrudDao<UserChargeLog> {
	public UserChargeLog getByRecordCode(@Param("recordCode")String code);
}