/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 汇款审核Entity
 * @author wyxiang
 * @version 2017-04-30
 */
public class UserCharge extends DataEntity<UserCharge> {
	
	private static final long serialVersionUID = 1L;
	


	private String userName;		// 用户编号
	private String recordcode;		// 记录编号
	private String changeMoney;		// 变化金额
	private String commt;		// 备注
	private String status;		// 状态 0:未处理 1:处理成功  2:拒绝充值
	private String ischeck;		// 是否统计
	private String changeType;		// 充值类型
	private String bankCode;		// 打款银行
	private String bankImage;		// 打款凭证
	private String bankNum;		// 银行号码
	private String bankUser;		// 打款人姓名
	private String isDispose;   //是否处理, 传0未处理.

	//关联查询
	private String bankName;
	private String trueName;

	/**
	 * 验证模型字段
	 */
	public  void validateModule(boolean isInsert) throws ValidationException {

		if(StringUtils2.isBlank(userName)){
			throw new ValidationException("用户名不能为空!");
		}
		if(StringUtils2.isBlank(recordcode)){
			throw new ValidationException("记录编号不能为空!");
		}
		if(StringUtils2.isBlank(changeMoney)){
			throw new ValidationException("变化金额不能为空!");
		}
		if(StringUtils2.isBlank(commt)){
			throw new ValidationException("备注不能为空!");
		}
		if(StringUtils2.isBlank(status)){
			throw new ValidationException("状态不能为空!");
		}
		if(StringUtils2.isBlank(bankCode)){
			throw new ValidationException("打款银行不能为空!");
		}
		if(StringUtils2.isBlank(bankImage)){
			throw new ValidationException("打款凭证不能为空!");
		}
		if(StringUtils2.isBlank(bankNum)){
			throw new ValidationException("银行号码不能为空!");
		}
		if(StringUtils2.isBlank(bankUser)){
			throw new ValidationException("打款人姓名不能为空!");
		}



	}

	@Override
	public void preUpdate() throws ValidationException {
		validateModule(false);
		super.preUpdate();

		//qydo 验证不为空
	}

	@Override
	public void preInsert() throws ValidationException {
		validateModule(true);
		super.preInsert();

		//qydo 验证不为空
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getIsDispose() {
		return isDispose;
	}

	public void setIsDispose(String isDispose) {
		this.isDispose = isDispose;
	}

	public UserCharge() {
		super();
	}

	public UserCharge(String id){
		super(id);
	}

	@Length(min=0, max=100, message="用户编号长度必须介于 0 和 100 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=255, message="记录编号长度必须介于 0 和 255 之间")
	public String getRecordcode() {
		return recordcode;
	}

	public void setRecordcode(String recordcode) {
		this.recordcode = recordcode;
	}
	
	public String getChangeMoney() {
		return changeMoney;
	}

	public void setChangeMoney(String changeMoney) {
		this.changeMoney = changeMoney;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getCommt() {
		return commt;
	}

	public void setCommt(String commt) {
		this.commt = commt;
	}
	
	@Length(min=0, max=1, message="状态 0:未处理 1:处理成功  2:拒绝充值长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=1, message="是否统计长度必须介于 0 和 1 之间")
	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	
	@Length(min=0, max=1, message="充值类型长度必须介于 0 和 1 之间")
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	@Length(min=0, max=255, message="打款银行长度必须介于 0 和 255 之间")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Length(min=0, max=500, message="打款凭证长度必须介于 0 和 500 之间")
	public String getBankImage() {
		return bankImage;
	}

	public void setBankImage(String bankImage) {
		this.bankImage = bankImage;
	}
	
	@Length(min=0, max=255, message="银行号码长度必须介于 0 和 255 之间")
	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	
	@Length(min=0, max=255, message="打款人姓名长度必须介于 0 和 255 之间")
	public String getBankUser() {
		return bankUser;
	}

	public void setBankUser(String bankUser) {
		this.bankUser = bankUser;
	}


	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


}