package com.thinkgem.jeesite.website.user.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.utils.VerifyUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.user.entity.UserBankWithdraw;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfoBank;
import com.thinkgem.jeesite.modules.user.service.UserBankWithdrawService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoBankService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yankai on 2017/7/13.
 */
@Controller
@RequestMapping(value = "${frontPath}/webUser/userBank")
public class UserBankController extends BaseController {
    @Resource
    private UserUserinfoBankService userUserinfoBankService;

    @Resource
    private UserBankWithdrawService userBankWithdrawService;

    @Resource
    private UserUserinfoService userUserinfoService;

    /**
     * 个人银行卡列表
     * 表名:
     * 条件:
     * 其他:
     */
    @RequestMapping(value = "/userBankList", method = RequestMethod.POST)
    public String userBankList(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userinfo = userUserinfoService.getByName(UserInfoUtils.getUser().getUserName());
        if (userinfo == null) {
            return error("用户信息查询失败,请稍后操作!", response, model);
        }
        UserUserinfoBank userinfoBank = new UserUserinfoBank();
        userinfoBank.setUserName(userinfo.getUserName());
        Page<UserUserinfoBank> page = userUserinfoBankService.findPage(new Page<UserUserinfoBank>(request, response), userinfoBank);
        model.addAttribute("userBankList", page);
        return success("成功!!", response, model);
    }

    /**
     * 个人银行卡删除
     * 表名: userinfo
     * 条件:
     * 其他:
     */
    @RequestMapping(value = "/deleteUserBank", method = RequestMethod.POST)
    public String deleteUserBank(HttpServletRequest request, HttpServletResponse response, Model model) throws ValidationException {

        String userName = UserInfoUtils.getUser().getUserName();
        if (StringUtils2.isBlank(userName)) {
            return error("网络繁忙,请稍后重试!", response, model);
        }
        String id = request.getParameter("id");
        UserUserinfoBank userinfoBank = userUserinfoBankService.get(id);
        if (!userName.equals(userinfoBank.getUserName())) {
            return error("只能删除自己的银行卡!!", response, model);
        }
        userUserinfoBankService.delete(userinfoBank);
        return success("删除成功!!", response, model);
    }

    /**
     * 个人银行卡修改/添加
     * 表名: userinfo
     * 条件:
     * 其他:
     */
    @RequestMapping(value = "/updateUserBank", method = RequestMethod.POST)
    public String updateUserBank(UserUserinfoBank userUserinfoBank, HttpServletRequest request, HttpServletResponse response, Model model) throws ValidationException {
        UserUserinfo userUserinfo = userUserinfoService.get(UserInfoUtils.getUser().getId());
        if (userUserinfo == null) {
            return error("绑定失败，请稍后再试", response, model);
        }
        int renzheng = Integer.valueOf(userUserinfo.getRenZheng());
        if (renzheng > 0) {
//            if (!userUserinfoBank.getBankUserName().equals(userUserinfo.getTrueName())) {
//                return error("只可绑定本人的银行卡", response, model);
//            }
            if (!VerifyUtils.isBankCard(userUserinfoBank.getBankUserNum())) {
                return error("请输入正确的银行卡号", response, model);
            }
            userUserinfoBank.setUserName(userUserinfo.getUserName());
            userUserinfoBank.setBankUserName(userUserinfo.getTrueName());
            userUserinfoBank.setBankName(userBankWithdrawService.getByBankCode(userUserinfoBank.getBankCode()).getBankName());
            try {
                userUserinfoBankService.save(userUserinfoBank);
            } catch (ValidationException e) {
                return error(e.getMessage(), response, model);
            }
        } else {
            return error("请先实名认证后,方可绑定银行卡!", response, model);
        }
        return success("个人银行卡添加成功!!", response, model);
    }

    /**
     * 银行列表
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/bankList", method = RequestMethod.POST)
    public String withdrawBankList(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserBankWithdraw userBankWithdraw = new UserBankWithdraw();
        userBankWithdraw.setStatus("1");
        model.addAttribute("bankList", userBankWithdrawService.findList(userBankWithdraw));
        return success("成功!!", response, model);
    }

    @RequestMapping(value = "/withdrawBankByUserBankid", method = RequestMethod.POST)
    public String withdrawBankByUserBankid(HttpServletRequest request, HttpServletResponse response, Model model) {
        String bankId = request.getParameter("bankId");
        UserBankWithdraw userBankWithdraw = userBankWithdrawService.getByBankCode(userUserinfoBankService.get(bankId).getBankCode());
        model.addAttribute("min", Global.getOption("system_user_set", "minMoney"));
        model.addAttribute("max", Global.getOption("system_user_set", "maxMoney"));
        model.addAttribute("multiple", Global.getOption("system_user_set", "multiple"));
        model.addAttribute("bankInfo", userBankWithdraw);
        return success("成功!!", response, model);
    }

    @RequestMapping(value = "/withdrawApply", method = RequestMethod.POST)
    public String withdrawApply(HttpServletRequest request, HttpServletResponse response, Model model) throws ValidationException {

        String money = request.getParameter("money");
        String password = request.getParameter("password");
        String bankCode = request.getParameter("bankCode");
        String userBankId = request.getParameter("userBankId");
        UserBankWithdraw userBankWithdraw = userBankWithdrawService.getByBankCode(bankCode);
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser().getId());
        if (!SystemService.validatePassword(password, userinfo.getBankPassword())) {
            return error("密码输入错误", response, model);
        }
        try {
            userUserinfoService.withdrawApply(UserInfoUtils.getUser().getUserName(), userBankWithdraw, money, userBankId);
            return success("成功!!", response, model);
        } catch (ValidationException e) {
            return success(e.getMessage(), response, model);
        }
    }

    @RequestMapping(value = "/bindingBankCard", method = RequestMethod.GET)
    public String bindingCard(HttpServletRequest request, HttpServletResponse response, Model model) {
        String isWap = request.getParameter("iswap");
        if (!StringUtils2.isBlank(isWap) && isWap.equalsIgnoreCase("1")) {
            return "website/themes/basic_web/system/bindingBankCard";
        }
        return "website/themes/basic_app/system/bindingBankCard";
    }

    @RequestMapping(value = "/addBankCard", method = RequestMethod.GET)
    public String addBankCard(HttpServletRequest request, HttpServletResponse response, Model model) {
        String isWap = request.getParameter("iswap");
        if (!StringUtils2.isBlank(isWap) && isWap.equalsIgnoreCase("1")) {
            return "website/themes/basic_web/system/addBankCard";
        }
        return "website/themes/basic_app/system/addBankCard";
    }
}
