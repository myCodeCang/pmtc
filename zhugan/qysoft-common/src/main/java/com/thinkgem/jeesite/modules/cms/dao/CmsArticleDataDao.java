/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cms.entity.CmsArticleData;

/**
 * 文章详情DAO接口
 * @author luo
 * @version 2017-05-19
 */
@MyBatisDao
public interface CmsArticleDataDao extends CrudDao<CmsArticleData> {
	
}