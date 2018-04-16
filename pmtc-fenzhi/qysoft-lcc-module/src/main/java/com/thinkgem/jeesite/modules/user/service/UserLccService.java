package com.thinkgem.jeesite.modules.user.service;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils2;
import com.thinkgem.jeesite.common.utils.HttpClientUtil;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.utils.VerifyUtils;
import com.thinkgem.jeesite.config.EnumLccUtil;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.user.dao.UserAccountchangeDao;
import com.thinkgem.jeesite.modules.user.dao.UserUserinfoDao;
import com.thinkgem.jeesite.modules.user.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private UserLevelService userLevelService;
    @Resource
    private UserLevelLogService userLevelLogService;

    @Resource
    private UserTeamLevelService userTeamLevelService;
    @Resource
    private UserUserinfoDao userUserinfoDao;

    @Resource
    private UserTransOutService transOutService ;

    @Resource
    private UserAccountchangeDao userAccountchangeDao;

    private Logger logger = LoggerFactory.getLogger(getClass());


    public void activeUser(String actName, BigDecimal actMoney) {
        UserUserinfo actUser = userUserinfoService.getByNameLock(actName);
//        if (EnumUtil.YesNO.YES.toString().equals(actUser.getActiveStatus())) {
//            return;
//        }
        if (actUser == null) {
            throw new ValidationException("用户不存在");
        }
        if (actMoney.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("数额输入有误");
        }

        //首次推荐返利
        firstDirectBonus(actUser, actMoney);

        if (EnumUtil.YesNO.NO.toString().equals(actUser.getActiveStatus())) {
            activeUser(actUser);
        }


    }

    /**
     * 用户激活
     *
     * @param actUser
     */
    public void activeUser(UserUserinfo actUser) {
        //激活用户
        actUser.setActiveStatus(EnumUtil.YesNO.YES.toString());
        userUserinfoService.save(actUser);
    }

    public void firstDirectBonus(UserUserinfo actUser, BigDecimal actMoney) {
        //首推奖发放开关
        if (!"on".equals(Global.getOption("system_help", "putDirectAward", "on"))) {
            return;
        }
        //首推奖励单次发放开关
        if ("on".equals(Global.getOption("system_help", "firstDirectAward", "on"))) {
            if (EnumUtil.YesNO.YES.toString().equals(actUser.getActiveStatus())) {
                return;
            }
        }




        UserUserinfo parentUser = userUserinfoService.getByNameLock(actUser.getWalterName());
        UserTeamLevel select = new UserTeamLevel();
        select.setOrderBy("a.condition_one DESC");
        List<UserTeamLevel> teamLevelList = userTeamLevelService.findList(select);
        if (parentUser == null) {
            throw new ValidationException("上级用户不存在无法激活!");
        }

        //判断用户是否冻结
        if(EnumUtil.UserStatusEnum.status_freeze.toString().equals(parentUser.getShopId())){
           //logger.error("奖励发送失败,失败用户:"+parentUser.getUserName()+"失败原因:该用户已冻结，暂停发奖");
            return;
        }

        if (!EnumUtil.YesNO.YES.toString().equals(parentUser.getActiveStatus())) {
            return;
        }
        if (teamLevelList.size() < 0) {
            throw new ValidationException("奖励配置错误!");
        }
        Optional<UserTeamLevel> levelOptional = teamLevelList.stream().filter(p -> parentUser.getMoney2().compareTo(p.getConditionOne()) >= 0).findFirst();
        if (levelOptional.isPresent()) {
            UserTeamLevel userTeamLevel = levelOptional.get();
            BigDecimal bonus = actMoney.multiply(new BigDecimal(userTeamLevel.getDirectEarnings())).setScale(2, BigDecimal.ROUND_HALF_UP);
            this.updateUserOtherMoney(parentUser.getUserName(), bonus, EnumUtil.MoneyType.money3, "首次直推返利,直推用户: " + actUser.getUserName(), EnumUtil.MoneyChangeType.spread);
        }
    }

    /**
     * 转入矿机钱包
     *
     * @param userName
     * @param moneyTemp
     * @param moneyType
     * @param message
     */
    public void transferToMoney2(String userName, String moneyTemp, Enum moneyType, String message) {
        if (this.isClosedBeta()) {
            throw new ValidationException("该功能未开放");
        }
        UserUserinfo user = userUserinfoService.getByNameLock(userName);
        BigDecimal money;
        if (user == null) {
            throw new ValidationException("用户不存在");
        }
        try {
            money = new BigDecimal(moneyTemp);
        } catch (Exception e) {
            throw new ValidationException("输入数额有误");
        }
        if (BigDecimal.ZERO.compareTo(money) >= 0) {
            throw new ValidationException("转换数额必须大于零");
        }
        if (!VerifyUtils.isInteger(money)) {
            throw new ValidationException("转换数额必须为整数");
        }
        if (user.getMoney().compareTo(money) < 0) {
            throw new ValidationException("余额不足");
        }

        if (moneyType.toString().equals(EnumUtil.MoneyType.money2.toString())) {
            userUserinfoService.updateUserMoney(user.getUserName(), money.multiply(new BigDecimal(-1)), "交易钱包转出到矿机钱包,备注:" + message, EnumUtil.MoneyChangeType.TRANSFER_MONEY2);
            this.updateUserOtherMoney(user.getUserName(), money, EnumUtil.MoneyType.money2, "交易钱包转入矿机钱包,备注:" + message, EnumUtil.MoneyChangeType.TRANSFER_MONEY2);
            this.activeUser(userName, money);
        }

    }

    /**
     * 转入总钱包
     *
     * @param userName
     * @param money
     * @param moneyType
     * @param changeType
     * @param hasCost
     * @param message
     */
    public synchronized void transferToMoney(String userName, BigDecimal money, EnumUtil.MoneyType moneyType, EnumUtil.MoneyChangeType changeType, boolean hasCost, String message) {
        if (this.isClosedBeta()) {
            throw new ValidationException("该功能未开放");
        }
        UserUserinfo user = userUserinfoService.getByNameLock(userName);
        if (user == null) {
            throw new ValidationException("用户不存在");
        }
        BigDecimal poundage;
        BigDecimal charge;
        try {
            poundage = new BigDecimal(Global.getOption("system_help", "poundage_wallet", "0.1"));
            charge = money.multiply(poundage);
        } catch (Exception e) {
            throw new ValidationException("手续费配置有误,或者输入数额有误");
        }
        if (EnumUtil.MoneyType.money2.toString().equalsIgnoreCase(moneyType.toString())) {
            this.permitTransFer(user.getUserName(), money);
            if (user.getMoney2().compareTo(money) < 0) {
                throw new ValidationException("余额不足");
            }

        }
        if (EnumUtil.MoneyType.money3.toString().equalsIgnoreCase(moneyType.toString())) {
            this.permitTransFer(user.getUserName(), money);
            if (user.getMoney3().compareTo(money) < 0) {
                throw new ValidationException("余额不足");
            }
        }
        if (BigDecimal.ZERO.compareTo(money) >= 0) {
            throw new ValidationException("转换数额必须大于零");
        }
        if (!VerifyUtils.isInteger(money)) {
            throw new ValidationException("转换数额必须为整数");
        }


        userUserinfoService.updateUserOtherMoney(user.getUserName(), money.multiply(new BigDecimal(-1)), moneyType, "转出交易钱包,备注:" + message, changeType);
        userUserinfoService.updateUserMoney(user.getUserName(), money.subtract(charge), "交易钱包转入,手续费:" + charge + ",备注:" + message, changeType);
//        userUserinfoService.updateUserMoney(user.getUserName(), charge.multiply(BigDecimal.valueOf(-1)), "收取转换手续费", changeType);

    }

    /**
     * 判断矿机钱包是否是第一次转入总钱包还有额度是否超过限制
     */
    public boolean permitTransFer(String userName, BigDecimal money) {
        if (this.isClosedBeta()) {
            throw new ValidationException("该功能未开放");
        }
        BigDecimal attornScale;
        try {
            attornScale = new BigDecimal(Global.getOption("system_help", "money2_scale"));
        } catch (Exception e) {
            throw new ValidationException("矿机钱包转入比例配置有误");
        }
        BigDecimal money2Sum = userAccountchangeDao.sumMoneyByTypeAndDate(userName,EnumUtil.MoneyChangeType.TRANSFER_MONEY2_IN.toString(), EnumUtil.MoneyType.money2.toString(),new Date());
        BigDecimal money3Sum = userAccountchangeDao.sumMoneyByTypeAndDate(userName,EnumUtil.MoneyChangeType.TRANSFER_MONEY3.toString(), EnumUtil.MoneyType.money3.toString(),new Date());
        if(money2Sum == null){
            money2Sum = BigDecimal.ZERO;
        }
        if(money3Sum == null){
            money3Sum = BigDecimal.ZERO;
        }

        BigDecimal sumMoneyZone = money2Sum.add(money3Sum).multiply(new BigDecimal(-1));

        BigDecimal thisTimeMoney = sumMoneyZone.add(money);

        UserUserinfo userUserinfo = userUserinfoService.getByNameLock(userName);
        BigDecimal canAttronMoney = userUserinfo.getMoney2().multiply(attornScale).setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal canAttMoney = canAttronMoney.subtract(sumMoneyZone).compareTo(BigDecimal.ZERO) > 0 ? canAttronMoney.subtract(sumMoneyZone) : BigDecimal.ZERO;

        if (thisTimeMoney.compareTo(canAttronMoney) > 0) {
            throw new ValidationException(",今日交易钱包转换超额，每日转换限额为矿机钱包的"+attornScale+"倍,今日剩余额度为:" + canAttMoney);
        }
        return true;
    }


    /**
     * 会员转账
     *
     * @param userName
     * @param money
     * @param address
     */
    public void transferOfAccount(String userName, BigDecimal money, String address) {
        if (this.isClosedBeta()) {
            throw new ValidationException("该功能未开放");
        }
        UserUserinfo user = userUserinfoService.getByNameLock(userName);

        if (user == null) {
            throw new ValidationException("用户不存在");
        }
        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("转账金额必须大于零");
        }
        if (!VerifyUtils.isInteger(money)) {
            throw new ValidationException("转账金额必须为整数");
        }
        if (money.compareTo(user.getMoney()) > 0) {
            throw new ValidationException("交易钱包数额不足");
        }

        BigDecimal canTransMoney = userTransferCheck(userName);
        String userMultiple = Global.getOption("system_help", "account_multiple", "3");
        if (canTransMoney.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("转账超出限额,每日转账限额为矿机钱包的"+userMultiple+"倍，今日剩余额度为:" + canTransMoney);
        }

        if (money.compareTo(canTransMoney) > 0) {
            throw new ValidationException("转账超出限额,每日转账限额为矿机钱包的"+userMultiple+"倍，今日剩余额度为:" + canTransMoney);
        }

        //UserUserinfo theOtherUser = userUserinfoDao.getByRemarks(address);
        UserUserinfo theOtherUser = userUserinfoDao.getByNameLock(address);

        if(address.equals(user.getUserName())){
            throw new ValidationException("会员不能自己给自己转账");
        }

        if (theOtherUser == null) {
            throw new ValidationException("对方账户编号有误,请重新输入");
        }
        userUserinfoService.updateUserMoney(user.getUserName(), money.multiply(new BigDecimal(-1)), user.getUserName() + "转账给" + theOtherUser.getUserName(), EnumUtil.MoneyChangeType.TRANSFER_ACCOUNTS);
        userUserinfoService.updateUserMoney(theOtherUser.getUserName(), money, theOtherUser.getUserName() + "收到" + user.getUserName() + "转账", EnumUtil.MoneyChangeType.TRANSFER_ACCOUNTS);

    }

    //检查当前会员转账剩余额度
    public BigDecimal userTransferCheck(String userName) {
        BigDecimal userMultiple;
        UserUserinfo userinfo = userUserinfoService.getByNameLock(userName);
        if (userinfo == null) {
            throw new ValidationException("用户不存在");
        }
        try {
            userMultiple = new BigDecimal(Global.getOption("system_help", "account_multiple", "3"));
        } catch (Exception e) {
            throw new ValidationException("会员转账限制倍数配置有误");
        }
        UserAccountchange accountchange = new UserAccountchange();
        accountchange.setChangeMoneyEnd(BigDecimal.ZERO);
        accountchange.setMoneyType(EnumUtil.MoneyType.money.toString());
        accountchange.setCreateDate(new Date());
        accountchange.setUserName(userName);
        accountchange.setChangeType(EnumUtil.MoneyChangeType.TRANSFER_ACCOUNTS.toString());
        List<UserAccountchange> accountchanges = userAccountchangeDao.findList(accountchange);
        BigDecimal nowAccountMoney = accountchanges.stream().map(p -> new BigDecimal(p.getChangeMoney()).multiply(new BigDecimal(-1))).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal maxMoney = userinfo.getMoney2().multiply(userMultiple);

        if(maxMoney.subtract(nowAccountMoney).compareTo(BigDecimal.ZERO)<=0){
            return BigDecimal.ZERO;
        }

        return maxMoney.subtract(nowAccountMoney);

    }

    public synchronized void updateUserOtherMoney(String userName, BigDecimal money, EnumUtil.MoneyType type, String message, EnumUtil.MoneyChangeType changeType) {
        if (type == EnumUtil.MoneyType.money2 && money.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal money2Check = this.userMoney2Check(userName);
            if (money.compareTo(money2Check) > 0) {
                throw new ValidationException("修改金额失败,当前用户矿机钱包剩余进账额度:" + (money2Check.compareTo(BigDecimal.ZERO) > 0 ? money2Check : 0));
            }

        }
        if (type == EnumUtil.MoneyType.money3 && money.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal money3Check = this.userMoney3Check(userName);
            if (money.compareTo(money3Check) > 0) {

                //投资钱包入账
                BigDecimal money4CanIn = this.userMoney4Check(userName, money.subtract(money3Check).setScale(2,BigDecimal.ROUND_HALF_UP));
                userUserinfoService.updateUserOtherMoney(userName, money4CanIn, EnumUtil.MoneyType.money4, message, changeType);
                money = money3Check;
            }
        }
        userUserinfoService.updateUserOtherMoney(userName, money, type, message, changeType);
    }

    //可进入投资钱包得钱
    private BigDecimal userMoney4Check(String userName, BigDecimal money) {
        UserUserinfo userNameLock = userUserinfoService.getByNameLock(userName);
        BigDecimal money2Max;

        try {
            money2Max = new BigDecimal(Global.getOption("system_help", "money4_scale","2")).multiply(userNameLock.getMoney2());
        } catch (Exception e) {
            throw new ValidationException("投资钱包入账比例配置有误");
        }
        BigDecimal canTransMoney = money2Max.subtract(userNameLock.getMoney4());
        if(canTransMoney.compareTo(BigDecimal.ZERO)<=0){
            return BigDecimal.ZERO;
        }

        return money.subtract(canTransMoney).compareTo(BigDecimal.ZERO)>0?canTransMoney:money;

    }


    public BigDecimal userMoney2Check(String userName) {
        UserUserinfo userinfo = userUserinfoService.getByNameLock(userName);
        if (userinfo == null) {
            throw new ValidationException("用户不存在");
        }
        BigDecimal maxMoney;
        try {
            maxMoney = new BigDecimal(Global.getOption("system_help", "money2_max"));
        } catch (Exception e) {
            throw new ValidationException("最低激活数额配置有误");
        }
        return maxMoney.subtract(userinfo.getMoney2());
    }

    public BigDecimal userMoney3Check(String userName) {
        UserUserinfo userinfo = userUserinfoService.getByNameLock(userName);
        if (userinfo == null) {
            throw new ValidationException("用户不存在");
        }
        UserAccountchange select = new UserAccountchange();
        select.setUserName(userName);
        select.setMoneyType(EnumUtil.MoneyType.money3.toString());
        select.setChangeMoneyBegin(BigDecimal.ZERO);
        select.setCreateDate(new Date());
        List<UserAccountchange> accountchangeList = userAccountchangeDao.findList(select);
        BigDecimal money3Expend = accountchangeList.stream().map(p -> new BigDecimal(p.getChangeMoney())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal scaleMoney;
        try {
            scaleMoney = new BigDecimal(Global.getOption("system_help", "money3_scale"));
        } catch (Exception e) {
            throw new ValidationException("动态钱包每日进账比例配置有误");
        }
        BigDecimal money = userinfo.getMoney2().multiply(scaleMoney).subtract(money3Expend);
        if (money.compareTo(BigDecimal.ZERO) < 0) {
            money = BigDecimal.ZERO;
        }
        return money.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 发放团队奖励
     *
     * @param userUserinfo
     * @param sumMoney
     */
    public void putTeamAward(UserUserinfo userUserinfo, BigDecimal sumMoney) {
        if (this.isClosedBeta()) {
            throw new ValidationException("该功能未开放");
        }
        BigDecimal aveMoney;
        List<UserUserinfo> teamUserList = userUserinfoService.findList(userUserinfo);
        if (teamUserList.size() <= 0) {
            throw new ValidationException("所选等级人数必须大于0");
        }
        aveMoney = sumMoney.divide(BigDecimal.valueOf(teamUserList.size()), 2, BigDecimal.ROUND_HALF_UP);
        if (aveMoney.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("奖金过低,无法发放");
        }
        for (UserUserinfo userinfo : teamUserList) {
            this.updateUserOtherMoney(userinfo.getUserName(), aveMoney, EnumUtil.MoneyType.money3, "团队奖励发放", EnumUtil.MoneyChangeType.PUT_TEAM_AWARD);
        }

    }

    /**
     * 市场架构接口
     *
     * @param topUserName
     * @return
     */
    public String getDataContent(String topUserName) {
        List<TreeData> treeData = new ArrayList<>();
        getData(topUserName, treeData);

        return JsonMapper.toJsonString(treeData);
    }

    /**
     * 数据组装
     *
     * @param topUserName
     * @param Data
     * @return
     */
    public List<TreeData> getData(String topUserName, List<TreeData> Data) {

        List<UserUserinfo> usersByParentName = userUserinfoService.getUsersByParentName(topUserName);
        for (UserUserinfo userinfo : usersByParentName) {
            List<UserUserinfo> childList = userUserinfoService.getUsersByParentName(userinfo.getUserName());
            List<TreeData> curentData = new ArrayList<>();
            StringBuffer content = new StringBuffer(userinfo.getUserNicename()).append("  (" + userinfo.getTrueName() + "   [" + userinfo.getAreaId() + "] [" + userinfo.getUserLevel().getLevelName() + "])");
            Data.add(new TreeData(content.toString(), curentData));
            if (childList.size() != 0) {
                getData(userinfo.getUserName(), curentData);
            }
        }

        return Data;
    }

    //是否 内测模式
    public boolean isClosedBeta() {
        if ("on".equals(Global.getOption("system_help", "isClosedBeta", "on"))) {
            return true;
        }
        return false;
    }


    public void updateParentList(String changeUser, String parentName) {


        UserUserinfo changeUserinfo = userUserinfoService.getByName(changeUser);
        UserUserinfo parentUserinfo = userUserinfoService.getByName(parentName);

        if (changeUserinfo == null || parentUserinfo == null) {
            throw new ValidationException("修改用户或者他的父级用户不存在");
        }

        if(parentUserinfo.getParentList().indexOf("/"+changeUserinfo.getId()+"/") > -1){
            throw new ValidationException("父级不能是伞下用户");
        }

        if (changeUserinfo.getUserName().equals(parentUserinfo.getUserName())) {
            throw new ValidationException("该用户不能自己作为自己的父级");
        }


        //修改父级
        changeUserinfo.setLevelNo(parentUserinfo.getLevelNo() + 1);
        changeUserinfo.setParentName(parentUserinfo.getUserName());
        changeUserinfo.setWalterName(parentUserinfo.getUserName());
        changeUserinfo.setServerName(parentUserinfo.getUserName());
        changeUserinfo.setParentList("/" + parentUserinfo.getId() + parentUserinfo.getParentList());

        try {
            userUserinfoService.save(changeUserinfo);

            List<UserUserinfo> usersByParentName = userUserinfoService.getUsersByParentName(changeUserinfo.getUserName());
            if(usersByParentName.size() != 0){
                for (UserUserinfo userinfo :usersByParentName) {
                    updateParentList(userinfo.getUserName(),userinfo.getParentName());
                }
            }

        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }

    }

    //会员外部转账
    public void moneyToUserOut(String userName,String address, BigDecimal money,String message) {
        if (this.isClosedBeta()) {
            throw new ValidationException("该功能未开放");
        }
        UserUserinfo user = userUserinfoService.getByNameLock(userName);
        if (user == null){
            throw new ValidationException("用户不存在");
        }
        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("转账金额必须大于零");
        }
        if (!VerifyUtils.isInteger(money)) {
            throw new ValidationException("转账金额必须为整数");
        }
        if (money.compareTo(user.getMoney()) > 0) {
            throw new ValidationException("交易钱包数额不足");
        }
        StringBuffer comment = new StringBuffer();
        comment.append("会员钱包地址:").append(user.getRemarks()).append(" 备注:").append(message);
        boolean res = transOutService.updateMoneyByAddress(address, money, comment.toString());
        if (!res){
            throw new ValidationException("转换失败,通信失败");
        }
        userUserinfoService.updateUserMoney(userName,money.multiply(BigDecimal.valueOf(-1)),"交易钱包转出至交易系统,交易钱包地址:"+address+"备注:"+message, EnumUtil.MoneyChangeType.MONEY_TRANS_OUT);

    }

    /**
     * 判断是否可以进行转账操作
     */
    public boolean canTransfer(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss") ;
        String beginTime = Global.getOption("system_help", "transfer_begin");
        String endTime = Global.getOption("system_help", "transfer_end");

        if(StringUtils2.isBlank(beginTime) || StringUtils2.isBlank(endTime)){
            throw new ValidationException("转账限制时间配置有误");
        }

        try {
            Date begin = dateFormat.parse(beginTime);
            Date end = dateFormat.parse(endTime);
            Date nowTime = dateFormat.parse(DateUtils2.getTime());
            if(nowTime.getTime() >= begin.getTime() || nowTime.getTime() <= end.getTime()){
                return false;
            }
        } catch (Exception e) {
            throw new ValidationException("转账限制时间配置有误");
        }
        return true;
    }

}
