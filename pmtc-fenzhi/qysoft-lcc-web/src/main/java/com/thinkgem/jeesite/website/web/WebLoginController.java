/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.website.web;

import com.google.common.collect.Maps;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO2;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.sys.entity.Principal;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilter2;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.user.entity.UserMsm;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.UserMsmService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 网站Controller
 *
 * @author ThinkGem
 * @version 2013-5-29
 */
@Controller
@RequestMapping(value = "${frontPath}")
public class WebLoginController extends WebBaseController {


    @Resource
    private SessionDAO2 sessionDAO2;

    @Resource
    private UserUserinfoService userUserinfoService;

    @Resource
    private UserMsmService msmService;

    @Resource
    private UserMsmService userMsmService;

    @ModelAttribute
    public void init(Model model) {


    }

    /**
     * 管理登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        //Principal principal = UserInfoUtils.getPrincipal();

        // 如果已登录，再次访问主页，则退出原账号。
        if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
            CookieUtils.setCookie(response, "LOGINED", "false");
        }

        //设置默认检测验证码
        Session session = UserInfoUtils.getSession();
        session.setAttribute("checkValidateCode", "true");
        model.addAttribute("isValidateCodeLogin", true);


//        // 如果已经登录，则跳转到管理首页
//        if (principal != null && !principal.isMobileLogin()) {
//            return "redirect:" + frontPath;
//        }

        return themesPath + "/login";
    }

    /**
     * 验证码校验
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getVerificationCode", method = RequestMethod.POST)
    public String getVerificationCode(HttpServletRequest request, HttpServletResponse response, Model model) {
        Session session = UserInfoUtils.getSession();
        String code = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
        String verificationCode = request.getParameter("validateCode");
        if (request.getParameter("validateCode") == null
                || !request.getParameter("validateCode").toUpperCase().equals(code)) {

            Object attribute = request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
            if(attribute == null){
                return error("验证码错误!", response, model);
            }
            if ("en_US".equals(attribute.toString())) {
                return error("Verification code error", response, model);
            }

        }
        return success("", response, model);
    }

    /**
     * 登录失败，真正登录的POST请求由Filter完成
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
        Principal principal = UserInfoUtils.getPrincipal();

        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter2.DEFAULT_USERNAME_PARAM);
        username = username.toLowerCase();
        boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter2.DEFAULT_REMEMBER_ME_PARAM);
        boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter2.DEFAULT_MOBILE_PARAM);
        String exception = (String) request.getAttribute(FormAuthenticationFilter2.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String message = (String) request.getAttribute(FormAuthenticationFilter2.DEFAULT_MESSAGE_PARAM);


        // 如果已经登录，则跳转到管理首页
        if (principal != null) {
            if (mobile) {
                return success("登录成功!!", response, model);
            }
            return "redirect:" + frontPath;
        }


        Session session = UserInfoUtils.getSession();
        String code = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
        if (request.getParameter("validateCode") == null || !request.getParameter("validateCode").toUpperCase().equals(code)) {
            return error("验证码错误!", response, model);

        }


        if (StringUtils2.isBlank(message) || StringUtils2.equals(message, "null")) {
            message = "用户或密码错误, 请重试.";
        }

        model.addAttribute(FormAuthenticationFilter2.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute(FormAuthenticationFilter2.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
        model.addAttribute(FormAuthenticationFilter2.DEFAULT_MOBILE_PARAM, mobile);
        model.addAttribute(FormAuthenticationFilter2.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
        model.addAttribute(FormAuthenticationFilter2.DEFAULT_MESSAGE_PARAM, message);

        if (logger.isDebugEnabled()) {
            logger.debug("login fail, active session size: {}, message: {}, exception: {}",
                    sessionDAO2.getActiveSessions(false).size(), message, exception);
        }

        // 非授权异常，登录失败，验证码加1。
        if (!UnauthorizedException.class.getName().equals(exception)) {
            model.addAttribute("isValidateCodeLogin", UserInfoUtils.isValidateCodeLogin(username, true, false));
        }

        // 非授权异常，登录失败，验证码加1。
        if (AuthenticationException.class.getName().equals(exception)) {
            return error(message, response, model);
        }

        // 验证失败清空验证码
        request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());


        // 如果是手机登录，则返回JSON字符串
        if (mobile || isAjaxRequest(request)) {
            return error("登录失败,用户名或密码错误!", response, model);
        }

        return themesPath + "/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerget(HttpServletRequest request, HttpServletResponse response, Model model) {
        String userName = request.getParameter("tj");
        model.addAttribute("userName", userName);
        return themesPath + "/register";
    }

    /**
     * 用户注册
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest request, HttpServletResponse response, Model model) {

        String mobile = request.getParameter("mobile");
        String userName = request.getParameter("userName");
        String password = request.getParameter("userPass");
        String validCode = request.getParameter("validCode");
        String walterName = request.getParameter("walterName");
        String trueName = request.getParameter("trueName");
        String userNicename = request.getParameter("userNicename");

        UserUserinfo walterUser = userUserinfoService.getByName(walterName);

        if (StringUtils2.isBlank(userName)) {
            return error("账号不能为空", response, model);
        }

        //用户编号转小写
        userName = userName.toLowerCase();

        if (!VerifyUtils.isAllowed(userName)) {
            return error("账号有误:应为7到10位小写字母数字组合", response, model);
        }
        //用户名转小写
        userName = userName.toLowerCase().trim();

        if (null == walterUser) {
            return error("当前推荐人不存在!", response, model);
        }

        if (StringUtils2.isBlank(validCode)) {
            return error("失败:验证码不能为空!", response, model);
        } else {
            if (!msmService.checkVerifyCode(mobile, validCode)) {
                return error("失败:验证码错误!", response, model);
            }
        }


        UserUserinfo userinfo = new UserUserinfo();

        userinfo.setMobile(mobile);
        userinfo.setUserPass(password);
        userinfo.setUserName(userName);
        userinfo.setTrueName(trueName);
        userinfo.setUserNicename(userNicename);
        userinfo.setBankPassword(password);
        userinfo.setIsShop(EnumUtil.YesNO.NO.toString());
        userinfo.setRemarks(UUID.randomUUID().toString().replace("-", ""));
        userinfo.setWalterName(walterUser.getUserName().toLowerCase().trim());
        userinfo.setServerName(walterUser.getUserName().toLowerCase().trim());
        userinfo.setParentName(walterUser.getUserName().toLowerCase().trim());
        userinfo.setRenZheng("1");

        userinfo.setUserLevelId("1");
        userinfo.setIsUsercenter("0");
        userinfo.setUserType("0");
        userinfo.setActiveStatus("0"); //默认为升级


        try {
            userUserinfoService.save(userinfo);
            String msgTemplate = Global.getOption("system_sms", "lk_msg_registe");
            if (StringUtils2.isNotBlank(msgTemplate)) {
                msmService.pushMessage(mobile, userName, userNicename, msgTemplate);
            }
            //注册成功之后删除验证码
            Optional<UserMsm> userMsm = msmService.getByUserName(mobile);
            msmService.delete(userMsm.get());

            return success("成功!", "", response, model);
        } catch (DataAccessException exception) {
            return error("服务器正忙,请稍后再试!", "", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        } catch (Exception e) {
            return error("失败!", response, model);
        }
    }


    /**
     * 首页忘记密码
     *
     * @return
     */
    @RequestMapping(value = {"/homeForgetPass"}, method = RequestMethod.GET)
    public String homeForgetPass(HttpServletRequest request, HttpServletResponse response, Model model) {

        return qyview("/user/homeForgetPass", request, model);
    }







}
