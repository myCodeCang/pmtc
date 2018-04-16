/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 通信记录Entity
 * @author xueyuliang
 * @version 2017-06-08
 */
public class ShopCommOrder extends DataEntity<ShopCommOrder> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String userName;		// 用户帐号
	private String changeMoney;		// 变化金额
	private String commt;		// 备注
	private String status;		// 状态
	private String ischeck;		// 是否统计
	private String orderId;		// 订单号
	private String shopId;		// 商城编号
	private String moneyType;		// 变化类型 1金币 2消费积分 3苏陕积分
	private String score;		// 消费积分
	
	public ShopCommOrder() {
		super();
	}

	public ShopCommOrder(String id){
		super(id);
	}

	@Length(min=0, max=100, message="用户帐号长度必须介于 0 和 100 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getChangeMoney() {
		return changeMoney;
	}

	public void setChangeMoney(String changeMoney) {
		this.changeMoney = changeMoney;
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
	
	@Length(min=0, max=255, message="订单号长度必须介于 0 和 255 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=2, message="商城编号长度必须介于 0 和 2 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	@Length(min=0, max=5, message="变化类型 1金币 2消费积分 3苏陕积分长度必须介于 0 和 5 之间")
	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	

	
}