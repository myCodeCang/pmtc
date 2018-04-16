/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.service;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.HttpClientUtil;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.shop.dao.ShopCategoryDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopCategory;
import com.thinkgem.jeesite.modules.sys.utils.ShopCommunicationConfigUtils;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 玩淘宝商城通信端口
 *
 * @author wyxiang
 * @version 2017-05-04
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class, ValidationException.class})
public class ShopUCApiService extends CrudService<ShopCategoryDao, ShopCategory> {

    public ShopUCApiService() {
    }

    /**
     * 检查通信接口
     *
     * @return
     */
    public Optional<Object> checkShopapi(String shopId) {

        //请求方法
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "authpwd"));

        String authUrl = Global.getOption("auth_shop", "url") + "/app/index.php?i=" + shopId + "&c=entry&m=ewei_shopv2&do=mobile&r=qyapi.client.check";
        String result = HttpClientUtil.sendHttpPost(authUrl, param);

        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);

        return Optional.ofNullable(resMap);
    }

    /**
     * 添加用户
     *
     * @param userinfo
     * @return
     */
    public boolean registerUser(UserUserinfo userinfo) {

        boolean isopen = Global.isOptionOpen("auth_shop", "auth_open");
        if (!isopen) {
            return true;
        }

        Optional<String> shopUrlOptional = ShopCommunicationConfigUtils.getConfigValue(StringUtils2.EMPTY, "shopUrl");
        if (!shopUrlOptional.isPresent()) {
            return false;
        }

        //请求方法
        String postUrlFun = "registerUser";

        String shopIds = Collections3.convertToString(ShopCommunicationConfigUtils.shopIdSet(), ",");
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "authpwd"));
        param.put("mobile", userinfo.getMobile());
        param.put("pwd", userinfo.getUserPass());
        param.put("nickName", userinfo.getTrueName());
        param.put("shopIds", shopIds);

        String result = HttpClientUtil.sendHttpPost(shopUrlOptional.get() + postUrlFun, param);

        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);

        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            return true;
        }
        return false;
    }


    /**
     * 删除用户
     *
     * @param
     * @return
     */
    public boolean deleteUser(String mobile) {
        boolean isopen = Global.isOptionOpen("auth_shop", "auth_open");
        if (!isopen) {
            return true;
        }

        Optional<String> shopUrlOptional = ShopCommunicationConfigUtils.getConfigValue(StringUtils2.EMPTY, "shopUrl");
        if (!shopUrlOptional.isPresent()) {
            return false;
        }

        //请求方法
        String postUrlFun = "deleteUser";

        String shopIds = Collections3.convertToString(ShopCommunicationConfigUtils.shopIdSet(), ",");
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "authpwd"));
        param.put("mobile", mobile);
        param.put("shopIds", shopIds);

        String result = HttpClientUtil.sendHttpPost(shopUrlOptional.get() + postUrlFun, param);

        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);

        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            return true;
        }
        return false;

    }

    /**
     * 修改用户信息
     *
     * @param newMobile
     * @param newPassword
     * @return
     */
    public boolean updateUserInfo(String newMobile, String oldMobile, String newPassword, String trueName, String shopId) {
        boolean isopen = Global.isOptionOpen("auth_shop", "auth_open");
        if (!isopen) {
            return true;
        }

        Optional<String> shopUrlOptional = ShopCommunicationConfigUtils.getConfigValue(StringUtils2.EMPTY, "shopUrl");
        if (!shopUrlOptional.isPresent()) {
            return false;
        }

        //请求方法
        String postUrlFun = "updateUserInfo";

        //如果商城端修改用户信息并传入商城ID，则不需同步发送请求的商城用户信息
        Set<String> shopIdSet = ShopCommunicationConfigUtils.shopIdSet();
        if (!StringUtils2.isBlank(shopId)) {
            if (shopIdSet.contains((String) shopId)) {
                shopIdSet.remove(shopId);
            }
        }

        String shopIds = Collections3.convertToString(shopIdSet, ",");
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "authpwd"));
        param.put("newMobile", newMobile);
        param.put("oldMobile", oldMobile);
        param.put("pwd", newPassword);
        param.put("shopIds", shopIds);
        param.put("trueName", trueName);

        String result = HttpClientUtil.sendHttpPost(shopUrlOptional.get() + postUrlFun, param);

        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);

        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            return true;
        }
        return false;
    }

    /**
     * 修改商城用户余额
     *
     * @param mobile
     * @param money
     * @param remark
     * @param shopKey
     * @return
     */
    private boolean updateShopUserMoney(String mobile, BigDecimal money, String remark, String shopKey, String moneyType) {
        boolean isopen = Global.isOptionOpen("auth_shop", "auth_open");
        if (!isopen) {
            return false;
        }

        if (StringUtils2.isBlank(mobile, shopKey)) {
            return false;
        }

        //根据shopKey获取商城配置
        Optional<Map<String, String>> shopConfigOption = ShopCommunicationConfigUtils.configValues().stream()
                .filter(value -> shopKey.equals(value.get("shopKey"))).distinct().findFirst();
        if (!shopConfigOption.isPresent()) {
            return false;
        }

        Map<String, String> shopConfig = shopConfigOption.get();
        String shopId = shopConfig.get("shopId");
        String shopUrl = shopConfig.get("shopUrl") + "updateUserMoney";

        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "authpwd"));
        param.put("mobile", mobile);
        param.put("type", moneyType);
        param.put("num", money.toString());
        param.put("remark", remark);
        param.put("shopIds", shopId);

        if (money.compareTo(BigDecimal.ZERO) < 0) {
            param.put("num", money.abs().toString());
            param.put("changetype", "1");
        } else {
            param.put("changetype", "0");
        }

        String result = HttpClientUtil.sendHttpPost(shopUrl, param);
        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);

        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            return true;
        }

        return false;
    }

    /**
     * 更新商城用户余额
     *
     * @param mobile
     * @param money
     * @param remark
     * @param shopKey
     * @return
     */
    public boolean updateUserMoneyWithoutCheck(String mobile, BigDecimal money, String remark, String shopKey) {
        return updateShopUserMoney(mobile, money, remark, shopKey, "credit2");
    }

    /**
     * 更新商城用户积分
     *
     * @param mobile
     * @param money
     * @param remark
     * @param shopKey
     * @return
     */
    public boolean updateUserScoreWithoutCheck(String mobile, BigDecimal money, String remark, String shopKey) {
        return updateShopUserMoney(mobile, money, remark, shopKey, "credit1");
    }

    /**
     * 修改用户金钱
     *
     * @param
     * @return
     */
    public boolean updateUserMoney(String mobile, BigDecimal money, String remark, Map<String, Object> params) {
        boolean isopen = Global.isOptionOpen("auth_shop", "auth_open");
        if (!isopen) {
            return false;
        }

        Optional<String> shopUrlOptional = ShopCommunicationConfigUtils.getConfigValue(StringUtils2.EMPTY, "shopUrl");
        if (!shopUrlOptional.isPresent()) {
            return false;
        }

        //请求方法
        String postUrlFun = "updateUserMoney";

        List<String> idList = ShopCommunicationConfigUtils.shopIdSet().stream().filter(key -> {
            Optional<String> syncMoneyStatus = ShopCommunicationConfigUtils.getConfigValue(key, "syncMoney");
            return syncMoneyStatus.isPresent() && "on".equals(syncMoneyStatus.get());
        }).collect(Collectors.toList());

        //如果商城端修改余额并传入商城ID，则不需同步发送请求的商城余额
        if (params != null && params.containsKey("shopId")) {
            Object shopId = params.getOrDefault("shopId", StringUtils2.EMPTY);
            if (shopId instanceof String) {
                if (idList.contains((String) shopId)) {
                    idList.remove(shopId);
                }
            }
        }

        if (Collections3.isEmpty(idList)) {
            return true;
        }

        String shopIds = Collections3.convertToString(idList, ",");
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "authpwd"));
        param.put("mobile", mobile);
        param.put("type", "credit2");
        param.put("num", money.toString());
        param.put("remark", remark);
        param.put("shopIds", shopIds);

        if (money.compareTo(BigDecimal.ZERO) < 0) {
            param.put("num", money.abs().toString());
            param.put("changetype", "1");
        } else {
            param.put("changetype", "0");
        }

        String result = HttpClientUtil.sendHttpPost(shopUrlOptional.get() + postUrlFun, param);
        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);

        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            return true;
        }
        return false;
    }

    public boolean updateUserMoneyToShop(String mobile, BigDecimal money, String remark, Map<String, Object> params) {
        boolean isopen = Global.isOptionOpen("auth_shop", "auth_open");
        if (!isopen) {
            return false;
        }

        Optional<String> shopUrlOptional = ShopCommunicationConfigUtils.getConfigValue(StringUtils2.EMPTY, "shopUrl");
        if (!shopUrlOptional.isPresent()) {
            return false;
        }

        //请求方法
        String postUrlFun = "updateUserMoney";

        List<String> idList = ShopCommunicationConfigUtils.shopIdSet().stream().collect(Collectors.toList());

        //如果商城端修改余额并传入商城ID，则不需同步发送请求的商城余额
        if (params != null && params.containsKey("shopId")) {
            Object shopId = params.getOrDefault("shopId", StringUtils2.EMPTY);
            if (shopId instanceof String) {
                if (idList.contains((String) shopId)) {
                    idList.remove(shopId);
                }
            }
        }

        if (Collections3.isEmpty(idList)) {
            return false;
        }

        String shopIds = Collections3.convertToString(idList, ",");
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "authpwd"));
        param.put("mobile", mobile);
        param.put("type", "credit2");
        param.put("num", money.toString());
        param.put("remark", remark);
        param.put("shopIds", shopIds);

        if (money.compareTo(BigDecimal.ZERO) < 0) {
            param.put("num", money.abs().toString());
            param.put("changetype", "1");
        } else {
            param.put("changetype", "0");
        }

        String result = HttpClientUtil.sendHttpPost(shopUrlOptional.get() + postUrlFun, param);
        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);

        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            return true;
        }
        return false;
    }


    /**
     * 修改用户积分
     *
     * @param
     * @return
     */
    public boolean updateUserScore(String mobile, BigDecimal money, EnumUtil.MoneyType moneyType, String remark) {
        boolean isopen = Global.isOptionOpen("auth_shop", "auth_open");
        if (!isopen) {
            return true;
        }

        if (moneyType == null) {
            return false;
        }

        Optional<String> shopIdOptional = ShopCommunicationConfigUtils.shopIdSet().stream().filter(key -> {
            Optional<String> syncMoneyStatus = ShopCommunicationConfigUtils.getConfigValue(key, "syncScore");
            Optional<String> syncField = ShopCommunicationConfigUtils.getConfigValue(key, "syncScoreField");
            return syncMoneyStatus.isPresent() && syncField.isPresent() && "on".equals(syncMoneyStatus.get()) && Integer.parseInt(syncField.get()) == moneyType.getCode();
        }).findFirst();

        if (!shopIdOptional.isPresent()) {
            return true;
        }

        Optional<String> shopUrl = ShopCommunicationConfigUtils.getConfigValue(shopIdOptional.get(), "shopUrl");
        if (!shopUrl.isPresent()) {
            return true;
        }
        //请求方法
        String postUrlFun = "updateUserMoney";

        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "authpwd"));
        param.put("mobile", mobile);
        param.put("type", "credit1");
        param.put("num", money.toString());
        param.put("remark", remark);

        if (money.compareTo(BigDecimal.ZERO) < 0) {
            param.put("num", money.abs().toString());
            param.put("changetype", "1");
        } else {
            param.put("changetype", "0");
        }

        String result = HttpClientUtil.sendHttpPost(shopUrl.get() + postUrlFun, param);

        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);

        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            return true;
        }
        return false;
    }

    public boolean updateUserScoreToShop(String mobile, BigDecimal money, EnumUtil.MoneyType moneyType, String remark) {
        boolean isopen = Global.isOptionOpen("auth_shop", "auth_open");
        if (!isopen) {
            return false;
        }

        if (moneyType == null) {
            return false;
        }

        Optional<String> shopIdOptional = ShopCommunicationConfigUtils.shopIdSet().stream().findFirst();

        if (!shopIdOptional.isPresent()) {
            return false;
        }

        Optional<String> shopUrl = ShopCommunicationConfigUtils.getConfigValue(shopIdOptional.get(), "shopUrl");
        if (!shopUrl.isPresent()) {
            return false;
        }
        //请求方法
        String postUrlFun = "updateUserMoney";

        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "authpwd"));
        param.put("mobile", mobile);
        param.put("type", "credit1");
        param.put("num", money.toString());
        param.put("remark", remark);

        if (money.compareTo(BigDecimal.ZERO) < 0) {
            param.put("num", money.abs().toString());
            param.put("changetype", "1");
        } else {
            param.put("changetype", "0");
        }

        String result = HttpClientUtil.sendHttpPost(shopUrl.get() + postUrlFun, param);

        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);

        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            return true;
        }
        return false;
    }

    /**
     * 同步用户金钱
     *
     * @param
     * @return
     */
    public boolean moneyToShop(String mobile, BigDecimal money) {
        boolean isopen = Global.isOptionOpen("auth_shop", "auth_open");
        if (!isopen) {
            return false;
        }

        Optional<String> shopUrlOptional = ShopCommunicationConfigUtils.getConfigValue(StringUtils2.EMPTY, "shopUrl");
        if (!shopUrlOptional.isPresent()) {
            return false;
        }

        //请求方法
        String postUrlFun = "moneyToShop";

        List<String> idList = ShopCommunicationConfigUtils.shopIdSet().stream().filter(key -> {
            Optional<String> syncMoneyStatus = ShopCommunicationConfigUtils.getConfigValue(key, "syncMoney");
            return syncMoneyStatus.isPresent() && "on".equals(syncMoneyStatus.get());
        }).collect(Collectors.toList());

        if (Collections3.isEmpty(idList)) {
            return true;
        }

        String shopIds = Collections3.convertToString(idList, ",");
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "authpwd"));
        param.put("mobile", mobile);
        param.put("type", "credit2");
        param.put("num", money.toString());
        param.put("shopIds", shopIds);

        if (money.compareTo(BigDecimal.ZERO) < 0) {
            param.put("num", money.abs().toString());
            param.put("changetype", "1");
        } else {
            param.put("changetype", "0");
        }

        String result = HttpClientUtil.sendHttpPost(shopUrlOptional.get() + postUrlFun, param);
        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);

        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            return true;
        }
        return false;
    }

    /**
     * 同步用户积分
     *
     * @param
     * @return
     */
    public boolean socreToShop(String mobile, BigDecimal money, EnumUtil.MoneyType moneyType) {
        boolean isopen = Global.isOptionOpen("auth_shop", "auth_open");
        if (!isopen) {
            return true;
        }

        if (moneyType == null) {
            return false;
        }

        Optional<String> shopIdOptional = ShopCommunicationConfigUtils.shopIdSet().stream().filter(key -> {
            Optional<String> syncMoneyStatus = ShopCommunicationConfigUtils.getConfigValue(key, "syncScore");
            Optional<String> syncField = ShopCommunicationConfigUtils.getConfigValue(key, "syncScoreField");
            return syncMoneyStatus.isPresent() && syncField.isPresent() && "on".equals(syncMoneyStatus.get()) && Integer.parseInt(syncField.get()) == moneyType.getCode();
        }).findFirst();

        if (!shopIdOptional.isPresent()) {
            return true;
        }

        Optional<String> shopUrl = ShopCommunicationConfigUtils.getConfigValue(shopIdOptional.get(), "shopUrl");
        if (!shopUrl.isPresent()) {
            return true;
        }
        //请求方法
        String postUrlFun = "moneyToShop";

        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "authpwd"));
        param.put("mobile", mobile);
        param.put("type", "credit1");
        param.put("num", money.toString());

        if (money.compareTo(BigDecimal.ZERO) < 0) {
            param.put("num", money.abs().toString());
            param.put("changetype", "1");
        } else {
            param.put("changetype", "0");
        }

        String result = HttpClientUtil.sendHttpPost(shopUrl.get() + postUrlFun, param);

        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);

        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            return true;
        }
        return false;
    }
}