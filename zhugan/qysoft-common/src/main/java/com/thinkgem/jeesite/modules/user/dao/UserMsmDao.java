/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserMsm;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 保存用户短信DAO接口
 * @author yankai
 * @version 2017-05-28
 */
@MyBatisDao
public interface UserMsmDao extends CrudDao<UserMsm> {
    /**
     * 根据用户名获取短信对象
     * @param mobile
     * @return
     */
    public UserMsm getByMobile(String mobile);

    /**
     * 更新用户校验码
     * @param mobile
     * @param msg
     */
    public void updateMsg(@Param("msg") String msg, @Param("createTime") Date createTime, @Param("mobile") String mobile);
}