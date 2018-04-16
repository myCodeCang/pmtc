/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserAddress;

/**
 * 用户地址表DAO接口
 * @author luojie
 * @version 2017-05-08
 */
@MyBatisDao
public interface UserAddressDao extends CrudDao<UserAddress> {
	
}