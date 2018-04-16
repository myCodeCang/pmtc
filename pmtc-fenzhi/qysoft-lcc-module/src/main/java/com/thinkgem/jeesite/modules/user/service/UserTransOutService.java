package com.thinkgem.jeesite.modules.user.service;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.HttpClientUtil;
import com.thinkgem.jeesite.common.utils.IPUtil;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * ,用户业务层
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackForClassName = {"RuntimeException", "Exception", "ValidationException"})
public class UserTransOutService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 检查通信接口
     *
     * @return
     */
    public Optional<Object> checkUserapi() {
        //请求方法
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "connectPwd"));

        String authUrl = Global.getOption("auth_shop", "connectUrl")+"api/userucapi/checkConnection";
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


    //会员外部转账
    public boolean updateMoneyByAddress(String address, BigDecimal money, String message) {
        boolean isopen = Global.isOptionOpen("auth_shop", "user_connect");
        if (!isopen) {
            return false;
        }
        String authUrl = Global.getOption("auth_shop", "connectUrl");
        if (StringUtils2.isBlank(authUrl)) {
            return false;
        }
        //请求方法
        String postUrlFun = "/api/userucapi/updateMoneyByAddressOut";
        Map<String, String> param = Maps.newHashMap();
        param.put("authpwd", Global.getOption("auth_shop", "connectPwd"));
        param.put("address", address);
        param.put("message", message);
        param.put("money", money.toString());
        String result = HttpClientUtil.sendHttpPost(authUrl + postUrlFun, param);
        HashMap<String, Object> resMap = (HashMap<String, Object>) JsonMapper.fromJsonString(result, HashMap.class);
        if (resMap != null && "1".equals(resMap.get("status").toString())) {
            return true;
        } else if(resMap != null && "0".equals(resMap.get("status").toString())){
            throw new ValidationException(resMap.get("info").toString());
        }
        return false;
    }


}
