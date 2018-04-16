/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.shop.service.ShopUCApiService;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.user.dao.*;
import com.thinkgem.jeesite.modules.user.entity.*;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 会员列表Service
 *
 * @author wyxiang
 * @version 2017-03-25
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackForClassName = {"RuntimeException", "Exception", "ValidationException"})
public class UserUserinfoService extends CrudService<UserUserinfoDao, UserUserinfo> {

    @Autowired
    private UserLevelService userLevelService;
    @Autowired
    private UserUserinfoBankService userUserinfoBankService ;
    @Autowired
    private UserAccountchangeDao userAccountchangeDao;

    @Autowired
    private UserChargeLogDao userChargeLogDao;

    @Autowired
    private ShopUCApiService shopUCApiService;
    @Autowired
    private AreaDao areaDao;

    @Autowired
    private UserUserinfoBankDao userinfoBankDao;

    @Autowired
    private UserWithdrawDao userWithdrawDao;

    @Autowired
    private UserLevelLogDao userLevelLogDao;



    /**
     * 根据id查询用户
     */
    public UserUserinfo get(String id) {

        UserUserinfo userinfo = super.get(id);
        if (userinfo == null) {
            return null;
        }
        UserLevel userLevel = userLevelService.get(userinfo.getUserLevel().getId());
        userinfo.setUserLevel(userLevel);
        return userinfo;
    }

    /**
     * 根据用户
     *
     * @param username
     * @return
     */

    public UserUserinfo getByName(String username) {

        UserUserinfo userinfo = dao.getByName(username);
        if (userinfo == null) {
            return null;
        }
        UserLevel userLevel = userLevelService.get(userinfo.getUserLevel().getId());
        userinfo.setUserLevel(userLevel);
        return userinfo;
    }
    /**
     * 根据用户

     */

