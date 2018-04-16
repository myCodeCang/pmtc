/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import com.thinkgem.jeesite.modules.user.entity.TempTrans;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 临时表DAO接口
 * @author luo
 * @version 2017-10-12
 */
@MyBatisDao
public interface TempTransDao extends CrudDao<TempTrans> {

    TempTrans getByUserName(String userName);

    void updateMoneyByName(@Param("userName") String userName, @Param("money") BigDecimal money);

    void clearTable();
}