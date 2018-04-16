/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.MsmUtils;
import com.thinkgem.jeesite.modules.sys.utils.ShopCommunicationConfigUtils;
import com.thinkgem.jeesite.modules.sys.utils.WechatConfigUtils;
import com.thinkgem.jeesite.modules.sys.utils.annotations.RecordLog;
import com.thinkgem.jeesite.modules.user.entity.UserOptions;
import com.thinkgem.jeesite.modules.user.service.UserOptionsService;
import com.thinkgem.jeesite.modules.wechat.service.WeixinService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常规配置Controller
 *
 * @author wyxiang
 * @version 2017-04-26
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userOptions")
public class UserOptionsController extends BaseController {

    @Resource
    private UserOptionsService userOptionsService;

    @Resource
    private WeixinService wexinService;


    @ModelAttribute
    public UserOptions get(@RequestParam(required = false) String optName) {
        UserOptions entity = null;
        if (StringUtils2.isNotBlank(optName)) {

            entity = userOptionsService.getByOptionName(optName);
        }
        if (entity == null) {
            entity = new UserOptions();
        }

        return entity;
    }


    @RequiresPermissions("user:userOptions:view")
    @RequestMapping(value = {"system"})
    public String system(String groupName, @RequestParam(required = false) String optLabel, Model model, RedirectAttributes redirectAttributes) {

        Map<String, Map<String, String>> optionsMap = new HashMap<String, Map<String, String>>();
        List<UserOptions> optionList = null;
        if (StringUtils2.isNotBlank(groupName)) {
            optionList = userOptionsService.getByGroupName(groupName);

            for (UserOptions userOptions : optionList) {
                optionsMap.put(userOptions.getOptionName(), (Map<String, String>) JSONUtils.parse(userOptions.getOptionValue()));
            }
        }

        if (optionsMap.size() == 0) {
            addMessage(redirectAttributes, "读取配置失败!");
            return "modules/user/userOptions_system";
        }

        if (StringUtils2.isBlank(optLabel)) {
            optLabel = optionList.get(0).getOptionName();
        }

        model.addAttribute("selOptLabel", optLabel);
        model.addAttribute("optionList", optionList);
        model.addAttribute("options", optionsMap);
        return "modules/user/userOptions_system";
    }

    @RecordLog(logTitle = "常规配置-系统参数设置-系统设置")
    @RequiresPermissions("user:userOptions:edit")
    @RequestMapping(value = "save")
    public String save(UserOptions userOptions, Model model, RedirectAttributes redirectAttributes, @RequestParam Map<String, String> params) throws ValidationException {
        if (!beanValidator(model, userOptions)) {

            return system(userOptions.getGroupName(), userOptions.getOptionName(), model, redirectAttributes);
        }

        String jsonString = JSONUtils.toJSONString(params);
        userOptions.setOptionValue(jsonString);

        userOptionsService.save(userOptions);

        Global.initOptionConfig(userOptions.getOptionName());

        addMessage(redirectAttributes, "保存配置成功");

        return "redirect:" + Global.getAdminPath() + "/user/userOptions/system?groupName=" + userOptions.getGroupName() + "&optLabel=" + userOptions.getOptionName();
    }


    @RequiresPermissions("user:userOptions:view")
    @RequestMapping(value = {"systempay"})
    public String systempay(String groupName, @RequestParam(required = false) String optLabel, Model model, RedirectAttributes redirectAttributes) {

        Map<String, Map<String, String>> optionsMap = new HashMap<String, Map<String, String>>();
        List<UserOptions> optionList = null;
        if (StringUtils2.isNotBlank(groupName)) {
            optionList = userOptionsService.getByGroupName(groupName);

            for (UserOptions userOptions : optionList) {
                optionsMap.put(userOptions.getOptionName(), (Map<String, String>) JSONUtils.parse(userOptions.getOptionValue()));
            }
        }

        if (optionsMap.size() == 0) {
            addMessage(redirectAttributes, "读取配置失败!");
            return "modules/user/userOptions_systempay";
        }

        if (StringUtils2.isBlank(optLabel)) {
            optLabel = optionList.get(0).getOptionName();
        }


        model.addAttribute("selOptLabel", optLabel);
        model.addAttribute("optionList", optionList);
        model.addAttribute("options", optionsMap);
        return "modules/user/userOptions_systempay";
    }

