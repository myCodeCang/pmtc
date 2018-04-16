/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 团队等级Entity
 * @author 薛玉良
 * @version 2017-08-11
 */
public class UserTeamLevel extends DataEntity<UserTeamLevel> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String teamName;		// 等级名称
	private BigDecimal conditionOne;
	private int directPeopleNum;		// 直推人数条件
	private int teamPeopleNum;		// 团队总人数条件

	/**
	 * 添加字段
	 */

	private String directEarnings;		// 直推收益
	private String indirectEarnings;		// 间推收益
	private String teamCode;		// 等级代码
	private int indirectLevelno;		// 间推层数
	private String message;		// 备注
	private String everyEarnings; //每日复利
	private String compoundInterest;  //领导奖比例
	
	public UserTeamLevel() {
		super();
	}

	public UserTeamLevel(String id){
		super(id);
	}

	@Length(min=0, max=255, message="等级名称长度必须介于 0 和 255 之间")
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getDirectPeopleNum() {
		return directPeopleNum;
	}

	public void setDirectPeopleNum(int directPeopleNum) {
		this.directPeopleNum = directPeopleNum;
	}

	public int getTeamPeopleNum() {
		return teamPeopleNum;
	}

	public String getEveryEarnings() {
		return everyEarnings;
	}

	public void setEveryEarnings(String everyEarnings) {
		this.everyEarnings = everyEarnings;
	}

	public void setTeamPeopleNum(int teamPeopleNum) {
		this.teamPeopleNum = teamPeopleNum;
	}

	@Length(min=0, max=255, message="直推收益长度必须介于 0 和 255 之间")
	public String getDirectEarnings() {
		return directEarnings;
	}

	public void setDirectEarnings(String directEarnings) {
		this.directEarnings = directEarnings;
	}
	
	@Length(min=0, max=255, message="间推收益长度必须介于 0 和 255 之间")
	public String getIndirectEarnings() {
		return indirectEarnings;
	}

	public void setIndirectEarnings(String indirectEarnings) {
		this.indirectEarnings = indirectEarnings;
	}
	
	@Length(min=0, max=10, message="等级代码长度必须介于 0 和 10 之间")
	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public int getIndirectLevelno() {
		return indirectLevelno;
	}

	public void setIndirectLevelno(int indirectLevelno) {
		this.indirectLevelno = indirectLevelno;
	}

	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BigDecimal getConditionOne() {
		return conditionOne;
	}

	public void setConditionOne(BigDecimal conditionOne) {
		this.conditionOne = conditionOne;
	}

	public String getCompoundInterest() {
		return compoundInterest;
	}

	public void setCompoundInterest(String compoundInterest) {
		this.compoundInterest = compoundInterest;
	}
}