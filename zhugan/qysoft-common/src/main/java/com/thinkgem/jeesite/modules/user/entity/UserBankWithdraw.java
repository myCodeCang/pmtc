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
 * 提款银行Entity
 * @author cai
 * @version 2017-03-24
 */
public class UserBankWithdraw extends DataEntity<UserBankWithdraw> {
	
	private static final long serialVersionUID = 1L;
	private String bankCode;		// 银行编号
	private String bankName;		// 银行名称
	private String minLength;		// 最小提现金额
	private String maxLength;		// 最大提现金额
	private String joinActive;		// 是否参加活动
	private String commt;		// 说明
	private String status;		// 状态
	private String sort;		// 排序
	private String address;//提现开户行地址
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}




	/**
	 * 验证模型字段
	 */
	public  void validateModule(boolean isInsert) throws ValidationException {

		if(StringUtils2.isBlank(bankCode)){
			throw new ValidationException("银行编号不能为空!");
		}
//		if(!VerifyUtils.isInteger (minLength)){
//			throw new ValidationException("最大提现金额输入有误,请输入正整数!");
//		}
//		if(!VerifyUtils.isInteger (maxLength)){
//			throw new ValidationException("最大提现金额输入有误,请输入正整数!");
//		}
//		if(Integer.parseInt (minLength)>Integer.parseInt (maxLength)){
//			throw new ValidationException("最大提现金额输入必须大于最小提现金额!");
//		}
		if(StringUtils2.isBlank(joinActive)){
			throw new ValidationException("是否参加活动不能为空!");
		}
		if(StringUtils2.isBlank(commt)){
			throw new ValidationException("说明不能为空!");
		}if(StringUtils2.isBlank(status)){
			throw new ValidationException("状态不能为空!");
		}


	}

	@Override
	public void preUpdate() throws ValidationException {
		validateModule(false);
		super.preUpdate();

		//qydo 字段判空
	}

	@Override
	public void preInsert() throws ValidationException {
		validateModule(true);
		super.preInsert();

		//qydo 字段判空
	}

	public UserBankWithdraw() {
		super();
	}

	public UserBankWithdraw(String id){
		super(id);
	}


	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Length(min=1, max=50, message="银行编号长度必须介于 1 和 50 之间")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Length(min=1, max=11, message="信用卡号最小长度长度必须介于 1 和 11 之间")
	public String getMinLength() {
		return minLength;
	}

	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}
	
	@Length(min=1, max=11, message="信用卡号最大长度长度必须介于 1 和 11 之间")
	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	
	@Length(min=1, max=1, message="是否参加活动长度必须介于 1 和 1 之间")
	public String getJoinActive() {
		return joinActive;
	}

	public void setJoinActive(String joinActive) {
		this.joinActive = joinActive;
	}
	
	@Length(min=1, max=255, message="说明长度必须介于 1 和 255 之间")
	public String getCommt() {
		return commt;
	}

	public void setCommt(String commt) {
		this.commt = commt;
	}
	
	@Length(min=1, max=1, message="状态长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=1, max=11, message="排序长度必须介于 1 和 11 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}