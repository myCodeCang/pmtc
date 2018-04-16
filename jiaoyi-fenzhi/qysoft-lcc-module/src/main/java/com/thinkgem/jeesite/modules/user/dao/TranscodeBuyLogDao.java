/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.TranscodeBuyLog;

/**
 * 撮合成功记录DAO接口
 * @author wyxiang
 * @version 2018-03-20
 */
@MyBatisDao
public interface TranscodeBuyLogDao extends CrudDao<TranscodeBuyLog> {

    TranscodeBuyLog getLock(String recordId);
}