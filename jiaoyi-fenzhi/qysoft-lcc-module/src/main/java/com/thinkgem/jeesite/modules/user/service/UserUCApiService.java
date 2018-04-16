/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.HttpClientUtil;
import com.thinkgem.jeesite.common.utils.IPUtil;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.shop.dao.ShopCategoryDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopCategory;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 会员端通信端口
 *
 * @author cph
 * @version 2018.3.21
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class, ValidationException.class})
public class UserUCApiService extends CrudService<ShopCategoryDao, ShopCategory> {


    public UserUCApiService() {

    }

    /**
     * 检查通信接口
     *
     * @return
     */
    public Optional<Object> checkUserapi() {
        //请求方法
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "connectPwd"));

        String authUrl = Global.getOption("auth_shop", "connectUrl")+"api/trans/checkConnection";
        String result = HttpClientUtil.sendHttpPost(authUrl, param);
        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);
        return Optional.ofNullable(resMap);
    }

    /**
     * 验证数据
     *
     * @return
     */
    public boolean checkIpAndPwd(HttpServletRequest request) {
        //请求方法
        String reqPwd = request.getParameter("authpwd");
        String pwd = Global.getOption("auth_shop", "connectPwd");
        if (!pwd.equals(reqPwd)){
            return false;
        }
        try {
            String ipAddress = IPUtil.getIpAddress(request);
            boolean res = IPUtil.checkLoginIP(ipAddress, Global.getOption("auth_shop", "whiteList"));
            return res;
        } catch (IOException e) {
            logger.error("ip获取失败");
        } catch (Exception e) {
            logger.error("验证失败");
        }
        return false;
    }

    /**
     * 绑定会员钱包地址
     *
     * @param
     * @return
     */
    public boolean checkUserBurseAdd(String address, String validCode) {


            boolean isopen = Global.isOptionOpen("auth_shop", "user_connect");
            if (!isopen) {
                return false;
            }
            String authUrl = Global.getOption("auth_shop", "connectUrl");
            if (StringUtils2.isBlank(authUrl)) {
                return false;
            }
            //请求方法
            String postUrlFun = "api/trans/verifyCode";
            Map<String, String> param = Maps.newHashMap();
            param.put("authpwd", Global.getOption("auth_shop", "connectPwd"));
            param.put("address", address);
            param.put("validCode", validCode);
            String result = null;
            try {
                result = HttpClientUtil.sendHttpPost(authUrl + postUrlFun, param);
            } catch (Exception e) {
                logger.error("通信失败",e);
                return  false;
            }
            HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);
            if (resMap != null && "1".equals(resMap.get("status").toString())) {
                return true;
            }else if(resMap != null && "0".equals(resMap.get("status").toString())){
                throw new ValidationException(resMap.get("info").toString());
            }
            return false;

    }

    /**
     * 发送短信
     *
     * @param
     * @return
     */
    public boolean sendCodeByAdd(String address) {

        boolean isopen = Global.isOptionOpen("auth_shop", "user_connect");
        if (!isopen) {
            return false;
        }
        String authUrl = Global.getOption("auth_shop", "connectUrl");
        if (StringUtils2.isBlank(authUrl)) {
            return false;
        }
        //请求方法
        String postUrlFun = "api/trans/sendVerifyCode";
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "connectPwd"));
        param.put("address", address);
        String result = null;
        try {
            result = HttpClientUtil.sendHttpPost(authUrl + postUrlFun, param);
        } catch (Exception e) {
            logger.error("通信失败",e);
            return  false;
        }
        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);
            if (resMap != null && "1".equals(resMap.get("status").toString())) {
                return true;
            } else if(resMap != null && "0".equals(resMap.get("status").toString())){
                throw new ValidationException(resMap.get("info").toString());
            }
            return false;
    }

    public boolean updateMoneyByAdd(String address, BigDecimal money, String message) {

        boolean isopen = Global.isOptionOpen("auth_shop", "user_connect");
        if (!isopen) {
            return false;
        }
        String authUrl = Global.getOption("auth_shop", "connectUrl");
        if (StringUtils2.isBlank(authUrl)) {
            return false;
        }
        //请求方法
        String postUrlFun = "api/trans/updateMoneyByAddress";
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "connectPwd"));
        param.put("address", address);
        param.put("message", message);
        param.put("money", money.toString());
        String result = null;
        try {
            result = HttpClientUtil.sendHttpPost(authUrl + postUrlFun, param);
        } catch (Exception e) {
            logger.error("通信失败");
            return false;
        }
        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);
        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            return true;
        }else if(resMap != null && "0".equals(resMap.get("status").toString())){
            throw new ValidationException(resMap.get("info").toString());
        }
        return false;
    }

    /**
     * 调用会员端接口修改最新价格
     * @@return
     */
    public boolean updateNowPrice(BigDecimal nowPrice) {
        boolean isopen = Global.isOptionOpen("auth_shop", "user_connect");
        if (!isopen) {
            return false;
        }
        String authUrl = Global.getOption("auth_shop", "connectUrl");
        if (StringUtils2.isBlank(authUrl)) {
            return false;
        }
        //请求方法
        String postUrlFun = "api/trans/updateNowPrice";
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "connectPwd"));
        param.put("nowPrice", nowPrice.toString());
        String result = null;
        try {
            result = HttpClientUtil.sendHttpPost(authUrl + postUrlFun, param);
        } catch (Exception e) {
            logger.error("通信失败",e);
            return  false;
        }
        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);
        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            return true;
        }else if(resMap != null && "0".equals(resMap.get("status").toString())){
            throw new ValidationException(resMap.get("info").toString());
        }
        return false;
    }

    public UserUserinfo getUserInfoByAdd(String address) {

        boolean isopen = Global.isOptionOpen("auth_shop", "user_connect");
        if (!isopen) {
            return null;
        }
        String authUrl = Global.getOption("auth_shop", "connectUrl");
        if (StringUtils2.isBlank(authUrl)) {
            return null;
        }
        //请求方法
        String postUrlFun = "api/trans/getMessageByAddress";
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "connectPwd"));
        param.put("address", address);
        String result = null;
        try {
            result = HttpClientUtil.sendHttpPost(authUrl + postUrlFun, param);
        } catch (Exception e) {
            logger.error("通信失败",e);
            return  null;
        }
        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);
        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            try {
                UserUserinfo userinfo = new UserUserinfo();
                userinfo.setUserName(resMap.get("userName").toString());
                userinfo.setMoney2(new BigDecimal(resMap.get("money2").toString()));
                return userinfo;
            } catch (Exception e) {
                return null;
            }
        }else if(resMap != null && "0".equals(resMap.get("status").toString())){
            throw new ValidationException(resMap.get("info").toString());
        }
        return null;
    }


}