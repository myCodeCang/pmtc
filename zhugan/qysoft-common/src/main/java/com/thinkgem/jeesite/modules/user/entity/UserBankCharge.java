/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.utils.VerifyUtils;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * 充值银行信息Entity
 * @author cai
 * @version 2017-03-24
 */
public class UserBankCharge extends DataEntity<UserBankCharge> {
	
	private static final long serialVersionUID = 1L;
	private String bankCode;		// 银行编号
	private String userBankName;		// 银行户名
	private String userBankNumber;		// 银行账号
	private BigDecimal minRechaege;		// 最小收款金额
	private BigDecimal maxRechaege;		// 最大收款金额
	private String outAddress;		// 外链地址
	private String rechaegeOut;		// 是否充值外联
	private String commt;		// 说明
	private String sort;		// 排序
	private String status;		// 状态

	/*扩展字段*/
	private String bankName;  //银行名称

	/**
	 * 验证模型字段
	 */
	public  void validateModule(boolean isInsert) throws ValidationException {
		if(StringUtils2.isBlank(bankCode)){
			throw new ValidationException("银行编号不能为空!");
		}
		if(StringUtils2.isBlank(userBankName)){
			throw new ValidationException("银行户名不能为空!");
		}
		if(userBankNumber == null){
			throw new ValidationException("银行账号不能为空!");
		}
//		if(maxRechaege.compareTo (minRechaege)<0){
//			throw new ValidationException("最大收款金额不能小于最小收款金额!");
//		}
//		if(StringUtils2.isBlank(outAddress)){
//			throw new ValidationException("外联地址不能为空!");
//		}
//		if(StringUtils2.isBlank(rechaegeOut)){
//			throw new ValidationException("是否充值外联不能为空!");
//		}
		if(StringUtils2.isBlank(status)){
			throw new ValidationException("状态不能为空!");
		}

	}

	@Override
	public void preInsert() throws ValidationException {
		validateModule(true);
		super.preInsert();


		//qydo  判断全部不为空
	}

	@Override
	public void preUpdate() throws ValidationException {
		validateModule(false);
		super.preUpdate();

		//qydo  判断全部不为空
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public UserBankCharge() {
		super();
	}

	public UserBankCharge(String id){
		super(id);
	}

	@Length(min=1, max=50, message="银行编号长度必须介于 1 和 50 之间")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Length(min=1, max=50, message="银行户名长度必须介于 1 和 50 之间")
	public String getUserBankName() {
		return userBankName;
	}

	public void setUserBankName(String userBankName) {
		this.userBankName = userBankName;
	}
	
	@Length(min=1, max=50, message="银行账号长度必须介于 1 和 50 之间")
	public String getUserBankNumber() {
		return userBankNumber;
	}

	public void setUserBankNumber(String userBankNumber) {
		this.userBankNumber = userBankNumber;
	}
	
	public BigDecimal getMinRechaege() {
		return minRechaege;
	}

	public void setMinRechaege(BigDecimal minRechaege) {
		this.minRechaege = minRechaege;
	}
	
	public BigDecimal getMaxRechaege() {
		return maxRechaege;
	}

	public void setMaxRechaege(BigDecimal maxRechaege) {
		this.maxRechaege = maxRechaege;
	}
	
	@Length(min=1, max=255, message="外链地址长度必须介于 1 和 255 之间")
	public String getOutAddress() {
		return outAddress;
	}

	public void setOutAddress(String outAddress) {
		this.outAddress = outAddress;
	}
	
	@Length(min=1, max=11, message="是否充值外联长度必须介于 1 和 11 之间")
	public String getRechaegeOut() {
		return rechaegeOut;
	}

	public void setRechaegeOut(String rechaegeOut) {
		this.rechaegeOut = rechaegeOut;
	}
	
	@Length(min=1, max=255, message="说明长度必须介于 1 和 255 之间")
	public String getCommt() {
		return commt;
	}

	public void setCommt(String commt) {
		this.commt = commt;
	}
	
	@Length(min=1, max=11, message="排序长度必须介于 1 和 11 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Length(min=1, max=1, message="状态长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}