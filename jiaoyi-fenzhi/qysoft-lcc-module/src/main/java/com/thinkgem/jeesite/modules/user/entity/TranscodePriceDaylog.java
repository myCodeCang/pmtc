/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * k线走势Entity
 * @author wyxiang
 * @version 2018-03-20
 */
public class TranscodePriceDaylog extends DataEntity<TranscodePriceDaylog> {
	
	private static final long serialVersionUID = 1L;
	

	
	private BigDecimal startMoney;		// 开盘价
	private BigDecimal nowMoney;		// 现价
	private BigDecimal amount;		// 交易量

	//扩展字段
	private Date createDateBegin;		// 开始时间
	private Date createDateEnd;		// 结束时间
	private String minId;
	private String maxId;

	
	public TranscodePriceDaylog() {
		super();
	}

	public TranscodePriceDaylog(String id){
		super(id);
	}

	public BigDecimal getStartMoney() {
		return startMoney;
	}

	public void setStartMoney(BigDecimal startMoney) {
		this.startMoney = startMoney;
	}

	public BigDecimal getNowMoney() {
		return nowMoney;
	}

	public void setNowMoney(BigDecimal nowMoney) {
		this.nowMoney = nowMoney;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getMinId() {
		return minId;
	}

	public void setMinId(String minId) {
		this.minId = minId;
	}

	public String getMaxId() {
		return maxId;
	}

	public void setMaxId(String maxId) {
		this.maxId = maxId;
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