    public void setUserAvatar(UserUserinfo userinfo) {

        dao.setUserAvatar(userinfo);
    }
    /**
     * 锁表查询用户,涉及到用户资金等操作时需要锁表查询
     *
     * @param username
     * @return
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ, readOnly = false, propagation = Propagation.REQUIRED, rollbackForClassName = {"RuntimeException", "Exception", "ValidationException"})
    public UserUserinfo getByNameLock(String username) {
        if (StringUtils2.isEmpty(username)) {
           return null;
        }

        UserUserinfo userinfo = dao.getByNameLock(username);
        return userinfo;
    }


    public UserUserinfo getByMobile(String mobile) {
        UserUserinfo userinfoList = dao.getByMobile(mobile);
        return userinfoList;
    }

    public List<UserUserinfo> findList(UserUserinfo userUserinfo) {
        return super.findList(userUserinfo);
    }

    public Page<UserUserinfo> findPage(Page<UserUserinfo> page, UserUserinfo userUserinfo) {
        return super.findPage(page, userUserinfo);
    }

    public void save(UserUserinfo userUserinfo) throws RuntimeException {
        save(userUserinfo, true);
    }


    public synchronized void save(UserUserinfo userUserinfo, boolean syncShop) throws RuntimeException {

        if (userUserinfo.getIsNewRecord()) {

            //新增用户
            //随机用户编号
            if(StringUtils2.isBlank(userUserinfo.getUserName())){
                String prefix = Global.getOption("system_user_set","user_id_prefix","hy");
                if("sequence".equals(Global.getOption("system_user_set","user_create_type"))){
                    userUserinfo.setUserName(prefix + IdGen.uuid("seq_userinfo_name"));
                }else{
                    for (int i = 0; i < 50; i++) {
                        userUserinfo.setUserName(IdGen.ramdomUserName(100000, 999999, prefix));
                        UserUserinfo tempUser = this.getByName(userUserinfo.getUserName());
                        if (tempUser == null) {
                            break;
                        }
                    }
                }
            }


            if (StringUtils2.isBlank(userUserinfo.getUserName())) {
                throw new ValidationException("失败:用户名不能为空!");
            }

            if (getByName(userUserinfo.getUserName()) !=null) {
                throw new ValidationException("失败:该会员编号已存在,请更换!");
            }

            if (StringUtils2.isBlank(userUserinfo.getMobile())) {
                throw new ValidationException("失败:手机号不能为空!");
            }
            if (!VerifyUtils.isMobile(userUserinfo.getMobile())) {
                throw new ValidationException("失败:手机号不合法!");
            }
//            if (getByMobile(userUserinfo.getMobile()) != null) {
//                throw new ValidationException("失败:该手机号已经注册,请更换!");
//            }
            //判断手机号注册用户是否达到上限
            checkMaxUser(userUserinfo);
//            if (StringUtils2.isBlank(userUserinfo.getWalterName())) {
//                throw new ValidationException("失败:推荐人不能为空!");
//            } else {
//                UserUserinfo walterUser = this.getByName(userUserinfo.getWalterName());
//                if (walterUser == null) {
//                    throw new ValidationException("失败:推荐人不存在!");
//                }
//
//            }
            if(StringUtils2.isBlank(userUserinfo.getUserLevelId())){
                userUserinfo.setUserLevelId("1");
            }

            UserLevel userLevel = userLevelService.findByLevalCode(userUserinfo.getUserLevelId());
            if (null == userLevel) {
                throw new ValidationException("失败:用户等级不存在!");
            }

            //非双线,如果接点人和报单中心为空则默认设置为推荐人
            if(!Global.isOptionOpen("system_user_set","double_line_open",false)){

                if(StringUtils2.isBlank(userUserinfo.getParentName())){
                    userUserinfo.setParentName(userUserinfo.getWalterName());
                }
                if(StringUtils2.isBlank(userUserinfo.getServerName())){
                    userUserinfo.setServerName(userUserinfo.getWalterName());
                }
            }



//            if (StringUtils2.isBlank(userUserinfo.getParentName())) {
//                throw new ValidationException("失败:接点人不能为空!");
//            } else {
//                UserUserinfo walterUser = this.getByName(userUserinfo.getParentName());
//                if (walterUser == null) {
//                    throw new ValidationException("失败:接点人不存在!");
//                }
//
//            }




//            if (StringUtils2.isBlank(userUserinfo.getServerName())) {
//                throw new ValidationException("失败:服务人不能为空!");
//            } else {
//                UserUserinfo walterUser = this.getByName(userUserinfo.getServerName());
//                if (walterUser == null) {
//                    throw new ValidationException("失败:服务人不存在!");
//                }
//
//            }

            UserUserinfo parentUser = this.getByName(userUserinfo.getParentName());
            userUserinfo.setParentList("/" + parentUser.getId() + parentUser.getParentList());

            //双线只可注册2个用户
            if(Global.isOptionOpen("system_user_set","double_line_open",false)){

                int subCount = this.getUsersByParentName(parentUser.getUserName()).size();
                if(subCount >= 2 ){
                    throw new ValidationException("失败:该用户已经存在左右两个下级,不可再挂接点位!");
                }

                if(subCount == 0){
                    userUserinfo.setUserNo(parentUser.getUserNo()*2 );
                }
                else if(subCount == 1){
                    userUserinfo.setUserNo(parentUser.getUserNo()*2 + 1 );
                }

                //推荐人必须是该报单中心所属团队
                if(!this.isSubChild(userUserinfo.getServerName(),userUserinfo.getWalterName())){
                    throw new ValidationException("失败:推荐人必须是本报单中心所属团队!");
                }


                //接点人必须是推荐人所属团队
                if(!this.isSubChild(userUserinfo.getWalterName(),userUserinfo.getParentName())){
                    throw new ValidationException("失败:接点人必须归属推荐人所属团队!");
                }


                //每个用户必须要有一个直推,才能给他右区挂接点位
                List<UserUserinfo> currUserSubList = this.getUsersByParentName(userUserinfo.getParentName());
                if(currUserSubList.size() > 0){

                    //插叙推荐人
                    List<UserUserinfo> currWaiterSubList = this.getUsersByWalterName(userUserinfo.getParentName());
                    if(currWaiterSubList.size() <=0){
                        throw new ValidationException("失败:该用户必须有一个直推,方可给他右区挂接点位!");
                    }

                }
            }


            //设置默认值
            userUserinfo.setLevelNo(parentUser.getLevelNo() + 1);
            if(StringUtils2.isBlank(userUserinfo.getUserType())){
                userUserinfo.setUserType("1");
            }
            userUserinfo.setPositionSite(this.getUsersByParentName(parentUser.getUserName()).size() + 1);
            userUserinfo.setIsCheck("0");
            userUserinfo.setShopId("0");
            userUserinfo.setIsShop("1");
            userUserinfo.setUsercenterLevel("0");

            if (!StringUtils2.isBlank(userUserinfo.getUserPass()) && userUserinfo.getUserPass().length() > 50) {
                userUserinfo.setUserPass(StringUtils2.EMPTY);
            }

            if (!StringUtils2.isBlank(userUserinfo.getBankPassword()) && userUserinfo.getBankPassword().length() > 50) {
                userUserinfo.setBankPassword(StringUtils2.EMPTY);
            }

            userUserinfo.validateModule(true);
            if (Global.isOptionOpen("auth_shop", "auth_open") && syncShop) {
                if (!shopUCApiService.registerUser(userUserinfo)) {
                    throw new ValidationException("通信失败, 注册用户失败!");
                }
            }
            userUserinfo.preInsert();
            //同步完商城后才能对密码加密，否则会造成商城端密码与会员系统不一致，无法登陆
            userUserinfo.setUserPass(SystemService.entryptPassword(userUserinfo.getUserPass()));
            userUserinfo.setBankPassword(SystemService.entryptPassword(userUserinfo.getBankPassword()));
            if(StringUtils2.isNoneBlank(userUserinfo.getZhuanquPass())){
                userUserinfo.setZhuanquPass(SystemService.entryptPassword(userUserinfo.getZhuanquPass()));
            }
            dao.insert(userUserinfo);

            //注册完成后,给用户升级表插入记录

            UserLevelLog userLevelLog = new UserLevelLog();
            userLevelLog.setUserName(userUserinfo.getUserName());
            userLevelLog.setUpdateType("1");
            userLevelLog.setCommt("用户注册升级");
            userLevelLog.setOldLevel("0");
            userLevelLog.setNewLevel(userUserinfo.getUserLevelId());
            userLevelLog.setPerformance(userLevel.getPerformance());
            userLevelLog.setIscheck("0");

            userLevelLog.preInsert();
            userLevelLogDao.insert(userLevelLog);

        } else {
            //判断手机号注册用户是否达到上限
            if(!this.get(userUserinfo.getId()).getMobile().equals(userUserinfo.getMobile())){
                checkMaxUser(userUserinfo);
            }
            if (StringUtils2.isBlank(userUserinfo.getUserName())) {
                throw new ValidationException("失败:用户名不能为空!");
            }
            if (StringUtils2.isBlank(userUserinfo.getMobile())) {
                throw new ValidationException("失败:手机号不能为空!");
            }
            if (!VerifyUtils.isMobile(userUserinfo.getMobile())) {
                throw new ValidationException("失败:手机号不合法!");
            }

//            UserUserinfo userByMobile = getByMobile(userUserinfo.getMobile());
//            if (userByMobile != null && !userByMobile.getUserName().equals(userUserinfo.getUserName())) {
//                throw new ValidationException("失败:该手机号已经注册,请更换!");
//            }

//            if (StringUtils2.isBlank(userUserinfo.getWalterName())) {
//                throw new ValidationException("失败:推荐人不能为空!");
//            } else {
//                UserUserinfo walterUser = this.getByName(userUserinfo.getWalterName());
//                if (walterUser == null) {
//                    throw new ValidationException("失败:该服务人不存在!");
//                }
//            }

            String userId = userUserinfo.getId();
            UserUserinfo userBeforUpdated = get(userId);
            userUserinfo.preUpdate();

            if (!StringUtils2.isBlank(userUserinfo.getUserPass()) && userUserinfo.getUserPass().length() > 50) {
                userUserinfo.setUserPass(StringUtils2.EMPTY);
            }

            if (!StringUtils2.isBlank(userUserinfo.getBankPassword()) && userUserinfo.getBankPassword().length() > 50) {
                userUserinfo.setBankPassword(StringUtils2.EMPTY);
            }
            if (!StringUtils2.isBlank(userUserinfo.getZhuanquPass()) && userUserinfo.getZhuanquPass().length() > 50) {
                userUserinfo.setZhuanquPass(StringUtils2.EMPTY);
            }
            if (Global.isOptionOpen("auth_shop", "auth_open") && syncShop) {
                if (!shopUCApiService.updateUserInfo(userUserinfo.getMobile(), userBeforUpdated.getMobile(), userUserinfo.getUserPass(), userUserinfo.getTrueName(), StringUtils2.EMPTY)) {
                    throw new ValidationException("通信失败, 更新用户失败!");
                }
            }
            if (!StringUtils2.isBlank(userUserinfo.getUserPass())) {
                userUserinfo.setUserPass(SystemService.entryptPassword(userUserinfo.getUserPass()));
            }

            if (!StringUtils2.isBlank(userUserinfo.getBankPassword())) {
                userUserinfo.setBankPassword(SystemService.entryptPassword(userUserinfo.getBankPassword()));
            }
            if (!StringUtils2.isBlank(userUserinfo.getZhuanquPass())) {
                userUserinfo.setZhuanquPass(SystemService.entryptPassword(userUserinfo.getZhuanquPass()));
            }
            UserUserinfo userByoffice = getByName(userUserinfo.getUserName());
            if (!userByoffice.getOfficeId().equals(userUserinfo.getOfficeId())) {
                updateUseroffice(userUserinfo.getOfficeId(),userUserinfo.getId());
            }
            dao.update(userUserinfo);
        }
    }

    //判断手机号注册用户是否达到上限
    private void checkMaxUser(UserUserinfo userUserinfo) {
        UserUserinfo selectUser = new UserUserinfo();
        selectUser.setMobile(userUserinfo.getMobile());
        List<UserUserinfo> mobileUserList = dao.findList(selectUser);
        //注册用户最多限制
        int num = 1;
        try {
             num = Integer.parseInt(Global.getOption("system_user_set","max_user","1"));
        } catch (NumberFormatException e) {
            throw new ValidationException("每个手机号最多注册用户配置错误!");
        }
        if(mobileUserList.size()>=num){
            throw new ValidationException("失败:该手机号已经注册达到上限!");
        }
    }


    public void updateUseroffice(String officeId,String parentListLike) throws ValidationException {
        dao.updateUseroffice(officeId,parentListLike);
    }
    public void delete(String userName) throws ValidationException {

        UserUserinfo currUser = getByNameLock(userName);
        List<UserUserinfo> childList = getUsersByParentName(userName);
        if (childList.size() > 0) {
            throw new ValidationException("该用户存在下级,不可删除");
        }
        super.delete(currUser);

    }


    /**
     * 根据接点人名称 user_name 查询所有的子用户
     *
     * @return
     */
    public List<UserUserinfo> getUsersByParentName(String parentName) {
        return dao.getUsersByParentName(parentName);
    }

