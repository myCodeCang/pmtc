/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 撮合成功记录Entity
 * @author wyxiang
 * @version 2018-03-20
 */
public class TranscodeBuyLog extends DataEntity<TranscodeBuyLog> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String buyUserName;		// 买家
	private String sellUserName;		// 卖家
	private String buyId;		// 买家订单号
	private String sellId;		// 卖家订单号
	private String num;		// 交易数量
	private String money;		// 交易单价
	private String images;		// 凭证图片
	private String type;		// 0:正常  1：客服处理
	private String status;		// 0：等待买家确认 1：等待卖家确认  2：交易成功  3：交易失败
	private String isCheck;		// 0：未检测  1：以检测
	private Date addDate;		// add_date

	//拓展字段
	private String allUser;
	private String statusBegin;
	private String statusEnd;

	
	public TranscodeBuyLog() {
		super();
	}

	public TranscodeBuyLog(String id){
		super(id);
	}

	@Length(min=0, max=255, message="买家长度必须介于 0 和 255 之间")
	public String getBuyUserName() {
		return buyUserName;
	}

	public void setBuyUserName(String buyUserName) {
		this.buyUserName = buyUserName;
	}
	
	@Length(min=0, max=255, message="卖家长度必须介于 0 和 255 之间")
	public String getSellUserName() {
		return sellUserName;
	}

	public void setSellUserName(String sellUserName) {
		this.sellUserName = sellUserName;
	}
	
	@Length(min=1, max=11, message="买家订单号长度必须介于 1 和 11 之间")
	public String getBuyId() {
		return buyId;
	}

	public void setBuyId(String buyId) {
		this.buyId = buyId;
	}
	
	@Length(min=1, max=11, message="卖家订单号长度必须介于 1 和 11 之间")
	public String getSellId() {
		return sellId;
	}

	public void setSellId(String sellId) {
		this.sellId = sellId;
	}
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@Length(min=0, max=1000, message="凭证图片长度必须介于 0 和 1000 之间")
	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
	
	@Length(min=0, max=1, message="0:正常  1：客服处理长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=1, message="0：等待买家确认 1：等待卖家确认  2：交易成功  3：交易失败长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=1, max=11, message="0：未检测  1：以检测长度必须介于 1 和 11 之间")
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getAllUser() {
		return allUser;
	}

	public void setAllUser(String allUser) {
		this.allUser = allUser;
	}

	public String getStatusBegin() {
		return statusBegin;
	}

	public void setStatusBegin(String statusBegin) {
		this.statusBegin = statusBegin;
	}

	public String getStatusEnd() {
		return statusEnd;
	}

	public void setStatusEnd(String statusEnd) {
		this.statusEnd = statusEnd;
	}
}