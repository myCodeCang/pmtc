/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserBankCommon;

/**
 * 银行信息DAO接口
 * @author kevin
 * @version 2017-03-23
 */
@MyBatisDao
public interface UserBankCommonDao extends CrudDao<UserBankCommon> {
    public UserBankCommon getByBankCode(String bankCode);
}