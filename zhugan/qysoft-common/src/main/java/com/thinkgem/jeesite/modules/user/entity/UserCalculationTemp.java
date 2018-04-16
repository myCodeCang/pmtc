/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 董事奖发放Entity
 * @author kevin
 * @version 2017-06-22
 */
public class UserCalculationTemp extends DataEntity<UserCalculationTemp> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String userName;		// 用户名
	private BigDecimal dividendOne = new BigDecimal(0);		// 奖金一
	private BigDecimal dividendTwo = new BigDecimal(0);	// 奖金二
	private BigDecimal dividendThree = new BigDecimal(0);	// 奖金三
	private BigDecimal dividendFour = new BigDecimal(0);	// 奖金四
	private BigDecimal dividendFive = new BigDecimal(0);		// 奖金五
	private BigDecimal dividendSix = new BigDecimal(0);		// 奖金六
	private BigDecimal dividendSeven = new BigDecimal(0);		// 奖金七
	private BigDecimal dividendEight = new BigDecimal(0);		// 奖金八
	private BigDecimal dividendNine = new BigDecimal(0);		// 奖金九
	private BigDecimal dividendTen = new BigDecimal(0);		// 奖金十
	private String isCheck;		// 是否检测
	private String status;		// 状态
	private String type;		// 类型

	//扩展字段
	private String moneyNum;


	public String getMoneyNum() {
		return moneyNum;
	}

	public void setMoneyNum(String moneyNum) {
		this.moneyNum = moneyNum;
	}

	public UserCalculationTemp() {
		super();
	}

	public UserCalculationTemp(String id){
		super(id);
	}

	@Length(min=0, max=100, message="用户名长度必须介于 0 和 100 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	@Length(min=0, max=1, message="是否检测长度必须介于 0 和 1 之间")
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=1, message="类型长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public BigDecimal getDividendOne() {
		return dividendOne;
	}

	public void setDividendOne(BigDecimal dividendOne) {
		this.dividendOne = dividendOne;
	}

	public BigDecimal getDividendTwo() {
		return dividendTwo;
	}

	public void setDividendTwo(BigDecimal dividendTwo) {
		this.dividendTwo = dividendTwo;
	}

	public BigDecimal getDividendThree() {
		return dividendThree;
	}

	public void setDividendThree(BigDecimal dividendThree) {
		this.dividendThree = dividendThree;
	}

	public BigDecimal getDividendFour() {
		return dividendFour;
	}

	public void setDividendFour(BigDecimal dividendFour) {
		this.dividendFour = dividendFour;
	}

	public BigDecimal getDividendFive() {
		return dividendFive;
	}

	public void setDividendFive(BigDecimal dividendFive) {
		this.dividendFive = dividendFive;
	}

	public BigDecimal getDividendSix() {
		return dividendSix;
	}

	public void setDividendSix(BigDecimal dividendSix) {
		this.dividendSix = dividendSix;
	}

	public BigDecimal getDividendSeven() {
		return dividendSeven;
	}

	public void setDividendSeven(BigDecimal dividendSeven) {
		this.dividendSeven = dividendSeven;
	}

	public BigDecimal getDividendEight() {
		return dividendEight;
	}

	public void setDividendEight(BigDecimal dividendEight) {
		this.dividendEight = dividendEight;
	}

	public BigDecimal getDividendNine() {
		return dividendNine;
	}

	public void setDividendNine(BigDecimal dividendNine) {
		this.dividendNine = dividendNine;
	}

	public BigDecimal getDividendTen() {
		return dividendTen;
	}

	public void setDividendTen(BigDecimal dividendTen) {
		this.dividendTen = dividendTen;
	}
}