/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wechat.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wechat.entity.SysWxKeyword;
import org.apache.ibatis.annotations.Param;

/**
 * 微信关键字回复DAO接口
 * @author kevin
 * @version 2017-07-30
 */
@MyBatisDao
public interface SysWxKeywordDao extends CrudDao<SysWxKeyword> {
    public SysWxKeyword findByKey(@Param("keyword") String keyword);
}