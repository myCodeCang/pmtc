/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopCategory;

/**
 * 商品分类DAO接口
 * @author wyxiang
 * @version 2017-05-04
 */
@MyBatisDao
public interface ShopCategoryDao extends CrudDao<ShopCategory> {
	
}