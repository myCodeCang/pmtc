/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserBankWithdraw;

/**
 * 提款银行DAO接口
 * @author cai
 * @version 2017-03-24
 */
@MyBatisDao
public interface UserBankWithdrawDao extends CrudDao<UserBankWithdraw> {
    public UserBankWithdraw getByBankCode(String bankCode);
}