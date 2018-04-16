/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 平台统计Entity
 * @author luo
 * @version 2018-04-02
 */
public class SystemReport extends DataEntity<SystemReport> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String userName;		// 用户名
	private String dividendOne = "0";		// 交易总量
	private String dividendTwo = "0";		// 矿机总量
	private String dividendThree = "0";		// 动态总量
	private String dividendFour = "0";		// 投资总量
	private String dividendFive = "0";		// 动态生成
	private String dividendSix = "0";		// 静态生成
	private String dividendSeven = "0";		// 投资生成
	private String dividendEight = "0";		// 手续费
	private String dividendNine = "0";		// 外部转入
	private String dividendTen = "0";		// 外部转出
	private String dividendEleven = "0";		// dividend_eleven
	private String dividendTwelve = "0";		// dividend_twelve
	private String dividendThirteen = "0";		// dividend_thirteen
	private String dividendFourteen = "0";		// dividend_fourteen
	private String dividendFifteen = "0";		// dividend_fifteen
	private String isCheck;		// 是否检测
	private String status;		// 状态
	
	public SystemReport() {
		super();
	}

	public SystemReport(String id){
		super(id);
	}

	@Length(min=0, max=100, message="用户名长度必须介于 0 和 100 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getDividendOne() {
		return dividendOne;
	}

	public void setDividendOne(String dividendOne) {
		this.dividendOne = dividendOne;
	}
	
	public String getDividendTwo() {
		return dividendTwo;
	}

	public void setDividendTwo(String dividendTwo) {
		this.dividendTwo = dividendTwo;
	}
	
	public String getDividendThree() {
		return dividendThree;
	}

	public void setDividendThree(String dividendThree) {
		this.dividendThree = dividendThree;
	}
	
	public String getDividendFour() {
		return dividendFour;
	}

	public void setDividendFour(String dividendFour) {
		this.dividendFour = dividendFour;
	}
	
	public String getDividendFive() {
		return dividendFive;
	}

	public void setDividendFive(String dividendFive) {
		this.dividendFive = dividendFive;
	}
	
	public String getDividendSix() {
		return dividendSix;
	}

	public void setDividendSix(String dividendSix) {
		this.dividendSix = dividendSix;
	}
	
	public String getDividendSeven() {
		return dividendSeven;
	}

	public void setDividendSeven(String dividendSeven) {
		this.dividendSeven = dividendSeven;
	}
	
	public String getDividendEight() {
		return dividendEight;
	}

	public void setDividendEight(String dividendEight) {
		this.dividendEight = dividendEight;
	}
	
	public String getDividendNine() {
		return dividendNine;
	}

	public void setDividendNine(String dividendNine) {
		this.dividendNine = dividendNine;
	}
	
	public String getDividendTen() {
		return dividendTen;
	}

	public void setDividendTen(String dividendTen) {
		this.dividendTen = dividendTen;
	}
	
	public String getDividendEleven() {
		return dividendEleven;
	}

	public void setDividendEleven(String dividendEleven) {
		this.dividendEleven = dividendEleven;
	}
	
	public String getDividendTwelve() {
		return dividendTwelve;
	}

	public void setDividendTwelve(String dividendTwelve) {
		this.dividendTwelve = dividendTwelve;
	}
	
	public String getDividendThirteen() {
		return dividendThirteen;
	}

	public void setDividendThirteen(String dividendThirteen) {
		this.dividendThirteen = dividendThirteen;
	}
	
	public String getDividendFourteen() {
		return dividendFourteen;
	}

	public void setDividendFourteen(String dividendFourteen) {
		this.dividendFourteen = dividendFourteen;
	}
	
	public String getDividendFifteen() {
		return dividendFifteen;
	}

	public void setDividendFifteen(String dividendFifteen) {
		this.dividendFifteen = dividendFifteen;
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
	
	

	
}