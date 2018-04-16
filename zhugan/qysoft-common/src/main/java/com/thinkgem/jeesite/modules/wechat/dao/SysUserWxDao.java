/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wechat.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wechat.entity.SysUserWx;
import org.apache.ibatis.annotations.Param;

/**
 * 微信用户关联DAO接口
 * @author kevin
 * @version 2017-07-30
 */
@MyBatisDao
public interface SysUserWxDao extends CrudDao<SysUserWx> {
	public SysUserWx findByOpenId(@Param("openId") String openId);

	public void changeSubscribeStatus(SysUserWx sysUserWx);
}