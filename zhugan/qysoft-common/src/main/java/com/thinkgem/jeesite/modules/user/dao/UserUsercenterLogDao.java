/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserUsercenterLog;

/**
 * 报单中心审核DAO接口
 * @author cai
 * @version 2017-04-25
 */
@MyBatisDao
public interface UserUsercenterLogDao extends CrudDao<UserUsercenterLog> {

	public void updateCenterLogStatus(String id, String status);
	
}