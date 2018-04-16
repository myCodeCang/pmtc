/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wechat.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 微信关键字回复Entity
 * @author kevin
 * @version 2017-07-30
 */
public class SysWxKeyword extends DataEntity<SysWxKeyword> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String keyword;		// 关键字
	private String reply;		// 回复内容
	private String msgType;
	
	public SysWxKeyword() {
		super();
	}

	public SysWxKeyword(String id){
		super(id);
	}

	@Length(min=1, max=64, message="关键字长度必须介于 1 和 64 之间")
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Length(min=1, max=255, message="回复内容长度必须介于 1 和 255 之间")
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
}