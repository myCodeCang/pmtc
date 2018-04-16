/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.website.web;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO2;
import com.thinkgem.jeesite.common.utils.IdcardUtils;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.utils.VerifyUtils;
import com.thinkgem.jeesite.config.EnumLccUtil;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.cms.service.CategoryService;
import com.thinkgem.jeesite.modules.sys.entity.Principal;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.user.dao.UserUserinfoDao;
import com.thinkgem.jeesite.modules.user.entity.*;
import com.thinkgem.jeesite.modules.user.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 网站Controller
 *
 * @author ThinkGem
 * @version 2013-5-29
 */
@Controller
@RequestMapping(value = "${frontPath}/user")
public class WebUserInfoController extends WebBaseController {
    @Resource
    private SessionDAO2 sessionDAO;
    @Resource
    private UserReportService userReportService;
    @Resource
    private UserUserinfoDao userUserInfoDao;
    @Resource
    private UserChargeService userChargeService;
    @Resource
    private UserLevelService userLevelService;
    @Resource
    private UserAccountchangeService accountchangeService;
    @Resource
    private UserUsercenterLogService userUsercenterLogService;
    @Resource
    private UserWithdrawService userWithdrawService;
    @Resource
    private UserMsmService msmService;
    @Resource
    private UserUserinfoService userUserinfoService;
    @Resource
    private UserUserinfoBankService userUserinfoBankService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private UserBankWithdrawService userBankWithdrawService;

    @Resource
    private UserBankChargeService userBankChargeService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private ArticleService articleService;
    @Resource
    private UserLccService userLccService;
    @Resource
    private TransPriceDaylogService priceDaylogService;


    @Resource
    private UserMsmService userMsmService;

    @ModelAttribute
    public void init(HttpServletRequest request, Model model) {

        //设置用户对象
        if (request.getMethod().equals(RequestMethod.GET.toString()) || StringUtils2.isNotBlank(request.getParameter("_pjax"))) {
            model.addAttribute("userinfo", userUserinfoService.get(UserInfoUtils.getUser().getId()));
        }

    }