    /**
     * 根据报单中心名称 user_name 查询所有的子用户
     *
     * @return
     */

    public List<UserUserinfo> getUsersByServerName(String serverName) {
        return dao.getUsersByServerName(serverName);

    }

    /**
     * 根据服务人名称 user_name 查询所有的子用户
     *
     * @return
     */
    public List<UserUserinfo> getUsersByWalterName(String walterName) {
        return dao.getUsersByWalterName(walterName);
    }

    /**
     * 修改用户手机号
     *
     * @param mobile 用户手机号
     * @return
     */
    public void updateUserMobile(String userName, String mobile, boolean syncShop) throws ValidationException {

        //qydo   需要通信,商城端修改手机号

        UserUserinfo mobileUser = getByMobile(mobile);

        if (mobileUser != null) {
            throw new ValidationException("该手机号已存在!");
        }

        if (Global.isOptionOpen("auth_shop", "auth_open") && syncShop) {
            UserUserinfo userInfo = getByName(userName);
            if (!shopUCApiService.updateUserInfo(mobile, userInfo.getMobile(), userInfo.getUserPass(), userInfo.getTrueName(), StringUtils2.EMPTY)) {
                throw new ValidationException("通信失败, 密码失败!");
            }
        }

        dao.updateUserMobile(userName, mobile);

    }

    public void updateUserMobile(String userName, String mobile) throws ValidationException {
        //qydo   需要通信,商城端修改手机号
        updateUserMobile(userName, mobile, true);
    }

    /**
     * 修改用户登录密码
     *
     * @param newpassword 用户登录密码
     * @return
     */

    public void updateUserPwd(String newpassword) throws ValidationException {
        updateUserPwd(UserInfoUtils.getUser().getUserName(), newpassword, true, StringUtils2.EMPTY);
    }

    public void forgetUserPwd(String username,String newpassword) throws ValidationException {
        updateUserPwd(username, newpassword, true, StringUtils2.EMPTY);
    }
    public void updateUserEmail(String email) throws ValidationException {
        dao.updateUserEmail(UserInfoUtils.getUser().getUserName(), email);
    }
    /**
     * 修改用户登录密码
     *
     * @param userName
     * @param newpassword
     * @param syncShop    是否从商城同步
     * @throws ValidationException
     */

    public void updateUserPwd(String userName, String newpassword, boolean syncShop, String shopId) throws ValidationException {
        if (StringUtils2.isEmpty(userName)) {
            return;
        }

        if (!VerifyUtils.isPassword(newpassword)) {
            throw new ValidationException("密码必须大于8位且为数字或字母");
        }

        try {
            if (Global.isOptionOpen("auth_shop", "auth_open") && syncShop) {
                UserUserinfo userInfo = getByName(userName);
                if (!shopUCApiService.updateUserInfo(userInfo.getMobile(), userInfo.getMobile(), newpassword, userInfo.getTrueName(), shopId)) {
                    throw new ValidationException("通信失败, 密码失败!");
                }
            }

            String newpasswords = SystemService.entryptPassword(newpassword);
            dao.updateUserPwd(userName, newpasswords);
        } catch (Exception ex) {
            throw new ValidationException("修改密码失败");
        }
    }

    /**
     * 修改用户支付密码
     *
     * @param newpaypass 用户登录密码
     * @return
     */

    public void updateUserPaypass(String newpaypass) throws ValidationException {
        if (!VerifyUtils.isPassword(newpaypass)) {
            throw new ValidationException("密码必须大于八位");
        }
        String newpaypassed = SystemService.entryptPassword(newpaypass);
        dao.updateUserPaypass(UserInfoUtils.getUser().getUserName(), newpaypassed);
    }

    /**
     * 修改用户状态
     *
     * @param id    用户id
     * @param state 用户状态常量  0: 冻结 1:激活
     * @return
     */
    public void updateUserState(String id, EnumUtil.UserStatusEnum state) {
        dao.updateUserState(id, state.toString());
    }


