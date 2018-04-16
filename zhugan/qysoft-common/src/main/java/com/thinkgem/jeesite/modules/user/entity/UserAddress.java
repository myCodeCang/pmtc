/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.utils.VerifyUtils;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户地址表Entity
 * @author luojie
 * @version 2017-05-08
 */
public class UserAddress extends DataEntity<UserAddress> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String userName;		// 用户名
	private String address;		// 地址
	private String postcode;		// 邮编
	private String trueName;		// 收货人姓名
	private String mobile;		// 手机号

	/**
	 * 验证模型字段
	 */
	public  void validateModule(boolean isInsert) throws ValidationException {
		if(StringUtils2.isBlank(userName)){
			throw new ValidationException("用户名不能为空!");
		}
		if(StringUtils2.isBlank(address)){
			throw new ValidationException("地址不能为空!");
		}
		if(!VerifyUtils.isMobile (mobile)){
			throw new ValidationException("手机号输入错误!");
		}
		if(StringUtils2.isBlank(trueName)){
			throw new ValidationException("收货人姓名不能为空!");
		}

	}

	@Override
	public void preInsert() throws ValidationException {
		validateModule(true);
		super.preInsert();

		//qydo 验证所有字段不为空
	}

	@Override
	public void preUpdate() throws ValidationException {
		validateModule(false);
		super.preUpdate();

		//qydo 验证所有字段不为空
	}

	public UserAddress() {
		super();
	}

	public UserAddress(String id){
		super(id);
	}

	@Length(min=0, max=255, message="用户名长度必须介于 0 和 255 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=100, message="地址长度必须介于 0 和 100 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=100, message="邮编长度必须介于 0 和 100 之间")
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	@Length(min=0, max=50, message="收货人姓名长度必须介于 0 和 50 之间")
	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	
	@Length(min=0, max=50, message="手机号长度必须介于 0 和 50 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	

	
}