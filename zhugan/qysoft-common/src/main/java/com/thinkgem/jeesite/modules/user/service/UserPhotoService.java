/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.UserPhoto;
import com.thinkgem.jeesite.modules.user.dao.UserPhotoDao;

/**
 * 图片Service
 * @author luojie
 * @version 2017-06-27
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserPhotoService extends CrudService<UserPhotoDao, UserPhoto> {

	public UserPhoto get(String id) {
		return super.get(id);
	}
	
	public List<UserPhoto> findList(UserPhoto userPhoto) {
		return super.findList(userPhoto);
	}
	
	public Page<UserPhoto> findPage(Page<UserPhoto> page, UserPhoto userPhoto) {
		return super.findPage(page, userPhoto);
	}
	

	public void save(UserPhoto userPhoto) {
		super.save(userPhoto);
	}
	

	public void delete(UserPhoto userPhoto) {
		super.delete(userPhoto);
	}
	
}