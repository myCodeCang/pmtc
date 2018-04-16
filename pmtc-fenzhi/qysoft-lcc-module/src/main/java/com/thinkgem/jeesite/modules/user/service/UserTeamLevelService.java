/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.dao.UserTeamLevelDao;
import com.thinkgem.jeesite.modules.user.entity.UserTeamLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 团队等级Service
 * @author 薛玉良
 * @version 2017-08-11
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserTeamLevelService extends CrudService<UserTeamLevelDao, UserTeamLevel> {

	public UserTeamLevel get(String id) {
		return super.get(id);
	}
	
	public List<UserTeamLevel> findList(UserTeamLevel userTeamLevel) {
		return super.findList(userTeamLevel);
	}
	
	public Page<UserTeamLevel> findPage(Page<UserTeamLevel> page, UserTeamLevel userTeamLevel) {
		return super.findPage(page, userTeamLevel);
	}
	

	public void save(UserTeamLevel userTeamLevel) {
		super.save(userTeamLevel);
	}
	

	public void delete(UserTeamLevel userTeamLevel) {
		super.delete(userTeamLevel);
	}

	public UserTeamLevel getTeamNameByTeamCode(String teamCode) {
		return	dao.getTeamNameByTeamCode(teamCode);
	}
}