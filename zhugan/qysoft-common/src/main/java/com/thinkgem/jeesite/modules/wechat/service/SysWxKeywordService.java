/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wechat.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wechat.dao.SysWxKeywordDao;
import com.thinkgem.jeesite.modules.wechat.entity.SysWxKeyword;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 微信关键字回复Service
 * @author kevin
 * @version 2017-07-30
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class SysWxKeywordService extends CrudService<SysWxKeywordDao, SysWxKeyword> {

	public SysWxKeyword get(String id) {
		return super.get(id);
	}
	
	public List<SysWxKeyword> findList(SysWxKeyword sysWxKeyword) {
		return super.findList(sysWxKeyword);
	}
	
	public Page<SysWxKeyword> findPage(Page<SysWxKeyword> page, SysWxKeyword sysWxKeyword) {
		return super.findPage(page, sysWxKeyword);
	}

	public SysWxKeyword findByKey(String keyword) {
		return dao.findByKey(keyword);
	}
	

	public void save(SysWxKeyword sysWxKeyword) {
		super.save(sysWxKeyword);
	}
	

	public void delete(SysWxKeyword sysWxKeyword) {
		super.delete(sysWxKeyword);
	}
	
}