/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员充值Entity
 * @author liweijia
 * @version 2017-03-23
 */
public class UserChargeLog extends DataEntity<UserChargeLog> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title="用户帐号", align=2, sort=1)
	private String userName;		// 用户帐号
	@ExcelField(title="记录编号", align=2, sort=1)
	private String recordcode;		// 记录编号
	@ExcelField(title="变化金额", align=2, sort=1)
	private String changeMoney;		// 变化金额
	private BigDecimal beforeMoney;		// 之前金额
	private String afterMoney;		// 之后金额
	@ExcelField(title="充值来源", align=2, sort=1)
	private String changeFrom;		// 充值来源
	@ExcelField(title="备注", align=2, sort=1)
	private String commt;		// 备注
	@ExcelField(title="充值时间", align=2, sort=1,value="createDate")
	private String status ;		// 状态
	private String ischeck ;		// 是否统计
	private String changeType;		// 变化类型 默认1


	//扩展字段
	private Date createDateBegin; //开始时间筛选
	private Date createDateEnd; //结束时间筛选

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
		if(StringUtils2.isBlank(beforeMoney.toString ())){
			throw new ValidationException("之前金额不能为空!");
		}
		if(StringUtils2.isBlank(commt.toString ())){
			throw new ValidationException("备注不能为空!");
		}

	}

	@Override
	public void preUpdate() throws ValidationException {
		validateModule(false);
		super.preUpdate();

		//qydo 全部不可为空 -  提示全错

	}

	@Override
	public void preInsert() throws ValidationException {
		validateModule(true);
		super.preInsert();

		//qydo 全部不可未空 - 提示全错

	}

	public UserChargeLog() {
		super();
	}

	public UserChargeLog(String id){
		super(id);
	}


	@Length(min=1, max=100, message="用户帐号长度必须介于 1 和 100 之间")
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
	
	public BigDecimal getBeforeMoney() {
		return beforeMoney;
	}

	public void setBeforeMoney(BigDecimal beforeMoney) {
		this.beforeMoney = beforeMoney;
	}
	
	public String getAfterMoney() {
		return afterMoney;
	}

	public void setAfterMoney(String afterMoney) {
		this.afterMoney = afterMoney;
	}
	

	@Length(min=0, max=255, message="充值来源长度必须介于 0 和 255 之间")
	public String getChangeFrom() {
		return changeFrom;
	}

	public void setChangeFrom(String changeFrom) {
		this.changeFrom = changeFrom;
	}
	
	@Length(min=1, max=255, message="备注长度必须介于 1 和 255 之间")
	public String getCommt() {
		return commt;
	}

	public void setCommt(String commt) {
		this.commt = commt;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=1, max=1, message="是否统计长度必须介于 1 和 1 之间")
	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	
	@Length(min=1, max=1, message="变化类型 默认1长度必须介于 1 和 1 之间")
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}
}

