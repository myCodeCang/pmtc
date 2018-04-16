package com.thinkgem.jeesite.modules.user.service;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils2;
import com.thinkgem.jeesite.common.utils.LogAppendHelper;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.utils.VerifyUtils;
import com.thinkgem.jeesite.config.EnumLccUtil;
import com.thinkgem.jeesite.modules.sys.utils.MsmUtils;
import com.thinkgem.jeesite.modules.user.dao.UserAccountchangeDao;
import com.thinkgem.jeesite.modules.user.dao.UserUserinfoDao;
import com.thinkgem.jeesite.modules.user.entity.*;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * ,用户业务层
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackForClassName = {"RuntimeException", "Exception", "ValidationException"})
public class UserLccService {
    @Resource
    private UserUserinfoService userUserinfoService;

    @Resource
    private UserUserinfoDao userUserinfoDao;

    @Resource
    private UserUCApiService userUCApiService;

    @Resource
    private UserMsmService msmService;

    @Resource
    private UserMsmService userMsmService;


    private Logger logger;

    public synchronized void bindUserAdd(String userName, String address) {
        UserUserinfo user = userUserinfoDao.getByName(userName);
        if (user == null) {
            throw new ValidationException("用户不存在");
        }
        if (!EnumUtil.YesNO.NO.toString().equals(user.getUsercenterAddress())) {
            throw new ValidationException("已绑定过会员钱包地址,请勿重新绑定");
        }
        UserUserinfo userByUsercenterAddress = userUserinfoDao.getUserByUsercenterAddress(address);
        if (userByUsercenterAddress != null) {
            throw new ValidationException("该会员钱包地址已被绑定");
        }
        userUserinfoDao.updateUsercenterAddressByName(userName, address);
    }

    public void moneyToUser(String verifyCode, String userName, BigDecimal money, String message) {
        UserUserinfo user = userUserinfoService.getByNameLock(userName);
        if (user == null) {
            throw new ValidationException("用户不存在");
        }
        if ("2".equals(user.getIsShop())){
            throw new ValidationException("对不起!您的账户已被冻结,禁止该操作");
        }
        Date beginTime = DateUtils2.formatStrTime(Global.getOption("system_trans", "trans_time_begin"));
        Date saleTime = DateUtils2.formatStrTime(Global.getOption("system_trans", "auto_sale_out"));
        Date nowtime = DateUtils2.formatStrTime(DateUtils2.getTime());
        if (!(nowtime.getTime() > beginTime.getTime() && nowtime.getTime() < saleTime.getTime())) {
            throw new ValidationException("不在转账时间内");
        }
        //验证短信
        if (StringUtils2.isBlank(verifyCode.trim())) {
            throw new ValidationException("手机验证码不能为空!");
        } else {
            if (!userMsmService.checkVerifyCode(user.getMobile(), verifyCode.trim())) {
                throw new ValidationException("手机验证码错误!");
            }
        }

        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("转换数量有误");
        }
        BigDecimal userMultiple;
        try {
            userMultiple = new BigDecimal(Global.getOption("system_trans", "user_multiple", "50"));
        } catch (Exception e) {
            throw new ValidationException("保证金配置错误!");
        }
        BigDecimal[] decimals = money.divideAndRemainder(userMultiple);
        if (!BigDecimal.ZERO.equals(decimals[1])) {
            throw new ValidationException("转换数量必须为" + userMultiple + "的倍数");
        }
        if (!VerifyUtils.isInteger(money)) {
            throw new ValidationException("转换数量必须为正整数");
        }
        if (user.getMoney().compareTo(money) < 0) {
            throw new ValidationException("剩余数量不足,无法转换");
        }
        StringBuffer comment = new StringBuffer();
        comment.append("交易钱包地址:").append(user.getRemarks()).append(" 备注:").append(message);
        boolean res = false;
        res = userUCApiService.updateMoneyByAdd(user.getUsercenterAddress(), money, comment.toString());
        if (!res) {
            throw new ValidationException("钱包地址,通信失败,请稍后再试");
        }
        try {
            userUserinfoService.updateUserMoney(userName, money.multiply(BigDecimal.valueOf(-1)), "交易钱包转出,转出钱包地址:" + user.getUsercenterAddress() + " 备注:" + message, EnumUtil.MoneyChangeType.moneyToShop);
        } catch (ValidationException e) {
            logger.error("用户:" + userName + " 交易钱包转出失败,原因:" + e.getMessage());
            throw new ValidationException(e.getMessage());
        } catch (Exception e) {
            logger.error("用户:" + userName + " 交易钱包转出失败,原因:" + e.getMessage());
            throw new ValidationException("交易钱包转出失败");
        }
        //删除短信
        Optional<UserMsm> userMsm = msmService.getByUserName(user.getMobile());
        if (userMsm.isPresent()) {
            msmService.delete(userMsm.get());
        }
    }

    /**
     * 发送短信
     *
     * @param mobile
     * @return
     */

    public String pushTransMessage(String mobile, Date date, String mobileMode) throws ValidationException {
        Optional<UserMsm> userMsmOptional = userMsmService.addOrUpdateMsm(mobile);
        if (!userMsmOptional.isPresent()) {
            return StringUtils2.EMPTY;
        }
        UserMsm userMsm = userMsmOptional.get();
        //String msg = userMsm.getMsg();
        String msg = mobileMode;
        if (!StringUtils2.isEmpty(msg)) {
            msg = msg.replace("{{date}}", DateUtils2.formatDateTime(date));
        }
        boolean sendSuccess = false;

        UserUserinfo userinfo = userUserinfoService.getByMobile(mobile);
        if (userinfo == null) {
            return StringUtils2.EMPTY;
        }

        try {
            //判断是否是美国用户
            if (EnumUtil.YesNO.YES.toString().equals(userinfo.getIsUsercenter())) {
                sendSuccess = userMsmService.sendSMSPost(userMsmService.getInitConfig(), mobile, msg, "");
            } else {
                sendSuccess = MsmUtils.getInstance().lingkaiSendMsg(mobile, msg);
            }
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sendSuccess) {
            return userMsm.getMsg();
        }
        return StringUtils2.EMPTY;
    }


    private org.apache.log4j.Logger getLogger() {
        Logger logger = org.apache.log4j.Logger.getLogger(getClass());
        Optional<RollingFileAppender> rollingFileAppender = LogAppendHelper.getRollingFileAppender(getClass().getName());
        if (rollingFileAppender.isPresent()) {
            logger.addAppender(rollingFileAppender.get());
        }
        return logger;
    }
}
