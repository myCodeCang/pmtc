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
    private UserUserinfoDao userUserinfoDao;

    @Resource
    private SystemReportService systemReportService;

    protected Logger logger = null;
    @Override
    public boolean doBusiness() {

        String openCalculate = Global.getConfig("isOpenCalculate");
        if("".equals(openCalculate) || "false".equals(openCalculate)){
            return false;
        }

        logger = getLogger();
        logger.error("start..................");

        // 充值提现统计
        UserAccountchange accountchange = new UserAccountchange();
        accountchange.setMoneyType(EnumUtil.MoneyType.money.toString());
        accountchange.setIscheck (EnumUtil.YesNO.NO.toString ());
        List<UserAccountchange> accountchangeList = userAccountchangeService.findList (accountchange);
        UserUserinfo userUserinfo =  userUserinfoDao.sumUserEveryMoney();
        if (userUserinfo != null){
            systemReportService.replaceSystemReportByType("","1" ,userUserinfo.getMoney());
        }
        for (UserAccountchange userchange : accountchangeList) {

            //转账(从会员入)
            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.MONEY_TRANS_IN.toString()) ){
                systemReportService.updateSystemReportByType("","2" ,new BigDecimal (userchange.getChangeMoney ()));
            }
            //转账(出)
            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.moneyToShop.toString())){
                systemReportService.updateSystemReportByType("","3" ,new BigDecimal (userchange.getChangeMoney ()));
            }
            //保证金
            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.AUCTION_GUARANTEE.toString())){
                systemReportService.updateSystemReportByType("","4" ,new BigDecimal (userchange.getChangeMoney ()));
            }
            //手续费
            if(userchange.getChangeType ().equals(EnumUtil.MoneyChangeType.MdTrade_FEE.toString())){
                systemReportService.updateSystemReportByType("","5" ,new BigDecimal (userchange.getChangeMoney ()));
            }
            userchange.setIscheck (EnumUtil.YesNO.YES.toString ());
            userAccountchangeService.save (userchange);
        }
        logger.error("end..................");

        return true;
    }




}
