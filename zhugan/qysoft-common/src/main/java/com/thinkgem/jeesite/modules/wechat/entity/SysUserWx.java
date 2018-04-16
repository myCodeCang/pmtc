/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wechat.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import org.hibernate.validator.constraints.Length;

/**
 * 微信用户关联Entity
 * @author kevin
 * @version 2017-07-30
 */
public class SysUserWx extends DataEntity<SysUserWx> {
	
	private static final long serialVersionUID = 1L;

	private String openId;		// 微信open_id
	private UserUserinfo user;		// 用户id
	private String unionId;		// 微信Union_id
	private String wxImg;		// 微信头像
	private String wxMember;		// 微信号
	private String wxNickname;		// 微信昵称
	private String isSubscribe;		//是否关注公众号 0: 未关注  1: 已关注
	
	public SysUserWx() {
		super();
	}

	public SysUserWx(String id){
		super(id);
	}

	@Length(min=1, max=128, message="微信open_id长度必须介于 1 和 128 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public UserUserinfo getUser() {
		return user;
	}

	public void setUser(UserUserinfo user) {
		this.user = user;
	}
	
	@Length(min=0, max=255, message="微信Union_id长度必须介于 0 和 255 之间")
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
	@Length(min=0, max=255, message="微信头像长度必须介于 0 和 255 之间")
	public String getWxImg() {
		return wxImg;
	}

	public void setWxImg(String wxImg) {
		this.wxImg = wxImg;
	}
	
	@Length(min=0, max=255, message="微信号长度必须介于 0 和 255 之间")
	public String getWxMember() {
		return wxMember;
	}

	public void setWxMember(String wxMember) {
		this.wxMember = wxMember;
	}
	
	@Length(min=0, max=255, message="微信昵称长度必须介于 0 和 255 之间")
	public String getWxNickname() {
		return wxNickname;
	}

	public void setWxNickname(String wxNickname) {
		this.wxNickname = wxNickname;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
}