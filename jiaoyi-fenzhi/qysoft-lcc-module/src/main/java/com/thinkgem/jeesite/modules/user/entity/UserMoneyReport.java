/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * 舆情分析Entity
 * @author luo
 * @version 2018-03-13
 */
public class UserMoneyReport extends DataEntity<UserMoneyReport> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String userName;		// 用户名
	private BigDecimal sumMoney2 = BigDecimal.ZERO;		// 产币数
	private BigDecimal sumMoney3 = BigDecimal.ZERO;		// 购币数(转账)
	private BigDecimal sumMoney4 = BigDecimal.ZERO;		// 购币数(交易)
	private BigDecimal sumMoney5 = BigDecimal.ZERO;		// 出币数(转账)
	private BigDecimal sumMoney6 = BigDecimal.ZERO;		// 出币数(交易)
	private BigDecimal sumMoney7 = BigDecimal.ZERO;		// 盈亏
	private BigDecimal sumMoney8 = BigDecimal.ZERO;		// sum_money8
	private BigDecimal isCheck;		// 是否检测
	
	public UserMoneyReport() {
		super();
	}

	public UserMoneyReport(String id){
		super(id);
	}

	@Length(min=0, max=255, message="用户名长度必须介于 0 和 255 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public BigDecimal getSumMoney4() {
		return sumMoney4;
	}

	public void setSumMoney4(BigDecimal sumMoney4) {
		this.sumMoney4 = sumMoney4;
	}

	public BigDecimal getSumMoney5() {
		return sumMoney5;
	}

	public void setSumMoney5(BigDecimal sumMoney5) {
		this.sumMoney5 = sumMoney5;
	}

	public BigDecimal getSumMoney6() {
		return sumMoney6;
	}

	public void setSumMoney6(BigDecimal sumMoney6) {
		this.sumMoney6 = sumMoney6;
	}

	public BigDecimal getSumMoney7() {
		return sumMoney7;
	}

	public void setSumMoney7(BigDecimal sumMoney7) {
		this.sumMoney7 = sumMoney7;
	}

	public BigDecimal getSumMoney8() {
		return sumMoney8;
	}

	public void setSumMoney8(BigDecimal sumMoney8) {
		this.sumMoney8 = sumMoney8;
	}

	public BigDecimal getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(BigDecimal isCheck) {
		this.isCheck = isCheck;
	}
}