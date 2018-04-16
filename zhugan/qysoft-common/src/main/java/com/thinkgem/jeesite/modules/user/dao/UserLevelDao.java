/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserLevel;

/**
 * 用户等级表DAO接口
 * @author cai
 * @version 2017-03-23
 */
@MyBatisDao
public interface UserLevelDao extends CrudDao<UserLevel> {
	

	/**
	 * 根据level_code查询userlevel
	 * @param levelCode
	 * @return
	 */
	public UserLevel findByLevalCode(String levelCode);
}