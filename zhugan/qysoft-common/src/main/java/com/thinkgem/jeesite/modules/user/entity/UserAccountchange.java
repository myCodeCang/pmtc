
/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 用户帐变明细Entity
 * @author xueyuliang
 * @version 2017-03-23
 */
public class UserAccountchange extends DataEntity<UserAccountchange> {
	
	private static final long serialVersionUID = 1L;
	private String id;

	@NotBlank
	private String userName;		// 用户帐号

	@NotBlank
	private String changeMoney;		// 帐变金额

	@NotBlank
	private BigDecimal beforeMoney;		// 帐变前金额
	private String afterMoney;		// 帐变后金额
	private String commt;		// 备注
	private String status ;		// 状态
	private String ischeck ;		// 是否统计
	private String changeType;		//	0充值 1提现
	private String moneyType;		// 1  金额   2积分
	private String isMonCheck ;		// 安全检测标识

	private UserAccountchange Accountchangedetail;		//帐变详情
	public UserAccountchange getAccountchangedetail() {
		return Accountchangedetail;
	}

	public void setAccountchangedetail(UserAccountchange accountchangedetail) {
		Accountchangedetail = accountchangedetail;
	}




	//扩展字段
	private Date createDateBegin;		// 开始时间
	private Date createDateEnd;		// 结束时间
	private Date createDateMonth;     //创建月份
	private BigDecimal changeMoneyBegin ; //金额大于等于
 	private BigDecimal changeMoneyEnd; //金额小于等于
	private String moenyTypeEnd;
	private List<Integer> changeTypeArray;
	private BigDecimal sumMoney;
	private BigDecimal sumMoney2;
	private BigDecimal sumMoney3;



	private UserUserinfo userUserinfo;		 //用户对象


	@Override
	public void preInsert() throws ValidationException {
		this.ischeck = "0";
		if(StringUtils2.isBlank(status)){
			this.status = "1";
		}


		validateModule(true);
		super.preInsert();
	}

	@Override
	public void preUpdate() throws ValidationException {
		validateModule(false);
		super.preUpdate();

	}

	/**
	 * 验证模型字段
	 */

	public  void validateModule(boolean isInsert) throws  ValidationException{

		// if(StringUtils2.isBlank(id)){
		// 	throw new ValidationException("id不能为空!");
		// }
		if(StringUtils2.isBlank(userName)){
			throw new ValidationException("用户名不能为空!");
		}
		if(StringUtils2.isBlank(changeMoney)){
			throw new ValidationException("账变金额不能为空!");
		}
		if(StringUtils2.isBlank(beforeMoney.toString())){
			throw new ValidationException("账变前金额不能为空!");
		}
		if(StringUtils2.isBlank(afterMoney)){
			throw new ValidationException("账变后金额不能为空!");
		}
		if(StringUtils2.isBlank(commt)){
			throw new ValidationException("备注不能为空!");
		}
		if(StringUtils2.isBlank(changeType)){
			throw new ValidationException("账变类型不能为空!");
		}
		if(StringUtils2.isBlank(moneyType)){
			throw new ValidationException("充值类型不能为空!");
		}
	}

	public UserAccountchange() {
		super();
	}

	public UserAccountchange(String id){
		super(id);
	}

	@Length(min=0, max=100, message="用户帐号长度必须介于 0 和 100 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public UserUserinfo getUserUserinfo() {
		return userUserinfo;
	}

	public void setUserUserinfo(UserUserinfo userUserinfo) {
		this.userUserinfo = userUserinfo;
	}

	public String getChangeMoney() {
		return changeMoney;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date creatDate) {
		this.createDate = creatDate;
	}
	
	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
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
	
	@Length(min=0, max=1, message="是否统计长度必须介于 0 和 1 之间")
	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	
	@Length(min=0, max=1, message="变化类型长度必须介于 0 和 1 之间")
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	@Length(min=0, max=1, message="变化类型长度必须介于 0 和 1 之间")
	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public BigDecimal getChangeMoneyBegin() {
		return changeMoneyBegin;
	}

	public void setChangeMoneyBegin(BigDecimal changeMoneyBegin) {
		this.changeMoneyBegin = changeMoneyBegin;
	}

	public BigDecimal getChangeMoneyEnd() {
		return changeMoneyEnd;
	}

	public void setChangeMoneyEnd(BigDecimal changeMoneyEnd) {
		this.changeMoneyEnd = changeMoneyEnd;
	}

	public Date getCreateDateMonth() {
		return createDateMonth;
	}

	public void setCreateDateMonth(Date createDateMonth) {
		this.createDateMonth = createDateMonth;
	}

	public String getMoenyTypeEnd() {
		return moenyTypeEnd;
	}

	public void setMoenyTypeEnd(String moenyTypeEnd) {
		this.moenyTypeEnd = moenyTypeEnd;
	}

	public List<Integer> getChangeTypeArray() {
		return changeTypeArray;
	}

	public void setChangeTypeArray(List<Integer> changeTypeArray) {
		this.changeTypeArray = changeTypeArray;
	}

	public BigDecimal getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}

	public BigDecimal getSumMoney2() {
		return sumMoney2;
	}

	public void setSumMoney2(BigDecimal sumMoney2) {
		this.sumMoney2 = sumMoney2;
	}

	public BigDecimal getSumMoney3() {
		return sumMoney3;
	}

	public void setSumMoney3(BigDecimal sumMoney3) {
		this.sumMoney3 = sumMoney3;
	}

	public String getIsMonCheck() {
		return isMonCheck;
	}

	public void setIsMonCheck(String isMonCheck) {
		this.isMonCheck = isMonCheck;
	}
}