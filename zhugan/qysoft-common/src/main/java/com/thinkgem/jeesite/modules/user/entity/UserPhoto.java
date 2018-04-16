/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * 图片Entity
 * @author luojie
 * @version 2017-06-27
 */
public class UserPhoto extends DataEntity<UserPhoto> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String keyword;		// 关键字
	private String name;		// 名称
	private String photo;		// 图片
	private String url;		// 链接
	private String status;		// 状态

	/**
	 * 验证模型字段
	 */
	@Override
	public  void validateModule(boolean isInsert) throws ValidationException {

		if(StringUtils2.isBlank(keyword)){
			throw new ValidationException("关键字不能为空!");
		}
		if(StringUtils2.isBlank(name)){
			throw new ValidationException("名称不能为空!");
		}
		if(StringUtils2.isBlank(photo)){
			throw new ValidationException("图片不能为空!");
		}

		if(StringUtils2.isBlank(remarks)){
			throw new ValidationException("备注不能为空!");
		}

	}

	@Override
	public void preInsert() throws ValidationException {
		validateModule(true);

		super.preInsert();

	}

	@Override
	public void preUpdate() throws  ValidationException{
		validateModule(false);
		super.preUpdate();
	}



	public UserPhoto() {
		super();
	}

	public UserPhoto(String id){
		super(id);
	}

	@Length(min=0, max=255, message="分类长度必须介于 0 和 255 之间")
	public String getkeyword() {
		return keyword;
	}

	public void setkeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1000, message="图片长度必须介于 0 和 1000 之间")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Length(min=0, max=255, message="链接长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

	
}