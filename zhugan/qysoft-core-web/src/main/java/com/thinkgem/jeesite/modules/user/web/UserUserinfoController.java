/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.utils.annotations.RecordLog;
import com.thinkgem.jeesite.modules.user.entity.UserChargeValidate;
import com.thinkgem.jeesite.modules.user.entity.UserLevel;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.UserLevelService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 会员列表Controller
 *
 * @author wyxiang
 * @version 2017-03-25
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userUserinfo")
public class UserUserinfoController extends BaseController {

    @Resource
    private UserUserinfoService userUserinfoService;

    @Resource
    private UserLevelService userLevelService;


    @ModelAttribute
    public UserUserinfo get(@RequestParam(required = false) String id) {


        UserUserinfo entity = null;
        if (StringUtils2.isNotBlank(id)) {

            entity = userUserinfoService.get(id);
        }
        if (entity == null) {
            entity = new UserUserinfo();
        }


        return entity;
    }


    @RequiresPermissions("userinfo:userinfo:view")
    @RequestMapping(value = {"list", ""})
    public String list(UserUserinfo userUserinfo, HttpServletRequest request, HttpServletResponse response, Model model) {

        String leader = request.getParameter("leader");
        UserUserinfo userinfo = userUserinfoService.getByName(leader);
        if(userinfo != null){
            userUserinfo.setParentListLike(userinfo.getId());
            model.addAttribute("leader",leader);
        }

        if(StringUtils2.isNotBlank(request.getParameter("isUsercenter"))){
            userUserinfo.setIsUsercenter("1");
            Page<UserUserinfo> page = userUserinfoService.findPage(new Page<UserUserinfo>(request, response), userUserinfo);
            model.addAttribute("page", page);
            return "modules/user/userCenterList";
        }else {
            Page<UserUserinfo> page = userUserinfoService.findPage(new Page<UserUserinfo>(request, response), userUserinfo);
            model.addAttribute("page", page);

            return "modules/user/userUserinfoList";
        }

    }

    @RequiresPermissions("userinfo:userinfo:view")
    @RequestMapping(value = "form")
    public String form(UserUserinfo userUserinfo, Model model) {

        java.util.List<UserLevel> userLevels = userLevelService.findList(new UserLevel());

        model.addAttribute("userLevels", userLevels);
        model.addAttribute("userUserinfo", userUserinfo);

        return "modules/user/userUserinfoForm";
    }

    @RequiresPermissions("user:userUserinfo:edit")
    @RequestMapping(value = "save")
    @RecordLog(logTitle = "用户管理-会员管理-会员添加-新增/修改")
    public String save(UserUserinfo userUserinfo, Model model, RedirectAttributes redirectAttributes) throws ValidationException {
        //qydo 删除--已修改
        try {
            userUserinfo.setParentName(userUserinfo.getWalterName());
            userUserinfoService.save(userUserinfo);
            addMessage(redirectAttributes, "保存会员成功");
            return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/?repage";

        } catch (ValidationException e) {
            addMessage(model, "失败:"+e.getMessage());
            return form(userUserinfo, model);
        }
    }

    @RecordLog(logTitle = "用户管理-报单中心列表-操作-关闭")
    @RequiresPermissions("user:userUserinfo:edit")
    @RequestMapping(value = "savecenter")
    public String savecenter(UserUserinfo userUserinfo, Model model, RedirectAttributes redirectAttributes) throws ValidationException {
        //qydo try catch //qydo 调用 setUserCenter--已解决
        try {

            userUserinfoService.setUserCenter(userUserinfoService.get(userUserinfo.getId()).getUserName(), userUserinfo.getIsUsercenter(),"","");
            addMessage(redirectAttributes, "修改成功");
            return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/list?isUsercenter=1";
        } catch (ValidationException e) {
            addMessage(model, "失败:"+e.getMessage());
            return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/list?isUsercenter=1";
        }

    }
    @RecordLog(logTitle = "用户管理-会员管理-会员删除")
    @RequiresPermissions("user:userUserinfo:edit")
    @RequestMapping(value = "delete")
    public String delete(UserUserinfo userUserinfo, RedirectAttributes redirectAttributes) throws ValidationException {

        //如果会员存下级用户则不可删除
        try {
            userUserinfoService.delete(userUserinfo.getUserName());
            addMessage(redirectAttributes, "删除会员成功");
            return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/?repage";
        } catch (ValidationException e) {
            addMessage(redirectAttributes, e.getMessage());
            return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/?repage";
        }
    }