    @RequiresPermissions("user:userOptions:view")
    @RequestMapping(value = {"trans"})
    public String trans(String groupName, @RequestParam(required = false) String optLabel, Model model, RedirectAttributes redirectAttributes) {

        Map<String, Map<String, String>> optionsMap = new HashMap<String, Map<String, String>>();
        List<UserOptions> optionList = null;
        if (StringUtils2.isNotBlank(groupName)) {
            optionList = userOptionsService.getByGroupName(groupName);

            for (UserOptions userOptions : optionList) {
                optionsMap.put(userOptions.getOptionName(), (Map<String, String>) JSONUtils.parse(userOptions.getOptionValue()));
            }
        }

        if (optionsMap.size() == 0) {
            addMessage(redirectAttributes, "读取配置失败!");
            return "modules/user/userOptions_trans";
        }

        if (StringUtils2.isBlank(optLabel)) {
            optLabel = optionList.get(0).getOptionName();
        }


        model.addAttribute("selOptLabel", optLabel);
        model.addAttribute("optionList", optionList);

        model.addAttribute("options", optionsMap);
        return "modules/user/userOptions_trans";
    }

    @RequiresPermissions("user:userOptions:view")
    @RequestMapping(value = {"help"})
    public String help(String groupName, @RequestParam(required = false) String optLabel, Model model, RedirectAttributes redirectAttributes) {

        Map<String, Map<String, String>> optionsMap = new HashMap<String, Map<String, String>>();
        List<UserOptions> optionList = null;
        if (StringUtils2.isNotBlank(groupName)) {
            optionList = userOptionsService.getByGroupName(groupName);

            for (UserOptions userOptions : optionList) {
                optionsMap.put(userOptions.getOptionName(), (Map<String, String>) JSONUtils.parse(userOptions.getOptionValue()));
            }
        }

        if (optionsMap.size() == 0) {
            addMessage(redirectAttributes, "读取配置失败!");
            return "modules/user/userOptions_help";
        }

        if (StringUtils2.isBlank(optLabel)) {
            optLabel = optionList.get(0).getOptionName();
        }


        model.addAttribute("selOptLabel", optLabel);
        model.addAttribute("optionList", optionList);

        model.addAttribute("options", optionsMap);
        return "modules/user/userOptions_help";
    }

    @RequiresPermissions("user:userOptions:view")
    @RequestMapping(value = {"userSet"})
    public String userSet(String groupName, @RequestParam(required = false) String optLabel, Model model, RedirectAttributes redirectAttributes) {

        Map<String, Map<String, String>> optionsMap = new HashMap<String, Map<String, String>>();
        List<UserOptions> optionList = null;
        if (StringUtils2.isNotBlank(groupName)) {
            optionList = userOptionsService.getByGroupName(groupName);

            for (UserOptions userOptions : optionList) {
                optionsMap.put(userOptions.getOptionName(), (Map<String, String>) JSONUtils.parse(userOptions.getOptionValue()));
            }
        }

        if (optionsMap.size() == 0) {
            addMessage(redirectAttributes, "读取配置失败!");
            return "modules/user/userOptions_vipset";
        }

        if (StringUtils2.isBlank(optLabel)) {
            optLabel = optionList.get(0).getOptionName();
        }


        model.addAttribute("selOptLabel", optLabel);
        model.addAttribute("optionList", optionList);

        model.addAttribute("options", optionsMap);
        return "modules/user/userOptions_vipset";
    }

    @RecordLog(logTitle = "常规配置-爱尚养猪")
    @RequiresPermissions("user:userOptions:edit")
    @RequestMapping(value = "savetransGroup")
    public String savetransGroup(UserOptions userOptions, Model model, RedirectAttributes redirectAttributes, @RequestParam Map<String, String> params) throws ValidationException {
        if (!beanValidator(model, userOptions)) {

            return systempay(userOptions.getGroupName(), userOptions.getOptionName(), model, redirectAttributes);
        }

        String jsonString = JSONUtils.toJSONString(params);
        userOptions.setOptionValue(jsonString);

        userOptionsService.save(userOptions);

        Global.initOptionConfig(userOptions.getOptionName());

        addMessage(redirectAttributes, "保存配置成功");

        return "redirect:" + Global.getAdminPath() + "/user/userOptions/trans?groupName=" + userOptions.getGroupName() + "&optLabel=" + userOptions.getOptionName();
    }

    @RecordLog(logTitle = "常规配置-互助系统-配置")
    @RequiresPermissions("user:userOptions:edit")
    @RequestMapping(value = "saveHelpGroup")
    public String saveHelpGroup(UserOptions userOptions, Model model, RedirectAttributes redirectAttributes, @RequestParam Map<String, String> params) throws ValidationException {
        if (!beanValidator(model, userOptions)) {

            return systempay(userOptions.getGroupName(), userOptions.getOptionName(), model, redirectAttributes);
        }

        String jsonString = JSONUtils.toJSONString(params);
        userOptions.setOptionValue(jsonString);

        userOptionsService.save(userOptions);

        Global.initOptionConfig(userOptions.getOptionName());

        addMessage(redirectAttributes, "保存配置成功");

        return "redirect:" + Global.getAdminPath() + "/user/userOptions/help?groupName=" + userOptions.getGroupName() + "&optLabel=" + userOptions.getOptionName();
    }

