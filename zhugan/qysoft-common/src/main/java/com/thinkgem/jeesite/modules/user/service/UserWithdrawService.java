/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.UserWithdraw;
import com.thinkgem.jeesite.modules.user.dao.UserWithdrawDao;

import javax.annotation.Resource;

/**
 * 用户提现Service
 * @author kevin
 * @version 2017-03-23
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserWithdrawService extends CrudService<UserWithdrawDao, UserWithdraw> {

	@Resource
	private UserUserinfoService userUserinfoService;

	public UserWithdraw get(String id) {
		return super.get(id);
	}
	
	public List<UserWithdraw> findList(UserWithdraw userWithdraw) {
		return super.findList(userWithdraw);
	}
	
	public Page<UserWithdraw> findPage(Page<UserWithdraw> page, UserWithdraw userWithdraw) {
		return super.findPage(page, userWithdraw);
	}
	

	public void save(UserWithdraw userWithdraw) throws ValidationException {
		super.save(userWithdraw);
	}


    public void updateStatus(UserWithdraw userWithdraw, String status, String message) throws ValidationException {
		if(!userWithdraw.getStatus ().equals (EnumUtil.CheckType.uncheck.toString ())){
			throw new ValidationException ("已审核过,不要重复审核");
		}
		if(status.equals (EnumUtil.YesNO.NO.toString ())){
			userWithdraw.setStatus(EnumUtil.CheckType.failed.toString ());
			userUserinfoService.updateUserMoney (userWithdraw.getUserName (),new BigDecimal(userWithdraw.getChangeMoney ()),"提现驳回退款,驳回原因: "+message,EnumUtil.MoneyChangeType.widthdrawReject);
		}
		else if(status.equals (EnumUtil.YesNO.YES.toString ())){
			userWithdraw.setStatus(EnumUtil.CheckType.success.toString ());
		}
		userWithdraw.setUpdateDate (new Date ());
		userWithdraw.setCommt(message);
		dao.updateStatus (userWithdraw);


    }
	public void updateRemittanceStatus(UserWithdraw userWithdraw) {

		userWithdraw.setUpdateDate (new Date ());

		dao.updateRemittanceStatus(userWithdraw);


	}
	public String getWithdrawWeek() {
		String week="";
		if("on".equals(Global.getOption("system_user_set","Sunday"))){
			week +="周日 ";
		}
		if("on".equals(Global.getOption("system_user_set","Monday"))){
			week +="周一 ";
		}
		if("on".equals(Global.getOption("system_user_set","Tuesday"))){
			week +="周二 ";
		}
		if("on".equals(Global.getOption("system_user_set","Wensday"))){
			week +="周三 ";
		}
		if("on".equals(Global.getOption("system_user_set","Thursday"))){
			week +="周四 ";
		}
		if("on".equals(Global.getOption("system_user_set","Friday"))){
			week +="周五 ";
		}
		if("on".equals(Global.getOption("system_user_set","Saturday"))){
			week +="周六 ";
		}
		return week;
	}
}