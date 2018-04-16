/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 银行信息Entity
 * @author kevin
 * @version 2017-03-23
 */
public class UserBankCommon extends DataEntity<UserBankCommon> {
	
	private static final long serialVersionUID = 1L;
	private String bankCode;		// 银行类别编码
	private String name;		// 银行名字
	private String image;		// 银行图片
	private String status;		// 状态


	/**
	 * 验证模型字段
	 */
	public  void validateModule(boolean isInsert) throws ValidationException {
		if(StringUtils2.isBlank(bankCode)){
			throw new ValidationException("银行卡编号不能为空!");
		}
		if(StringUtils2.isBlank(name)){
			throw new ValidationException("银行名字不能为空!");
		}
		if(StringUtils2.isBlank(status)){
			throw new ValidationException("状态不能为空!");
		}
	}


	@Override
	public void preInsert() throws ValidationException {
		validateModule(true);
		super.preInsert();

		//qydo  字段判空
	}

	@Override
	public void preUpdate() throws ValidationException {
		validateModule(false);
		super.preUpdate();

		//qydo  字段判空
	}

	public UserBankCommon() {
		super();
	}

	public UserBankCommon(String id){
		super(id);
	}

	@Length(min=1, max=50, message="银行类别编码长度必须介于 1 和 50 之间")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Length(min=1, max=50, message="银行名字长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=200, message="银行图片长度必须介于 0 和 200 之间")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Length(min=1, max=1, message="状态长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}