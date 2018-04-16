/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.website.web;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO2;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.DateUtils2;
import com.thinkgem.jeesite.common.utils.IdcardUtils;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.config.EnumLccUtil;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.cms.service.CategoryService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.user.dao.UserUserinfoDao;
import com.thinkgem.jeesite.modules.user.entity.*;
import com.thinkgem.jeesite.modules.user.service.*;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.session.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

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
    private UserAccountchangeService accountchangeService;
    @Resource
    private UserMsmService msmService;
    @Resource
    private UserUserinfoService userUserinfoService;
    @Resource
    private UserUserinfoBankService userUserinfoBankService;
    @Resource
    private UserBankWithdrawService userBankWithdrawService;
    @Resource
    private UserLccService userLccService;
    @Resource
    private TranscodePriceDaylogService priceDaylogService;
    @Resource
    private UserUCApiService userUCApiService;
    @Resource
    private UserUserinfoDao userUserinfoDao;
    @Resource
    private UserMsmService userMsmService;

    @ModelAttribute
    @Override
    public void init(HttpServletRequest request, Model model) {

        //设置用户对象
        if (request.getMethod().equals(RequestMethod.GET.toString()) || StringUtils2.isNotBlank(request.getParameter("_pjax"))) {
            model.addAttribute("userinfo", userUserinfoService.get(UserInfoUtils.getUser().getId()));
        }

    }



    /**
     * 发送验证码
     *
     * @return
     */
    @RequestMapping(value = {"/sendCodeByUserApi"})
    public String sendCodeByUserApi(HttpServletRequest request, HttpServletResponse response, Model model) {

        String address = request.getParameter("address");
        if (StringUtils2.isBlank(address)) {
            return error("请输入钱包地址", response, model);
        }
        UserUserinfo userinfo = userUserinfoService.getByName(UserInfoUtils.getUser().getUserName());
        if (userinfo == null) {
            return error("对不起,账户不存在", response, model);
        }
        if ("2".equals(userinfo.getIsShop())){
            throw new ValidationException("对不起!您的账户已被冻结,禁止该操作");
        }
        if (!EnumUtil.YesNO.NO.toString().equals(userinfo.getUsercenterAddress())) {
            return error("已绑定过会员钱包地址,不可重新绑定", response, model);
        }
        UserUserinfo userByUsercenterAddress = userUserinfoDao.getUserByUsercenterAddress(address);
        if (userByUsercenterAddress != null) {
            return error ("该会员钱包地址已被他人绑定", response, model);
        }
        try {
            if (!userUCApiService.sendCodeByAdd(address)) {
                return error("发送失败请检查输入钱包地址,或稍后再试", response, model);
            }
            return success("短信发送成功,请您注意查收!", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        } catch (Exception ex2) {
            return error("发送失败", response, model);
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
        if (userinfo == null) {
            return error("用户不存在", response, model);
        }
        if (StringUtils2.isBlank(userName) || StringUtils2.isBlank(userPass)) {
            return error("失败:新密码不能为空", response, model);
        }
        if (StringUtils2.isBlank(validCode)) {
            return error("失败:验证码不能为空!", response, model);
        } else {
            if (!msmService.checkVerifyCode(userinfo.getMobile(), validCode)) {
                return error("失败:验证码错误!", response, model);
            }
        }

        try {
            userUserinfoService.forgetUserPwd(userinfo.getUserName(), userPass);
            //注册成功之后删除验证码
            Optional<UserMsm> userMsm = msmService.getByUserName(userinfo.getMobile());
            if (userMsm.isPresent()){
                msmService.delete(userMsm.get());
            }
            return success("修改密码成功!!", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        }

    }


    /**
     * 安全记录
     *
     * @return
     */
    @RequestMapping(value = {"/loginHistory"}, method = RequestMethod.GET)
    public String loginHistory(HttpServletRequest request, HttpServletResponse response, Model model) {
        return qyview("/user/loginHistory", request, model);
    }


    /**
     * 转内网记录
     *
     * @return
     */
    @RequestMapping(value = {"/account"}, method = RequestMethod.GET)
    public String account(HttpServletRequest request, HttpServletResponse response, Model model) {
        return qyview("/user/account", request, model);
    }

    /**
     * 转内网记录
     *
     * @return
     */
    @RequestMapping(value = {"/realnameAuth"}, method = RequestMethod.GET)
    public String realnameAuth(HttpServletRequest request, HttpServletResponse response, Model model) {
        return qyview("/user/realnameAuth", request, model);
    }

    /**
     * 资产
     *
     * @return
     */
    @RequestMapping(value = {"/zichan"}, method = RequestMethod.GET)
    public String tradeLCC(HttpServletRequest request, HttpServletResponse response, Model model) {

        UserUserinfo userinfo = UserInfoUtils.getUser();
        if (userinfo == null) {
            return error("服务器正忙,请稍后再试!", response, model);
        }
        Page<UserAccountchange> accountPage = new Page<>(request, response);
        accountPage.setPageSize(10);

        UserAccountchange accountchange = new UserAccountchange();
        accountchange.setUserName(userinfo.getUserName());
        Page<UserAccountchange> page = accountchangeService.findPage(accountPage, accountchange);

        BigDecimal nowMoney = BigDecimal.ZERO;
        TranscodePriceDaylog nowLog = priceDaylogService.getNowLog();
        if (nowLog != null) {
            nowMoney = nowLog.getNowMoney();
        }
        model.addAttribute("page", page);
        model.addAttribute("nowMoney", nowMoney);
        return qyview("/user/zichan", request, model);
    }


    /**
     * 我的银行卡
     *
     * @return
     */
    @RequestMapping(value = {"/myBankCard"}, method = RequestMethod.GET)
    public String myBankCard(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userinfo = UserInfoUtils.getUser();

        if (userinfo == null) {
            return error("服务器正忙,请刷新重试!", response, model);
        }
        UserUserinfoBank userinfoBank = new UserUserinfoBank();
        userinfoBank.setUserName(userinfo.getUserName());
        userinfoBank.setTopLimit(1);
        List<UserUserinfoBank> userBankList = userUserinfoBankService.findList(userinfoBank);
        if (userBankList.size() > 0) {

            model.addAttribute("userbank", userBankList.get(0));
            return qyview("/user/bankCardList", request, model);

        } else {
            UserBankWithdraw userBankWithdraw = new UserBankWithdraw();
            userBankWithdraw.setStatus("1");
            List<UserBankWithdraw> bankList = userBankWithdrawService.findList(userBankWithdraw);
            model.addAttribute("bankList", bankList);
            return qyview("/user/myBankCard", request, model);

        }


    }

    /**
     * 绑定银行卡
     *
     * @return
     */
    @RequestMapping(value = {"/myBankCardEdit"}, method = RequestMethod.GET)
    public String myBankCardEdit(HttpServletRequest request, HttpServletResponse response, Model model) {


        UserBankWithdraw userBankWithdraw = new UserBankWithdraw();
        userBankWithdraw.setStatus("1");
        List<UserBankWithdraw> bankList = userBankWithdrawService.findList(userBankWithdraw);
        model.addAttribute("bankList", bankList);


        //查询用户是否已经绑定银行卡
        UserUserinfo userinfo = UserInfoUtils.getUser();

        UserUserinfoBank userinfoBankSearch = new UserUserinfoBank();
        userinfoBankSearch.setUserName(userinfo.getUserName());
        userinfoBankSearch.setTopLimit(1);
        List<UserUserinfoBank> userBankList = userUserinfoBankService.findList(userinfoBankSearch);

        if(userBankList.size() > 0){
            model.addAttribute("userbank", userBankList.get(0));
        }


        return qyview("/user/myBankCard", request, model);
    }

    /**
     * 我的钱包
     *
     * @return
     */
    @RequestMapping(value = {"/myBankCardEdit"}, method = RequestMethod.POST)
    public String myBankCardEditSave(HttpServletRequest request, HttpServletResponse response, Model model) {

        String advPassword = request.getParameter("advPassword");
        String bnum = request.getParameter("bnum");

        String openBank = request.getParameter("openBank");
        String bbranch = request.getParameter("bbranch");

        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser());

        if (userinfo == null) {
            return error("服务器正忙,请稍后再试!", response, model);
        }

        if (StringUtils2.isBlank(bnum)) {
            return error("银行号不能为空!", response, model);
        }
        if (StringUtils2.isBlank(bbranch)) {
            return error("分支行名称不能为空!", response, model);
        }

        if (StringUtils2.isBlank(advPassword)) {
            return error("密码不能为空!", response, model);
        }
        if (!SystemService.validatePassword(advPassword, userinfo.getBankPassword())) {
            return error("资金密码输入错误", response, model);
        }
        try {

            UserBankWithdraw userBankWithdraw = userBankWithdrawService.getByBankCode(openBank);
            if(userBankWithdraw == null){
                return error("该银行不存在，请重新选择", response, model);
            }


            //查询用户是否已经绑定银行卡
            UserUserinfoBank userinfoBankSearch = new UserUserinfoBank();
            userinfoBankSearch.setUserName(userinfo.getUserName());
            userinfoBankSearch.setTopLimit(1);
            List<UserUserinfoBank> userBankList = userUserinfoBankService.findList(userinfoBankSearch);

            if (userBankList.size()  == 0) {
                UserUserinfoBank userinfoBank = new UserUserinfoBank();
                userinfoBank.setUserName(userinfo.getUserName());
                userinfoBank.setBankUserName(userinfo.getTrueName());
                userinfoBank.setBankName(userBankWithdraw.getBankName());
                userinfoBank.setBankUserNum(bnum);
                userinfoBank.setBankUserAddress(bbranch);
                userinfoBank.setBankCode(openBank);
                userUserinfoBankService.save(userinfoBank);
                return success("添加成功!", "", response, model);
            } else {
                UserUserinfoBank userinfoBank = userBankList.get(0);

                userinfoBank.setBankUserNum(bnum);
                userinfoBank.setBankUserAddress(bbranch);
                userinfoBank.setBankCode(openBank);
                userinfoBank.setBankName(userBankWithdraw.getBankName());

                userUserinfoBankService.save(userinfoBank);
                return success("修改成功!", "", response, model);
            }

        }
        catch (ValidationException e) {
            return error("绑定银行卡失败!", response, model);

        }
    }

    /**
     * 我的钱包
     *
     * @return
     */
    @RequestMapping(value = {"/myBurse"}, method = RequestMethod.GET)
    public String myBurse(HttpServletRequest request, HttpServletResponse response, Model model) {
        return qyview("/user/myBurse", request, model);
    }

    /**
     * 转内网
     *
     * @return
     */
    @RequestMapping(value = {"/myWithdraw"}, method = RequestMethod.GET)
    public String myWithdraw(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userUserinfo = userUserinfoService.get(UserInfoUtils.getUser());
        if (userUserinfo == null) {
            return qyview("/login", request, model);
        }
        if (EnumUtil.YesNO.NO.toString().equals(userUserinfo.getUsercenterAddress())) {
            return qyview("/user/bingUserBurseAdd", request, model);
        }
        return qyview("/user/myWithdraw", request, model);
    }

    /**
     * 转内网
     *
     * @return
     */
    @RequestMapping(value = {"/myWithdraw"}, method = RequestMethod.POST)
    public String myWithdrawEdit(HttpServletRequest request, HttpServletResponse response, Model model) {
        String money = request.getParameter("amount");
        String verfiyCode = request.getParameter("verfiyCode");
        String advPassword = request.getParameter("advPassword");
        String message = request.getParameter("memo");
        UserUserinfo userinfo = userUserinfoService.getByNameLock(UserInfoUtils.getUser().getUserName());

        if (userinfo == null) {
            return error("账户登录超时,请重新登录!", response, model);
        }
        if (StringUtils2.isBlank(money)) {
            return error("转内网数量不能为空!", response, model);
        }
        if (StringUtils2.isBlank(advPassword)) {
            return error("资金密码不能为空!", response, model);
        }
        if (EnumUtil.YesNO.NO.toString().toString().equals(userinfo.getUsercenterAddress())) {
            return error("未绑定内网钱包地址,请先去绑定!", response, model);
        }
        if (!SystemService.validatePassword(advPassword, userinfo.getBankPassword())) {
            return error("资金密码输入错误", response, model);
        }

        try {
            userLccService.moneyToUser(verfiyCode,userinfo.getUserName(), new BigDecimal(money), message);

            return success("成功!", "", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        } catch (Exception e) {
            return error("转内网失败", response, model);
        }
    }

    /**
     * 转内网记录
     *
     * @return
     */
    @RequestMapping(value = {"/myWithdrawLog"}, method = RequestMethod.GET)
    public String myWithdrawLog(HttpServletRequest request, HttpServletResponse response, Model model) {

        UserUserinfo userinfo = UserInfoUtils.getUser();

        if (userinfo == null) {
            return error("账户登录超时,请重新登录!", response, model);
        }
        Page<UserAccountchange> accountPage = new Page<>(request, response);
        accountPage.setPageSize(10);

        UserAccountchange accountchange = new UserAccountchange();
        accountchange.setUserName(userinfo.getUserName());
        accountchange.setMoneyType(EnumUtil.MoneyType.money.toString());
        List<Integer> list = new ArrayList<>();
        list.add(EnumUtil.MoneyChangeType.moneyToShop.getCode());
        list.add(EnumUtil.MoneyChangeType.MONEY_TRANS_IN.getCode());
        accountchange.setChangeTypeArray(list);
        Page<UserAccountchange> page = accountchangeService.findPage(accountPage, accountchange);
        model.addAttribute("page", page);

        return qyview("/user/myWithdrawLog", request, model);
    }


    /**
     *绑定邮箱
     *
     * @return
     */
    @RequestMapping(value = {"/bindEmail"}, method = RequestMethod.GET)
    public String bindEmail(HttpServletRequest request, HttpServletResponse response, Model model) {
        return qyview("/user/bindEmail", request, model);
    }

    /**
     * 绑定邮箱
     *
     * @return
     */
    @RequestMapping(value = {"/bindEmail"}, method = RequestMethod.POST)
    public String bindEmailEdit(HttpServletRequest request, HttpServletResponse response, Model model) {

        String email = request.getParameter("email");

        UserUserinfo userinfo = UserInfoUtils.getUser();

        if (userinfo == null) {
            return error("账户登录超时,请重新登录!", response, model);
        }
        Session session = UserInfoUtils.getSession();
        String code = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
        if (request.getParameter("validateCode") == null || !request.getParameter("validateCode").toUpperCase().equals(code)) {
            return error("图文验证码错误!", response, model);

        }
        if (StringUtils2.isBlank(email)) {
            return error("邮箱不能为空!", response, model);
        }
        try {
            userUserinfoService.updateUserEmail(email);
            return success("绑定成功!", "", response, model);
        } catch (DataAccessException exception) {
            return error("服务器正忙,请稍后再试!", "", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        }
    }

    /**
     * 修改登录密码
     *
     * @return
     */
    @RequestMapping(value = {"/changeLoginPwd"}, method = RequestMethod.GET)
    public String changeLoginPwd(HttpServletRequest request, HttpServletResponse response, Model model) {
        return qyview("/user/changeLoginPwd", request, model);
    }

    /**
     * 修改登录密码
     *
     * @return
     */
    @RequestMapping(value = {"/changeLoginPwd"}, method = RequestMethod.POST)
    public String changeLoginPwdEdit(HttpServletRequest request, HttpServletResponse response, Model model) {
        String mobileCode = request.getParameter("verfiyCode");
        String pwd = request.getParameter("npasswd");
        String checkPwd = request.getParameter("rpasswd");
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser());
        if (userinfo == null) {
            return error("账户登录超时,请重新登录!", response, model);
        }

        if (StringUtils2.isBlank(pwd) || StringUtils2.isBlank(checkPwd)) {
            return error("新密码不能为空!", response, model);
        }
        if (!checkPwd.trim().equals(pwd.trim())) {
            return error("俩次输入密码不一致!", response, model);
        }

        if (StringUtils2.isBlank(mobileCode.trim())) {
            return error("手机验证码不能为空!", response, model);
        } else {
            if (!userMsmService.checkVerifyCode(userinfo.getMobile(), mobileCode.trim())) {
                return error("手机验证码错误!", response, model);
            }
        }

        try {
            userUserinfoService.updateUserPwd(pwd.trim());
            return success("重置成功!", "", response, model);

        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        } catch (Exception exception) {
            return error("修改失败!", "", response, model);
        }
    }

    /**
     * 忘记支付密码
     *
     * @return
     */
    @RequestMapping(value = {"/resetAssetPwd"}, method = RequestMethod.GET)
    public String resetAssetPwd(HttpServletRequest request, HttpServletResponse response, Model model) {
        return qyview("/user/resetAssetPwd", request, model);
    }

    /**
     * 忘记支付密码
     *
     * @return
     */
    @RequestMapping(value = {"/resetAssetPwd"}, method = RequestMethod.POST)
    public String resetAssetPwdEdit(HttpServletRequest request, HttpServletResponse response, Model model) {

        String mobileCode = request.getParameter("mobileCode");
        String pwd = request.getParameter("npasswd");
        String checkPwd = request.getParameter("rpasswd");

        UserUserinfo userinfo = UserInfoUtils.getUser();

        if (userinfo == null) {
            return error("账户登录超时,请重新登录!", response, model);
        }
        if (StringUtils2.isBlank(mobileCode)) {
            return error ("手机验证码不能为空!", response, model);
        }else{
            if (!userMsmService.checkVerifyCode(userinfo.getMobile(), mobileCode.trim())) {
                return error ("手机验证码错误!", response, model);
            }
        }
        if (StringUtils2.isBlank(pwd) || StringUtils2.isBlank(checkPwd)) {
            return error("密码不能为空!", response, model);
        }
        if (!checkPwd.trim().equals(pwd.trim())) {
            return error("俩次输入密码不一致!", response, model);
        }
        try {

            userUserinfoService.updateUserPaypass(pwd.trim());
            return success("修改成功!", "", response, model);
        } catch (DataAccessException exception) {
            return error("服务器正忙,请稍后再试!", "", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);

        }

    }


    /**
     * 绑定会员端钱包地址
     *
     * @return
     */
    @RequestMapping(value = {"/bingUserBurseAdd"}, method = RequestMethod.GET)
    public String showBingUserBurseAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
        return qyview("/user/bingUserBurseAdd", request, model);
    }

    /**
     * 绑定会员端钱包地址
     *
     * @return
     */
    @RequestMapping(value = {"/bingUserBurseAdd"}, method = RequestMethod.POST)
    public String bingUserBurseAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
        String address = request.getParameter("address");
        String code = request.getParameter("verfiyCode");
        UserUserinfo userinfo = userUserinfoService.getByName(UserInfoUtils.getUser().getUserName());
        if (userinfo == null) {
            return error("账户登录超时,请重新登录!", response, model);
        }
        try {
            boolean resalt = userUCApiService.checkUserBurseAdd(address, code);
            if (!resalt) {
                return error("手机验证码输入错误", response, model);
            }
            userLccService.bindUserAdd(userinfo.getUserName(), address);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        } catch (Exception exception) {
            return error("修改失败!", "", response, model);
        }
        return success("重置成功!", "", response, model);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (file == null) {
            return error("上传失败,请更换图片重试", response, model);
        }

        if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")  ){
            return error("文件类型错误,请上传图片类型", response, model);
        }

        String path = request.getSession().getServletContext().getRealPath("userProof")+"/"+DateUtils2.getDate();

        String fileName = UUID.randomUUID().toString().replace("-", "")+"."+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            return error(e.getMessage(), response, model);
        }
        model.addAttribute("path", request.getContextPath() + "/userProof/" +DateUtils2.getDate()+"/"+ fileName);
        return success("成功!!", response, model);

    }

}
