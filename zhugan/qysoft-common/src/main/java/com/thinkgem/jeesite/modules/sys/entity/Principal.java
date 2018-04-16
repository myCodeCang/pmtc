package com.thinkgem.jeesite.modules.sys.entity;

import java.io.Serializable;

import javax.servlet.ServletRequest;

import org.apache.shiro.web.util.WebUtils;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;

/**
 * 授权用户信息
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id; // 编号
	private String loginName; // 登录名
	private String name; // 姓名
	private boolean mobileLogin; // 是否手机登录
	private String userType;

	// private Map<String, Object> cacheMap;



	public Principal(User user, boolean mobileLogin,String userType) {
		this.id = user.getId();
		this.loginName = user.getLoginName();
		this.name = user.getName();
		this.mobileLogin = mobileLogin;
		this.userType = userType;
	}
	
	public Principal(UserUserinfo user, boolean mobileLogin,String userType) {
		this.id = user.getId();
		this.loginName = user.getUserName();
		this.name = user.getTrueName();
		this.mobileLogin = mobileLogin;
		this.userType = userType;
	}

	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getId() {
		return id;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getName() {
		return name;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}

	// @JsonIgnore
	// public Map<String, Object> getCacheMap() {
	// if (cacheMap==null){
	// cacheMap = new HashMap<String, Object>();
	// }
	// return cacheMap;
	// }

	/**
	 * 获取SESSIONID
	 */
	public String getSessionid() {
		try {
			return (String) UserUtils.getSession().getId();
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public String toString() {
		return id;
	}
	
	
	/**
	 * 判断是否前台用户
	 * @return
	 */
	public boolean isWebSiteUser(){
		

		if (this.getUserType().equals(Global.USER_WEBSITE)) {

			return true;

		} 
		
		return false;
		
		
	}
	
	/**
	 * 判断是否后台用户
	 * @return
	 */
	public boolean isAdminUser(){
		
		
		if (this.getUserType().equals(Global.USER_ADMIN)) {

			return true;
		} 
		
		return false;
	}

}