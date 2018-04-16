/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户奖金Entity
 * @author xueyuliang
 * @version 2017-04-25
 */
public class UserReport extends DataEntity<UserReport> {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String userName;		// 用户名
	private UserUserinfo userUserinfo; //用户对象
	private String trueName;       //真实姓名
	private BigDecimal dividendOne = new BigDecimal(0);		// 奖金一
	private BigDecimal dividendTwo = new BigDecimal(0);		// 奖金二
	private BigDecimal dividendThree = new BigDecimal(0);		// 奖金三
	private BigDecimal dividendFour = new BigDecimal(0);		// 奖金四
	private BigDecimal dividendFive = new BigDecimal(0);		// 奖金五
	private BigDecimal dividendSix = new BigDecimal(0);		// 奖金六
	private BigDecimal dividendSeven = new BigDecimal(0);;		// 奖金七
	private BigDecimal dividendEight = new BigDecimal(0);;		// 奖金八
	private BigDecimal dividendNine = new BigDecimal(0);;		// 奖金九
	private BigDecimal dividendTen = new BigDecimal(0);; 		// 奖金十

	private BigDecimal dividendEleven = new BigDecimal(0);; 		// 奖金11
	private BigDecimal dividendTwelve = new BigDecimal(0);; 		// 奖金12
	private BigDecimal dividendThirteen = new BigDecimal(0);; 		// 奖金13
	private BigDecimal dividendFourteen = new BigDecimal(0);; 		// 奖金14
	private BigDecimal dividendFifteen = new BigDecimal(0);; 		// 奖金15

	private String isCheck;		// 是否检测
	private String status;		// status

	//查询字段
	private Date createDateByDate; //按照每天查询报表
	private Date createDateBegin;
	private Date createDateEnd;


	//拓展字段
	private  String[] officeArray;   //机构包含
	private BigDecimal buyBonus;
	private BigDecimal applyBonus;
	private BigDecimal procedureBonus;
	private BigDecimal shopBonus;



	/**
	 * 验证模型字段
	 */
	public  void validateModule(boolean isInsert) throws ValidationException {

		if(StringUtils2.isBlank(userName)){
			throw new ValidationException("用户名不能为空!");
		}
	}

	@Override
	public void preInsert() throws ValidationException {
		if(StringUtils2.isBlank(isCheck)){
			isCheck = "0";
		}
		if (StringUtils2.isBlank(status)) {
			status = "1";
		}


		validateModule(true);
		super.preInsert();

	}

	@Override
	public void preUpdate() throws ValidationException {
		validateModule(false);
		super.preUpdate();

	}


	public BigDecimal getBuyBonus() {
		return buyBonus;
	}

	public void setBuyBonus(BigDecimal buyBonus) {
		this.buyBonus = buyBonus;
	}

	public BigDecimal getApplyBonus() {
		return applyBonus;
	}

	public void setApplyBonus(BigDecimal applyBonus) {
		this.applyBonus = applyBonus;
	}

	public BigDecimal getProcedureBonus() {
		return procedureBonus;
	}

	public void setProcedureBonus(BigDecimal procedureBonus) {
		this.procedureBonus = procedureBonus;
	}

	public BigDecimal getShopBonus() {
		return shopBonus;
	}

	public void setShopBonus(BigDecimal shopBonus) {
		this.shopBonus = shopBonus;
	}

	public String[] getOfficeArray() {
		return officeArray;
	}

	public void setOfficeArray(String[] officeArray) {
		this.officeArray = officeArray;
	}

	public UserReport() {
		super();
	}

	public UserReport(String id){
		super(id);
	}

	@Length(min=0, max=100, message="用户名长度必须介于 0 和 100 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public UserUserinfo getUserUserinfo() {
		return userUserinfo;
	}
	
	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public void setUserUserinfo(UserUserinfo userUserinfo) {
		this.userUserinfo = userUserinfo;
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

	@Length(min=0, max=1, message="是否检测长度必须介于 0 和 1 之间")
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	
	@Length(min=0, max=1, message="status长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Date getCreateDateByDate() {
		return createDateByDate;
	}

	public void setCreateDateByDate(Date createDateByDate) {
		this.createDateByDate = createDateByDate;
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

	public BigDecimal getDividendEleven() {
		return dividendEleven;
	}

	public void setDividendEleven(BigDecimal dividendEleven) {
		this.dividendEleven = dividendEleven;
	}

	public BigDecimal getDividendTwelve() {
		return dividendTwelve;
	}

	public void setDividendTwelve(BigDecimal dividendTwelve) {
		this.dividendTwelve = dividendTwelve;
	}

	public BigDecimal getDividendThirteen() {
		return dividendThirteen;
	}

	public void setDividendThirteen(BigDecimal dividendThirteen) {
		this.dividendThirteen = dividendThirteen;
	}

	public BigDecimal getDividendFourteen() {
		return dividendFourteen;
	}

	public void setDividendFourteen(BigDecimal dividendFourteen) {
		this.dividendFourteen = dividendFourteen;
	}

	public BigDecimal getDividendFifteen() {
		return dividendFifteen;
	}

	public void setDividendFifteen(BigDecimal dividendFifteen) {
		this.dividendFifteen = dividendFifteen;
	}
}