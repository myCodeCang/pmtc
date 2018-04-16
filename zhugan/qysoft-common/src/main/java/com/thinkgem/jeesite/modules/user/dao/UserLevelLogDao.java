/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserLevelLog;

/**
 * 用户升级明细表DAO接口
 * @author luojie
 * @version 2017-04-25
 */
@MyBatisDao
public interface UserLevelLogDao extends CrudDao<UserLevelLog> {

    public UserLevelLog contPerformance(UserLevelLog userLevelLog);
}