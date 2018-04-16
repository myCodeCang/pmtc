package com.thinkgem.jeesite.modules.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.user.dao.SysDataClearDao;

import javax.annotation.Resource;

@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class SysDataClearService extends BaseService {

	@Resource
	private SysDataClearDao sysDataClearDao;


	public void clearUserAccountChange() {
		sysDataClearDao.clearUserAccountChangeTable();
	}

	public void clearUserCharge() {
		sysDataClearDao.clearUserChargeTable();
	}

	public void clearUserChargeBackRecord() {
		sysDataClearDao.clearUserChargeBackRecordTable();
	}

	public void clearUserChargeLog() {
		sysDataClearDao.clearUserChargeLogTable();
	}

	public void clearUserLevelLog() {
		sysDataClearDao.clearUserLevelLogTable();
	}

	public void clearUserMailMessage() {
		sysDataClearDao.clearUserMailMessageTable();
	}

	public void clearUserReport() {
		sysDataClearDao.clearUserReportTable();
	}

	public void clearUserUserCenterLog() {
		sysDataClearDao.clearUserUserCenterLogTable();
	}

	public void clearUserUserInfo() {
		sysDataClearDao.clearUserUserInfoTable();
		sysDataClearDao.updateUserinfoInit();
	}

	public void clearUserUserInfoBank() {
		sysDataClearDao.clearUserUserInfoBankTable();
	}

	public void clearUserWithDraw() {
		sysDataClearDao.clearUserWithDrawTable();
	}

	public void clearTransOrder() {
		sysDataClearDao.clearTransOrder();
	}

	public void clearTransGoodsGroup() {
		sysDataClearDao.clearTransGoodsGroup();
	}

	public void clearTransGoods() {
		sysDataClearDao.clearTransGoods();
	}

	public void clearTransApply() {
		sysDataClearDao.clearTransApply();
	}

	public void clearTransApplyCondition() {
		sysDataClearDao.clearTransApplyCondition();
	}

	public void clearTransBuy() {
		sysDataClearDao.clearTransBuy();
	}

	public void clearTransBuyDayTrend() {
		sysDataClearDao.clearTransBuyDayTrend();
	}

	public void clearTransBuyLog() {
		sysDataClearDao.clearTransBuyLog();
	}

	public void clearSysLog() {
		sysDataClearDao.clearSysLogTable();
	}

	public void clearAllTable() {
		sysDataClearDao.clearUserAccountChangeTable();
		sysDataClearDao.clearUserChargeTable();
		sysDataClearDao.clearUserChargeBackRecordTable();
		sysDataClearDao.clearUserChargeLogTable();
		sysDataClearDao.clearUserLevelLogTable();
		sysDataClearDao.clearUserMailMessageTable();
		sysDataClearDao.clearUserReportTable();
		sysDataClearDao.clearUserUserCenterLogTable();
		sysDataClearDao.clearUserUserInfoTable();
		sysDataClearDao.clearUserUserInfoBankTable();
		sysDataClearDao.clearUserWithDrawTable();
		sysDataClearDao.clearSysLogTable();
		sysDataClearDao.clearTransApply ();
		sysDataClearDao.clearTransBuy ();
		sysDataClearDao.clearTransBuyDayTrend ();
		sysDataClearDao.clearTransBuyLog ();
		sysDataClearDao.clearTransGoods ();
		sysDataClearDao.clearTransGoodsGroup ();
		sysDataClearDao.clearTransOrder ();
		sysDataClearDao.updateUserinfoInit();
	}

}
