/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.user.service.SysDataClearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 系统数据清除Controller
 *
 * @author hou
 * @version 2017-05-03
 */
@Controller
@RequestMapping(value = "${adminPath}/user/sysDataClear")
public class SysDataClearController extends BaseController {

    @Resource
    private SysDataClearService sysDataClearService;

    @RequestMapping(value = "")
    public String list() {
        return "modules/user/sysDataClear";
    }

    @RequestMapping(value = "singleTable")
    public String clearSingleTable(String name, Model model) {
        switch (name) {
            case "user_accountchange": {
                sysDataClearService.clearUserAccountChange();
                break;
            }
            case "user_charge": {
                sysDataClearService.clearUserCharge();
                break;
            }
            case "user_charge_back_record": {
                sysDataClearService.clearUserChargeBackRecord();
                break;
            }
            case "user_charge_log": {
                sysDataClearService.clearUserChargeLog();
                break;
            }
            case "user_level_log": {
                sysDataClearService.clearUserLevelLog();
                break;
            }
            case "user_mailmessage": {
                sysDataClearService.clearUserMailMessage();
                break;
            }
            case "user_report": {
                sysDataClearService.clearUserReport();
                break;
            }
            case "user_usercenter_log": {
                sysDataClearService.clearUserUserCenterLog();
                break;
            }
            case "user_userinfo": {
                sysDataClearService.clearUserUserInfo();
                break;
            }
            case "user_userinfo_bank": {
                sysDataClearService.clearUserUserInfoBank();
                break;
            }
            case "user_withdraw": {
                sysDataClearService.clearUserWithDraw();
                break;
            }
            case "sys_log": {
                sysDataClearService.clearSysLog ();
                break;
            }
            case "trans_apply": {
                sysDataClearService.clearTransApply();
                break;
            }
            case "trans_apply_condition": {
                sysDataClearService.clearTransApplyCondition();
                break;
            }
            case "trans_buy": {
                sysDataClearService.clearTransBuy();
                break;
            }
            case "trans_buy_day_trend": {
                sysDataClearService.clearTransBuyDayTrend();
                break;
            }
            case "trans_buy_log": {
                sysDataClearService.clearTransBuyLog();
                break;
            }
            case "trans_goods": {
                sysDataClearService.clearTransGoods();
                break;
            }
            case "trans_goods_group": {
                sysDataClearService.clearTransGoodsGroup();
                break;
            }
            case "trans_order": {
                sysDataClearService.clearTransOrder();
                break;
            }
            default: {
                addMessage(model, "清除失败!");
                return "modules/user/sysDataClear";
            }

        }
        addMessage(model, "清除成功");
        return "modules/user/sysDataClear";
    }

    @RequestMapping(value = "allTable")
    public String clearOldTable(Model model) {
        sysDataClearService.clearAllTable();
        addMessage(model, "清除成功");
        return "modules/user/sysDataClear";
    }

}