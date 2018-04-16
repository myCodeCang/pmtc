/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.ShopCommOrder;

/**
 * 通信记录DAO接口
 * @author xueyuliang
 * @version 2017-06-08
 */
@MyBatisDao
public interface ShopCommOrderDao extends CrudDao<ShopCommOrder> {
	
}