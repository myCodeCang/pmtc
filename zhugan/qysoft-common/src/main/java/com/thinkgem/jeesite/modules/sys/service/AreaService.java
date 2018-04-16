/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) throws ValidationException {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) throws ValidationException{
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}

	/**
	 * 通过传入地区id拼接地区的全称
	 * @param id
	 * @return
	 */
	public StringBuilder getAreaById(String id, StringBuilder name){
		if(StringUtils2.isNotBlank(id)){
			name.insert(0, dao.get(id).getName());
			//name = dao.get(id).getName()+name;
		}
		Area area = dao.get(id);
		if(!"0".equals(area.getParentId())){
			getAreaById(area.getParentId(),name);
		}
		return name;
	}
	public List<Area> findListByPid(String pid) {
		return dao.findListByPid(pid);
	}

	public boolean pIdContainzId(String pId ,String zId){
		if(pId.equals(zId)){
			return true;
		}
		Area zArea = dao.get(zId);
		if(zArea.getParentIds().indexOf(pId)>-1){
			return true;
		}else{
			return false;
		}
	}
}
