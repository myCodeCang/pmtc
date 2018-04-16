/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 交易表Entity
 * @author wyxiang
 * @version 2018-03-20
 */
public class TranscodeBuy extends DataEntity<TranscodeBuy> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String userName;		// 用户编号
	private BigDecimal money;		// 价格
	private BigDecimal sellNum;		// 出售数量
	private BigDecimal nowNum;		// 剩余数量
	private BigDecimal downNum;		// 下架数量
	private BigDecimal guarantees;
	private String status;		// 状态  0:售卖中  1 已出售 2用户下架 3系统下架
	private String type;		// 2 买入  1.出售
	private Date addDate;		// add_date
	private Integer sort;    //排序

	//查询字段
	private String statusBegin;
	private Date createDateByMonth;
	
	public TranscodeBuy() {
		super();
	}

	public TranscodeBuy(String id){
		super(id);
	}

	@Length(min=0, max=255, message="用户编号长度必须介于 0 和 255 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getSellNum() {
		return sellNum;
	}

	public void setSellNum(BigDecimal sellNum) {
		this.sellNum = sellNum;
	}

	public BigDecimal getNowNum() {
		return nowNum;
	}

	public void setNowNum(BigDecimal nowNum) {
		this.nowNum = nowNum;
	}

	public BigDecimal getDownNum() {
		return downNum;
	}

	public void setDownNum(BigDecimal downNum) {
		this.downNum = downNum;
	}

	@Length(min=0, max=1, message="状态  1:售卖中  0 已出售 2用户下架 3系统下架长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getStatusBegin() {
		return statusBegin;
	}

	public void setStatusBegin(String statusBegin) {
		this.statusBegin = statusBegin;
	}

	public BigDecimal getGuarantees() {
		return guarantees;
	}

	public void setGuarantees(BigDecimal guarantees) {
		this.guarantees = guarantees;
	}

	public Date getCreateDateByMonth() {
		return createDateByMonth;
	}

	public void setCreateDateByMonth(Date createDateByMonth) {
		this.createDateByMonth = createDateByMonth;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}