    /**
     * 根据用户编号修改用户金额, 并插入账变表UserAccountchangeService
     * 金额只可增加加减, 不可直接修改
     *
     * @param userName
     * @param money
     * @param message
     * @return
     */


    public void updateUserMoney(String userName, BigDecimal money, String message, EnumUtil.MoneyChangeType changeType) throws ValidationException {
        updateUserMoney(userName, money, message, changeType, null);
    }

    /**
     * 根据用户编号修改用户金额, 并插入账变表UserAccountchangeService
     * 金额只可增加加减, 不可直接修改
     *
     * @param userName
     * @param money
     * @param message
     * @return
     */


    public void updateUserMoney(String userName, BigDecimal money, String message, EnumUtil.MoneyChangeType changeType, Map<String, Object> params) throws ValidationException {
        UserUserinfo userUserinfo = this.getByNameLock(userName);
        if (userUserinfo == null) {
            return;
        }

        if (money == null) {
            return;
        }

        if(money.compareTo(BigDecimal.ZERO) ==0){
            return;
        }
        //qydo   删除commt参数, 和message重复了
        if (!StringUtils2.isBlank(userUserinfo.getMobile()) && VerifyUtils.isMobile(userUserinfo.getMobile())) {
            if (Global.isOptionOpen("auth_shop", "auth_open") && changeType != EnumUtil.MoneyChangeType.wxshop && changeType != EnumUtil.MoneyChangeType.WXSHOP_CONSUME) {
                if (!shopUCApiService.updateUserMoney(userUserinfo.getMobile(), money, message, params)) {
                    throw new ValidationException("通信失败, 修改金额失败!");
                }
            }
        }

        try {
            //插入用户帐变
            UserAccountchange userAccountchange = new UserAccountchange();
            userAccountchange.setUserName(userUserinfo.getUserName());
            userAccountchange.setChangeMoney(money.toString());
            userAccountchange.setBeforeMoney(userUserinfo.getMoney());
            userAccountchange.setAfterMoney(userUserinfo.getMoney().add(money).toString());
            userAccountchange.setCommt(message);
            if (changeType == EnumUtil.MoneyChangeType.ALIPAY_RECHARGE) {
                userAccountchange.setChangeType(EnumUtil.MoneyChangeType.charget.toString());
            } else {
                userAccountchange.setChangeType(changeType.toString());
            }

            userAccountchange.setMoneyType(EnumUtil.MoneyType.money.toString());
            userAccountchange.preInsert();

            userAccountchangeDao.insert(userAccountchange);

            if (changeType == EnumUtil.MoneyChangeType.charget) {

                UserChargeLog userChargeLog = new UserChargeLog();
                userChargeLog.setUserName(userName);
                userChargeLog.setRecordcode(IdGen.randomuuid());
                userChargeLog.setChangeMoney(money.toString());
                userChargeLog.setBeforeMoney(userUserinfo.getMoney());
                userChargeLog.setStatus(EnumUtil.YesNO.YES.toString());
                userChargeLog.setAfterMoney(userUserinfo.getMoney().add(money).toString());
                userChargeLog.setChangeFrom("后台充值");
                userChargeLog.setChangeType(EnumUtil.MoneyType.money.toString());
                userChargeLog.setCommt(message + "-" + UserUtils.getUser().getLoginName());
                userChargeLog.preInsert();
                userChargeLogDao.insert(userChargeLog);
            } else if (changeType == EnumUtil.MoneyChangeType.widthdraw) {

                if (!params.containsKey("userBankId")) {
                    throw new ValidationException("申请提现失败,缺失银行卡信息!");
                }
                String recordcode = IdGen.uuid("seq_recordcode");
                String userBankId = params.get("userBankId").toString();
                UserWithdraw userWithdraw = new UserWithdraw();
                userWithdraw.setUserName(userUserinfo.getUserName());
                userWithdraw.setRecordcode(recordcode);
                userWithdraw.setUserBankAddress(params.get("userBankAddress").toString());
                userWithdraw.setProvinces(params.get("provinces").toString());
                userWithdraw.setCitys(params.get("citys").toString());
                userWithdraw.setArea(params.get("area").toString());
                userWithdraw.setStatus(EnumUtil.YesNO.NO.toString());
                userWithdraw.setBeforeMoney(userUserinfo.getMoney().toString());
                userWithdraw.setChangeMoney(money.multiply(new BigDecimal(-1)).toString());
                userWithdraw.setAfterMoney(userUserinfo.getMoney().add(money).toString());
                userWithdraw.setUserBankNum(userinfoBankDao.get(userBankId).getBankUserNum());
                userWithdraw.setUserBankName(userinfoBankDao.get(userBankId).getBankUserName());
                userWithdraw.setUserBankCode(userinfoBankDao.get(userBankId).getBankCode());
                userWithdraw.setCommt("用户提现");
                userWithdraw.preInsert();
                userWithdrawDao.insert(userWithdraw);
            } else if (changeType == EnumUtil.MoneyChangeType.ALIPAY_RECHARGE) {
                UserChargeLog userChargeLog = new UserChargeLog();
                userChargeLog.setUserName(userName);
                userChargeLog.setRecordcode(message);
                userChargeLog.setChangeMoney(money.toString());
                userChargeLog.setBeforeMoney(userUserinfo.getMoney());
                userChargeLog.setStatus(EnumUtil.YesNO.YES.toString());
                userChargeLog.setAfterMoney(userUserinfo.getMoney().add(money).toString());
                userChargeLog.setChangeFrom("支付宝充值");
                userChargeLog.setChangeType(EnumUtil.MoneyType.money.toString());
                userChargeLog.setCommt(message + "-" + UserUtils.getUser().getLoginName());
                userChargeLog.preInsert();
                userChargeLogDao.insert(userChargeLog);
            }else if (changeType == EnumUtil.MoneyChangeType.HUANXUN_PAY) {
                UserChargeLog userChargeLog = new UserChargeLog();
                userChargeLog.setUserName(userName);
                userChargeLog.setRecordcode(message);
                userChargeLog.setChangeMoney(money.toString());
                userChargeLog.setBeforeMoney(userUserinfo.getMoney());
                userChargeLog.setStatus(EnumUtil.YesNO.YES.toString());
                userChargeLog.setAfterMoney(userUserinfo.getMoney().add(money).toString());
                userChargeLog.setChangeFrom("环迅充值充值");
                userChargeLog.setChangeType(EnumUtil.MoneyType.money.toString());
                userChargeLog.setCommt(message + "-" + UserUtils.getUser().getLoginName());
                userChargeLog.preInsert();
                userChargeLogDao.insert(userChargeLog);
            }else if(changeType == EnumUtil.MoneyChangeType.LEVEL_UP){
                UserLevelLog userLevelLog = new UserLevelLog();
                userLevelLog.setUserName(userName);
                userLevelLog.setUpdateType("1");
                userLevelLog.setCommt("用户升级");
                userLevelLog.setOldLevel(userUserinfo.getUserLevelId());
                userLevelLog.setNewLevel(params.get("leveId").toString());
                userLevelLog.preInsert();
                userLevelLogDao.insert(userLevelLog);
            }else if (changeType == EnumUtil.MoneyChangeType.WEIXIN_PAY) {
                UserChargeLog userChargeLog = new UserChargeLog();
                userChargeLog.setUserName(userName);
                userChargeLog.setRecordcode(message);
                userChargeLog.setChangeMoney(money.toString());
                userChargeLog.setBeforeMoney(userUserinfo.getMoney());
                userChargeLog.setStatus(EnumUtil.YesNO.YES.toString());
                userChargeLog.setAfterMoney(userUserinfo.getMoney().add(money).toString());
                userChargeLog.setChangeFrom("微信充值充值");
                userChargeLog.setChangeType(EnumUtil.MoneyType.money.toString());
                userChargeLog.setCommt(message + "-" + UserUtils.getUser().getLoginName());
                userChargeLog.preInsert();
                userChargeLogDao.insert(userChargeLog);
            }

            //修改用户金额
            dao.updateUserMoney(userName, money);
        } catch (Exception e) {
            throw new ValidationException("充值失败");
        }
    }

