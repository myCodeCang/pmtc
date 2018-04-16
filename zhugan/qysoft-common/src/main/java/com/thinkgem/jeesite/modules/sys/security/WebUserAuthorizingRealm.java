/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import com.thinkgem.jeesite.common.utils.VerifyUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Principal;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;

/**
 * 系统安全认证实现类
 * 
 * @author ThinkGem
 * @version 2014-7-5
 */
@Service
// @DependsOn({"userDao","roleDao","menuDao"})
public class WebUserAuthorizingRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private SystemService systemService;
	
	private UserUserinfoService userUserinfoService;

	public WebUserAuthorizingRealm() {
		this.setCachingEnabled(false);
	}

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) {
		UsernamePasswordToken2 token = (UsernamePasswordToken2) authcToken;
		String userName = (String)authcToken.getPrincipal();

		int activeSessionSize = getSystemService().getSessionDao()
				.getActiveSessions(false).size();
		if (logger.isDebugEnabled()) {
			logger.debug("login submit, active session size: {}, username: {}",
					activeSessionSize, token.getUsername());
		}

		if (token.getUserType().equals(Global.USER_WEBSITE)) {
			// 校验登录验证码
			/*if (UserInfoUtils.isValidateCodeLogin(token.getUsername(), false,
					false)) {
				Session session = UserInfoUtils.getSession();
				String code = (String) session
						.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
				if (token.getCaptcha() == null
						|| !token.getCaptcha().toUpperCase().equals(code)) {
					throw new AuthenticationException("msg:验证码错误, 请重试.");
				}
			}*/

			// 校验用户名密码
			UserUserinfo user = null;
			user =	getUserInfoService().getByName(token.getUsername());
			if(Global.TRUE.equals(Global.getConfig("webMobileLogin")) && VerifyUtils.isMobile(token.getUsername())){
				user =	getUserInfoService().getByMobile(token.getUsername());
			}else if(Global.TRUE.equals(Global.getConfig("webUserNameLogin"))){
				user =	getUserInfoService().getByName(token.getUsername());
			}


//			if(VerifyUtils.isMobile(token.getUsername()) ){
//				user =	getUserInfoService().getByMobile(token.getUsername());
//			}
//			else
//			{
//				user =	getUserInfoService().getByName(token.getUsername());
//			}

			if (user != null) {
				if ("-1".equals(user.getUserStatus())) {
					throw new AuthenticationException("msg:该已帐号禁止登录.");
				}
				if(!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))){
					//处理session
					DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
					DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
					Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();//获取当前已登录的用户session列表
					for (Session session : sessions) {
						//清除该用户以前登录时保存的session
						if (user.getId().equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
							sessionManager.getSessionDAO().delete(session);
						}
					}
				}

				//插入最后登录时间
				getUserInfoService().updateUserLoginInfo(user);

				byte[] salt = Encodes.decodeHex(user.getUserPass().substring(0,
						16));
				return new SimpleAuthenticationInfo(new Principal(user,
						token.isMobileLogin(),token.getUserType()), user.getUserPass()
						.substring(16), ByteSource.Util.bytes(salt), getName());
			} else {
				return null;
			}
		}
		
		return null;

	}

	/**
	 * 获取权限授权信息，如果缓存中存在，则直接从缓存中获取，否则就重新获取， 登录成功后调用
	 */
	protected AuthorizationInfo getAuthorizationInfo(
			PrincipalCollection principals) {
		if (principals == null) {
			return null;
		}

		AuthorizationInfo info = null;
		
		Principal principal = (Principal) getAvailablePrincipal(principals);
		if(principal.getUserType().equals(Global.USER_ADMIN)){
			info = (AuthorizationInfo) UserUtils
					.getCache(UserUtils.CACHE_AUTH_INFO);

			if (info == null) {
				info = doGetAuthorizationInfo(principals);
				if (info != null) {
					UserUtils.putCache(UserUtils.CACHE_AUTH_INFO, info);
				}
			}
		}
		else if(principal.getUserType().equals(Global.USER_WEBSITE)){
			
			info = (AuthorizationInfo) UserInfoUtils
					.getCache(UserInfoUtils.CACHE_AUTH_INFO);

			if (info == null) {
				info = doGetAuthorizationInfo(principals);
				if (info != null) {
					UserInfoUtils.putCache(UserInfoUtils.CACHE_AUTH_INFO, info);
				}
			}
		}
		

		return info;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		// 获取当前已登录的用户
		if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))) {
			Collection<Session> sessions = getSystemService().getSessionDao()
					.getActiveSessions(true, principal, UserUtils.getSession());
			if (sessions.size() > 0) {
				// 如果是登录进来的，则踢出已在线用户
				if (UserUtils.getSubject().isAuthenticated()) {
					for (Session session : sessions) {
						getSystemService().getSessionDao().delete(session);
					}
				}
				// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
				else {
					UserUtils.getSubject().logout();
					throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
				}
			}
		}
		User user = getSystemService().getUserByLoginName(
				principal.getLoginName());
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<Menu> list = UserUtils.getMenuList();
			for (Menu menu : list) {
				if (StringUtils.isNotBlank(menu.getPermission())) {
					// 添加基于Permission的权限信息
					for (String permission : StringUtils.split(
							menu.getPermission(), ",")) {
						info.addStringPermission(permission);
					}
				}
			}
			// 添加用户权限
			info.addStringPermission("user");
			// 添加用户角色信息
			for (Role role : user.getRoleList()) {
				info.addRole(role.getEnname());
			}
			// 更新登录IP和时间
			getSystemService().updateUserLoginInfo(user);
			// 记录登录日志
			LogUtils.saveLog(Servlets.getRequest(), "系统登录");
			return info;
		} else {
			return null;
		}
	}

	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}

	@Override
	protected boolean[] isPermitted(List<Permission> permissions,
			AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
			for (Permission permission : permissions) {
				authorizationValidate(permission);
			}
		}
		return super.isPermitted(permissions, info);
	}

	@Override
	public boolean isPermitted(PrincipalCollection principals,
			Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}

	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions,
			AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
			for (Permission permission : permissions) {
				authorizationValidate(permission);
			}
		}
		return super.isPermittedAll(permissions, info);
	}

	/**
	 * 授权验证方法
	 * 
	 * @param permission
	 */
	private void authorizationValidate(Permission permission) {
		// 模块授权预留接口
	}

	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
				SystemService.HASH_ALGORITHM);
		matcher.setHashIterations(SystemService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

	// /**
	// * 清空用户关联权限认证，待下次使用时重新加载
	// */
	// public void clearCachedAuthorizationInfo(Principal principal) {
	// SimplePrincipalCollection principals = new
	// SimplePrincipalCollection(principal, getName());
	// clearCachedAuthorizationInfo(principals);
	// }

	/**
	 * 清空所有关联认证
	 * 
	 * @Deprecated 不需要清空，授权缓存保存到session中
	 */
	@Deprecated
	public void clearAllCachedAuthorizationInfo() {
		// Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		// if (cache != null) {
		// for (Object key : cache.keys()) {
		// cache.remove(key);
		// }
		// }
	}

	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null) {
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}

	
	/**
	 * 获取用户对象
	 */
	public UserUserinfoService getUserInfoService() {
		if (userUserinfoService == null) {
			userUserinfoService = SpringContextHolder.getBean("userUserinfoService");
		}
		return userUserinfoService;
	}


	
}