    @RequestMapping(value = {"/updateUserinfo"}, method = RequestMethod.GET)
    public String updateUserinfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser());
        model.addAttribute("userinfo", userinfo);

        return qyview("/user/updateUserinfo", request, model);
    }

    @RequestMapping(value = {"/share"}, method = RequestMethod.GET)
    public String share(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser());
        model.addAttribute("userinfo", userinfo);
        return qyview("/user/shareCode", request, model);
    }


    /**
     * 修改用户资料
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = {"/updateUserinfo"}, method = RequestMethod.POST)
    public String updateUserinfo(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser());
        String idCard = request.getParameter("idCard");
        if (StringUtils2.isNoneBlank(idCard)) {
            if (!IdcardUtils.validateCard(idCard)) {
                return error("身份证号码有误,请重新输入", response, model);
            }
        }

        String trueName = request.getParameter("trueName");
        String userEmail = request.getParameter("userEmail");
        String userNiceName = request.getParameter("userNicename");

        if (StringUtils2.isBlank(userinfo.getUserName())) {
            return error("您已掉线,请重新登录", response, model);
        }
        try {
            if (StringUtils2.isBlank(userinfo.getIdCard())) {
                userinfo.setIdCard(idCard);
            }
            if (StringUtils2.isBlank(userinfo.getTrueName())) {
                userinfo.setTrueName(trueName);
            }
            if (StringUtils2.isBlank(userinfo.getUserEmail())) {
                userinfo.setUserEmail(userEmail);
            }
            userinfo.setUserNicename(userNiceName);
            userUserinfoService.save(userinfo);
            return success("修改用户信息成功!!", response, model);
        } catch (ValidationException e) {
            return error("修改用户信息失败:"+e.getMessage(), response, model);
        }

    }


    @RequestMapping(value = {"/updatePwd"}, method = RequestMethod.GET)
    public String updatePwd(HttpServletRequest request, HttpServletResponse response, Model model) {

        return qyview("/user/updatePwd", request, model);
    }


    /**
     * 修改密码
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = {"/updatePwd"}, method = RequestMethod.POST)
    public String updatePwd(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {

        String newpwd = request.getParameter("newpwd");
        String validCode = request.getParameter("validCode");
        String pwd = request.getParameter("pwd");
        String bankpwd = request.getParameter("bankpwd");
        String banknewpwd = request.getParameter("banknewpwd");
        String type = request.getParameter("type");


        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser().getId());

        if ("pwd".equals(type)) {
            if (!SystemService.validatePassword(pwd, userinfo.getUserPass())) {
                return error("原始密码输入错误", response, model);
            }
            try {
                userUserinfoService.updateUserPwd(newpwd);
                return success("修改密码成功!!", response, model);
            } catch (ValidationException e) {
                return error("修改密码失败:"+e.getMessage(), response, model);
            }
        } else if ("paypwd".equals(type)) {

            if (StringUtils2.isBlank(validCode)) {
                return error("失败:验证码不能为空!", response, model);
            } else {
                if (!msmService.checkVerifyCode(userinfo.getMobile(), validCode)) {
                    return error("失败:验证码错误!", response, model);
                }
            }
            try {
                userUserinfoService.updateUserPaypass(banknewpwd);
                //注册成功之后删除验证码
                Optional<UserMsm> userMsm = msmService.getByUserName(userinfo.getMobile());
                msmService.delete(userMsm.get());
                return success("修改密码成功!!", response, model);
            } catch (ValidationException e) {
                return error("修改密码失败:"+e.getMessage(), response, model);
            }
        }
        return error("密码参数错误,修改失败!", response, model);

    }


    /**
     * 会员列表
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/userList"}, method = RequestMethod.GET)
    public String userList(HttpServletRequest request, HttpServletResponse response, Model model) {

        String userName = request.getParameter("userName");
        model.addAttribute("userName", userName);
        return qyview("/user/userList", request, model);
    }

    /**
     * 公告
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/message"}, method = RequestMethod.GET)
    public String message(HttpServletRequest request, HttpServletResponse response, Model model) {
        String articleId = request.getParameter("articleId");
        String cateId = request.getParameter("cateId");
        //栏目分类
        List<Category> list = categoryService.findList(new Category());
        if (list.size() == 0) {
            return qyview("/user/message", request, model);
        }
        Article articleData;
        List<Article> byKeyGroup;

        if (StringUtils2.isBlank(cateId)) {
            cateId = list.get(0).getId();
        }
        byKeyGroup = articleService.findByKeyGroup(cateId);
        Article article = new Article();
        if (byKeyGroup.size() != 0) {
            article = byKeyGroup.get(0);
        }
        if (StringUtils2.isNotBlank(articleId)) {
            articleData = articleService.getArticleDataById(articleId);
        } else {
            articleData = articleService.getArticleDataById(article.getId());
        }
        model.addAttribute("select", cateId);
        if (articleData != null){
            model.addAttribute("article", articleData.getArticleData().getContent());
        }
        model.addAttribute("cateList", list);
        model.addAttribute("titleList", byKeyGroup);
        return qyview("/user/message", request, model);
    }


    @RequestMapping(value = {"/userList"}, method = RequestMethod.POST)
    public String userList(UserUserinfo userUserinfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser().getId());
        if (userinfo == null) {
            return dataTablePage(response, new Page());
        }
        userUserinfo.setWalterName(userinfo.getUserName());
        Page<UserUserinfo> page = userUserinfoService.findPage(new Page<UserUserinfo>(request, response), userUserinfo);
        return dataTablePage(response, page);
    }


    /**
     * 奖金报表
     *
     * @return
     */
    @RequestMapping(value = {"/bonusReport"}, method = RequestMethod.GET)
    public String bonusReport(HttpServletRequest request, HttpServletResponse response, Model model) {

        return qyview("/user/bonusReport", request, model);
    }

    /**
     * 行情
     *
     * @return
     */
    @RequestMapping(value = {"/echarts"}, method = RequestMethod.GET)
    public String echarts(HttpServletRequest request, HttpServletResponse response, Model model) {
        String type = request.getParameter("type");
        List<TransPriceDaylog> priceList;
        if("day".equals(type)){
            priceList = priceDaylogService.findList(new TransPriceDaylog());
        }else if("week".equals(type)){
            priceList = priceDaylogService.findWeekData();
            for (TransPriceDaylog priceDaylog :priceList) {
                priceDaylog.setStartMoney(priceDaylogService.get(priceDaylog.getMinId()).getStartMoney());
                priceDaylog.setEndMoney(priceDaylogService.get(priceDaylog.getMaxId()).getEndMoney());
            }
        }else if("month".equals(type)){
            priceList = priceDaylogService.findMonthData();
            for (TransPriceDaylog priceDaylog :priceList) {
                priceDaylog.setStartMoney(priceDaylogService.get(priceDaylog.getMinId()).getStartMoney());
                priceDaylog.setEndMoney(priceDaylogService.get(priceDaylog.getMaxId()).getEndMoney());
            }
        }else {
            return error("类型有误", response, model);
        }
        model.addAttribute("type",type);
        model.addAttribute("endMoney", priceList.get(priceList.size() - 1).getEndMoney());
        model.addAttribute("priceList", priceList);
        return qyview("/user/echarts", request, model);
    }


    @RequestMapping(value = {"/bonusReport"}, method = RequestMethod.POST)
    public String bonusReport(UserReport userReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {

        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser());
        UserReport report = new UserReport();
        report.setUserName(userinfo.getUserName());
        Page<UserReport> page = userReportService.findPage(new Page<UserReport>(request, response), report);
        return dataTablePage(response, page);
    }

    /**
     * 账变列表
     *
     * @return
     */
    @RequestMapping(value = {"/accoundChange"}, method = RequestMethod.GET)
    public String accoundChange(HttpServletRequest request, HttpServletResponse response, Model model) {


        return qyview("/user/accoundChange", request, model);
    }


    @RequestMapping(value = {"/accoundChange"}, method = RequestMethod.POST)
    public String accoundChange(UserAccountchange userAccountchange, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
        List<Integer> arrayList = null;
        userAccountchange.setUserName(UserInfoUtils.getUser().getUserName());
        Page<UserAccountchange> page;
        userAccountchange.setStatus("1");
        if ("300".equals(userAccountchange.getChangeType())) {
            userAccountchange.setChangeType("");
            arrayList = Arrays.asList(201, 203, 202, 205);
            userAccountchange.setChangeTypeArray(arrayList);
            page = accountchangeService.findPage(new Page<UserAccountchange>(request, response), userAccountchange);

            return dataTablePage(response, page);
        }
        if ("400".equals(userAccountchange.getChangeType())) {
            userAccountchange.setChangeType("");
            arrayList = Arrays.asList(9, 14, 16, 20);
            userAccountchange.setChangeTypeArray(arrayList);
            page = accountchangeService.findPage(new Page<UserAccountchange>(request, response), userAccountchange);

            return dataTablePage(response, page);
        }
        page = accountchangeService.findPage(new Page<UserAccountchange>(request, response), userAccountchange);

        return dataTablePage(response, page);
    }


    /**
     * 奖金币转报单币
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/moneyToCoin"}, method = RequestMethod.GET)
    public String moneyToCoin(HttpServletRequest request, HttpServletResponse response, Model model) {


        return qyview("/user/moneyToCoin", request, model);
    }


    /**
     * 奖金币转报单币
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/coinToServer"}, method = RequestMethod.GET)
    public String coinToServer(HttpServletRequest request, HttpServletResponse response, Model model) {


        return qyview("/user/coinToServer", request, model);
    }

    /**
     * 报单币转移
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/userCoinToServer"}, method = RequestMethod.GET)
    public String userCoinToServer(HttpServletRequest request, HttpServletResponse response, Model model) {
        String userName = request.getParameter("userName");
        model.addAttribute("userName",userName);
        return qyview("/user/userCoinToServer", request, model);
    }


    /**
     * 会员注册....
     *
     * @return
     */
    @RequestMapping(value = {"/addSubuser"}, method = RequestMethod.GET)
    public String addSubuser(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("userinfo", UserInfoUtils.getUser());
        return qyview("/user/addSubuser", request, model);
    }

    //判断是否掉线
    @RequestMapping(value = "/getUser")
    public String getUser(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser().getId());
        if (userinfo != null) {
            model.addAttribute("user", userinfo);
            return success("成功", response, model);
        }
        return error("您已掉线", response, model);
    }


    /**
     * 转入交易钱包
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/transferToMoney")
    public String transferToMoney(HttpServletRequest request, HttpServletResponse response, Model model) {


        String moneyTemp = request.getParameter("moneyNum");
        String bankPass = request.getParameter("bankPwd");
        String moneyType = request.getParameter("porto_is");
        String message = request.getParameter("message");
        BigDecimal money;
        UserUserinfo user = userUserinfoService.get(UserInfoUtils.getUser());
        Boolean canTransfer;
        try {
            canTransfer =   userLccService.canTransfer();
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        }
        if(!canTransfer){
            return error("系统孵化中,请稍后操作", response, model);
        }

        if(EnumUtil.UserStatusEnum.status_freeze.toString().equals(user.getShopId())){
            return error("对不起，您的账户处于冻结状态，暂停转账功能", response, model);
        }
        if(EnumUtil.UserStatusEnum.status_enable.toString().equals(user.getShopId())){
            return error("对不起，您的账户处于睡眠状态，暂停转账功能", response, model);
        }

        if (StringUtils2.isBlank(user.getUserName())) {
            return error("您已掉线,请重新登录", response, model);
        }
        if (!SystemService.validatePassword(bankPass, user.getBankPassword())) {
            return error("支付密码输入错误", response, model);
        }
        try {
            money = new BigDecimal(moneyTemp);
        } catch (Exception e) {
            return error("转换金额有误", response, model);
        }
        int attornBeiShu;
        try {
            attornBeiShu = Integer.parseInt(Global.getOption("system_help", "multiple","50"));
        } catch (NumberFormatException e) {
            return error("后台最低转换倍数配置错误", response, model);
        }

        BigDecimal[] decimals = money.divideAndRemainder(new BigDecimal(attornBeiShu));
        if(!BigDecimal.ZERO.equals(decimals[1])){
            return error("转账金额必须为"+attornBeiShu+"的倍数", response, model);
        }


        if (StringUtils2.isBlank(moneyType)) {
            return error("转账账户错误", response, model);
        }
        try {
            if ("money".equals(moneyType)) {
                userLccService.transferToMoney2(user.getUserName(), moneyTemp, EnumLccUtil.MoneyType.DYNAMIC_BURSE, message);
            } else if ("money2".equals(moneyType)) {
                userLccService.transferToMoney(user.getUserName(), money, EnumUtil.MoneyType.money2, EnumUtil.MoneyChangeType.TRANSFER_MONEY2_IN, true, message);
            } else if ("money3".equals(moneyType)) {
                userLccService.transferToMoney(user.getUserName(), money, EnumUtil.MoneyType.money3, EnumUtil.MoneyChangeType.TRANSFER_MONEY3, true, message);
            } else {
                return error("转账账户选择错误", response, model);
            }
        } catch (ValidationException e) {
            return error("转换失败:"+e.getMessage(), response, model);
        } catch (Exception e) {
            return error("转换失败", response, model);
        }
        return success("转换成功", response, model);
    }

    /**
     * 会员转账
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/transferOfAccount")
    public String transferOfAccount(HttpServletRequest request, HttpServletResponse response, Model model) {
        String moneyTemp = request.getParameter("moneyNum");
        String bankPass = request.getParameter("bankPwd");
        String address = request.getParameter("address");
        String validCode = request.getParameter("validCode");

        BigDecimal money;
        UserUserinfo user = userUserinfoService.get(UserInfoUtils.getUser());

        Boolean canTransfer;
        try {
            canTransfer =   userLccService.canTransfer();
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        }
        if(!canTransfer){
            return error("系统孵化中,请稍后操作", response, model);
        }

        if(EnumUtil.UserStatusEnum.status_freeze.toString().equals(user.getShopId())){
            return error("对不起，您的账户处于冻结状态，暂停转账功能", response, model);
        }
        if(EnumUtil.UserStatusEnum.status_enable.toString().equals(user.getShopId())){
            return error("对不起，您的账户处于睡眠状态，暂停转账功能", response, model);
        }

        if (StringUtils2.isBlank(user.getUserName())) {
            return error("您已掉线,请重新登录", response, model);
        }
        if (StringUtils2.isBlank(bankPass)) {
            return error("请输入支付密码", response, model);
        }

        //判断是否短信验证
        if(!EnumUtil.YesNO.NO.toString().equals(user.getIsShop())){
            if (StringUtils2.isBlank(validCode)) {
                return error("失败:验证码不能为空!", response, model);
            } else {
                if (!msmService.checkVerifyCode(user.getMobile(), validCode)) {
                    return error("失败:验证码错误!", response, model);
                }
            }
        }

        if (!SystemService.validatePassword(bankPass, user.getBankPassword())) {
            return error("支付密码输入错误", response, model);
        }


        try {
            money = new BigDecimal(moneyTemp);
            userLccService.transferOfAccount(user.getUserName(), money, address);
            //转账成功之后删除验证码
            if(!EnumUtil.YesNO.NO.toString().equals(user.getIsShop())){
                Optional<UserMsm> userMsm = msmService.getByUserName(user.getMobile());
                msmService.delete(userMsm.get());
            }
        } catch (ValidationException e) {
            return error("失败:"+e.getMessage() , response, model);
        } catch (Exception e) {
            return error("会员转账失败", response, model);
        }
        return success("会员转账成功", response, model);
    }

    /**
     * 会员转账
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectUserByName")
    public String selectUserByName(HttpServletRequest request, HttpServletResponse response, Model model) {
        String userName = request.getParameter("userName");
        UserUserinfo userByUserName = userUserinfoService.getByName(userName);
        UserUserinfo userByRemarks = userUserInfoDao.getByRemarks(userName);
        if(userByUserName != null ){
            model.addAttribute("User", userByUserName);
        }
        if(userByRemarks != null){
            model.addAttribute("User", userByRemarks);
        }

        return success("成功", response, model);
    }

    /**
     * 会员外部转账
     *
     * @return
     */
    @RequestMapping(value = {"/userCoinToTrans"}, method = RequestMethod.GET)
    public String userCoinToTrans(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("userinfo", userUserInfoDao.get(UserInfoUtils.getUser().getId()));
        return qyview("/user/userCoinToTrans", request, model);
    }

    /**
     * 会员外部转账
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/userCoinToTrans")
    public String transferToOut(HttpServletRequest request, HttpServletResponse response, Model model) {
        String moneyTemp = request.getParameter("moneyNum");
        String bankPass = request.getParameter("bankPwd");
        String address = request.getParameter("address");
        String validCode = request.getParameter("validCode");
        String message = request.getParameter("message");
        BigDecimal money;
        UserUserinfo user = userUserinfoService.get(UserInfoUtils.getUser());

        Boolean canTransfer;
        try {
            canTransfer =   userLccService.canTransfer();
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        }
        if(!canTransfer){
            return error("系统孵化中,请稍后操作", response, model);
        }

        if(EnumUtil.UserStatusEnum.status_freeze.toString().equals(user.getShopId())){
            return error("对不起，您的账户处于冻结状态，暂停转账功能", response, model);
        }
        if(EnumUtil.UserStatusEnum.status_enable.toString().equals(user.getShopId())){
            return error("对不起，您的账户处于睡眠状态，暂停转账功能", response, model);
        }

        if (user == null) {
            return error("您已掉线,请重新登录", response, model);
        }
        if (StringUtils2.isBlank(address)) {
            return error("请输入钱包地址", response, model);
        }
        if (StringUtils2.isBlank(bankPass)) {
            return error("请输入支付密码", response, model);
        }

        try {
            money = new BigDecimal(moneyTemp);
        } catch (Exception e) {
            return error("转换金额有误",response,model);
        }

        int attornBeiShu;
        try {
            attornBeiShu = Integer.parseInt(Global.getOption("system_help", "outMultiple","50"));
        } catch (NumberFormatException e) {
            return error("会员外部转账倍数配置错误", response, model);
        }

        BigDecimal[] decimals = money.divideAndRemainder(new BigDecimal(attornBeiShu));
        if(!BigDecimal.ZERO.equals(decimals[1])){
            return error("转账金额必须为"+attornBeiShu+"的倍数", response, model);
        }

        //判断是否短信验证
        if(!EnumUtil.YesNO.NO.toString().equals(user.getIsShop())){
            if (StringUtils2.isBlank(validCode)) {
                return error("失败:验证码不能为空!", response, model);
            } else {
                if (!msmService.checkVerifyCode(user.getMobile(), validCode)) {
                    return error("失败:验证码错误!", response, model);
                }
            }
        }

        if (!SystemService.validatePassword(bankPass, user.getBankPassword())) {
            return error("支付密码输入错误", response, model);
        }

        try {
            userLccService.moneyToUserOut(user.getUserName(),address,money,message);
        } catch (ValidationException e) {
            return error("转入外网失败,"+e.getMessage(),response,model);
        } catch (Exception e){
            return error("转入外网失败",response,model);
        }

        return success("转入外网成功",response,model);
    }


    /**
     * 发送验证码
     *
     * @return
     */
    @RequestMapping(value = {"/sendCode"}, method = RequestMethod.POST)
    public String sendCode(HttpServletRequest request, HttpServletResponse response, Model model) {

        String userName = request.getParameter("userName");
        if (StringUtils2.isBlank(userName)){
            userName = UserInfoUtils.getUser().getUserName();
        }
        UserUserinfo userinfo = userUserinfoService.getByName(userName);

        if(userinfo == null){
            return error("对不起,账户不存在", response, model);
        }


        try {
            model.addAttribute("info",userMsmService.sendVerifyCode(userinfo.getMobile()));
            return success ("短信发送成功!", response, model);
        } catch (ValidationException e) {
            return error ("短信发送失败:"+e.getMessage(), response, model);
        } catch (Exception ex2) {
            return error (ex2.getMessage(), response, model);
        }

    }


    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value = {"/updatePass"}, method = RequestMethod.POST)
    public String updatePass(HttpServletRequest request, HttpServletResponse response, Model model) {

        String userName = request.getParameter("userName");
        String userPass = request.getParameter("userPass");
        String validCode = request.getParameter("validCode");

        UserUserinfo userinfo = userUserinfoService.getByName(userName);
        if(userinfo == null){
            return error("用户不存在", response, model);
        }
        if(StringUtils2.isBlank(userName) || StringUtils2.isBlank(userPass)){
            return error("修改密码失败", response, model);
        }
        if (StringUtils2.isBlank(validCode)) {
            return error("失败:验证码不能为空!", response, model);
        } else {
            if (!msmService.checkVerifyCode(userinfo.getMobile(), validCode)) {
                return error("失败:验证码错误!", response, model);
            }
        }

        try {
            userUserinfoService.forgetUserPwd(userinfo.getUserName(),userPass);
            //注册成功之后删除验证码
            Optional<UserMsm> userMsm = msmService.getByUserName(userinfo.getMobile());
            msmService.delete(userMsm.get());

            return success("修改密码成功!!", response, model);

        } catch (ValidationException e) {
            return error("修改密码失败:"+e.getMessage(), response, model);
        }

    }


}
