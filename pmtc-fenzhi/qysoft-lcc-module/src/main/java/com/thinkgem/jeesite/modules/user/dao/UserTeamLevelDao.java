/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserTeamLevel;

/**
 * 团队等级DAO接口
 * @author 薛玉良
 * @version 2017-08-11
 */
@MyBatisDao
public interface UserTeamLevelDao extends CrudDao<UserTeamLevel> {

public UserTeamLevel getTeamNameByTeamCode(String teamCode);
}