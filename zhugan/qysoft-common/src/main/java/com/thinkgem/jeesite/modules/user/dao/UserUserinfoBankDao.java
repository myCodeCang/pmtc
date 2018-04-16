/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfoBank;
import org.apache.ibatis.annotations.Param;

/**
 * 个人银行卡DAO接口
 * @author luo
 * @version 2017-05-12
 */
@MyBatisDao
public interface UserUserinfoBankDao extends CrudDao<UserUserinfoBank> {

   public UserUserinfoBank getByUserName(String userName);

    UserUserinfoBank getByBankUserNum(@Param("userName") String userName, @Param("bankUserNum") String bankUserNum);
    public void byBankIdUpdateBankInfo(UserUserinfoBank userUserinfoBank);

}