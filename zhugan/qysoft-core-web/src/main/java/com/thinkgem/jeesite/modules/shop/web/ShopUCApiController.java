/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.web;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.shop.service.ShopCategoryService;
import com.thinkgem.jeesite.modules.shop.service.ShopUCApiService;
import com.thinkgem.jeesite.modules.sys.utils.ShopCommunicationConfigUtils;
import com.thinkgem.jeesite.modules.user.dao.UserUserinfoDao;
import com.thinkgem.jeesite.modules.user.entity.ShopCommOrder;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.ShopCommOrderService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

/**
 * 玩淘宝商城通信接口
 *
 * @author wyxiang
 * @version 2017-05-04
 */
@Controller
@RequestMapping(value = "/api/shopucapi")
public class ShopUCApiController extends BaseController {

    @Resource
    private ShopCategoryService shopCategoryService;

    @Resource
    private ShopCommOrderService shopCommOrderService;

    @Resource
    private UserUserinfoService userUserinfoService;

    @Resource
    private UserUserinfoDao userUserinfoDao;

    @Resource
    private ShopUCApiService shopUCApiService;

    @RequestMapping(value = "checkShopApi")
    public String checkShopApi(HttpServletRequest request, HttpServletResponse response, Model model) {
        String shopId = request.getParameter("shopId");
        if (StringUtils2.isBlank(shopId)) {
            return error("失败", response, model);
        }

        Optional<Object> result = shopUCApiService.checkShopapi(shopId);
        if (!result.isPresent()) {
            return error("失败", response, model);
        }

        Map<String, Object> resMap = (Map<String, Object>) result.get();
        model.addAttribute("data", resMap.get("result"));
        if ("1".equals(resMap.get("status").toString())) {
            return success("成功!!", response, model);
        }
        return error("失败", response, model);
    }

