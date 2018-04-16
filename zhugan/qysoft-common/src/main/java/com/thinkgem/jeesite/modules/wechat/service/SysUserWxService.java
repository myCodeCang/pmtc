/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wechat.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wechat.dao.SysUserWxDao;
import com.thinkgem.jeesite.modules.wechat.entity.SysUserWx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 微信用户关联Service
 * @author kevin
 * @version 2017-07-30
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class SysUserWxService extends CrudService<SysUserWxDao, SysUserWx> {

	public SysUserWx get(String id) {
		return super.get(id);
	}
	
	public List<SysUserWx> findList(SysUserWx sysUserWx) {
		return super.findList(sysUserWx);
	}
	
	public Page<SysUserWx> findPage(Page<SysUserWx> page, SysUserWx sysUserWx) {
		return super.findPage(page, sysUserWx);
	}
	

	public void save(SysUserWx sysUserWx) {
		super.save(sysUserWx);
	}
	

	public void delete(SysUserWx sysUserWx) {
		super.delete(sysUserWx);
	}

	public SysUserWx findByOpenId(String openId) {
		return dao.findByOpenId(openId);
	}

	public void changeSubscribeStatus(SysUserWx sysUserWx) {
		if (sysUserWx == null) {
			return;
		}
		dao.changeSubscribeStatus(sysUserWx);
	}
}