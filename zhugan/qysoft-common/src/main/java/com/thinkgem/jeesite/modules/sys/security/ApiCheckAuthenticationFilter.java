/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 商城通信秘钥校验
 *
 * @author YanKai
 * @version 2014-5-19
 */
@Service
public class ApiCheckAuthenticationFilter extends
		org.apache.shiro.web.filter.authc.UserFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
									  ServletResponse response, Object mappedValue) {

		boolean  isopen = Global.isOptionOpen("auth_shop","auth_open");

		if(!isopen){
			return false;
		}

		String authpwd = request.getParameter("authpwd");
		if (StringUtils2.isEmpty(authpwd)) {
			return false;
		}

		String optionPwd = Global.getOption("auth_shop", "authpwd");
		if (authpwd.equals(optionPwd)) {
			return true;
		}

		return false;
	}
}