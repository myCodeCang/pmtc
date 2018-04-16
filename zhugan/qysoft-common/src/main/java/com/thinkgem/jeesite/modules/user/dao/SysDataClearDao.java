package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.BaseDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface SysDataClearDao extends BaseDao{

	public void clearUserAccountChangeTable();

	public void clearUserChargeTable();

	public void clearUserChargeBackRecordTable();

	public void clearUserChargeLogTable();

	public void clearUserLevelLogTable();

	public void clearUserMailMessageTable();

	public void clearUserReportTable();

	public void clearUserUserCenterLogTable();

	public void clearUserUserInfoTable();

	public void clearUserUserInfoBankTable();

	public void clearUserWithDrawTable();

    public void clearTransOrder();

	public void clearTransBuy();

	public void clearTransBuyDayTrend();

	public void clearTransBuyLog();

	public void clearTransApply();

	public void clearTransGoods();

	public void clearTransGoodsGroup();

	public void clearSysLogTable();

	public void clearTransApplyCondition();

	public void updateUserinfoInit();
}	
