package com.thinkgem.jeesite.website.task;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.tasks.entity.UserTaskBusiness;
import com.thinkgem.jeesite.modules.user.dao.UserUserinfoDao;
import com.thinkgem.jeesite.modules.user.entity.UserAccountchange;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.*;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
@Lazy(false)
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserReportTask implements UserTaskBusiness {


    @Resource
    private UserReportService userReportService;

    @Resource
    private UserAccountchangeService userAccountchangeService;

    @Resource
    private UserMoneyReportService userMoneyReportService;

    @Resource
    private UserUserinfoDao userinfoDao ;

    @Resource
    private SystemReportService systemReportService ;

    protected Logger logger = null;
    @Override
    public boolean doBusiness() {
        logger = getLogger();

        String openCalculate = Global.getConfig("isOpenCalculate");
        if("".equals(openCalculate) || "false".equals(openCalculate)){
            return false;
        }

        logger.error("start..................");
        /**
         * 更新平台各钱包总量统计
         */

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss") ;
        String time = dateFormat.format(new Date());
        if("23:50:00".compareTo(time)<0){
            UserUserinfo userinfo = userinfoDao.sumUserEveryMoney();
            if(userinfo != null){
                systemReportService.updateSystemReportByType("1",userinfo.getMoney());
                systemReportService.updateSystemReportByType("2",userinfo.getMoney2());
                systemReportService.updateSystemReportByType("3",userinfo.getMoney3());
                systemReportService.updateSystemReportByType("4",userinfo.getMoney4());
            }
        }

        // 充值提现统计
        UserAccountchange accountchange = new UserAccountchange();
        accountchange.setIscheck (EnumUtil.YesNO.NO.toString ());
        accountchange.setTopLimit(200);
        List<UserAccountchange> accountchangeList = userAccountchangeService.findList (accountchange);

       // List<UserAccountchange> collect = accountchangeList.stream().filter(p -> p.getMoneyType().equals(EnumUtil.MoneyType.money.toString()) || p.getMoneyType().equals(EnumUtil.MoneyType.money5.toString())).collect(Collectors.toList());
        for (UserAccountchange userchange : accountchangeList) {
              BigDecimal bonus = BigDecimal.ZERO;
              BigDecimal kuangBonus = BigDecimal.ZERO;
              BigDecimal dongBonus = BigDecimal.ZERO;
              BigDecimal shouXu = BigDecimal.ZERO;

//            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.JiantuiMoney.toString())){
//                userReportService.updateUserReportByType(userchange.getUserName (),"3" ,new BigDecimal (userchange.getChangeMoney ()));
//            }
            //转账(入)
            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.UPDATE_MONEY_BYTRANS.toString())){
                userMoneyReportService.updateUserMoneyReport(userchange.getUserName (),"2" ,new BigDecimal (userchange.getChangeMoney ()));
            }
            //转账(出)
            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.MONEY_TRANS_OUT.toString())){
                userMoneyReportService.updateUserMoneyReport(userchange.getUserName (),"3" ,new BigDecimal (userchange.getChangeMoney ()).multiply(BigDecimal.valueOf(-1)));
            }
            //充值(交易钱包)
            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.charget.toString())&&userchange.getMoneyType ().equals(EnumUtil.MoneyType.money.toString())){
                systemReportService.updateSystemReportByType("11",new BigDecimal(userchange.getChangeMoney()));
            }
            //充值(矿机钱包)
            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.charget.toString())&&userchange.getMoneyType ().equals(EnumUtil.MoneyType.money2.toString())){
                systemReportService.updateSystemReportByType("12",new BigDecimal(userchange.getChangeMoney())); }
            //充值(动态钱包)
            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.charget.toString())&&userchange.getMoneyType ().equals(EnumUtil.MoneyType.money3.toString())){
                systemReportService.updateSystemReportByType("13",new BigDecimal(userchange.getChangeMoney()));
            }

            //直推收益
            if (userchange.getChangeType ().equals (EnumUtil.MoneyChangeType.spread.toString ())) {
                userReportService.updateUserReportByType (userchange.getUserName (), "2", new BigDecimal (userchange.getChangeMoney ()));
                userMoneyReportService.updateUserMoneyReport(userchange.getUserName (),"1",new BigDecimal (userchange.getChangeMoney ()));
            }
            //复利 进矿机
            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.FuxiaoMoney.toString())){
                bonus = bonus.add(new BigDecimal(userchange.getChangeMoney()));
                kuangBonus = kuangBonus.add(new BigDecimal(userchange.getChangeMoney()));
                userMoneyReportService.updateUserMoneyReport(userchange.getUserName (),"1",new BigDecimal (userchange.getChangeMoney ()));
                userReportService.updateUserReportByType(userchange.getUserName (),"6" ,new BigDecimal (userchange.getChangeMoney ()));
            }
            //代数奖 进矿机
            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.secondTouch.toString())){
                bonus = bonus.add(new BigDecimal(userchange.getChangeMoney()));
                kuangBonus = kuangBonus.add(new BigDecimal(userchange.getChangeMoney()));
                userMoneyReportService.updateUserMoneyReport(userchange.getUserName (),"1",new BigDecimal (userchange.getChangeMoney ()));
                userReportService.updateUserReportByType(userchange.getUserName (),"7" ,new BigDecimal (userchange.getChangeMoney ()));
            }
            //领导奖励
            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.adminMoney.toString())){
                userReportService.updateUserReportByType(userchange.getUserName (),"8" ,new BigDecimal (userchange.getChangeMoney ()));
                userMoneyReportService.updateUserMoneyReport(userchange.getUserName (),"1",new BigDecimal (userchange.getChangeMoney ()));
            }
            //团队奖励
            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.PUT_TEAM_AWARD.toString())){
                userReportService.updateUserReportByType(userchange.getUserName (),"10" ,new BigDecimal (userchange.getChangeMoney ()));
                userMoneyReportService.updateUserMoneyReport(userchange.getUserName (),"1",new BigDecimal (userchange.getChangeMoney ()));
            }

            //动态钱包进账
            if (EnumUtil.MoneyType.money3.toString().equals(userchange.getMoneyType())) {

                //直推推收益
                if (userchange.getChangeType ().equals (EnumUtil.MoneyChangeType.spread.toString ()) && userchange.getMoneyType().equals(EnumUtil.MoneyType.money3.toString())) {
                    bonus = bonus.add(new BigDecimal(userchange.getChangeMoney()));
                    dongBonus = dongBonus.add(new BigDecimal(userchange.getChangeMoney()));
                }
                //领导奖励
                if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.adminMoney.toString())){
                    bonus = bonus.add(new BigDecimal(userchange.getChangeMoney()));
                    dongBonus = dongBonus.add(new BigDecimal(userchange.getChangeMoney()));
                }
                //团队奖励
                if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.PUT_TEAM_AWARD.toString())){
                    bonus = bonus.add(new BigDecimal(userchange.getChangeMoney()));
                    dongBonus = dongBonus.add(new BigDecimal(userchange.getChangeMoney()));

                }
            }

            //投资收益
            if(userchange.getMoneyType ().equals(EnumUtil.MoneyType.money4.toString())){
                systemReportService.updateSystemReportByType("7",new BigDecimal(userchange.getChangeMoney()));
            }

            try {
                BigDecimal poundage = new BigDecimal(Global.getOption("system_help", "poundage_wallet", "0.1"));

                //手续费
                if(userchange.getMoneyType().equals(EnumUtil.MoneyType.money2.toString()) && userchange.getChangeType().equals(EnumUtil.MoneyChangeType.TRANSFER_MONEY2_IN.toString())){
                    shouXu = shouXu.add(new BigDecimal(userchange.getChangeMoney()).multiply(poundage.multiply(new BigDecimal(-1))));
                }
                if(userchange.getMoneyType().equals(EnumUtil.MoneyType.money3.toString()) && userchange.getChangeType().equals(EnumUtil.MoneyChangeType.TRANSFER_MONEY3.toString())){
                    shouXu = shouXu.add(new BigDecimal(userchange.getChangeMoney()).multiply(poundage.multiply(new BigDecimal(-1))));
                }
            } catch (Exception e) {
                logger.error("手续费配置有误");
            }


            systemReportService.updateSystemReportByType("8",shouXu);
            //转入交易系统
            if(userchange.getChangeType().equals(EnumUtil.MoneyChangeType.MONEY_TRANS_OUT.toString())){
                systemReportService.updateSystemReportByType("10",new BigDecimal(userchange.getChangeMoney()).multiply(BigDecimal.valueOf(-1)));
            }
            //交易转入会员
            if(userchange.getChangeType().equals(EnumUtil.MoneyChangeType.UPDATE_MONEY_BYTRANS.toString())){
                systemReportService.updateSystemReportByType("9",new BigDecimal(userchange.getChangeMoney()));
            }
            //奖金收益
            if (bonus.compareTo(BigDecimal.ZERO) > 0){
                userReportService.updateUserReportByType(userchange.getUserName (),"11" ,bonus);
            }
            //矿机收益
            if (kuangBonus.compareTo(BigDecimal.ZERO) > 0){
                userReportService.updateUserReportByType(userchange.getUserName (),"12" ,kuangBonus);
                systemReportService.updateSystemReportByType("6",new BigDecimal(userchange.getChangeMoney()));
            }
            //动态收益
            if (dongBonus.compareTo(BigDecimal.ZERO) > 0){
                userReportService.updateUserReportByType(userchange.getUserName (),"13" ,dongBonus);
                systemReportService.updateSystemReportByType("5",new BigDecimal(userchange.getChangeMoney()));
            }
            userchange.setIscheck (EnumUtil.YesNO.YES.toString ());
            userAccountchangeService.save (userchange);
        }
        logger.error("end..................");

        return true;
    }




}
