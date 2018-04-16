/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserCharge;

/**
 * 汇款审核DAO接口
 * @author wyxiang
 * @version 2017-04-30
 */
@MyBatisDao
public interface UserChargeDao extends CrudDao<UserCharge> {

    public   void updateStatus(UserCharge userCharge);
}