    /**
     * 检查接口配置
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "check")
    public String check(HttpServletRequest request, HttpServletResponse response, Model model) {
        return success("成功!!", response, model);
    }

    /**
     * 商城端添加用户
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "registerUser")
    public String registerUser(HttpServletRequest request, HttpServletResponse response, Model model) {
        return error("失败", response, model);
        //return success("成功!!", response, model);
    }


    /**
     * 商城删除加用户
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deleteUser")
    public String deleteUser(HttpServletRequest request, HttpServletResponse response, Model model) {
        return error("失败", response, model);
    }


    /**
     * 商城修改用户信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "updateUserInfo")
    public String updateUserInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        //仅同步密码
        String mobile = request.getParameter("mobile");
        if (StringUtils2.isEmpty(mobile)) {
            return error("修改用户信息失败", response, model);
        }

        String password = request.getParameter("pwd");
        if (StringUtils2.isEmpty(password)) {
            return error("修改用户信息失败", response, model);
        }

        String shopId = request.getParameter("shopId");
        boolean isSync = false;
        if (!StringUtils2.isBlank(shopId)) {
            isSync = true;
        }

        UserUserinfo userInfo = userUserinfoService.getByMobile(mobile);
        if (null == userInfo) {
            return error("修改用户信息失败", response, model);
        }

        try {
            userUserinfoService.updateUserPwd(userInfo.getUserName(), password, isSync, shopId);
        } catch (Exception e) {
            return error("修改用户信息失败", response, model);
        }
        return success("成功!!", response, model);
    }


    /**
     * 商城修改用户金钱
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "updateUserMoney")
    public String updateUserMoney(HttpServletRequest request, HttpServletResponse response, Model model) {
        String mobile = request.getParameter("mobile");
        if (StringUtils2.isEmpty(mobile)) {
            return error("修改用户余额失败", response, model);
        }

        String money = request.getParameter("money");
        if (StringUtils2.isEmpty(money)) {
            return error("修改用户余额失败", response, model);
        }

        EnumUtil.MoneyChangeType changeType = EnumUtil.MoneyChangeType.wxshop;
        String paramMoneyType = request.getParameter("moneyType");
        if (!StringUtils2.isBlank(paramMoneyType)) {
            try {
                changeType = EnumUtil.MoneyChangeType.getChangeTypeByCode(Integer.parseInt(paramMoneyType));
            } catch (NumberFormatException e) {
                logger.error("错误的帐变类型");
            }
        }

        UserUserinfo userInfo = userUserinfoService.getByMobile(mobile);
        if (userInfo == null) {
            return error("修改用户余额失败", response, model);
        }

        String message = request.getParameter("message");
        String shopId = request.getParameter("shopId");
        Optional<String> syncScore = ShopCommunicationConfigUtils.getConfigValue(shopId, "syncMoney");
        if (!syncScore.isPresent() || !"on".equals(syncScore.get())) {
            return success("成功!!", response, model);
        }


        Map<String, Object> params = null;
        if (!StringUtils2.isBlank(shopId)) {

            //changeType = EnumUtil.MoneyChangeType.charget;
            params = Maps.newHashMap();
            params.put("shopId", shopId);
        }

        try {
            userUserinfoService.updateUserMoney(userInfo.getUserName(), new BigDecimal(money), "商城"+message, changeType, params);
        } catch (ValidationException e) {
            return error("修改用户余额失败", response, model);
        }
        return success("成功!!", response, model);
    }

    /**
     * 商城修改用户积分
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "updateUserScore")
    public String updateUserScore(HttpServletRequest request, HttpServletResponse response, Model model) {
        String mobile = request.getParameter("mobile");
        if (StringUtils2.isEmpty(mobile)) {
            return error("修改用户积分失败", response, model);
        }

        String money = request.getParameter("money");
        if (StringUtils2.isEmpty(money)) {
            return error("修改用户积分失败", response, model);
        }

        UserUserinfo userInfo = userUserinfoService.getByMobile(mobile);
        if (userInfo == null) {
            return error("修改用户积分失败", response, model);
        }

        EnumUtil.MoneyChangeType changeType = EnumUtil.MoneyChangeType.wxshop;
        String paramMoneyType = request.getParameter("moneyType");
        if (!StringUtils2.isBlank(paramMoneyType)) {
            try {
                changeType = EnumUtil.MoneyChangeType.getChangeTypeByCode(Integer.parseInt(paramMoneyType));
            } catch (NumberFormatException e) {
                logger.error("错误的帐变类型");
            }
        }

        String message = request.getParameter("message");
        String shopId = request.getParameter("shopId");
        if (StringUtils2.isBlank(shopId)) {
            return error("修改用户积分失败，无法获取商城信息", response, model);
        }

        try {
            Optional<String> syncScore = ShopCommunicationConfigUtils.getConfigValue(shopId, "syncScore");
            if (syncScore.isPresent() && "on".equals(syncScore.get())) {
                Optional<String> syncScoreField = ShopCommunicationConfigUtils.getConfigValue(shopId, "syncScoreField");
                if (syncScoreField.isPresent()) {
                    String syncFile = syncScoreField.get();
                    if (EnumUtil.MoneyType.score.toString().equals(syncFile)) {
                        userUserinfoService.updateUserScore(userInfo.getUserName(), new BigDecimal(money), "商城:"+message, changeType);
                    } else {
                        Optional<EnumUtil.MoneyType> instanceByCode = EnumUtil.MoneyType.getInstanceByCode(syncFile);
                        if (instanceByCode.isPresent()) {
                            userUserinfoService.updateUserOtherMoney(userInfo.getUserName(), new BigDecimal(money), instanceByCode.get(), "商城:"+message, changeType);
                        }
                    }
                }
            }

        } catch (ValidationException e) {
            return error("修改用户积分失败", response, model);
        }
        return success("成功!!", response, model);
    }


    /**
     * 商城修改用户成功下单
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "buyOrder")
    public String buyOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
//        String mobile =  request.getParameter("mobile");
//        UserUserinfo userinfo =  userUserinfoService.getByMobile(mobile);
//        userinfo.setUserLevelId("2");
//        userUserinfoDao.update(userinfo);
        return success("成功!!", response, model);
    }


    /**
     * 商城修改用户确认收货
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "receiveOrder")
    public String receiveOrder(HttpServletRequest request, HttpServletResponse response, Model model) {

        try {

            String mobile =  request.getParameter("mobile");
            String orderid =  request.getParameter("orderid");
            String orderSn =  request.getParameter("orderSn");
            String orderMoney =  request.getParameter("orderMoney");
            String shopId = request.getParameter("shopId");
            UserUserinfo userinfo =  userUserinfoService.getByMobile(mobile);

            //TODO: 插入统计表
            ShopCommOrder shopCommOrder = new ShopCommOrder();
            shopCommOrder.setUserName(userinfo.getUserName());
            shopCommOrder.setChangeMoney(orderMoney);
            shopCommOrder.setCommt("商城购物");
            shopCommOrder.setStatus("1");
            shopCommOrder.setIscheck("0");
            shopCommOrder.setShopId(shopId);
            shopCommOrder.setOrderId(orderSn);
            //shopCommOrder.setScore();
            shopCommOrder.setMoneyType("1");
            shopCommOrderService.save(shopCommOrder);


            return success("成功!!", response, model);

        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        } catch (Exception e) {
            return error("失败!", response, model);
        }

    }
}