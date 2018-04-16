/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 报单中心审核Entity
 * @author cai
 * @version 2017-04-25
 */
public class UserUsercenterLog extends DataEntity<UserUsercenterLog> {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String userName;		// 用户名
	private String status;		// 状态
	private String level;		// 申请级别
	private String centerName;		// 报单中心名字
	private String centerAddress;		// 报单中心地址
	private Date addtime;		// 申请时间
	private String commt;		// 备注

	/**
	 * 验证模型字段
	 */
	public  void validateModule(boolean isInsert) throws ValidationException {

		if(StringUtils2.isBlank(userName)){
			throw new ValidationException("用户名不能为空!");
		}
		if(StringUtils2.isBlank(status)){
			throw new ValidationException("状态不能为空!");
		}
		if(StringUtils2.isBlank(level)){
			throw new ValidationException("级别不能为空!");
		}
		if(StringUtils2.isBlank(centerName)){
			throw new ValidationException("报单中心名字不能为空!");
		}
		if(StringUtils2.isBlank(centerAddress)){
			throw new ValidationException("报单中心地址不能为空!");
		}
		if(addtime == null){
			throw new ValidationException("申请时间不能为空!");
		}


	}


	@Override
	public void preInsert() throws ValidationException {
		validateModule(true);
		super.preInsert();
	}

	@Override
	public void preUpdate() throws  ValidationException{
		validateModule(false);
		super.preUpdate();
	}

	public UserUsercenterLog() {
		super();
	}

	public UserUsercenterLog(String id){
		super(id);
	}

	@Length(min=0, max=100, message="用户名长度必须介于 0 和 100 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=10, message="申请级别长度必须介于 0 和 10 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=100, message="报单中心名字长度必须介于 0 和 100 之间")
	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	
	@Length(min=0, max=255, message="报单中心地址长度必须介于 0 和 255 之间")
	public String getCenterAddress() {
		return centerAddress;
	}

	public void setCenterAddress(String centerAddress) {
		this.centerAddress = centerAddress;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getCommt() {
		return commt;
	}

	public void setCommt(String commt) {
		this.commt = commt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}