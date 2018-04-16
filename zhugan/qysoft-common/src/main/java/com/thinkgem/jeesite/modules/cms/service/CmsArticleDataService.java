/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.service;

import java.util.List;

import com.thinkgem.jeesite.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cms.entity.CmsArticleData;
import com.thinkgem.jeesite.modules.cms.dao.CmsArticleDataDao;

/**
 * 文章详情Service
 * @author luo
 * @version 2017-05-19
 */
@Service
@Transactional(readOnly = true)
public class CmsArticleDataService extends CrudService<CmsArticleDataDao, CmsArticleData> {

	public CmsArticleData get(String id) {
		return super.get(id);
	}
	
	public List<CmsArticleData> findList(CmsArticleData cmsArticleData) {
		return super.findList(cmsArticleData);
	}
	
	public Page<CmsArticleData> findPage(Page<CmsArticleData> page, CmsArticleData cmsArticleData) {
		return super.findPage(page, cmsArticleData);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsArticleData cmsArticleData) throws ValidationException {
		super.save(cmsArticleData);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsArticleData cmsArticleData)throws ValidationException {
		super.delete(cmsArticleData);
	}
	
}