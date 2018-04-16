/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;

/**
 * 序列管理Entity
 * @author wyxiang
 * @version 2017-04-30
 */
public class UserSequence extends DataEntity<UserSequence> {
	
	private static final long serialVersionUID = 1L;
	
	private int id;				//序列编号
	
	private String name;		// 序列名称
	private String currentValue;		// 序列值
	private String increment;		// 步长
	private String remarks;			// 备注信息

	/**
	 * 验证模型字段
	 */
	public  void validateModule(boolean isInsert) throws ValidationException {

		if(StringUtils2.isBlank(name)){
			throw new ValidationException("序列名称不能为空!");
		}
		if(StringUtils2.isBlank(currentValue)){
			throw new ValidationException("序列值不能为空!");
		}
		if(StringUtils2.isBlank(increment)){
			throw new ValidationException("步长不能为空!");
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

	public UserSequence() {
		super();
	}

	public UserSequence(String id){
		super(id);
	}

	@Length(min=1, max=50, message="序列名称长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=11, message="序列值长度必须介于 1 和 11 之间")
	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}
	
	@Length(min=1, max=11, message="步长长度必须介于 1 和 11 之间")
	public String getIncrement() {
		return increment;
	}

	public void setIncrement(String increment) {
		this.increment = increment;
	}

	public int getRownum() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Length(min=1, max=255, message="步长长度必须介于 1 和 255 之间")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}