    @RecordLog(logTitle = "用户管理-报单中心审核")
    @RequiresPermissions("user:userUserinfo:edit")
    @RequestMapping(value = "updateCenterLevel")
    public String updateCenterLevel(UserUserinfo userUserinfo, String promValue, Model model, RedirectAttributes redirectAttributes) throws ValidationException {
        if (!beanValidator(model, userUserinfo)) {
            return form(userUserinfo, model);
        }
        //qydo 抛异常
        //qydo 调用 setUserCenter--已解决
        userUserinfo.setUsercenterLevel(promValue);
        try {
            userUserinfoService.setUserCenter(userUserinfoService.get(userUserinfo.getId()).getUserName(),"",userUserinfo.getUsercenterLevel(),"");
            addMessage(redirectAttributes, "修改成功");
            return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/list?isUsercenter=1";
        } catch (ValidationException e) {
            addMessage(redirectAttributes, e.getMessage());
            return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/list?isUsercenter=1";
        }
    }


    @RequestMapping(value = "selectUserTagList")
    public String selectUserTagList(UserUserinfo userUserinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserUserinfo> page = userUserinfoService.findPage(new Page<UserUserinfo>(request, response), userUserinfo);
        model.addAttribute("page", page);

        return "modules/user/selectUserTagList";
    }


