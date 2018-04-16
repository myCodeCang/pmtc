/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wechat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.TreeEntity;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 微信自定义菜单Entity
 * @author kevin
 * @version 2017-07-29
 */
public class SysWxMenu extends TreeEntity<SysWxMenu> {
	
	private static final long serialVersionUID = 1L;
	private SysWxMenu parent;		// 父级菜单编号
	private String parentIds;		// parent_ids
	private String name;		// 菜单名称
	private String typeId;		// 菜单类型（0：跳转URL 1：点击事件）
	private String status;		// 菜单状态（0：隐藏  1：显示）
	private String url;		// 跳转url
	private String eventKey;		// 菜单事件key
	private String extend;		// 扩展菜单名称
	private Integer sort;		// 菜单排序
	
	public SysWxMenu() {
		super();
	}

	public SysWxMenu(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="父级菜单编号不能为空")
	public SysWxMenu getParent() {
		return parent;
	}

	public void setParent(SysWxMenu parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=2000, message="parent_ids长度必须介于 1 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=1, max=32, message="菜单名称长度必须介于 1 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=11, message="菜单类型（0：跳转URL 1：点击事件）长度必须介于 1 和 11 之间")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	@Length(min=1, max=1, message="菜单状态（0：隐藏  1：显示）长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="跳转url长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=255, message="菜单事件key长度必须介于 0 和 255 之间")
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
	@Length(min=0, max=255, message="扩展菜单名称长度必须介于 0 和 255 之间")
	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	@Override
	public void preInsert() throws ValidationException {
		validateModule(true);
		super.preInsert();
	}

	@Override
	public void preUpdate() throws ValidationException {
		validateModule(false);
		super.preUpdate();
	}

	@Override
	protected void validateModule(boolean isInsert) throws ValidationException {
		if (StringUtils2.isBlank(getName())) {
			throw new ValidationException("菜单名不能为空");
		}

		String typeId = getTypeId();
		if (!StringUtils2.ZERO.equals(typeId) && !StringUtils2.ONE.equals(typeId)) {
			throw new ValidationException("菜单类型只能为0（跳转URL）和1（点击事件）");
		}

		if (null == getSort()) {
			throw new ValidationException("排序不能为空");
		}

		if (StringUtils2.ZERO.equals(typeId)) {
			String url = getUrl();
			if (StringUtils2.isBlank(url)) {
				throw new ValidationException("跳转URL类型菜单URL地址不能为空");
			}

			if (!StringUtils2.startsWithIgnoreCase(url, "http://") &&
					!StringUtils2.startsWithIgnoreCase(url, "https://")) {
				throw new ValidationException("跳转URL必须以http://或https://开头");
			}
		} else {
			if (StringUtils2.isBlank(getEventKey())) {
				throw new ValidationException("事件点击类型菜单事件Key不能为空");
			}
		}
	}
}