    /**
     * 根据用户编号修改用户积分, 并插入账变表UserAccountchangeService
     * 金额只可增加加减, 不可直接修改
     *
     * @param userName
     * @param money
     * @param message
     * @return
     */

    public void updateUserScore(String userName, BigDecimal money, String message, EnumUtil.MoneyChangeType changeType) throws ValidationException {
        UserUserinfo userUserinfo = this.getByNameLock(userName);
        if (userUserinfo == null) {
            return;
        }
        //qydo   删除commt和 message重复了 - 已处理(cai)
        if(money.compareTo(BigDecimal.ZERO) ==0){
            return;
        }
        if (!StringUtils2.isBlank(userUserinfo.getMobile()) && VerifyUtils.isMobile(userUserinfo.getMobile())) {
            if (Global.isOptionOpen("auth_shop", "auth_open") && changeType != EnumUtil.MoneyChangeType.wxshop && changeType != EnumUtil.MoneyChangeType.WXSHOP_CONSUME) {
                if (!shopUCApiService.updateUserScore(userUserinfo.getMobile(), money, EnumUtil.MoneyType.score, message)) {
                    throw new ValidationException("通信失败, 修改金额失败!");
                }
            }
        }

        //插入用户帐变
        UserAccountchange userAccountchange = new UserAccountchange();

        userAccountchange.setUserName(userUserinfo.getUserName());
        userAccountchange.setChangeMoney(money.toString());
        userAccountchange.setBeforeMoney(userUserinfo.getScore());
        userAccountchange.setAfterMoney(userUserinfo.getScore().add(money).toString());
        userAccountchange.setCommt(message);
        userAccountchange.setChangeType(changeType.toString());
        userAccountchange.setMoneyType(EnumUtil.MoneyType.score.toString());

        userAccountchange.preInsert();
        userAccountchangeDao.insert(userAccountchange);

        if (changeType == EnumUtil.MoneyChangeType.charget) {

            UserChargeLog userChargeLog = new UserChargeLog();
            userChargeLog.setUserName(userName);
            userChargeLog.setRecordcode(IdGen.randomuuid());
            userChargeLog.setChangeMoney(money.toString());
            userChargeLog.setBeforeMoney(userUserinfo.getScore());
            userChargeLog.setStatus (EnumUtil.YesNO.YES.toString ());
            userChargeLog.setAfterMoney(userUserinfo.getScore().add(money).toString());
            userChargeLog.setChangeFrom("后台充值");
            userChargeLog.setCommt(message + "-" + UserUtils.getUser().getLoginName());
            userChargeLog.setChangeType(EnumUtil.MoneyType.score.toString());

            userChargeLog.preInsert();
            userChargeLogDao.insert(userChargeLog);
        }

        dao.updateUserScore(userName, money);
    }

    /**
     * 根据用户编号修改用户积分, 并插入账变表UserAccountchangeService
     * 金额只可增加加减, 不可直接修改
     *
     * @param userName
     * @param money
     * @param message
     * @return
     */

    public void updateUserCoin(String userName, BigDecimal money, String message, EnumUtil.MoneyChangeType changeType) throws ValidationException {
        UserUserinfo userUserinfo = this.getByNameLock(userName);
        if (userUserinfo == null) {
            return;
        }
        if(money.compareTo(BigDecimal.ZERO) ==0){
            return;
        }
        //插入用户帐变
        UserAccountchange userAccountchange = new UserAccountchange();

        userAccountchange.setUserName(userUserinfo.getUserName());
        userAccountchange.setChangeMoney(money.toString());
        userAccountchange.setBeforeMoney(userUserinfo.getCoin());
        userAccountchange.setAfterMoney(userUserinfo.getCoin().add(money).toString());
        userAccountchange.setCommt(message);
        userAccountchange.setChangeType(changeType.toString());
        userAccountchange.setMoneyType(EnumUtil.MoneyType.coin.toString());

        userAccountchange.preInsert();
        userAccountchangeDao.insert(userAccountchange);

        if (changeType == EnumUtil.MoneyChangeType.charget) {

            UserChargeLog userChargeLog = new UserChargeLog();
            userChargeLog.setUserName(userName);
            userChargeLog.setRecordcode(IdGen.randomuuid());
            userChargeLog.setChangeMoney(money.toString());
            userChargeLog.setBeforeMoney(userUserinfo.getCoin());
            userChargeLog.setStatus (EnumUtil.YesNO.YES.toString ());
            userChargeLog.setAfterMoney(userUserinfo.getCoin().add(money).toString());
            userChargeLog.setChangeFrom("后台充值");
            userChargeLog.setCommt(message + "-" + UserUtils.getUser().getLoginName());
            userChargeLog.setChangeType(EnumUtil.MoneyType.coin.toString());

            userChargeLog.preInsert();
            userChargeLogDao.insert(userChargeLog);
        }

        dao.updateUserCoin(userName, money);
    }