    /**
     * 用户充值
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "userCharge", method = RequestMethod.GET)
    public String userCharge(Model model) {


        return "modules/user/userAdminChargeForm";
    }


    /**
     * 用户充值
     *
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RecordLog(logTitle = "财务管理-会员充值")
    @RequestMapping(value = "userCharge", method = RequestMethod.POST)
    public String userCharge(UserChargeValidate chargeValidate, Model model, RedirectAttributes redirectAttributes) throws ValidationException {

        try {
            if(chargeValidate.getChargeType().equals(EnumUtil.MoneyType.money.toString())){

                userUserinfoService.updateUserMoney(chargeValidate.getUserName(), new BigDecimal(chargeValidate.getChargeMoney()), chargeValidate.getCommt()+"-系统充值" + UserUtils.getUser().getLoginName(), EnumUtil.MoneyChangeType.charget);
            }
            else if(chargeValidate.getChargeType().equals(EnumUtil.MoneyType.score.toString())){

                userUserinfoService.updateUserScore(chargeValidate.getUserName(), new BigDecimal(chargeValidate.getChargeMoney()), chargeValidate.getCommt()+"-系统充值" + UserUtils.getUser().getLoginName(), EnumUtil.MoneyChangeType.charget);
            }
            else if(chargeValidate.getChargeType().equals(EnumUtil.MoneyType.coin.toString())){

                userUserinfoService.updateUserCoin(chargeValidate.getUserName(), new BigDecimal(chargeValidate.getChargeMoney()), chargeValidate.getCommt()+"-系统充值" + UserUtils.getUser().getLoginName(), EnumUtil.MoneyChangeType.charget);
            }
            else if(chargeValidate.getChargeType().equals(EnumUtil.MoneyType.money2.toString())){

                userUserinfoService.updateUserOtherMoney(chargeValidate.getUserName(), new BigDecimal(chargeValidate.getChargeMoney()), EnumUtil.MoneyType.money2, chargeValidate.getCommt()+"-系统充值" + UserUtils.getUser().getLoginName(), EnumUtil.MoneyChangeType.charget);
            } else if(chargeValidate.getChargeType().equals(EnumUtil.MoneyType.money3.toString())){

                userUserinfoService.updateUserOtherMoney(chargeValidate.getUserName(), new BigDecimal(chargeValidate.getChargeMoney()), EnumUtil.MoneyType.money3, chargeValidate.getCommt()+"-系统充值" + UserUtils.getUser().getLoginName(), EnumUtil.MoneyChangeType.charget);
            } else if(chargeValidate.getChargeType().equals(EnumUtil.MoneyType.money4.toString())){

                userUserinfoService.updateUserOtherMoney(chargeValidate.getUserName(), new BigDecimal(chargeValidate.getChargeMoney()), EnumUtil.MoneyType.money4, chargeValidate.getCommt()+"-系统充值" + UserUtils.getUser().getLoginName(), EnumUtil.MoneyChangeType.charget);
            }
            else{

                addMessage(redirectAttributes, "充值失败,充值类型不存在!");
                return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/userCharge?repage";
            }
            addMessage(redirectAttributes, "充值用户资金成功!");
            return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/userCharge?repage";
        } catch (ValidationException e) {
            return userCharge(model);
        }catch (Exception e){
            addMessage(redirectAttributes, "充值失败!");
            return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/userCharge?repage";
        }

    }


    /**
     * 会员网络图
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "userGrid")
    public String userGrid(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {

        String userName = request.getParameter("userName");
        String mobile = request.getParameter("mobile");

        Map<String, Object> treeMap = null;

        if(StringUtils2.isNotBlank(userName)){
            UserUserinfo userinfo =  userUserinfoService.getByName(userName);
            if(null == userinfo || StringUtils2.isBlank(userinfo.getId())){
                addMessage(redirectAttributes, "未找到该用户!");
                return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/userGrid?repage";
            }

            treeMap =  userUserinfoService.getUsersTreeByParentName(userName,4,"");
        }
        else if(StringUtils2.isNotBlank(mobile)){
            UserUserinfo userinfoList =   userUserinfoService.getByMobile(mobile);
            if(userinfoList != null){
                treeMap = userUserinfoService.getUsersTreeByParentName(userinfoList.getUserName(),4,"");
            }
            else{
                addMessage(redirectAttributes, "未找到该用户!");
                return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/userGrid?repage";
            }
        }
        else {

             List<UserUserinfo> infoList  =  userUserinfoService.getUsersByParentName("");
             if(infoList.size() > 0){
                 treeMap = userUserinfoService.getUsersTreeByParentName(infoList.get(0).getUserName(),4,"");
             }
             else{
                 addMessage(redirectAttributes, "网络图查询失败,未找到顶点用户!");
                 return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/userGrid?repage";
             }

        }

        if(treeMap.size() ==0){
            return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/userGrid?repage";
        }

        model.addAttribute("treeMap", JsonMapper.toJsonString(treeMap));
        return "modules/user/userGrid";
    }


    /**
     *  查询会员网络图父级
     *
     * @param model
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "userGridParent" )
    public Map<String, Object> userGridParent(HttpServletRequest request, HttpServletResponse response, Model model) {

        String userid = request.getParameter("id");

        UserUserinfo userinfo = userUserinfoService.get(userid);

        Map<String, Object> treeMap = null;

        treeMap =  userUserinfoService.getUsersTreeByParentName(userinfo.getParentName(),1,"");


        return treeMap;
    }


    /**
     *  查询会员网络图下级
     *
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "userGridChildren" )
    public Map<String, Object>  userGridChildren(HttpServletRequest request, HttpServletResponse response, Model model) {


        String userid = request.getParameter("id");

        UserUserinfo userinfo = userUserinfoService.get(userid);

        Map<String, Object> treeMap = null;

        treeMap =  userUserinfoService.getUsersTreeByParentName(userinfo.getUserName(),2,"");


        return treeMap;
    }


    /**
     *  查询会员网络图家族
     *
     * @param model
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "userGridFamilies" )
    public Map<String, Object> userGridFamilies(HttpServletRequest request, HttpServletResponse response, Model model) {

        String userid = request.getParameter("id");

        UserUserinfo userinfo = userUserinfoService.get(userid);

        Map<String, Object> treeMap = null;

        treeMap =  userUserinfoService.getUsersTreeByParentName(userinfo.getParentName(),2,userid);


        return treeMap;
    }



    /**
     *  查询会员网络图平级
     *
     * @param model
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "userGridSiblings" )
    public Map<String, Object> userGridSiblings(HttpServletRequest request, HttpServletResponse response, Model model) {

        String userid = request.getParameter("id");

        UserUserinfo userinfo = userUserinfoService.get(userid);

        Map<String, Object> treeMap = null;

        treeMap =  userUserinfoService.getUsersTreeByParentName(userinfo.getParentName(),2,userid);


        return treeMap;
    }


    @RequiresPermissions("user:userUserinfo:view")
    @RequestMapping(value = "userCenterform")
    public String userCenterform(UserUserinfo userUserinfo, Model model) {
        model.addAttribute("userUserinfo", userUserinfo);
        return "modules/user/userUserCenterForm";
    }

    @RecordLog(logTitle = "用户管理-报单中心列表-报单中心管理添加-新增")
    @RequiresPermissions("user:userUserinfo:edit")
    @RequestMapping(value = "updateUserCenterByName")
    public String updateUserCenterByName(UserUserinfo userInfo, Model model, RedirectAttributes redirectAttributes) throws ValidationException {

        userUserinfoService.setUserCenter (userInfo.getUserName (),EnumUtil.YesNO.YES.toString (),EnumUtil.YesNO.YES.toString (),userInfo.getUsercenterAddress ());
        addMessage(redirectAttributes, "添加报单中心成功!");
        return "redirect:" + Global.getAdminPath() + "/user/userUserinfo/list?isUsercenter=1";
    }

    }