    @RecordLog(logTitle = "常规配置-会员管理配置-会员管理配置")
    @RequiresPermissions("user:userOptions:edit")
    @RequestMapping(value = "saveUserSet")
    public String saveUserSet(UserOptions userOptions, Model model, RedirectAttributes redirectAttributes, @RequestParam Map<String, String> params) throws ValidationException {
        if (!beanValidator(model, userOptions)) {

            return systempay(userOptions.getGroupName(), userOptions.getOptionName(), model, redirectAttributes);
        }

        String jsonString = JSONUtils.toJSONString(params);
        userOptions.setOptionValue(jsonString);

        userOptionsService.save(userOptions);

        Global.initOptionConfig(userOptions.getOptionName());

        addMessage(redirectAttributes, "保存配置成功");

        return "redirect:" + Global.getAdminPath() + "/user/userOptions/userSet?groupName=" + userOptions.getGroupName() + "&optLabel=" + userOptions.getOptionName();
    }

    @RecordLog(logTitle = "常规配置-第三方支付-微信支付/支付宝支付")
    @RequiresPermissions("user:userOptions:edit")
    @RequestMapping(value = "savepay")
    public String savePay(UserOptions userOptions, Model model, RedirectAttributes redirectAttributes, @RequestParam Map<String, String> params) throws ValidationException {
        if (!beanValidator(model, userOptions)) {

            return systempay(userOptions.getGroupName(), userOptions.getOptionName(), model, redirectAttributes);
        }

        String jsonString = JSONUtils.toJSONString(params);
        userOptions.setOptionValue(jsonString);

        userOptionsService.save(userOptions);

        Global.initOptionConfig(userOptions.getOptionName());

        addMessage(redirectAttributes, "保存配置成功");

        return "redirect:" + Global.getAdminPath() + "/user/userOptions/systempay?groupName=" + userOptions.getGroupName() + "&optLabel=" + userOptions.getOptionName();
    }

    @RequiresPermissions("user:userOptions:view")
    @RequestMapping(value = {"wechatConfig"})
    public String wechatConfig(String groupName, @RequestParam(required = false) String optLabel, Model model, RedirectAttributes redirectAttributes) {
        Map<String, Map<String, String>> optionsMap = new HashMap<String, Map<String, String>>();
        List<UserOptions> optionList = null;
        if (StringUtils2.isNotBlank(groupName)) {
            optionList = userOptionsService.getByGroupName(groupName);

            for (UserOptions userOptions : optionList) {
                optionsMap.put(userOptions.getOptionName(), (Map<String, String>) JSONUtils.parse(userOptions.getOptionValue()));
            }
        }

        if (optionsMap.size() == 0) {
            addMessage(redirectAttributes, "读取配置失败!");
            return "modules/wechat/wechatConfig";
        }

        if (StringUtils2.isBlank(optLabel)) {
            optLabel = optionList.get(0).getOptionName();
        }


        model.addAttribute("selOptLabel", optLabel);
        model.addAttribute("optionList", optionList);
        model.addAttribute("options", optionsMap);

        return "modules/wechat/wechatConfig";
    }

    @RequiresPermissions("user:userOptions:view")
    @RequestMapping(value = {"authGroup"})
    public String authGroup(String groupName, @RequestParam(required = false) String optLabel, Model model, RedirectAttributes redirectAttributes) {

        Map<String, Map<String, String>> optionsMap = new HashMap<String, Map<String, String>>();
        List<UserOptions> optionList = null;
        if (StringUtils2.isNotBlank(groupName)) {
            optionList = userOptionsService.getByGroupName(groupName);

            for (UserOptions userOptions : optionList) {
                optionsMap.put(userOptions.getOptionName(), (Map<String, String>) JSONUtils.parse(userOptions.getOptionValue()));
            }
        }

        if (optionsMap.size() == 0) {
            addMessage(redirectAttributes, "读取配置失败!");
            return "modules/user/userOptions_authGroup";
        }

        if (StringUtils2.isBlank(optLabel)) {
            optLabel = optionList.get(0).getOptionName();
        }


        model.addAttribute("selOptLabel", optLabel);
        model.addAttribute("optionList", optionList);
        model.addAttribute("options", optionsMap);
        return "modules/user/userOptions_authGroup";
    }

