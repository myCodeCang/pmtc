/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserReport;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户奖金DAO接口
 * @author xueyuliang
 * @version 2017-04-25
 */
@MyBatisDao
public interface UserReportDao extends CrudDao<UserReport> {

    public void updateUserReportByType(@Param ("id") String id , @Param ("reportType") String reportType,@Param ("money") BigDecimal money);

    public UserReport getOrganReport(UserReport userReport);

    public UserReport getTeamReport(UserReport userReport);

    public UserReport sumUserReport(UserReport userReport);

    List<UserReport> findQuarterList(@Param ("order") String order, @Param ("topLimit") int topLimit, @Param ("startDate") Date startDate, @Param ("lastDate") Date lastDate);

    List<UserReport> findListSum(UserReport userReport);

    List<UserReport> sumFuHuaMoney(UserReport userReport );

    UserReport sumChargeMoney();

    UserReport getByUserNameAndDate(@Param("userName") String userName,@Param("date") Date date);
}