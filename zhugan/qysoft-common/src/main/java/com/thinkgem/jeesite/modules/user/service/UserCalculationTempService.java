/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.user.dao.UserCalculationTempDao;
import com.thinkgem.jeesite.modules.user.entity.UserCalculationTemp;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 董事奖发放Service
 * @author kevin
 * @version 2017-06-22
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserCalculationTempService extends CrudService<UserCalculationTempDao, UserCalculationTemp> {

	@Resource
	UserUserinfoService userUserinfoService;
	@Resource
	UserReportService userReportService;

	public UserCalculationTemp get(String id) {
		return super.get(id);
	}
	
	public List<UserCalculationTemp> findList(UserCalculationTemp userCalculationTemp) {
		return super.findList(userCalculationTemp);
	}
	
	public Page<UserCalculationTemp> findPage(Page<UserCalculationTemp> page, UserCalculationTemp userCalculationTemp) {
		return super.findPage(page, userCalculationTemp);
	}
	

	public void save(UserCalculationTemp userCalculationTemp) {
		super.save(userCalculationTemp);
	}
	

	public void delete(UserCalculationTemp userCalculationTemp) {
		super.delete(userCalculationTemp);
	}

	public void clearUserCalculationTemp() {
		dao.clearUserCalculationTemp();
	}

	public void countCalculationTemp(String moneyNum) {

		if(StringUtils2.isNotBlank(moneyNum)){
			BigDecimal moneyBasic = new BigDecimal(moneyNum);
			this.clearUserCalculationTemp(); //清除数据

			//董事奖百分比
			BigDecimal oneMakePersent =new BigDecimal(Global.getOption("system_lead_bonus","one_make_persent","0.02"));
			BigDecimal twoMakePersent = new BigDecimal(Global.getOption("system_lead_bonus","two_make_persent","0.01"));
			BigDecimal threeMakePersent = new BigDecimal(Global.getOption("system_lead_bonus","three_make_persent","0.01"));
			BigDecimal fourMakePersent = new BigDecimal(Global.getOption("system_lead_bonus","four_make_persent","0.005"));
			BigDecimal fiveMakePersent = new BigDecimal(Global.getOption("system_lead_bonus","five_make_persent","0.005"));

			UserUserinfo userUserinfo = new UserUserinfo();

			userUserinfo.setUserType("2");//1星
			List<UserUserinfo> oneUserinfoList = userUserinfoService.findList(userUserinfo);
			if(oneUserinfoList.size()>0){
				BigDecimal oneUserMoney = moneyBasic.multiply(oneMakePersent).divide(new BigDecimal(oneUserinfoList.size()),2,BigDecimal.ROUND_HALF_UP);
				this.insertUserCalculationTemp(oneUserinfoList,oneUserMoney);
			}
			userUserinfo.setUserType("3");//2星
			List<UserUserinfo> twoUserinfoList = userUserinfoService.findList(userUserinfo);
			if(twoUserinfoList.size()>0){
				BigDecimal twoUserMoney = moneyBasic.multiply(twoMakePersent).divide(new BigDecimal(twoUserinfoList.size()),2,BigDecimal.ROUND_HALF_UP);
				this.insertUserCalculationTemp(twoUserinfoList,twoUserMoney);
			}
			userUserinfo.setUserType("4");//3星
			List<UserUserinfo> threeUserinfoList = userUserinfoService.findList(userUserinfo);
			if(threeUserinfoList.size()>0){
				BigDecimal threeUserMoney = moneyBasic.multiply(threeMakePersent).divide(new BigDecimal(threeUserinfoList.size()),2,BigDecimal.ROUND_HALF_UP);
				this.insertUserCalculationTemp(threeUserinfoList,threeUserMoney);
			}
			userUserinfo.setUserType("5");//4星
			List<UserUserinfo> fourUserinfoList = userUserinfoService.findList(userUserinfo);
			if(fourUserinfoList.size()>0){
				BigDecimal fourUserMoney = moneyBasic.multiply(fourMakePersent).divide(new BigDecimal(fourUserinfoList.size()),2,BigDecimal.ROUND_HALF_UP);
				this.insertUserCalculationTemp(fourUserinfoList,fourUserMoney);
			}
			userUserinfo.setUserType("6");//5星
			List<UserUserinfo> fiveUserinfoList = userUserinfoService.findList(userUserinfo);
			if(fiveUserinfoList.size()>0){
				BigDecimal fiveUserMoney = moneyBasic.multiply(fiveMakePersent).divide(new BigDecimal(fiveUserinfoList.size()),2,BigDecimal.ROUND_HALF_UP);
				this.insertUserCalculationTemp(fiveUserinfoList,fiveUserMoney);
			}
		}
	}

	public void insertUserCalculationTemp(List<UserUserinfo> oneUserinfoList, BigDecimal userMoney) {

		for(UserUserinfo oneUserinfo:oneUserinfoList){
			if (oneUserinfo==null) {
				continue;
			}

			this.updateCalculationTempByName(oneUserinfo.getUserName(),"7",userMoney);
		}
	}

	public void updateCalculationTempByName(String userName , String reportType , BigDecimal money){
		if(money.compareTo(BigDecimal.ZERO) ==0){
			return;
		}
		UserCalculationTemp userCalculationTemp = new UserCalculationTemp ();
		userCalculationTemp.setUserName (userName);
		List<UserCalculationTemp> userCalculationTemps = this.findList (userCalculationTemp);
		if(userCalculationTemps.size ()==0){
			UserCalculationTemp userCalculationTempss = new UserCalculationTemp ();
			userCalculationTempss.setUserName (userName);
			this.save (userCalculationTempss);
			userCalculationTemps = this.findList (userCalculationTemp);
		}
		dao.updateCalculationTempByType(userCalculationTemps.get(0).getUserName(),reportType,money);

	}

	public void calculateUserReport() {
		UserCalculationTemp userCalculationTemp = new UserCalculationTemp ();
		List<UserCalculationTemp> userCalculationTemps = this.findList (userCalculationTemp);

		for(UserCalculationTemp userCalculationTempss:userCalculationTemps){
			if (userCalculationTempss==null) {
				continue;
			}
			userReportService.updateUserReportByDateAndLog(userCalculationTempss.getUserName(),"7",userCalculationTempss.getDividendSeven(),"董事奖发放", EnumUtil.MoneyChangeType.SensibleMoney,EnumUtil.MoneyType.money.toString());

		}

		this.clearUserCalculationTemp(); //清除数据

	}
}