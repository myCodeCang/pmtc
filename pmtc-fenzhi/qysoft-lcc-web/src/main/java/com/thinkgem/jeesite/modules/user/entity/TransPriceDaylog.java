/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 物品价格记录表Entity
 * @author luo
 * @version 2018-02-10
 */
public class TransPriceDaylog extends DataEntity<TransPriceDaylog> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String groupId;		// 交易品组编号
	private String nowMoney;		// 当前价
	private String startMoney;		// 开盘价
	private String endMoney;		// 收盘价
	private String higMoney;		// 最高价
	private String lowMoney;		// 最低价
	private String amount;		// 交易量
	private Date addDate;		// add_date
	private String minId;
	private String maxId;
	
	public TransPriceDaylog() {
		super();
	}

	public TransPriceDaylog(String id){
		super(id);
	}

	@Length(min=0, max=11, message="交易品组编号长度必须介于 0 和 11 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	public String getNowMoney() {
		return nowMoney;
	}

	public void setNowMoney(String nowMoney) {
		this.nowMoney = nowMoney;
	}
	
	public String getStartMoney() {
		return startMoney;
	}

	public void setStartMoney(String startMoney) {
		this.startMoney = startMoney;
	}
	
	public String getEndMoney() {
		return endMoney;
	}

	public void setEndMoney(String endMoney) {
		this.endMoney = endMoney;
	}
	
	public String getHigMoney() {
		return higMoney;
	}

	public void setHigMoney(String higMoney) {
		this.higMoney = higMoney;
	}
	
	public String getLowMoney() {
		return lowMoney;
	}

	public void setLowMoney(String lowMoney) {
		this.lowMoney = lowMoney;
	}
	
	@Length(min=0, max=11, message="交易量长度必须介于 0 和 11 之间")
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	

	
}