package com.thinkgem.jeesite.website.task;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.DateUtils2;
import com.thinkgem.jeesite.modules.tasks.entity.UserTaskBusiness;
import com.thinkgem.jeesite.modules.user.entity.*;
import com.thinkgem.jeesite.modules.user.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Lazy(false)
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class TransPriceLogAutoTask implements UserTaskBusiness {

    @Resource
    private TranscodePriceDaylogService transcodePriceDaylogService;
    @Resource
    private UserUCApiService userUCApiService ;

    private Logger logger ;

    public TransPriceLogAutoTask(){

        logger = getLogger();
    }

    @Override
    public boolean doBusiness() {

        String openCalculate = Global.getConfig("isOpenCalculate");
        if("".equals(openCalculate) || "false".equals(openCalculate)){
            return false;
        }

        logger.error("starting..............");
        TranscodePriceDaylog nowLog = transcodePriceDaylogService.getNowLog();
        if (nowLog == null){
            logger.error("未插入第一条记录");
            return false;
        }
        if (DateUtils2.isNow(nowLog.getCreateDate())){
            logger.error("以手动插入,无须自动跟新");
            return false;
        }
        BigDecimal up;
        try {
            up = new BigDecimal(Global.getOption("system_trans","trans_autoUp","1"));
        } catch (Exception e) {
            logger.error("获取涨幅失败");
            return false;
        }
        TranscodePriceDaylog newLog = new TranscodePriceDaylog();
        newLog.setAmount(BigDecimal.ZERO);
        newLog.setStartMoney(nowLog.getNowMoney());
        newLog.setNowMoney(nowLog.getNowMoney().add(up));
        transcodePriceDaylogService.save(newLog);


        try {
            userUCApiService.updateNowPrice(newLog.getNowMoney());
        } catch (Exception e) {
            logger.error("修改价格失败"+e.getMessage());
        }


        logger.error("ending................");

        return  true;
    }
}