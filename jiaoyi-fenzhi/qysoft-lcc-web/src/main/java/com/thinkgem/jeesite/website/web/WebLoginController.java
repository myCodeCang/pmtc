/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.website.web;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO2;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.config.EnumTransUtil;
import com.thinkgem.jeesite.modules.cms.entity.Site;
import com.thinkgem.jeesite.modules.cms.utils.CmsUtils;
import com.thinkgem.jeesite.modules.sys.entity.Principal;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilter2;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.user.entity.*;
import com.thinkgem.jeesite.modules.user.service.*;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private UserPhotoService userPhotoService;

    @Resource
    private UserUserinfoService userUserinfoService;

    @Resource
    private UserMsmService msmService;

    @Resource
    private UserMsmService userMsmService;

    @Resource
    private TranscodePriceDaylogService daylogService;

    @Resource
    private TranscodeBuyService transcodeBuyService;

    @Resource
    private TranscodeBuyLogService transcodeBuyLogService;





    /**
     * 管理登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {

        // 如果已登录，再次访问主页，则退出原账号。
        if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
            CookieUtils.setCookie(response, "LOGINED", "false");
        }
        //设置默认检测验证码
        Session session = UserInfoUtils.getSession();
        session.setAttribute("checkValidateCode", "true");
        model.addAttribute("isValidateCodeLogin", true);

        return themesPath + "/login";
    }

    /**
     * 首页管理登录
     */
    @RequestMapping(value = "/indexl", method = RequestMethod.GET)
    public String  indexl (HttpServletRequest request, HttpServletResponse response, Model model) {
        boolean isLogin = false;
        UserUserinfo userUserinfo = userUserinfoService.get(UserInfoUtils.getUser());
        //检测是否登录
        if (userUserinfo != null){
            isLogin = true;
        }
        UserPhoto userPhoto=new UserPhoto();
        userPhoto.setkeyword("index_img");
        List <UserPhoto> list1=userPhotoService.findList(userPhoto);

            //************************kline
        try{
            List<TranscodePriceDaylog> allList = daylogService.findList(new TranscodePriceDaylog());
            //周成交
            TranscodePriceDaylog priceDaylog = new TranscodePriceDaylog();
            priceDaylog.setCreateDateBegin(DateUtils2.getDiffDatetime(new Date(),-6));
            priceDaylog.setCreateDateEnd(new Date());
            List<TranscodePriceDaylog> weekList = daylogService.findList(priceDaylog);
            BigDecimal weekMoney = weekList.stream().map(p -> p.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);

            //月成交
            priceDaylog.setCreateDateBegin(DateUtils2.getDiffDatetime(new Date(),-29));
            List<TranscodePriceDaylog> monthList = daylogService.findList(priceDaylog);
            BigDecimal  monthMoney = monthList.stream().map(p ->p.getAmount()).reduce(BigDecimal.ZERO,BigDecimal::add);


            model.addAttribute("weekAmount",weekMoney);
            model.addAttribute("monthAmount",monthMoney);

            if(allList != null && allList.size() > 0){
                model.addAttribute("dayAmount",allList.get(allList.size()-1).getAmount());
                BigDecimal allAmount = allList.stream().map(p -> p.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                model.addAttribute("allAmount",allAmount);
            }else {
                model.addAttribute("dayAmount",BigDecimal.ZERO);
                model.addAttribute("allAmount",BigDecimal.ZERO);
            }

            model.addAttribute("priceList", allList);
            //K*********************************
        }catch (Exception e){
            return error("k线获取失败",response,model);
        }

        TranscodePriceDaylog nowLog = daylogService.getNowLog();
        BigDecimal nowMoney = BigDecimal.ZERO;
        if (nowLog != null){
            nowMoney = nowLog.getNowMoney();
        }
        BigDecimal buyNowNum = transcodeBuyService.sumNowNumByTypeAndDate(EnumUtil.TransCodeBuyType.buy.toString(), new Date());
        BigDecimal sellNowNum = transcodeBuyService.sumNowNumByTypeAndDate(EnumUtil.TransCodeBuyType.sell.toString(),new Date());
        TranscodeBuyLog transcodeBuyLog = new TranscodeBuyLog();
        transcodeBuyLog.setCreateDate(new Date());
        transcodeBuyLog.setTopLimit(10);
        transcodeBuyLog.setOrderBy("a.create_date DESC");
        List<TranscodeBuyLog> logList = transcodeBuyLogService.findList(transcodeBuyLog);
        model.addAttribute("buyNowNum", buyNowNum);
        model.addAttribute("sellNowNum",sellNowNum);
        model.addAttribute("logList",logList);
        model.addAttribute("nowMoney", nowMoney);
        model.addAttribute("cfg",Global.getOptionMap("system_static_bonus"));
        model.addAttribute("imgTab",list1);
        model.addAttribute("userinfo",userUserinfo);
        model.addAttribute("isLogin",isLogin);
        return themesPath + "/loginIndex";
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
            return error("图文验证码错误!", response, model);
        }

        // 验证失败清空验证码
        request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
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

        String country = request.getParameter("country");
        String mobile = request.getParameter("mobile");
        String userName = request.getParameter("userName");
        //String password = request.getParameter("userPass");
        String password = "123123123";
        String validCode = request.getParameter("phoneCode");
        String trueName = request.getParameter("idName");
        String idCard = request.getParameter("idNo");
        String userNicename = request.getParameter("userNicename");

        if (StringUtils2.isBlank(validCode)) {
            return error("失败:手机验证码不能为空!", response, model);
        } else {
            if (!msmService.checkVerifyCode(mobile, validCode)) {
                return error("失败:手机验证码错误!", response, model);
            }
        }
        if (StringUtils2.isBlank(trueName)) {
            return error("请输入真实姓名", response, model);
        }
        if (StringUtils2.isBlank(idCard)) {
            return error("请输入身份证", response, model);
        }
        if(!IdcardUtils.validateCard(idCard)){
           return error("请输入合法身份证号",response,model);
        }
        if (!IdcardUtils.validateCard(idCard)) {
            return error("身份证号码有误,请重新输入", response, model);
        }
        //添加身份证重复验证
        UserUserinfo userinfoIds = new UserUserinfo();
        userinfoIds.setIdCard(idCard);
        List<UserUserinfo> list = userUserinfoService.findList(userinfoIds);
        if(list != null && list.size()>0){
            return error("身份证号码重复,请更换",response,model);
        }

        //获取顶点账户
        UserUserinfo user = userUserinfoService.get("1");
        if (user == null){
            return error("平台用户配置错误", response, model);
        }
        String walterName = user.getUserName();
        UserUserinfo walterUser = userUserinfoService.getByName(walterName);
        UserUserinfo userinfo = new UserUserinfo();
        userinfo.setMobile(mobile);
        userinfo.setIdCard(idCard);
        userinfo.setUserPass(password);
        userinfo.setUserName(userName);
        userinfo.setTrueName(trueName);
        userinfo.setUserNicename(userNicename);
        userinfo.setBankPassword(password);
        userinfo.setIsShop(EnumUtil.YesNO.NO.toString());
        userinfo.setRemarks(UUID.randomUUID().toString().replace("-", ""));
        userinfo.setWalterName(walterName);
        userinfo.setUsercenterAddress("0");//默认未绑定钱包地址
        userinfo.setServerName(walterName);
        userinfo.setParentName(walterName);
        userinfo.setRenZheng("1");

        userinfo.setUserLevelId("1");
        userinfo.setIsUsercenter("0");
        if(EnumUtil.YesNO.YES.toString().equals(country)){
            userinfo.setIsUsercenter("1");
        }
        userinfo.setUserType("0");
        String openAuction = Global.getOption("system_trans", "open_auction", "on");
        if("on".equals(openAuction)){
            userinfo.setActiveStatus("-1"); //默认为未发布交易
        }else{
            userinfo.setActiveStatus("0"); //已发布
        }

        try {
            userUserinfoService.save(userinfo);
            String msgTemplate = Global.getOption("system_sms", "lk_msg_registe");
            if (StringUtils2.isNotBlank(msgTemplate)) {
                if(EnumUtil.YesNO.YES.toString().equals(country)){
                    msmService.sendSuccessCode(mobile, userinfo.getUserName(), "", msgTemplate);
                }else {
                    msmService.pushMessage(mobile, userinfo.getUserName(), "", msgTemplate);
                }
            }
            //注册成功之后删除验证码
            Optional<UserMsm> userMsm = msmService.getByUserName(mobile);
            if (userMsm.isPresent()){
                msmService.delete(userMsm.get());
            }
            return success("成功!", "", response, model);
        } catch (DataAccessException exception) {
            return error("服务器正忙,请稍后再试!", "", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        } catch (Exception e) {
            return error("失败!", response, model);
        }
    }


    /**用户退出
     * @param response
     * @param model
     * @return
     * @throws ValidationException
     */
    @RequestMapping(value = "logout")
    public String logOut(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUtils.getSubject().logout();
        return indexl(request,response,model);
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
    /**
     * 首页忘记密码
     *
     * @return
     */
    @RequestMapping(value = {"/homeForgetPass"}, method = RequestMethod.POST)
    public String homeForgetPassEdit(HttpServletRequest request, HttpServletResponse response, Model model) {
        String mobile = request.getParameter("mobile");
        String mobileCode = request.getParameter("mobileCode");
        String pwd = request.getParameter("pwd");
        String checkPwd = request.getParameter("checkPwd");


        UserUserinfo userinfo=userUserinfoService.getByMobile(mobile);
        if(userinfo==null){
            return error("手机用户不存在!", response, model);
        }

        if (StringUtils2.isBlank(mobileCode.trim())) {
            return error ("手机验证码不能为空!", response, model);
        }else{
            if (!userMsmService.checkVerifyCode(mobile, mobileCode.trim())) {
                return error ("手机验证码错误!", response, model);
            }
        }
        if(!checkPwd.trim().equals(pwd.trim())){
            return error("两次输入密码不一致!", response, model);
        }
        try {
            userUserinfoService.forgetUserPwd(userinfo.getUserName(),pwd.trim());
            return success("找回成功!", response, model);
        } catch (DataAccessException exception) {
            return error("服务器正忙,请稍后再试!", "", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);

        }
    }

}
