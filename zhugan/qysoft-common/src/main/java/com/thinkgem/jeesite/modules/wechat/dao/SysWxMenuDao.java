/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wechat.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wechat.entity.SysWxMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 微信自定义菜单DAO接口
 * @author kevin
 * @version 2017-07-29
 */
@MyBatisDao
public interface SysWxMenuDao extends TreeDao<SysWxMenu> {
    public List<SysWxMenu> findByParentId(@Param("parentId") String parentId);

    public int subMenuNum(@Param("parentId") String parentId);
}