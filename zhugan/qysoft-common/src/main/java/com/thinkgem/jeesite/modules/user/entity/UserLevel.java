/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * 用户等级表Entity
 * @author cai
 * @version 2017-03-23
 */
public class UserLevel extends DataEntity<UserLevel> {
	
	private static final long serialVersionUID = 1L;
	private String id;      //编号
	private String levelName;		// 等级名称
	private String levelCode;		// 等级代码
	private BigDecimal money;		// 升级金额
	private int performance;		// 业绩量
	private String sort;		// 排序
	private BigDecimal staticMoney;  //静态奖励
	private BigDecimal staticMoneyHigh;  //静态奖励封顶
	private BigDecimal amountPer; //量奖百分比
	private BigDecimal amountCap; //量奖封顶
	private int staticMoneyDay; //静态奖励天数


	//查询扩展
	private String levelCodeBegin;
	private String levelCodeEnd;

	@Override
	public void preInsert() throws ValidationException {
		validateModule(true);
		super.preInsert();

		//qydo  新增数据验证
	}

	@Override
	public void preUpdate() throws ValidationException {
		validateModule(false);
		super.preUpdate();

		//qydo 修改数据验证不能为空
	}

	/**
	 * 验证模型字段
	 */
	public  void validateModule(boolean isInsert) throws ValidationException {

		if(StringUtils2.isBlank(levelName)){
			throw new ValidationException("等级名称不能为空!");
		}
		if(StringUtils2.isBlank(levelCode)){
			throw new ValidationException("等级编号不能为空!");
		}


	}


	public BigDecimal getAmountPer() {
		return amountPer;
	}

	public void setAmountPer(BigDecimal amountPer) {
		this.amountPer = amountPer;
	}

	public BigDecimal getAmountCap() {
		return amountCap;
	}

	public void setAmountCap(BigDecimal amountCap) {
		this.amountCap = amountCap;
	}

	public int getStaticMoneyDay() {
		return staticMoneyDay;
	}

	public void setStaticMoneyDay(int staticMoneyDay) {
		this.staticMoneyDay = staticMoneyDay;
	}

	public BigDecimal getStaticMoney() {
		return staticMoney;
	}

	public void setStaticMoney(BigDecimal staticMoney) {
		this.staticMoney = staticMoney;
	}

	public BigDecimal getStaticMoneyHigh() {
		return staticMoneyHigh;
	}

	public void setStaticMoneyHigh(BigDecimal staticMoneyHigh) {
		this.staticMoneyHigh = staticMoneyHigh;
	}

	public UserLevel() {
		super();
	}

	public UserLevel(String id){
		super(id);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Length(min=1, max=255, message="等级名称长度必须介于 1 和 255 之间")
	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	@Length(min=1, max=255, message="等级代码长度必须介于 1 和 255 之间")
	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	@Length(min=1, max=8, message="业绩量长度必须介于 1 和 8 之间")
	public int getPerformance() {
		return performance;
	}

	public void setPerformance(int performance) {
		this.performance = performance;
	}
	
	@Length(min=1, max=8, message="排序长度必须介于 1 和 8 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getLevelCodeBegin() {
		return levelCodeBegin;
	}

	public void setLevelCodeBegin(String levelCodeBegin) {
		this.levelCodeBegin = levelCodeBegin;
	}

	public String getLevelCodeEnd() {
		return levelCodeEnd;
	}

	public void setLevelCodeEnd(String levelCodeEnd) {
		this.levelCodeEnd = levelCodeEnd;
	}
}