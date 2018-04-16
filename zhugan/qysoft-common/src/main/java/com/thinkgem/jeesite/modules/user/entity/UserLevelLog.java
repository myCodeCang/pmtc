/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户升级明细表Entity
 * @author luojie
 * @version 2017-04-25
 */
public class UserLevelLog extends DataEntity<UserLevelLog> {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String userName;		// 用户名
	private UserUserinfo userUserinfo; //用户对象
	private String updateType;		// 升级类型
	private String oldLevel;		// 之前等级
	private String newLevel;		// 现在等级
	private String commt;		// 备注
	private String trueName;     //真实姓名
	private int performance; //业绩量
	private String ischeck ; //是否检查


	private Date createDateBegin;
	private Date createDateEnd;

	private String oldLevelName;		// 之前等级
	private String newLevelName;		// 现在等级

	/**
	 * 验证模型字段
	 */
	public  void validateModule(boolean isInsert) throws ValidationException {

		if(StringUtils2.isBlank(userName)){
			throw new ValidationException("用户名不能为空!");
		}
		if(StringUtils2.isBlank(updateType)){
			throw new ValidationException("升级类型不能为空!");
		}
		if(StringUtils2.isBlank(oldLevel)){
			throw new ValidationException("之前不能为空!");
		}
		if(StringUtils2.isBlank(newLevel)){
			throw new ValidationException("现在等级不能为空!");
		}
		if(StringUtils2.isBlank(commt)){
			throw new ValidationException("备注不能为空!");
		}

	}

	@Override
	public void preInsert() throws ValidationException {
		validateModule(true);
		super.preInsert();

	}

	@Override
	public void preUpdate() throws ValidationException {
		validateModule(false);
		super.preUpdate();



	}


	public UserLevelLog() {
		super();
	}

	public UserLevelLog(String id){
		super(id);
	}

	@Length(min=0, max=100, message="用户账户长度必须介于 0 和 100 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public UserUserinfo getUserUserinfo() {
		return userUserinfo;
	}

	public void setUserUserinfo(UserUserinfo userUserinfo) {
		this.userUserinfo = userUserinfo;
	}

	@Length(min=0, max=1, message="升级类型长度必须介于 0 和 1 之间")
	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getCommt() {
		return commt;
	}

	public void setCommt(String commt) {
		this.commt = commt;
	}
	
	@Length(min=0, max=50, message="之前等级长度必须介于 0 和 50 之间")
	public String getOldLevel() {
		return oldLevel;
	}

	public void setOldLevel(String oldLevel) {
		this.oldLevel = oldLevel;
	}
	
	@Length(min=0, max=50, message="现在等级长度必须介于 0 和 50 之间")
	public String getNewLevel() {
		return newLevel;
	}

	public void setNewLevel(String newLevel) {
		this.newLevel = newLevel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public int getPerformance() {
		return performance;
	}

	public void setPerformance(int performance) {
		this.performance = performance;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public String getOldLevelName() {
		return oldLevelName;
	}

	public void setOldLevelName(String oldLevelName) {
		this.oldLevelName = oldLevelName;
	}

	public String getNewLevelName() {
		return newLevelName;
	}

	public void setNewLevelName(String newLevelName) {
		this.newLevelName = newLevelName;
	}
}