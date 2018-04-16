/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 用户提现Entity
 * @author kevin
 * @version 2017-03-23
 */
public class UserWithdraw extends DataEntity<UserWithdraw> {
	public static final String editStatus ="1";
	private static final long serialVersionUID = 1L;
	@ExcelField(title="用户账户", align=2, sort=1)
	private String userName;		// 用户账户
	@ExcelField(title="手机号", align=2, sort=1,value="userinfo.mobile")
	private String recordcode;		// 记录编号

	@ExcelField(title="身份证号", align=2, sort=1,value="userinfo.idCard")
	private String beforeMoney;		// 之前金额
	@ExcelField(title="银行卡开户名", align=2, sort=1)
	private String userBankName;		// 银行卡开户名
	@ExcelField(title="银行名称", align=2, sort=1)
	private String  bankCodeName;
	private String userBankCode;		// 银行名称
	@ExcelField(title="银行卡号", align=2, sort=1)
	private String userBankNum;		// 银行卡号
	@ExcelField(title="所在省", align=2, sort=1)
	private String provinces;
	@ExcelField(title="所在市", align=2, sort=1)
	private String citys;
	@ExcelField(title="所在区", align=2, sort=1)
	private String area;
	@ExcelField(title="开户行地址", align=2, sort=1)
	private String userBankAddress;		// 开户行地址
	private String afterMoney;		// 之后金额
	@ExcelField(title="提现金额", align=2, sort=1)
	private String changeMoney;		// 变化金额
	@ExcelField(title="实际到账金额", align=2, sort=1)
	private String realMoney;		// 实际到账金额
	@ExcelField(title="提现时间", align=2, sort=1,value="createDate")
	private Date addtime;		// 添加时间
	private String commt;		// 备注
	@ExcelField(title="审核状态", align=2, sort=1,dictType="usercenter_type")
	private String status;		// 状态
	private String ischeck ="0";		// 是否统计
	private String changeType;		// 变化类型
	//扩展字段
	private Date createDateBegin;		// 开始时间
	private Date createDateEnd;		// 结束时间
	private String BatchNo;//环迅委托付款批次号
	public String getBatchNo() {
		return BatchNo;
	}

	public void setBatchNo(String batchNo) {
		BatchNo = batchNo;
	}




	private Date limitDate;  //提现限制

	private UserUserinfo userinfo;//会员详情
	private String userBackAddress;
	@ExcelField(title="打款请求状态", align=2, sort=1,dictType="weituo_type")
	private String remittanceStatus;
	public String getRemittanceStatus() {
		return remittanceStatus;
	}

	public void setRemittanceStatus(String remittanceStatus) {
		this.remittanceStatus = remittanceStatus;
	}


	public String getUserBackAddress() {
		return userBackAddress;
	}

	public void setUserBackAddress(String userBackAddress) {
		this.userBackAddress = userBackAddress;
	}

	public UserUserinfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserUserinfo userinfo) {
		this.userinfo = userinfo;
	}

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
		if(StringUtils2.isBlank(userName)){
			throw new ValidationException("用户名不能为空!");
		}
		if(StringUtils2.isBlank(recordcode)){
			throw new ValidationException("记录编号不能为空!");
		}
		if(StringUtils2.isBlank(changeMoney)){
			throw new ValidationException("变化金额不能为空!");
		}
		if(beforeMoney == null){
			throw new ValidationException("充值前金额不能为空!");
		}
		if(afterMoney == null){
			throw new ValidationException("充值后金额不能为空!");
		}
		// if(addtime == null){
		// 	throw new ValidationException("添加时间不能为空!");
		// }

		if(StringUtils2.isBlank(userBankName)){
			throw new ValidationException("银行卡开户名不能为空!");
		}
		if(StringUtils2.isBlank(userBankNum)){
			throw new ValidationException("银行卡号不能为空!");
		}
		if(StringUtils2.isBlank(userBankCode)){
			throw new ValidationException("银行编号不能为空!");
		}
		// if(StringUtils2.isBlank(userBankAddress)){
		// 	throw new ValidationException("开户行地址不能为空!");
		// }
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

	//join  properties


	public Date getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}

	private String trueName;



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

	public UserWithdraw() {
		super();
	}

	public UserWithdraw(String id){
		super(id);
	}

	@Length(min=1, max=100, message="用户账户长度必须介于 1 和 100 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=1, max=255, message="记录编号长度必须介于 1 和 255 之间")
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
	
	public String getBeforeMoney() {
		return beforeMoney;
	}

	public void setBeforeMoney(String beforeMoney) {
		this.beforeMoney = beforeMoney;
	}
	
	public String getAfterMoney() {
		return afterMoney;
	}

	public void setAfterMoney(String afterMoney) {
		this.afterMoney = afterMoney;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
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
	
	@Length(min=1, max=1, message="是否统计长度必须介于 1 和 1 之间")
	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	
	@Length(min=1, max=1, message="变化类型长度必须介于 1 和 1 之间")
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	@Length(min=1, max=100, message="银行卡开户名长度必须介于 1 和 100 之间")
	public String getUserBankName() {
		return userBankName;
	}

	public void setUserBankName(String userBankName) {
		this.userBankName = userBankName;
	}
	
	@Length(min=1, max=50, message="银行卡号长度必须介于 1 和 50 之间")
	public String getUserBankNum() {
		return userBankNum;
	}

	public void setUserBankNum(String userBankNum) {
		this.userBankNum = userBankNum;
	}
	
	@Length(min=1, max=100, message="银行名称长度必须介于 1 和 100 之间")
	public String getUserBankCode() {
		return userBankCode;
	}

	public void setUserBankCode(String userBankCode) {
		this.userBankCode = userBankCode;
	}
	
	@Length(min=1, max=255, message="开户行地址长度必须介于 1 和 255 之间")
	public String getUserBankAddress() {
		return userBankAddress;
	}

	public void setUserBankAddress(String userBankAddress) {
		this.userBankAddress = userBankAddress;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getBankCodeName() {
		return bankCodeName;
	}

	public void setBankCodeName(String bankCodeName) {
		this.bankCodeName = bankCodeName;
	}

	public String getRealMoney() {return realMoney;}

	public void setRealMoney(String realMoney) {this.realMoney = realMoney;}
}