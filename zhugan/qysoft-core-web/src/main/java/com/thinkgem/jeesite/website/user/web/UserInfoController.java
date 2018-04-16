package com.thinkgem.jeesite.website.user.web;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils2;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.user.entity.UserAccountchange;
import com.thinkgem.jeesite.modules.user.entity.UserChargeLog;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.UserAccountchangeService;
import com.thinkgem.jeesite.modules.user.service.UserChargeLogService;
import com.thinkgem.jeesite.modules.user.service.UserMsmService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yankai on 2017/7/13.
 */
@Controller
@RequestMapping(value = "${frontPath}/webUser/userInfo")
public class UserInfoController extends BaseController {
    @Resource
    private UserUserinfoService userUserinfoService;

    @Resource
    private UserAccountchangeService userAccountchangeService;

    @Resource
    private UserChargeLogService userChargeLogService;

    @Resource
    private UserMsmService userMsmService;

    /**
     * 查询当前用户个人信息
     * 表名: userinfo
     * 条件:
     * 其他:
     */
    @RequestMapping(value = "/userinfo", method = RequestMethod.POST)
    public String userinfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("user", userUserinfoService.get(UserInfoUtils.getUser().getId()));
        return success("成功!!", response, model);
    }

    /**
     * 密码修改
     * 表名: userinfo
     * 条件:
     * 其他:
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser().getId());
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        if (!SystemService.validatePassword(oldPwd, userinfo.getUserPass())) {
            return error("原始密码输入错误", response, model);
        }
        try {
            userUserinfoService.updateUserPwd(newPwd);
            return success("修改密码成功!!", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        }
    }

    /**
     * 支付密码修改
     * 表名: userinfo
     * 条件:
     * 其他:
     */
    @RequestMapping(value = "/updatePayPassword", method = RequestMethod.POST)
    public String updatePayPassword(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser().getId());
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        if (!SystemService.validatePassword(oldPwd, userinfo.getBankPassword())) {
            return error("原始支付密码输入错误", response, model);
        }
        try {
            userUserinfoService.updateUserPaypass(newPwd);
            return success("修改支付密码成功!!", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        }
    }

    /**
     * 二级密码修改
     * 表名: userinfo
     * 条件:
     * 其他:
     */
    @RequestMapping(value = "/updateOtherPassword", method = RequestMethod.POST)
    public String updateOtherPassword(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser().getId());
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        if (!SystemService.validatePassword(oldPwd, userinfo.getZhuanquPass())) {
            return error("原始二级密码输入错误", response, model);
        }
        try {
            userUserinfoService.updateUserZhuanqupass(newPwd);
            return success("修改二级密码成功!!", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        }
    }

    /**
     * 个人账变查询
     * 表名: userinfo
     * 条件:
     * 其他:
     */

    @RequestMapping(value = "/userAccountChange", method = RequestMethod.POST)
    public String userAccountchange(HttpServletRequest request, HttpServletResponse response, Model model) {
        String moneyType = request.getParameter("moneyType");
        String changeType = request.getParameter("changeType");
        String date = request.getParameter("date");
        UserAccountchange userAccountchange = new UserAccountchange();
        if (StringUtils2.isNotBlank(changeType)) {
            if (changeType.equals(EnumUtil.YesNO.YES.toString())) {
                userAccountchange.setChangeMoneyEnd(new BigDecimal(0));
            } else {
                userAccountchange.setChangeMoneyBegin(new BigDecimal(0));
            }
        }
        if (StringUtils2.isNotBlank(date)) {
            Date afterDate = DateUtils2.getDiffDatetime(new Date(), Integer.parseInt(date) * (-1));
            userAccountchange.setCreateDateBegin(afterDate);
        }
        if (StringUtils2.isNotBlank(moneyType)) {
            userAccountchange.setMoneyType(moneyType);
        }
        userAccountchange.setUserName(UserInfoUtils.getUser().getUserName());
        Page<UserAccountchange> page = userAccountchangeService.findPage(new Page<UserAccountchange>(request, response), userAccountchange);
        model.addAttribute("userAccountchange", page);
        return success("成功!!", response, model);
    }

    /**
     * 充值记录查询
     * 表名: userinfo
     * 条件:
     * 其他:
     */

    @RequestMapping(value = "/userChargeLog", method = RequestMethod.POST)
    public String userChargeLog(HttpServletRequest request, HttpServletResponse response, Model model) {

        UserChargeLog userChargeLog = new UserChargeLog();
        userChargeLog.setUserName(UserInfoUtils.getUser().getUserName());
        Page<UserChargeLog> page = userChargeLogService.findPage(new Page<UserChargeLog>(request, response), userChargeLog);
        model.addAttribute("userChargeLogList", page);
        return success("成功!!", response, model);
    }

    @RequestMapping(value = "/forgetPassword", method = RequestMethod.GET)
    public String forgetPasswordPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        String isWap = request.getParameter("iswap");
        if (!StringUtils2.isBlank(isWap) && isWap.equalsIgnoreCase("1")) {
            return "website/themes/basic_web/system/forgetPassword";
        }
        return "website/themes/basic_app/system/forgetPassword";
    }

    /**
     * 忘记密码
     * 表名: userinfo
     * 条件:
     * 其他:
     */
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    public String forgetPassword(HttpServletRequest request, HttpServletResponse response, Model model) {
        String newPwd = request.getParameter("newPwd");
        String newPwds = request.getParameter("newPwds");
        String mobile = request.getParameter("mobile");
        String verifyCode = request.getParameter("code");
        UserUserinfo userinfo = userUserinfoService.getByMobile(mobile);
        if (StringUtils2.isBlank(mobile)) {
            return error("手机号不能为空!", response, model);
        }
        if (null == userinfo) {
            return error("该用户不存在!", response, model);
        }
        if (StringUtils2.isBlank(verifyCode)) {
            return error("验证码不能为空!", response, model);
        } else {
            if (!userMsmService.checkVerifyCode(mobile, verifyCode)) {
                return error("验证码错误!", response, model);
            }
        }
        if (StringUtils2.isBlank(newPwd) || StringUtils2.isBlank(newPwds)) {
            return error("请输入新密码!", response, model);
        } else {
            if (!newPwds.equals(newPwd)) {
                return error("两次输入密码不一致!请重新输入!", response, model);
            }
        }

        try {
            userUserinfoService.forgetUserPwd(userinfo.getUserName(), newPwds);
            return success("恭喜你已成功设置新密码!", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        }
    }
}
