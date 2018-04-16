/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.entity.Principal;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * 表单验证（包含验证码）过滤类
 *
 * @author ThinkGem
 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilter2 extends
        org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

    public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
    public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
    public static final String DEFAULT_MESSAGE_PARAM = "message";
    public static final String DEFAULT_USERTYPE_PARAM = "userType";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
    private String messageParam = DEFAULT_MESSAGE_PARAM;
    @Autowired
    private HttpServletRequest httpServletRequestrequest;

    protected AuthenticationToken createToken(ServletRequest request,
                                              ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        if (password == null) {
            password = "";
        }
        boolean rememberMe = isRememberMe(request);

        String userType = getUserType(request);

        String host = StringUtils2.getRemoteAddr((HttpServletRequest) request);
        String captcha = getCaptcha(request);
        boolean mobile = isMobileLogin(request);
        return new UsernamePasswordToken2(username, password.toCharArray(),
                rememberMe, host, captcha, mobile, userType);
    }

    private String getUserType(ServletRequest request) {
        String userType = WebUtils.getCleanParam(request,
                DEFAULT_USERTYPE_PARAM);
        if (StringUtils2.isBlank(userType)) {
            userType = StringUtils2.toString(
                    request.getAttribute(DEFAULT_USERTYPE_PARAM),
                    StringUtils2.EMPTY);
        }
        if (StringUtils2.isBlank(userType)) {
            userType = Global.USER_WEBSITE;
        }
        return userType;
    }


    private boolean isAccessAllowedCus(ServletRequest request,
                                       ServletResponse response, Object mappedValue) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Subject subject = getSubject(request, response);
        Principal principal = (Principal) subject.getPrincipal();
        if (principal != null) {

            String contextPath = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));


            if (contextPath.startsWith(Global.getAdminPath())) {
                if (principal.isAdminUser()) {
                    return true;
                }
            }

            if (contextPath.startsWith(Global.getFrontPath())) {
                if (contextPath.equals(Global.getFrontPath() + "/login") && "POST".equals(httpRequest.getMethod())) {
                    subject.logout();
                    return false;
                }
                if (principal.isWebSiteUser()) {
                    return true;
                }
            }

            return false;
        }

        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) {
        return this.isAccessAllowedCus(request, response, mappedValue) ||
                (!isLoginRequest(request, response) && isPermissive(mappedValue));
    }


    /**
     * 获取登录用户名
     */
    protected String getUsername(ServletRequest request) {
        String username = super.getUsername(request);
        if (StringUtils2.isBlank(username)) {
            username = StringUtils2
                    .toString(request.getAttribute(getUsernameParam()),
                            StringUtils2.EMPTY);
        }
        return username;
    }

    /**
     * 获取登录密码
     */
    @Override
    protected String getPassword(ServletRequest request) {
        String password = super.getPassword(request);
        if (StringUtils2.isBlank(password)) {
            password = StringUtils2
                    .toString(request.getAttribute(getPasswordParam()),
                            StringUtils2.EMPTY);
        }
        return password;
    }

    /**
     * 获取记住我
     */
    @Override
    protected boolean isRememberMe(ServletRequest request) {
        String isRememberMe = WebUtils.getCleanParam(request,
                getRememberMeParam());
        if (StringUtils2.isBlank(isRememberMe)) {
            isRememberMe = StringUtils2.toString(
                    request.getAttribute(getRememberMeParam()),
                    StringUtils2.EMPTY);
        }
        return StringUtils2.toBoolean(isRememberMe);
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    public String getMobileLoginParam() {
        return mobileLoginParam;
    }

    protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }

    public String getMessageParam() {
        return messageParam;
    }

    /**
     * 登录成功之后跳转URL
     */
    public String getSuccessUrl() {
        return super.getSuccessUrl();
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request,
                                        ServletResponse response) throws Exception {
        // Principal p = UserUtils.getPrincipal();
        // if (p != null && !p.isMobileLogin()){
        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
        // }else{
        // super.issueSuccessRedirect(request, response);
        // }
    }

    /**
     * 登录失败调用事件
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
                                     AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        String className = e.getClass().getName(), message = "";
        if (IncorrectCredentialsException.class.getName().equals(className)
                || UnknownAccountException.class.getName().equals(className)
                || AuthenticationException.class.getName().equals(className)) {
            Object attribute =  UserInfoUtils.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
//            if(attribute == null || "en_US".equals(attribute.toString())){
//                message = "User name or password error";
//            }else {
                message = "用户或密码错误, 请重试.";
//            }
        } else if (e.getMessage() != null
                && StringUtils2.startsWith(e.getMessage(), "msg:")) {
            message = StringUtils2.replace(e.getMessage(), "msg:", "");
        } else {
            Object attribute =  UserInfoUtils.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
//            if(attribute == null || "en_US".equals(attribute.toString())){
//                message = "System Problems";
//            }else {
                message = "系统出现点问题，请稍后再试！";
//            }

            e.printStackTrace(); // 输出到控制台
        }
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        return true;
    }

}