    /**
     * 根据用户编号修改用户其他金额即接口, 并插入账变表UserAccountchangeService
     * 金额只可增加加减, 不可直接修改
     *
     * @param userName
     * @param money
     * @param message
     * @return
     */

    public void updateUserOtherMoney(String userName, BigDecimal money, EnumUtil.MoneyType type, String message, EnumUtil.MoneyChangeType changeType) throws ValidationException {

        if(money.compareTo(BigDecimal.ZERO) ==0){
            return;
        }

        UserUserinfo userUserinfo = this.getByNameLock(userName);

        if (Global.isOptionOpen("auth_shop", "auth_open") && changeType != EnumUtil.MoneyChangeType.wxshop && changeType != EnumUtil.MoneyChangeType.WXSHOP_CONSUME) {
            if (!shopUCApiService.updateUserScore(userUserinfo.getMobile(), money, type, message)) {
                throw new ValidationException("通信失败, 修改金额失败!");
            }
        }
        UserAccountchange userAccountchange = new UserAccountchange();
        BigDecimal beforeMoney = new BigDecimal(0);
        if( EnumUtil.MoneyType.money2.toString().equals(type.toString())){
            beforeMoney = userUserinfo.getMoney2();
        }else if(EnumUtil.MoneyType.money3.toString().equals(type.toString())){
            beforeMoney = userUserinfo.getMoney3();
        }else if(EnumUtil.MoneyType.money4.toString().equals(type.toString())){
            beforeMoney = userUserinfo.getMoney4();
        }else if(EnumUtil.MoneyType.money5.toString().equals(type.toString())){
            beforeMoney = userUserinfo.getMoney5();
        }else if(EnumUtil.MoneyType.money6.toString().equals(type.toString())){
            beforeMoney = userUserinfo.getMoney6();
        }


        //插入用户帐变

        if(changeType != EnumUtil.MoneyChangeType.none_log){
            userAccountchange.setUserName(userUserinfo.getUserName());
            userAccountchange.setChangeMoney(money.toString());
            userAccountchange.setBeforeMoney(beforeMoney);
            userAccountchange.setAfterMoney(beforeMoney.add(money).toString());
            userAccountchange.setCommt(message);
            userAccountchange.setChangeType(changeType.toString());

            userAccountchange.setMoneyType(type.toString());
            userAccountchange.preInsert();

            userAccountchangeDao.insert(userAccountchange);
        }

        if (changeType == EnumUtil.MoneyChangeType.charget) {

            UserChargeLog userChargeLog = new UserChargeLog();
            userChargeLog.setUserName(userName);
            userChargeLog.setRecordcode(IdGen.randomuuid());
            userChargeLog.setChangeMoney(money.toString());
            userChargeLog.setBeforeMoney(userUserinfo.getMoney2());
            userChargeLog.setStatus (EnumUtil.YesNO.YES.toString ());
            userChargeLog.setAfterMoney(userUserinfo.getMoney2().add(money).toString());
            userChargeLog.setChangeFrom("后台充值");
            userChargeLog.setCommt(message + "-" + UserUtils.getUser().getLoginName());
            userChargeLog.setChangeType(type.toString());

            userChargeLog.preInsert();
            userChargeLogDao.insert(userChargeLog);
        }

        dao.updateUserOtherMoney(userName, money, type.toString());
    }


