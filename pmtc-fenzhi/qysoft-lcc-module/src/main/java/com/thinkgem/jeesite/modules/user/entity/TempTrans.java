/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * 临时表Entity
 * @author luo
 * @version 2017-10-12
 */
public class TempTrans extends DataEntity<TempTrans> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String userName;		// usernama
	private BigDecimal money;		// money
	
	public TempTrans() {
		super();
	}

	public TempTrans(String id){
		super(id);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
}