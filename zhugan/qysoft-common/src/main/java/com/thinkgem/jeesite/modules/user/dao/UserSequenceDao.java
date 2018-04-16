/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserSequence;

/**
 * 序列管理DAO接口
 * @author wyxiang
 * @version 2017-04-30
 */
@MyBatisDao
public interface UserSequenceDao extends CrudDao<UserSequence> {
    public String getNextSequence(String seqName);
    public String getCurrSequence(String seqName);

}