/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserPhoto;

/**
 * 图片DAO接口
 * @author luojie
 * @version 2017-06-27
 */
@MyBatisDao
public interface UserPhotoDao extends CrudDao<UserPhoto> {
	
}