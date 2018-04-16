/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.dao.UserSequenceDao;
import com.thinkgem.jeesite.modules.user.entity.UserSequence;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员充值Service
 * @author liweijia
 * @version 2017-03-23
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.NOT_SUPPORTED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserSequenceService extends CrudService<UserSequenceDao, UserSequence> {

	@Transactional(isolation = Isolation.READ_COMMITTED,readOnly = false,propagation= Propagation.NOT_SUPPORTED)
	public String getNextSequence(String seqName){
		String seqid =  dao.getNextSequence(seqName);
		logger.debug("eqquence["+seqName+"]:"+seqid);
		return  seqid;

	}
	@Transactional(isolation = Isolation.READ_COMMITTED,readOnly = false,propagation= Propagation.NOT_SUPPORTED)
	public String getCurrSequence(String seqName){
		return  dao.getCurrSequence(seqName);
	}
	
}