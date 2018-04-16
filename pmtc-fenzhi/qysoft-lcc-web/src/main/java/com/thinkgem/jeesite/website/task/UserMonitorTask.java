package com.thinkgem.jeesite.website.task;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.tasks.entity.UserTaskBusiness;
import com.thinkgem.jeesite.modules.user.entity.UserAccountchange;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.UserAccountchangeService;
import com.thinkgem.jeesite.modules.user.service.UserMoneyReportService;
import com.thinkgem.jeesite.modules.user.service.UserReportService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
@Service
@Lazy(false)
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserMonitorTask implements UserTaskBusiness {


    @Resource
    private UserReportService userReportService;

    @Resource
    private UserUserinfoService userUserinfoService;

    @Resource
    private UserAccountchangeService userAccountchangeService;

    @Resource
    private UserMoneyReportService userMoneyReportService;

    protected Logger logger = null;
    @Override
    public boolean doBusiness() {
        logger = getLogger();

        String openCalculate = Global.getConfig("isOpenCalculate");
        if("".equals(openCalculate) || "false".equals(openCalculate)){
            return false;
        }

        logger.error("start..................");

        // 充值提现统计
        UserAccountchange accountchange = new UserAccountchange();
        accountchange.setIsMonCheck (EnumUtil.YesNO.NO.toString ());
        accountchange.setTopLimit(200);
        accountchange.setChangeType("41");
        accountchange.setMoneyType("1");
        accountchange.setChangeMoneyEnd(BigDecimal.ZERO);
        accountchange.setOrderBy("id desc");
        List<UserAccountchange> accountchangeList = userAccountchangeService.findList (accountchange);

        for (UserAccountchange userchange : accountchangeList) {

            try{
                String commit = userchange.getCommt();
                Pattern p = Pattern.compile("转账给(.*)");
                Matcher m = p.matcher(commit);
                ArrayList<String> strs = new ArrayList<String>();

                while (m.find()) {
                    strs.add(m.group(1));
                }


                String targetUserName = strs.get(0).trim();
                String fromeUserName = userchange.getUserName().trim();

                UserUserinfo target = userUserinfoService.getByName(targetUserName);
                UserUserinfo from = userUserinfoService.getByName(fromeUserName);

                //查询是否同团队内
                UserUserinfo searchUser = new UserUserinfo();
                searchUser.setParentListLike(from.getId());
                searchUser.setUserName(target.getUserName());


                //被转人是自己的下级
                List<UserUserinfo> userList = userUserinfoService.findList(searchUser);
                if (userList.size() > 0) {
                    userMoneyReportService.updateUserMoneyReport(from.getUserName(), "6", new BigDecimal(userchange.getChangeMoney()).multiply(new BigDecimal("-1")));

                } else {
                    //被转人是自己的上级
                    searchUser = new UserUserinfo();
                    searchUser.setParentListLike(target.getId());
                    searchUser.setUserName(from.getUserName());


                    //被转人是自己的下级
                    userList = userUserinfoService.findList(searchUser);
                    if (userList.size() > 0) {
                        userMoneyReportService.updateUserMoneyReport(from.getUserName(), "6", new BigDecimal(userchange.getChangeMoney()).multiply(new BigDecimal("-1")));

                    } else {
                        //不是同一个团队

                        userMoneyReportService.updateUserMoneyReport(from.getUserName(), "7", new BigDecimal(userchange.getChangeMoney()).multiply(new BigDecimal("-1")));
                    }

                }

            }
            catch (Exception e){

                logger.error("安全检测失败: 账变编号为:"+userchange.getId(),e);

            }



            userchange.setIsMonCheck (EnumUtil.YesNO.YES.toString ());
            userAccountchangeService.save (userchange);
        }
        logger.error("end..................");

        return true;
    }




}