    @RecordLog(logTitle = "常规配置-平台整合-玩淘宝整合")
    @RequiresPermissions("user:userOptions:edit")
    @RequestMapping(value = "saveauthGroup")
    public String saveauthGroup(UserOptions userOptions, Model model, RedirectAttributes redirectAttributes, @RequestParam Map<String, String> params) throws ValidationException {
        if (!beanValidator(model, userOptions)) {

            return systempay(userOptions.getGroupName(), userOptions.getOptionName(), model, redirectAttributes);
        }

        String jsonString = JSONUtils.toJSONString(params);
        userOptions.setOptionValue(jsonString);

        userOptionsService.save(userOptions);

        Global.initOptionConfig(userOptions.getOptionName());
        ShopCommunicationConfigUtils.initShopConfigs();

        addMessage(redirectAttributes, "保存配置成功");

        return "redirect:" + Global.getAdminPath() + "/user/userOptions/authGroup?groupName=" + userOptions.getGroupName() + "&optLabel=" + userOptions.getOptionName();
    }

    @RecordLog(logTitle = "常规配置-系统参数设置-短信设置")
    @RequiresPermissions("user:userOptions:edit")
    @RequestMapping(value = "saveLKMsm")
    public String saveLKSms(UserOptions userOptions, Model model, RedirectAttributes redirectAttributes, @RequestParam Map<String, String> params) throws ValidationException {
        if (!beanValidator(model, userOptions)) {
            return system(userOptions.getGroupName(), userOptions.getOptionName(), model, redirectAttributes);
        }

        String jsonString = JSONUtils.toJSONString(params);
        userOptions.setOptionValue(jsonString);

        userOptionsService.save(userOptions);

        Global.initOptionConfig(userOptions.getOptionName());

        MsmUtils.getInstance().initLingKaiAccountConfig();

        addMessage(redirectAttributes, "保存配置成功");

        return "redirect:" + Global.getAdminPath() + "/user/userOptions/system?groupName=" + userOptions.getGroupName() + "&optLabel=" + userOptions.getOptionName();
    }

    @RecordLog(logTitle = "常规配置-系统参数设置-APP版本设置")
    @RequiresPermissions("user:userOptions:edit")
    @RequestMapping(value = "saveAppVersionInfo")
    public String saveAppVersionInfo(UserOptions userOptions, Model model, RedirectAttributes redirectAttributes, @RequestParam Map<String, String> params) throws ValidationException {
        if (!beanValidator(model, userOptions)) {
            return system(userOptions.getGroupName(), userOptions.getOptionName(), model, redirectAttributes);
        }

        String jsonString = JSONUtils.toJSONString(params);
        userOptions.setOptionValue(jsonString);

        userOptionsService.save(userOptions);

        Global.initOptionConfig(userOptions.getOptionName());

        addMessage(redirectAttributes, "保存配置成功");

        return "redirect:" + Global.getAdminPath() + "/user/userOptions/system?groupName=" + userOptions.getGroupName() + "&optLabel=" + userOptions.getOptionName();
    }

    @RecordLog(logTitle = "微信公众号-基本功能-基础设置")
    @RequiresPermissions("user:userOptions:edit")
    @RequestMapping(value = "saveWeChatConfig")
    public String saveWeChatConfig(UserOptions userOptions, Model model, RedirectAttributes redirectAttributes, @RequestParam Map<String, String> params) throws ValidationException {
        if (!beanValidator(model, userOptions)) {
            return system(userOptions.getGroupName(), userOptions.getOptionName(), model, redirectAttributes);
        }

        String jsonString = JSONUtils.toJSONString(params);
        userOptions.setOptionValue(jsonString);

        userOptionsService.save(userOptions);

        //Global.initOptionConfig(userOptions.getOptionName());
        WechatConfigUtils.getInstance().initWechatConfig();
        wexinService.init();

        addMessage(redirectAttributes, "保存配置成功");

        return "redirect:" + Global.getAdminPath() + "/user/userOptions/wechatConfig?groupName=" + userOptions.getGroupName() + "&optLabel=" + userOptions.getOptionName();
    }

    @RequiresPermissions("user:userOptions:view")
    @RequestMapping(value = {"wxMsgSend"})
    public String wxMsgSendGroup(String groupName, @RequestParam(required = false) String optLabel, Model model, RedirectAttributes redirectAttributes) {

        Map<String, Map<String, String>> optionsMap = new HashMap<String, Map<String, String>>();
        List<UserOptions> optionList = null;
        if (StringUtils2.isNotBlank(groupName)) {
            optionList = userOptionsService.getByGroupName(groupName);

            for (UserOptions userOptions : optionList) {
                optionsMap.put(userOptions.getOptionName(), (Map<String, String>) JSONUtils.parse(userOptions.getOptionValue()));
            }
        }

        if (optionsMap.size() == 0) {
            addMessage(redirectAttributes, "读取配置失败!");
            return "modules/wechat/sysWxMsgSend";
        }

        if (StringUtils2.isBlank(optLabel)) {
            optLabel = optionList.get(0).getOptionName();
        }


        model.addAttribute("selOptLabel", optLabel);
        model.addAttribute("optionList", optionList);
        model.addAttribute("options", optionsMap);

        return "modules/wechat/sysWxMsgSend";
    }
}