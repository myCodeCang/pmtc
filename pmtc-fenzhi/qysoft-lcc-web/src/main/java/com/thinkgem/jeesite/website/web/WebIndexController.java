/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.website.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO2;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.DateUtils2;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.sys.entity.Principal;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.user.dao.UserAccountchangeDao;
import com.thinkgem.jeesite.modules.user.dao.UserReportDao;
import com.thinkgem.jeesite.modules.user.dao.UserUserinfoDao;
import com.thinkgem.jeesite.modules.user.entity.UserOptions;
import com.thinkgem.jeesite.modules.user.entity.UserReport;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.UserAccountchangeService;
import com.thinkgem.jeesite.modules.user.service.UserOptionsService;
import com.thinkgem.jeesite.modules.user.service.UserReportService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.springframework.context.i18n.LocaleContextHolder;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 网站Controller
 *
 * @author ThinkGem
 * @version 2013-5-29
 */
@Controller
@RequestMapping(value = "${frontPath}")
public class WebIndexController extends WebBaseController {
    @Resource
    private SessionDAO2 sessionDAO2;
    @Resource
    private UserUserinfoService userUserinfoService;
    @Resource
    private UserReportService userReportService;

    @Resource
    private ArticleService articleService;

    @Resource
    private UserUserinfoDao userinfoDao ;
    @Resource
    private UserOptionsService optionsService ;
    @Resource
    private UserReportDao userReportDao ;

    @ModelAttribute
    public void init(Model model) {


    }

    /**
     * 网站首页
     */
    @RequestMapping(value = "")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        Principal principal = UserInfoUtils.getPrincipal();
        if (principal == null) {
            return "redirect:" + frontPath + "/login";
        }
        // 登录成功后，验证码计算器清零
        UserInfoUtils.isValidateCodeLogin(principal.getLoginName(), false, true);

        if (logger.isDebugEnabled()) {
            logger.debug("show index, active session size: {}", sessionDAO2.getActiveSessions(false).size());
        }

        // 如果已登录，再次访问主页，则退出原账号。
        if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
            String logined = CookieUtils.getCookie(request, "LOGINED");
            if (StringUtils2.isBlank(logined) || "false".equals(logined)) {
                CookieUtils.setCookie(response, "LOGINED", "true");
            } else if (StringUtils2.equals(logined, "true")) {
                UserInfoUtils.getSubject().logout();
                return "redirect:" + frontPath + "/login";
            }
        }

        // 如果是手机登录，则返回JSON字符串
        if (principal.isMobileLogin() || isAjaxRequest(request)) {
            UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser().getId());
            if (userinfo != null && userinfo.getUserStatus().equals("-1")) {
                UserInfoUtils.getSubject().logout();
                return error("该账户已被禁用!", response, model);
            }
            return success("登录成功!", response, model);

        }
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser().getId());

        //获取团队持币数
        BigDecimal allMoney = userinfoDao.sumUserMoney2(userinfo.getId());
        if(allMoney == null){
            allMoney = new BigDecimal(0);
        }
        BigDecimal nowPrice = BigDecimal.ZERO;

        //添加价格动态
        UserOptions config = optionsService.getByOptionName("trans_price");
        String optionValue = config.getOptionValue();
        if(!StringUtils2.isBlank(optionValue)){
            try {
                HashMap<String, String> resMap = (HashMap<String, String>) JsonMapper.fromJsonString(optionValue, HashMap.class);

                nowPrice = new BigDecimal(resMap.get("nowPrice"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        BigDecimal userBonusSum = BigDecimal.ZERO;
        UserReport userReport = userReportDao.getByUserNameAndDate(userinfo.getUserName(), new Date());
        if(userReport != null){
            userBonusSum = userBonusSum.add(userReport.getDividendTwo()).add(userReport.getDividendSix()).add(userReport.getDividendSeven()).add(userReport.getDividendEight()).add(userReport.getDividendTen());
        }

        /**
         * 添加首页公告查询
         */

        UserUserinfo userinfoSearch = new UserUserinfo();
        userinfoSearch.setWalterName(userinfo.getUserName());
        userinfoSearch.setActiveStatus("1");

        List<UserUserinfo> walterList =  userUserinfoService.findList(userinfoSearch);
        List<Article> byKeyGroup = articleService.findByKeyGroup("70606");


        model.addAttribute("allBonus",userBonusSum);
        model.addAttribute("nowPrice",nowPrice);
        model.addAttribute("allMoney", allMoney);
        model.addAttribute("articleList", byKeyGroup);
        model.addAttribute("userinfo", userinfo);
        model.addAttribute("walterListSize", walterList.size());
        return qyview("/index", request, model);
    }


    //主题
    @RequestMapping(value = "/common/display")
    public String display(HttpServletRequest request, HttpServletResponse response, Model model) {

        return qyview("/common/display", request, model);
    }

    @RequestMapping(value = "language", method = RequestMethod.GET)
    public String language(HttpServletRequest request, HttpServletResponse response) {
        String langType = request.getParameter("type");
        if (langType == null || langType.equals("")) {
            return "forward:/f/login";
        } else {
            if (langType.equals("zh_CN")) {
                Locale locale1 = new Locale("zh", "CN");
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale1);
            } else if (langType.equals("en_US")) {
                Locale locale1 = new Locale("en", "US");
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale1);
            } else if (langType.equals("zh_HK")) {
                Locale locale1 = new Locale("zh", "HK");
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale1);
            } else {
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, LocaleContextHolder.getLocale());
            }
        }

        return "forward:/f/login";
    }


}
