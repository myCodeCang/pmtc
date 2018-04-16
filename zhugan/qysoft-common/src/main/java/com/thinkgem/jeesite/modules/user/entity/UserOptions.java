/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 常规配置Entity
 * @author wyxiang
 * @version 2017-04-26
 */
public class UserOptions extends DataEntity<UserOptions> {
	
	private static final long serialVersionUID = 1L;
	
	private int rownum;
	
	private String optionName;		// 配置名
	private String optionValue;		// 配置值
	private String autoload = "1";		// 是否自动加载
	private String groupName;
	
	private String optionLabel;

	/**
	 * 验证模型字段
	 */
	public  void validateModule(boolean isInsert) throws ValidationException {
		if(StringUtils2.isBlank(optionName)){
			throw new ValidationException("配置名不能为空!");
		}
		if(StringUtils2.isBlank(optionValue)){
			throw new ValidationException("配置值不能为空!");
		}
		if(StringUtils2.isBlank(groupName)){
			throw new ValidationException("组名不能为空!");
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


	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(String optionLabel) {
		this.optionLabel = optionLabel;
	}

	public UserOptions() {
		super();
	}

	public UserOptions(String id){
		super(id);
	}

	@Length(min=1, max=64, message="配置名长度必须介于 1 和 64 之间")
	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	
	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	
	@Length(min=1, max=2, message="是否自动加载长度必须介于 1 和 2 之间")
	public String getAutoload() {
		return autoload;
	}

	public void setAutoload(String autoload) {
		this.autoload = autoload;
	}
	
	
	
	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	
}

