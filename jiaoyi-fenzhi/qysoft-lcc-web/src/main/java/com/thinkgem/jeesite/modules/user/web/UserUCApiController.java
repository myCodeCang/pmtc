/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.DateUtils2;
import com.thinkgem.jeesite.common.utils.LogAppendHelper;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.shop.service.ShopCategoryService;
import com.thinkgem.jeesite.modules.shop.service.ShopUCApiService;
import com.thinkgem.jeesite.modules.sys.utils.ShopCommunicationConfigUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.user.dao.UserUserinfoDao;
import com.thinkgem.jeesite.modules.user.entity.ShopCommOrder;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.ShopCommOrderService;
import com.thinkgem.jeesite.modules.user.service.UserUCApiService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import com.thinkgem.jeesite.website.web.WebBaseController;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * 玩淘宝商城通信接口
 *
 * @author wyxiang
 * @version 2017-05-04
 */
@Controller
@RequestMapping(value = "/api/userucapi")
public class UserUCApiController extends BaseController {

    @Resource
    private UserUserinfoDao userinfoDao ;

    @Resource
    private UserUserinfoService userUserinfoService;

    @Resource
    private UserUCApiService userUCApiService;

    @RequestMapping(value = "checkShopApi")
    public String checkShopApi(HttpServletRequest request, HttpServletResponse response, Model model) {
        Optional<Object> result = userUCApiService.checkUserapi();
        if (!result.isPresent()) {
            return error("失败", response, model);
        }
        Map<String, Object> resMap = (Map<String, Object>) result.get();
        if ("1".equals(resMap.get("status").toString())) {
            model.addAttribute("data", resMap.get("siteName"));
            return success("成功!!", response, model);
        }
        if ("0".equals(resMap.get("status").toString())){
            model.addAttribute("data", resMap.get("info"));
            return error("失败", response, model);
        }
        return error("失败", response, model);
    }


    /**
     * 检查连接是否成功
     * @return
     */
    @RequestMapping(value = "/checkConnection")
    public String checkConnection(HttpServletRequest request, HttpServletResponse response, Model model){
        if (!userUCApiService.checkIpAndPwd(request)){
            return error("通信未成功",response,model);
        }
        String siteName = Global.getOption("system_sys", "site_name");
        model.addAttribute("siteName",siteName);
        return success("成功",response,model);
    }


    /**
     * 根据钱包地址修改总钱包金额
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateMoneyByAddressOut")
    public String updateMoneyByAddress(HttpServletRequest request, HttpServletResponse response, Model model){
        String address = request.getParameter("address");
        String moneyTemp = request.getParameter("money");
        String message = request.getParameter("message");
        BigDecimal money;
        if (!userUCApiService.checkIpAndPwd(request)){
            return error("通信未成功",response,model);
        }
        if(StringUtils2.isBlank(address) || StringUtils2.isBlank(moneyTemp)){
            return error("失败,参数有误",response,model);
        }
        Date beginTime = DateUtils2.formatStrTime(Global.getOption("system_trans", "trans_time_begin"));
        Date saleTime = DateUtils2.formatStrTime(Global.getOption("system_trans", "auto_sale_out"));
        Date nowtime = DateUtils2.formatStrTime(DateUtils2.getTime());
        if (!(nowtime.getTime() > beginTime.getTime() && nowtime.getTime() < saleTime.getTime())){
            return error( "不在转账时间内",response,model);
        }
        UserUserinfo userinfo = userinfoDao.getByRemarks(address);
        if(userinfo == null){
            return error("失败,未查询到该钱包地址",response,model);
        }
        try {
            money = new BigDecimal(moneyTemp);
        } catch (Exception e) {
            return error("失败,金额有误",response,model);
        }

        try {

            userUserinfoService.updateUserMoney(userinfo.getUserName(),money,"交易钱包转入,"+message, EnumUtil.MoneyChangeType.MONEY_TRANS_IN);
        } catch (ValidationException e) {
            return error("修改失败,原因:"+e.getMessage(),response,model);
        }catch (Exception e){
            return error("失败,原因:"+e.getMessage(),response,model);
        }

        return success("成功",response,model);
    }

}