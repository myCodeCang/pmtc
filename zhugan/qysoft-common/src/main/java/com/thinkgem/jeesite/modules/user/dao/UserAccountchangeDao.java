/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserAccountchange;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户帐变明细DAO接口
 * @author xueyuliang
 * @version 2017-03-23
 */
@MyBatisDao
public interface UserAccountchangeDao extends CrudDao<UserAccountchange> {

    public BigDecimal sumAddMoneyByMoneyType(@Param("moneyType") String moneyType, @Param("date") Date date);
    public BigDecimal sumAddMoneyByChangeType(@Param("userName") String userName,@Param("changeType") String changeType,@Param("moneyType") String moneyType,@Param("date") Date date);
    public BigDecimal sumMoneyByChangeTypebydDay(@Param("userName") String userName,@Param("changeType") String changeType,@Param("date") Date date);
    public BigDecimal changeTypeSumMoney(UserAccountchange userAccountchange);

    List<UserAccountchange> sumMoneyByTypeAndDateGroupByName(@Param("changeType")String changeType, @Param("moneyType")String moneyType, @Param("createDate") Date createDate);

    public List<UserAccountchange> findListAll(UserAccountchange entity);
    public List<UserAccountchange> findPagelistSevenDay(UserAccountchange entity);
    public List<UserAccountchange> findPageSevenDayHalfYear(UserAccountchange entity);
    public List<UserAccountchange> findPageHalfYearAfters(UserAccountchange entity);

    List<UserAccountchange> groupByChangeAndMoneyType(UserAccountchange userAccountchange);

    BigDecimal sumMoneyByType(@Param("changeType") String changeType,@Param("moneyType") String moneyType);

    BigDecimal sumMoneyByTypeAndDate(@Param("userName") String userName,@Param("changeType") String changeType,@Param("moneyType") String moneyType,@Param("date") Date date);
}