/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 个人银行卡Entity
 * @author luo
 * @version 2017-05-12
 */
public class UserUserinfoBank extends DataEntity<UserUserinfoBank> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String userName;		// 用户帐号
	private String bankName;		// 银行名称
	private String bankUserNum;		// 银行卡号
	private String bankUserName;		// 开户人姓名
	private String provinces;		// 开户份
	private String citys;		// 开户市
	private String area;		// 开户区
	private String bankUserAddress;		// 开户地址
	private String bankCode;				//银行代码

	public String getProvinces() {
		return provinces;
	}

	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	public String getCitys() {
		return citys;
	}

	public void setCitys(String citys) {
		this.citys = citys;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * 验证模型字段
	 */

	public  void validateModule(boolean isInsert) throws ValidationException {
		if(StringUtils2.isBlank (userName)){
			throw new ValidationException ("用户名不能为空");
		}
		if(StringUtils2.isBlank (bankName)){
			throw new ValidationException ("银行名称不能为空");
		}
		if(StringUtils2.isBlank (bankUserName)){
			throw new ValidationException ("开户人姓名不能为空");
		}
		if(StringUtils2.isBlank (bankUserNum)){
			throw new ValidationException ("银行卡号不能为空");
		}
	}

	@Override
	public void preUpdate() throws ValidationException {
		validateModule(false);
		super.preUpdate();


	}

	@Override
	public void preInsert() throws ValidationException {
		validateModule(true);
		super.preInsert();

	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public UserUserinfoBank() {
		super();
	}

	public UserUserinfoBank(String id){
		super(id);
	}

	@Length(min=0, max=100, message="用户帐号长度必须介于 0 和 100 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=255, message="银行代码长度必须介于 0 和 255 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=255, message="银行卡号长度必须介于 0 和 255 之间")
	public String getBankUserNum() {
		return bankUserNum;
	}

	public void setBankUserNum(String bankUserNum) {
		this.bankUserNum = bankUserNum;
	}
	
	@Length(min=0, max=255, message="开户人姓名长度必须介于 0 和 255 之间")
	public String getBankUserName() {
		return bankUserName;
	}

	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}
	
	@Length(min=0, max=255, message="开户地址长度必须介于 0 和 255 之间")
	public String getBankUserAddress() {
		return bankUserAddress;
	}

	public void setBankUserAddress(String bankUserAddress) {
		this.bankUserAddress = bankUserAddress;
	}
	
	

	
}