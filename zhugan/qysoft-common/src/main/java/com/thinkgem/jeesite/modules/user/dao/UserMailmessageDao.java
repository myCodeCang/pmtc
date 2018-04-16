/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserMailmessage;

/**
 * 站内信DAO接口
 * @author cais
 * @version 2017-04-26
 */
@MyBatisDao
public interface UserMailmessageDao extends CrudDao<UserMailmessage> {
	

}