    /**
     * 根据接点人名称 user_name 查询所有的子用户 -- 树列表
     *
     * @param userName 接点人编号
     * @param deepNum  查询几层 : 包含当前用户
     * @return
     */
    public Map<String, Object> getUsersTreeByParentName(String userName, int deepNum, String excludeUserId) {

        UserUserinfo userinfo = dao.getByName(userName);
        Map<String, Object> treeMap = Maps.newHashMap();
        if (!excludeUserId.equals(userinfo.getId())) {
            treeMap = templateUserTree(userinfo);
        }

        if (deepNum > 1) {
            deepUserList(userinfo.getUserName(), --deepNum, treeMap, excludeUserId);
       }

        return treeMap;

    }
    /**
     * 根据推荐人名称 user_name 查询所有的子用户 -- 树列表
     *
     * @param userName 接点人编号
     * @param deepNum  查询几层 : 包含当前用户
     * @return
     */
    public Map<String, Object> getUsersTreeByWalterName(String userName, int deepNum, String excludeUserId) {

        UserUserinfo userinfo = dao.getByName(userName);
        Map<String, Object> treeMap = Maps.newHashMap();
        if (!excludeUserId.equals(userinfo.getId())) {
            treeMap = templateUserTreeTo(userinfo);
        }

        if (deepNum > 1) {
            deepUserListTo(userinfo.getUserName(), --deepNum, treeMap, excludeUserId);
        }

        return treeMap;

    }
    /**
     * 获取用户树显示模版
     *
     * @param userinfo
     * @return
     */
    private Map<String, Object> templateUserTree(UserUserinfo userinfo) {

        Map<String, Object> map = Maps.newHashMap();

        map.put("id", userinfo.getId());
        map.put("name", userinfo.getUserName() + "[" + userinfo.getTrueName() + "]");
        map.put("className", "userLevel_" + userinfo.getUserLevelId());
        if(dao.getUsersByParentName(userinfo.getUserName()).size() > 0){
            map.put("className", "node2");
        }

        //拼接上下级存在情况
        String relate = "";
        //上级
        relate += StringUtils2.isNotBlank(userinfo.getParentName()) ? "1" : 0;
        //平级
        relate += "1";
        //下级
        relate += dao.getUsersByParentName(userinfo.getUserName()).size() > 0 ? "1" : "0";

        map.put("relationship", relate);
        String doubleLine = "";
        if(Global.isOptionOpen("system_user_set","double_line_open",false)){
            doubleLine ="<div class='performance'>" + "<span >"+ userinfo.getMainPerformance()+ "</span>" + "<span >量</span>"+"<span >"+ userinfo.getExtendPerformance()+ "</span>"+"</div>" +
                    "<div class='remainder'>" + "<span >"+ userinfo.getMainPerformanceRemainder()+ "</span>" + "<span >余</span>"+"<span >"+ userinfo.getExtendPerformanceRemainder()+ "</span>"+"</div>";

        }

        String template =
                "<div class='network'>" +
                        "<div>" +
                        "<div class='mobile'>" + "<span>手机:" + "</span>" + userinfo.getMobile() + "</div>" +
                        "<div class='name'>" + "<span>姓名:" + "</span>" + userinfo.getTrueName() + "</div>" +
                        "<div class='level'>" + "<span>等级:" + "</span>" + userinfo.getUserLevel().getLevelName() + "</div>" +
                        doubleLine +
                "</div>";



        map.put("title", template);

        return map;

    }
    /**
     * 获取用户树显示模版
     *
     * @param userinfo
     * @return
     */
    private Map<String, Object> templateUserTreeTo(UserUserinfo userinfo) {

        Map<String, Object> map = Maps.newHashMap();

        map.put("id", userinfo.getId());
        map.put("name", userinfo.getUserName() + "[" + userinfo.getTrueName() + "]");
        map.put("className", "userLevel_" + userinfo.getUserLevelId());
        if(dao.getUsersByWalterName(userinfo.getUserName()).size() > 0){
            map.put("className", "node2");
        }

        //拼接上下级存在情况
        String relate = "";
        //上级
        relate += StringUtils2.isNotBlank(userinfo.getWalterName()) ? "1" : 0;
        //平级
        relate += "1";
        //下级
        relate += dao.getUsersByWalterName(userinfo.getUserName()).size() > 0 ? "1" : "0";

        map.put("relationship", relate);
        String doubleLine = "";

        String template =
                "<div class='network'>" +
                        "<div>" +
                        "<div class='mobile'>" + "<span>手机:" + "</span>" + userinfo.getMobile() + "</div>" +
                        "<div class='name'>" + "<span>姓名:" + "</span>" + userinfo.getTrueName() + "</div>" +
                        "<div class='level'>" + "<span>等级:" + "</span>" + userinfo.getUserLevel().getLevelName() + "</div>" +
                        doubleLine +
                        "</div>";



        map.put("title", template);

        return map;

    }
    private void deepUserList(String parentUserName, Integer deepNum, Map<String, Object> map, String excludeUserId) {


        List<UserUserinfo> userinfoList = dao.getUsersByParentName(parentUserName);
        for (UserUserinfo entry : userinfoList) {

            if (excludeUserId.equals(entry.getId())) {
                continue;
            }

            Map<String, Object> child = templateUserTree(entry);

            if (!map.containsKey("children")) {
                map.put("children", new ArrayList<Object>());
            }

            List childList = (ArrayList) map.get("children");
            childList.add(child);


            if (deepNum > 1) {

                deepUserList(entry.getUserName(), --deepNum, child, excludeUserId);
            }
        }

    }
    private void deepUserListTo(String parentUserName, Integer deepNum, Map<String, Object> map, String excludeUserId) {


        List<UserUserinfo> userinfoList = dao.getUsersByWalterName(parentUserName);
        for (UserUserinfo entry : userinfoList) {

            if (excludeUserId.equals(entry.getId())) {
                continue;
            }

            Map<String, Object> child = templateUserTreeTo(entry);

            if (!map.containsKey("children")) {
                map.put("children", new ArrayList<Object>());
            }

            List childList = (ArrayList) map.get("children");
            childList.add(child);


            if (deepNum > 1) {

                deepUserListTo(entry.getUserName(), --deepNum, child, excludeUserId);
            }
        }

    }

    /**
     * 根据用户名称 user_name 修改是否保单中心
     *
     * @param userName
     * @return
     */

    public boolean setUserCenter(String userName, String stutes, String level, String address) throws ValidationException {
        UserUserinfo userinfo = getByName(userName);
        if (StringUtils2.isNotBlank(stutes)) {
            userinfo.setIsUsercenter(stutes);
        }
        if (StringUtils2.isNotBlank(level)) {
            userinfo.setUsercenterLevel(level);
        }
        if (StringUtils2.isNotBlank(address)) {
            userinfo.setUsercenterAddress(address);
        }
        userinfo.setUsercenterAddtime(new Date());

        dao.setUserCenter(userinfo);
        return true;
    }

    /**
     * 用户提现
     * @param userName
     * @return
     */
    public void withdrawApply(String userName, UserBankWithdraw userBankWithdraw, String money, String userBankId) throws ValidationException {
        //判断当天是否可以提现
        String week = DateUtils2.getWeekOfDate(new Date());
        if(!"on".equals(Global.getOption("system_user_set",week))){
            throw new ValidationException("抱歉,不在提现日期内!");
        }
        //判断是否在规定提现时间内
        Date beginTime = DateUtils2.formatStrTime(Global.getOption("system_user_set", "withdraw_time_begin"));
        Date endTime = DateUtils2.formatStrTime(Global.getOption("system_user_set", "withdraw_time_end"));
        Date nowtime =DateUtils2.formatStrTime(DateUtils2.getTime());
        if (beginTime.getTime() > nowtime.getTime() || nowtime.getTime() > endTime.getTime()) {
            throw new ValidationException("请在规定时间内提现! ");
        }

        UserUserinfo userinfo = getByNameLock(userName);
        UserWithdraw userWithdraw = new UserWithdraw();
        userWithdraw.setStatus(EnumUtil.YesNO.NO.toString());
        userWithdraw.setUserName(userName);
        BigDecimal userMoney = userinfo.getMoney();
        BigDecimal changeMoney = new BigDecimal(money);

        BigDecimal min = new BigDecimal(Global.getOption("system_user_set","minMoney"));
        BigDecimal max = new BigDecimal(Global.getOption("system_user_set","maxMoney"));
        BigDecimal poundage = new BigDecimal(Global.getOption("system_user_set","poundage"));
        BigDecimal multiple = new BigDecimal(Global.getOption("system_user_set","multiple"));

        if (EnumUtil.YesNO.NO.toString().equals(userinfo.getRenZheng())) {
            throw new ValidationException("实名认证后方可提现");
        }
        if (userWithdrawDao.findList(userWithdraw).size() > 0) {
            throw new ValidationException("有申请未审核,不能提现");
        }
        if (changeMoney.compareTo(min) < 0 || changeMoney.compareTo(max) > 0) {
            throw new ValidationException("提现金额必须在"+min+"-"+max+"之间!");
        }
        if (userMoney.compareTo(changeMoney) == -1) {
            throw new ValidationException("提现失败,余额不足");
        }
        if (!VerifyUtils.isInteger(changeMoney.divide(multiple))) {
            throw new ValidationException("提现金额必须为"+multiple+"的倍数!");
        }
        HashMap<String, Object> paraMap = Maps.newHashMap();
        paraMap.put("userBankId", userBankId);
//        UserUserinfoBank bankCode = userUserinfoBankService.get(userBankId);
//        String userBankAddr = userBankWithdraw.getAddress();
//        if (StringUtils2.isBlank(userBankAddr)) {
//            userBankAddr = StringUtils2.EMPTY;
//        }
//        paraMap.put("userBankAddress", userBankAddr);
        UserUserinfoBank bankCode = userUserinfoBankService.get(userBankId);
        String userBankAddr = bankCode.getBankUserAddress();
        String provinces = bankCode.getProvinces();
        String citys = bankCode.getCitys();
        String area = bankCode.getArea();
        if (StringUtils2.isBlank(userBankAddr)) {
            userBankAddr = StringUtils2.EMPTY;
        }
        if (StringUtils2.isBlank(provinces)) {
            provinces = StringUtils2.EMPTY;
        }
        if (StringUtils2.isBlank(citys)) {
            citys = StringUtils2.EMPTY;
        }
        if (StringUtils2.isBlank(area)) {
            area = StringUtils2.EMPTY;
        }
        paraMap.put("userBankAddress", userBankAddr);
        paraMap.put("provinces", provinces);
        paraMap.put("citys", citys);
        paraMap.put("area", area);
        this.updateUserMoney(userinfo.getUserName(),changeMoney.multiply(new BigDecimal(-1)), "用户提现", EnumUtil.MoneyChangeType.widthdraw,paraMap);


    }

