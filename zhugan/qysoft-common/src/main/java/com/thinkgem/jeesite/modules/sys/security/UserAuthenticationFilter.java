/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.entity.Principal;

/**
 * 表单验证（包含验证码）过滤类
 *
 * @author ThinkGem
 * @version 2014-5-19
 */
@Service
public class UserAuthenticationFilter extends
		org.apache.shiro.web.filter.authc.UserFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
									  ServletResponse response, Object mappedValue) {

		if (isLoginRequest(request, response)) {
			return true;
		} else {
			Subject subject = getSubject(request, response);
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {

				String contextPath =  WebUtils.getPathWithinApplication(WebUtils.toHttp(request));


				if (contextPath.startsWith(Global.getAdminPath())) {
					if (principal.isAdminUser()) {
						return true;
					}
				}

				if (contextPath.startsWith(Global.getFrontPath())) {
					if (principal.isWebSiteUser()) {
						return true;
					}
				}

				return false;
			}

			return false;
		}

	}

}