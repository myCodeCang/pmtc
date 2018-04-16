package com.thinkgem.jeesite.website.task;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.modules.tasks.entity.UserTaskBusiness;
import com.thinkgem.jeesite.modules.user.entity.TempTrans;
import com.thinkgem.jeesite.modules.user.entity.UserLevel;
import com.thinkgem.jeesite.modules.user.entity.UserTeamLevel;
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
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Lazy(false)
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class AllBonusTask implements UserTaskBusiness {

    private Logger logger ;

    @Resource
    private UserUserinfoService userUserinfoService;
    @Resource
    private UserTeamLevelService userTeamLevelService;
    @Resource
    private UserLccService userLccService;
    @Resource
    private UserLevelService userLevelService;
    @Resource
    private TempTransService tempTransService;

    public AllBonusTask(){

        logger = getLogger();
    }

    @Override
    public boolean doBusiness() {

        String openCalculate = Global.getConfig("isOpenCalculate");
        if("".equals(openCalculate) || "false".equals(openCalculate)){
            return false;
        }

        levelCount();
        algebraBonus();
        multiplierBonus();
        leaderBonus();
        teamBonus();
        return true;
    }

    private boolean algebraBonus() {
        logger.error("代数奖starting..............");
        if(userLccService.isClosedBeta()){
            logger.error("该功能未开放");
            return false;
        }
        // 代数奖励
//        userTeamLevelService.findList()
        UserUserinfo seachUser= new UserUserinfo();
        seachUser.setActiveStatus(EnumUtil.YesNO.YES.toString());
        seachUser.setOrderBy("a.level_no Desc");
        List<UserUserinfo> userinfoList = userUserinfoService.findList(seachUser);

        UserTeamLevel select = new UserTeamLevel();
        select.setOrderBy("a.condition_one DESC");
        List<UserTeamLevel> teamLevelList = userTeamLevelService.findList(select);

        for(UserUserinfo bonusUser : userinfoList){
            try{
                if(bonusUser == null){
                    continue;
                }
                //判断用户是否冻结
                if(EnumUtil.UserStatusEnum.status_freeze.toString().equals(bonusUser.getShopId())){
                    //logger.error("奖励发送失败,失败用户:"+bonusUser.getUserName()+"失败原因:该用户已冻结，暂停发奖");
                    continue;
                }
                UserUserinfo parentUser = userUserinfoService.getByNameLock(bonusUser.getParentName());
                if(parentUser == null){
                    continue;
                }

                BigDecimal money2Sum = bonusUser.getMoney2().compareTo(parentUser.getMoney2())>0?parentUser.getMoney2():bonusUser.getMoney2();
                if(money2Sum.compareTo(BigDecimal.valueOf(10))<0){
                    continue;
                }
                Optional<UserTeamLevel> levelOptional = teamLevelList.stream().filter(p ->money2Sum.compareTo(p.getConditionOne()) >= 0).findFirst();
                if (levelOptional.isPresent()) {
                    UserTeamLevel userTeamLevel = levelOptional.get();
                    BigDecimal bonus = money2Sum.multiply(new BigDecimal(userTeamLevel.getEveryEarnings())).multiply(new BigDecimal(userTeamLevel.getIndirectEarnings())).setScale(2, BigDecimal.ROUND_HALF_UP);
                    try {
                        BigDecimal money2Check = userLccService.userMoney2Check(bonusUser.getUserName());
                        if(bonus.compareTo(money2Check) >0 ){
                            bonus = money2Check;
                        }
                        userLccService.updateUserOtherMoney(bonusUser.getUserName(), bonus, EnumUtil.MoneyType.money2, "每日代数奖:", EnumUtil.MoneyChangeType.secondTouch);
                    } catch (ValidationException e) {
                        logger.error("奖励发送失败,失败用户:"+bonusUser.getUserName()+"失败原因:"+e.getMessage());
                        continue;
                    }
                }
            }catch (Exception e){
                logger.error("奖励发送失败,失败用户:"+bonusUser.getUserName()+"失败原因:"+e.getMessage());
            }

        }


        logger.error("代数奖ending................");

        return  true;
    }

    private boolean levelCount() {
        logger.error("等级计算starting..............");
//        userTeamLevelService.findList()
        UserUserinfo seachUser= new UserUserinfo();
        seachUser.setActiveStatus(EnumUtil.YesNO.YES.toString());
        List<UserUserinfo> userinfoList = userUserinfoService.findList(seachUser);


        //用户等级
        UserTeamLevel select = new UserTeamLevel();
        select.setOrderBy("a.condition_one DESC");
        List<UserTeamLevel> teamLevelList = userTeamLevelService.findList(select);

        //团队等级
        UserLevel selectLevel = new UserLevel();
        selectLevel.setOrderBy("a.static_money desc");
        List<UserLevel> userLevelList = userLevelService.findList(selectLevel);

        //用户等级上限
        UserTeamLevel maxLevel = teamLevelList.get(0);
        UserTeamLevel minLevel = teamLevelList.get(teamLevelList.size()-1);
        int maxCode = 0;
        int minCode = 0;
        if(maxLevel != null){
            maxCode = Integer.parseInt(maxLevel.getTeamCode());
        }
        if(minLevel != null){
            minCode = Integer.parseInt(minLevel.getTeamCode());
        }

        for(UserUserinfo upUser : userinfoList){

            try {
                //升级 用户等级  userType
                Optional<UserTeamLevel> levelOptional = teamLevelList.stream().filter(p -> upUser.getMoney2().compareTo(p.getConditionOne()) >= 0).findFirst();
                if (levelOptional.isPresent()) {
                    UserTeamLevel userTeamLevel = levelOptional.get();
                    int levelCode = Integer.parseInt(userTeamLevel.getTeamCode())+upUser.getMainPerformance();
                    if(levelCode > maxCode){
                        levelCode =  maxCode;
                    }
                    if(levelCode < minCode){
                        levelCode = minCode;
                    }
                    userUserinfoService.updateUserType(upUser.getUserName(),String.valueOf(levelCode));
                }

                //升级 团队等级 userLevelId
                int levelNum;
                BigDecimal levelMoney;
                try {
                    levelNum = Integer.parseInt(Global.getOption("system_help","teamLevel_ztNum","20"));
                    levelMoney =  new BigDecimal(Global.getOption("system_help","teamLevel_money","10000"));
                } catch (NumberFormatException e) {
                    logger.error("团队升级限制配置错误");
                    return false;
                }
                String upCode;
                int ztNum = userinfoList.stream().filter(p -> p.getWalterName().toLowerCase().trim().equals(upUser.getUserName().toLowerCase().trim()) && p.getMoney2().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList()).size();
                if (ztNum < levelNum || upUser.getMoney2().compareTo(levelMoney) < 0 ){
                    userUserinfoService.updateUserLevelId(upUser.getId(),"1");
                }else {
                    BigDecimal teamMoney = userinfoList.stream().filter(p -> p.getParentList().indexOf("/"+upUser.getId()+"/") > -1).map(UserUserinfo::getMoney2).reduce(BigDecimal.ZERO, BigDecimal::add);
                    Optional<UserLevel> userLevelOptional = userLevelList.stream().filter(p -> teamMoney.add(upUser.getMoney2()).compareTo(p.getStaticMoney().multiply(BigDecimal.valueOf(10000))) >= 0).findFirst();
                    if(userLevelOptional.isPresent()){
                        UserLevel userLevel = userLevelOptional.get();
                        userUserinfoService.updateUserLevelId(upUser.getId(),userLevel.getLevelCode());
                    }
                }
            }catch (Exception e){
                logger.error(e.getMessage());
            }

        }


        logger.error("等级计算ending................");

        return  true;
    }

    private boolean multiplierBonus() {
        logger.error("复利奖starting..............");
        // 倍增奖励
        if(userLccService.isClosedBeta()){
            logger.error("该功能未开放");
            return false;
        }
        UserUserinfo seachUser= new UserUserinfo();
        seachUser.setActiveStatus(EnumUtil.YesNO.YES.toString());
        List<UserUserinfo> userinfoList = userUserinfoService.findList(seachUser);

        UserTeamLevel select = new UserTeamLevel();
        select.setOrderBy("a.condition_one DESC");
        List<UserTeamLevel> teamLevelList = userTeamLevelService.findList(select);

        for(UserUserinfo bonusUser : userinfoList){

            try{
                if(bonusUser == null){
                    continue;
                }
                //判断用户是否冻结
                if(EnumUtil.UserStatusEnum.status_freeze.toString().equals(bonusUser.getShopId())){
                    //logger.error("奖励发送失败,失败用户:"+bonusUser.getUserName()+"失败原因:该用户已冻结，暂停发奖");
                    continue;
                }
                if(bonusUser.getMoney2().compareTo(BigDecimal.valueOf(10))<0){
                    continue;
                }
                Optional<UserTeamLevel> levelOptional = teamLevelList.stream().filter(p -> bonusUser.getMoney2().compareTo(p.getConditionOne()) >= 0).findFirst();
                if (levelOptional.isPresent()) {
                    UserTeamLevel userTeamLevel = levelOptional.get();
                    BigDecimal bonus = bonusUser.getMoney2().multiply(new BigDecimal(userTeamLevel.getEveryEarnings())).setScale(2, BigDecimal.ROUND_HALF_UP);
                    try {
                        BigDecimal money2Check = userLccService.userMoney2Check(bonusUser.getUserName());
                        if(bonus.compareTo(money2Check) >0 ){
                            bonus = money2Check;
                        }
                        userLccService.updateUserOtherMoney(bonusUser.getUserName(), bonus, EnumUtil.MoneyType.money2, "每日复利" + bonusUser.getUserName(), EnumUtil.MoneyChangeType.FuxiaoMoney);
                    } catch (ValidationException e) {
                        logger.error("奖励发送失败,失败用户:"+bonusUser.getUserName()+"失败原因:"+e.getMessage());
                        continue;
                    }
                }
            }catch (Exception e){
                logger.error("奖励发送失败,失败用户:"+bonusUser.getUserName()+"失败原因:"+e.getMessage());
            }

        }


        logger.error("复利奖ending................");

        return  true;
    }

    private boolean leaderBonus() {
        logger.error("领导奖starting..............");
        if(userLccService.isClosedBeta()){
            logger.error("该功能未开放");
            return false;
        }
        // 领导奖
        UserUserinfo seachUser= new UserUserinfo();
        seachUser.setActiveStatus(EnumUtil.YesNO.YES.toString());
        List<UserUserinfo> userinfoList = userUserinfoService.findList(seachUser);

        UserTeamLevel select = new UserTeamLevel();
        select.setOrderBy("a.condition_one DESC");
        List<UserTeamLevel> teamLevelList = userTeamLevelService.findList(select);

        for(UserUserinfo bonusUser : userinfoList){

            try{
                if(bonusUser == null){
                    continue;
                }
                //判断用户是否冻结
                if(EnumUtil.UserStatusEnum.status_freeze.toString().equals(bonusUser.getShopId())){
                    //logger.error("奖励发送失败,失败用户:"+bonusUser.getUserName()+"失败原因:该用户已冻结，暂停发奖");
                    continue;
                }
                if(bonusUser.getMoney2().compareTo(BigDecimal.valueOf(10))<0){
                    continue;
                }
                int ztNum = userinfoList.stream().filter(p -> p.getWalterName().toLowerCase().trim().equals(bonusUser.getUserName().toLowerCase().trim()) && p.getMoney2().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList()).size();
                Optional<UserTeamLevel> levelOptional = teamLevelList.stream().filter(
                        p -> (Integer.parseInt(p.getTeamCode()) <= Integer.parseInt(bonusUser.getUserType())) && ztNum >= p.getDirectPeopleNum()
                ).findFirst();
                if (levelOptional.isPresent()) {
                    UserTeamLevel userTeamLevel = levelOptional.get();
                    int bonusNo = userTeamLevel.getIndirectLevelno();
                    BigDecimal teamMoney = userinfoList.stream().filter(p -> (p.getLevelNo() <= bonusUser.getLevelNo() + bonusNo) && p.getParentList().indexOf("/"+bonusUser.getId()+"/") > -1 ).map(UserUserinfo::getMoney2).reduce(BigDecimal.ZERO, BigDecimal::add);
                    try {
                        userLccService.updateUserOtherMoney(bonusUser.getUserName(), teamMoney.multiply(new BigDecimal(userTeamLevel.getCompoundInterest())), EnumUtil.MoneyType.money3, "每日领导奖" , EnumUtil.MoneyChangeType.adminMoney);
                    } catch (ValidationException e) {
                        logger.error("奖励发送失败,失败用户:"+bonusUser.getUserName()+"失败原因:"+e.getMessage());
                        continue;
                    }
                }
            }catch (Exception e){
                logger.error("奖励发送失败,失败用户:"+bonusUser.getUserName()+"失败原因:"+e.getMessage());
            }

        }


        logger.error("领导奖ending................");

        return  true;
    }

    private boolean teamBonus() {
        logger.error("团队奖starting..............");
        if(userLccService.isClosedBeta()){
            logger.error("该功能未开放");
            return false;
        }
        // 团队人数
        UserUserinfo seachUser= new UserUserinfo();
        seachUser.setActiveStatus(EnumUtil.YesNO.YES.toString());
        List<UserUserinfo> userinfoList = userUserinfoService.findList(seachUser);

        //团队等级
        UserLevel selectLevel = new UserLevel();
        selectLevel.setOrderBy("a.static_money desc");
        List<UserLevel> userLevelList = userLevelService.findList(selectLevel);

        //层级配置
        int noBegin;
        try {
            noBegin = Integer.parseInt(Global.getOption("system_help","teamBonus_noBegin"));
        } catch (NumberFormatException e) {
            logger.error("层级配置错误");
            return false;
        }
        tempTransService.clearTable();
        for (UserUserinfo userinfo:userinfoList){

            try {
                if(userinfo == null){
                    continue;
                }
                //判断用户是否冻结
                if(EnumUtil.UserStatusEnum.status_freeze.toString().equals(userinfo.getShopId())){
                    //logger.error("奖励发送失败,失败用户:"+userinfo.getUserName()+"失败原因:该用户已冻结，暂停发奖");
                    continue;
                }
                Optional<UserLevel> userOptional = userLevelList.stream().filter(p -> p.getLevelCode().equals(userinfo.getUserLevelId())).findFirst();
                if (!userOptional.isPresent()){
                    continue;
                }
                UserLevel userLevel = userOptional.get();
                if ("1".equals(userLevel.getLevelCode())){
                    continue;
                }
                BigDecimal sonScale = userLevel.getAmountPer().divide(BigDecimal.valueOf(1000),5,BigDecimal.ROUND_HALF_UP);

                BigDecimal money = userinfoList.stream().filter(p ->(p.getLevelNo() > userinfo.getLevelNo()+noBegin) && (p.getParentList().indexOf("/"+userinfo.getId()+"/") > -1)).map(UserUserinfo::getMoney2).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal bonus = money.multiply(sonScale).setScale(2,BigDecimal.ROUND_HALF_UP);
                if (bonus.compareTo(BigDecimal.ZERO) == 0){
                    continue;
                }
                tempTransService.updateMoneyByName(userinfo.getUserName(),bonus);
                //处理 直属父级
                UserUserinfo parentUser = userUserinfoService.getByName(userinfo.getParentName());
                if (parentUser == null){
                    continue;

                }
                tempTransService.updateMoneyByName(parentUser.getUserName(),bonus.multiply(BigDecimal.valueOf(-1)));
            }catch (Exception e){
                logger.error(e.getMessage());
            }

        }

        //发奖励
        List<TempTrans> bonusList =  tempTransService.findList(new TempTrans());
        for(TempTrans bonusUser: bonusList) {
            try {
                if (bonusUser.getMoney().compareTo(BigDecimal.ZERO) <= 0){
                    continue;
                }
                userLccService.updateUserOtherMoney(bonusUser.getUserName(), bonusUser.getMoney(), EnumUtil.MoneyType.money3,"团队奖励", EnumUtil.MoneyChangeType.PUT_TEAM_AWARD);

            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }

        logger.error("团队奖ending................");

        return  true;
    }
}