    /**
     * 修改用户等级
     * @param leveId
     */
    public void userLevelUp(String leveId) {
        UserUserinfo userinfo = getByNameLock(UserInfoUtils.getUser().getUserName());
        UserLevel userLevel = userLevelService.findByLevalCode(leveId);
        BigDecimal levelMoney = userLevel.getMoney();
        if(userinfo.getMoney().compareTo(levelMoney)<0){
            throw new ValidationException("余额不足无法升级",1001);
        }
        HashMap<String, Object> paraMap = Maps.newHashMap();
        paraMap.put("leveId", leveId);
        updateUserMoney(userinfo.getUserName(),levelMoney.multiply(new BigDecimal(-1)),"用户升级",EnumUtil.MoneyChangeType.LEVEL_UP,paraMap);
        dao.updateUserLevelId(userinfo.getId(),leveId);
    }

    /**
     * 增量增加左区业绩量
     * @param userName
     * @param performance
     */
    public void updateUserMainPerformance(String userName, int performance){
        dao.updateUserMainPerformance(userName,performance);
    }


    /**
     * 增量增加右区业绩量
     * @param userName
     * @param performance
     */
    public void updateUserExtendPerformance(String userName, int performance){
        dao.updateUserExtendPerformance(userName,performance);
    }

    /**
     * 增量增加左区业绩量余量
     * @param userName
     * @param performance
     */
    public void updateUserMainRemainderPerformance(String userName, int performance){
        dao.updateUserMainRemainderPerformance(userName,performance);
    }


    /**
     * 增量增加右区业绩量余量
     * @param userName
     * @param performance
     */
    public void updateUserExtendRemainderPerformance(String userName, int performance){
        dao.updateUserExtendRemainderPerformance(userName,performance);
    }


    //判断用户b 是否 是用户A的团队下级
    public boolean isSubChild(String parentName , String subName){

        if(parentName.equals(subName)){
            return  true;
        }

        UserUserinfo subUser = this.getByName(subName);
        UserUserinfo parentUser = this.getByName(parentName);

        if( subUser.getParentList().contains("/" + parentUser.getId() + "/")){
            return true;
        }

        return false;
    }

    public void updateUserType(String userName,String userType){
        dao.updateUserType(userName,userType);
    }

    /**
     * 查询最大的Userno
     * @return
     */
    public int getMaxUserNo(){
        return  dao.getMaxUserNo();
    }

    public void updateUserZhuanqupass(String newPwd) {
        if (!VerifyUtils.isPassword(newPwd)) {
            throw new ValidationException("密码必须大于八位");
        }
        String newZhuanquPass = SystemService.entryptPassword(newPwd);
        dao.updateUserZhuanqupass(UserInfoUtils.getUser().getUserName(), newZhuanquPass);

    }

    public BigDecimal getTeamLevelMoney(UserUserinfo searchUser) {
        return  dao.getTeamLevelMoney(searchUser);
    }

    public BigDecimal getSumMoney2() {
        return dao.getSumMoney2();
    }

    public void updateUserNiceName(String userName, String nickName) {
        dao.updateUserNiceName(userName,nickName);
    }

    public List<UserUserinfo> findTeamByUserType(String parentListLike, int userType) {
        return dao.findTeamByUserType(parentListLike,userType);
    }

    public void updateUserIsUsercenter(String userName, String isUsercenter) {
        dao.updateUserIsUsercenter(userName,isUsercenter);
    }

    public void updateIdCard(String userName, String idCard) {
        if(IdcardUtils.validateCard(idCard)){
            dao.updateIdCard(userName,idCard);
        }else {
            throw new ValidationException("身份证号码不合法,请重新输入");
        }
    }

    public void updateUserLevelId(String userId , String userLevelId){
        dao.updateUserLevelId(userId ,userLevelId );
    }

    public void updateUserLoginInfo(UserUserinfo user) {

        // 更新本次登录信息
        user.setLastLoginIp(StringUtils2.getRemoteAddr(Servlets.getRequest()));
        user.setLastLoginTime(new Date());
        dao.updateLoginInfo(user);
    }
    public void updateShopId(String userName ,String shopId){
        dao.updateShopId(userName,shopId);
    }


    /**
     * 判断验证码是否正常
     * @param request
     * @return
     */
    public boolean checkValidateCode(HttpServletRequest request ){
        UserUserinfo userinfo = this.getByNameLock(UserInfoUtils.getUser().getUserName());
        Session session = UserInfoUtils.getSession();
        String code = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
        if (request.getParameter("vCode") == null || !request.getParameter("vCode").toUpperCase().equals(code)) {
           throw  new ValidationException("图文验证码错误!");
        }

        return  true;

    }

    /**
     * 销毁验证码
     * @param request
     */
    public void disableValidateCode(HttpServletRequest request){

        request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());

    }

    public void updateActiveStatus(String userName ,String status){
        dao.updateActiveStatus(userName,status);
    }
}