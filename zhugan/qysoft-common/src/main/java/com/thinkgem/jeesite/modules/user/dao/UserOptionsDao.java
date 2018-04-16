/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserOptions;

/**
 * 常规配置DAO接口
 * 
 * @author wyxiang
 * @version 2017-04-26
 */
@MyBatisDao
public interface UserOptionsDao extends CrudDao<UserOptions> {

	public UserOptions getByOptionName(String optName);

	public List<UserOptions> getByGroupName(String groupName);

}