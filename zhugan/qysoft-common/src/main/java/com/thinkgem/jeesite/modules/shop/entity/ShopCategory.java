/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 商品分类Entity
 * @author wyxiang
 * @version 2017-05-04
 */
public class ShopCategory extends DataEntity<ShopCategory> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String uniacid;		// uniacid
	private String name;		// name
	private String thumb;		// thumb
	private String parentid;		// parentid
	private String isrecommand;		// isrecommand
	private String description;		// description
	private String displayorder;		// displayorder
	private String enabled;		// enabled
	private String ishome;		// ishome
	private String level;		// level
	private String advimg;		// advimg
	private String advurl;		// advurl
	
	public ShopCategory() {
		super();
	}

	public ShopCategory(String id){
		super(id);
	}

	@Length(min=0, max=11, message="uniacid长度必须介于 0 和 11 之间")
	public String getUniacid() {
		return uniacid;
	}

	public void setUniacid(String uniacid) {
		this.uniacid = uniacid;
	}
	
	@Length(min=0, max=50, message="name长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="thumb长度必须介于 0 和 255 之间")
	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	
	@Length(min=0, max=11, message="parentid长度必须介于 0 和 11 之间")
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	@Length(min=0, max=10, message="isrecommand长度必须介于 0 和 10 之间")
	public String getIsrecommand() {
		return isrecommand;
	}

	public void setIsrecommand(String isrecommand) {
		this.isrecommand = isrecommand;
	}
	
	@Length(min=0, max=500, message="description长度必须介于 0 和 500 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=3, message="displayorder长度必须介于 0 和 3 之间")
	public String getDisplayorder() {
		return displayorder;
	}

	public void setDisplayorder(String displayorder) {
		this.displayorder = displayorder;
	}
	
	@Length(min=0, max=1, message="enabled长度必须介于 0 和 1 之间")
	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	@Length(min=0, max=3, message="ishome长度必须介于 0 和 3 之间")
	public String getIshome() {
		return ishome;
	}

	public void setIshome(String ishome) {
		this.ishome = ishome;
	}
	
	@Length(min=0, max=3, message="level长度必须介于 0 和 3 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=255, message="advimg长度必须介于 0 和 255 之间")
	public String getAdvimg() {
		return advimg;
	}

	public void setAdvimg(String advimg) {
		this.advimg = advimg;
	}
	
	@Length(min=0, max=500, message="advurl长度必须介于 0 和 500 之间")
	public String getAdvurl() {
		return advurl;
	}

	public void setAdvurl(String advurl) {
		this.advurl = advurl;
	}
	
	

	
}