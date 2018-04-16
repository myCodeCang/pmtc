package com.thinkgem.jeesite.website.task;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.DateUtils2;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.config.EnumTransUtil;
import com.thinkgem.jeesite.modules.tasks.entity.UserTaskBusiness;
import com.thinkgem.jeesite.modules.user.entity.TranscodeBuy;
import com.thinkgem.jeesite.modules.user.entity.TranscodePriceDaylog;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.*;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Lazy(false)
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class TransTask implements UserTaskBusiness {

    @Resource
    private TranscodeBuyService transcodeBuyService;
    @Resource
    private TranscodeBuyLogService transcodeBuyLogService;
    @Resource
    private UserUserinfoService userUserinfoService;
    @Resource
    private TranscodePriceDaylogService transcodePriceDaylogService;
    @Resource
    private UserLccService userLccService;
    private Logger logger ;

    public TransTask(){

        logger = getLogger();
    }

    @Override
    public boolean doBusiness() {

        String openCalculate = Global.getConfig("isOpenCalculate");
        if("".equals(openCalculate) || "false".equals(openCalculate)){
            return false;
        }

        logger.error("starting..............");
        //取出买卖队列
        TranscodeBuy transcodeBuy = new TranscodeBuy();
        transcodeBuy.setStatus(EnumTransUtil.TransBuyStatus.Selling.toString());
        transcodeBuy.setOrderBy("a.sort ASC , a.create_date ASC");
        List<TranscodeBuy> listLock = transcodeBuyService.findListLock(transcodeBuy);
        List<TranscodeBuy> sellList = listLock.stream().filter(p -> EnumUtil.TransCodeBuyType.sell.toString().equals(p.getType())).collect(Collectors.toList());
        List<TranscodeBuy> buyList = listLock.stream().filter(p -> EnumUtil.TransCodeBuyType.buy.toString().equals(p.getType())).collect(Collectors.toList());
        try {
            Date saleTime = DateUtils2.formatStrTime(Global.getOption("system_trans", "auto_sale_out"));;
            Date nowtime = DateUtils2.formatStrTime(DateUtils2.getTime());
            if (nowtime.getTime() >= saleTime.getTime()){
                //执行下架
                for (TranscodeBuy saleOut:listLock){
                    try {
                        transcodeBuyService.saleOut(saleOut.getUserName(),saleOut.getId(),EnumTransUtil.TransBuyStatus.downAuto);
                    } catch (Exception e) {
                        logger.error("自动撤单失败,失败单号:"+saleOut.getId());
                        continue;
                    }
                }
                logger.error("自动撤单完成");
                return true;
            }
        } catch (Exception e) {
            logger.error("自动撤单失败");
        }

        for (TranscodeBuy sell:sellList){
            BigDecimal needNum = sell.getNowNum();

            for (TranscodeBuy buy:buyList){
                if (needNum.compareTo(BigDecimal.ZERO) <= 0){
                    break;
                }
                if (!EnumTransUtil.TransBuyStatus.Selling.toString().equals(buy.getStatus())){
                    continue;
                }
                //买家正在交易的数量
                BigDecimal nowNum = buy.getNowNum();

                //本次匹配成功的数量默认等于卖家卖的数量
                BigDecimal transNum = needNum;

                //买家没有可交易数量--跳过
                if (nowNum.compareTo(BigDecimal.ZERO) <= 0){

                    buy.setStatus(EnumTransUtil.TransBuyStatus.Selled.toString());
                    transcodeBuyService.updateNowNumAndStatus(buy.getId(),buy.getNowNum(),buy.getStatus());
                    continue;
                }

                needNum = needNum.subtract(nowNum);
                if (needNum.compareTo(BigDecimal.ZERO) >= 0){
                    //处理队列中的状态
                    buy.setNowNum(BigDecimal.ZERO);
                    transNum = nowNum;
                    buy.setStatus(EnumTransUtil.TransBuyStatus.Selled.toString());
                }else{
                    buy.setNowNum(needNum.multiply(BigDecimal.valueOf(-1)));
                }
                //处理实际状态
                try {
                    transcodeBuyService.updateNowNumAndStatus(buy.getId(),buy.getNowNum(),buy.getStatus());
                    //插入匹配订单
                    transcodeBuyLogService.createBuyLog(sell.getUserName(),sell.getId(),buy.getUserName(),buy.getId(),transNum,sell.getMoney());

                    //发送短信
                    UserUserinfo byName = userUserinfoService.getByName(buy.getUserName());
                    UserUserinfo sellUser = userUserinfoService.getByName(sell.getUserName());
                    String msgTemplate = Global.getOption("system_trans", "transMes");
                    if (StringUtils2.isNotBlank(msgTemplate)) {
                        userLccService.pushTransMessage(byName.getMobile(), new Date(), msgTemplate);
                        userLccService.pushTransMessage(sellUser.getMobile(), new Date(), msgTemplate);
                    }

                } catch (Exception e) {
                    logger.error("撮合异常卖:"+sell.getId() +"买:"+buy.getId(),e);
                }
                if (needNum.compareTo(BigDecimal.ZERO) <= 0){
                    break;
                }
                continue;
            }

            try {
                if (needNum.compareTo(BigDecimal.ZERO) <= 0){
                    //全部卖出
                    sell.setNowNum(BigDecimal.ZERO);
                    sell.setStatus(EnumTransUtil.TransBuyStatus.Selled.toString());


                }else {
                    sell.setNowNum(needNum);
                }
                transcodeBuyService.updateNowNumAndStatus(sell.getId(),sell.getNowNum(),sell.getStatus());
            } catch (Exception e) {
                logger.error("撮合异常买:"+sell.getId());
                continue;
            }
            continue;
        }

        logger.error("ending................");
        return  true;
    }
}