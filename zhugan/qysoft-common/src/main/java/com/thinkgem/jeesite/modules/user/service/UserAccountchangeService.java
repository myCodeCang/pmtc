/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.UserAccountchange;
import com.thinkgem.jeesite.modules.user.entity.UserLevel;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.dao.UserAccountchangeDao;

/**
 * 用户帐变明细Service
 * @author xueyuliang
 * @version 2017-03-23
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class UserAccountchangeService extends CrudService<UserAccountchangeDao, UserAccountchange> {
	
	/**
	 *	 根据id查询用户
	 */
	public UserAccountchange get(String id) {
		  return super.get(id);
	}
	
	
	public List<UserAccountchange> findList(UserAccountchange userAccountchange) {
		return super.findList(userAccountchange);
	}


	public Page<UserAccountchange> findPage(Page<UserAccountchange> page, UserAccountchange userAccountchange) {
		return super.findPage(page, userAccountchange);
	}

	public Page<UserAccountchange> findPageAll(Page<UserAccountchange> page, UserAccountchange userAccountchange) {
		userAccountchange.setPage(page);
		page.setList(dao.findListAll(userAccountchange));
		return page;
		//return super.findPage(page, userAccountchange);
	}
	public Page<UserAccountchange> findPagelistSevenDays(Page<UserAccountchange> page, UserAccountchange userAccountchange) {
		userAccountchange.setPage(page);
		page.setList(dao.findPagelistSevenDay(userAccountchange));
		return page;
	}
	public Page<UserAccountchange> findPageSevenDaysHalfYear(Page<UserAccountchange> page, UserAccountchange userAccountchange) {
		userAccountchange.setPage(page);
		page.setList(dao.findPageSevenDayHalfYear(userAccountchange));
		return page;
	}
	public Page<UserAccountchange> findPageHalfYearAfter(Page<UserAccountchange> page, UserAccountchange userAccountchange) {
		userAccountchange.setPage(page);
		page.setList(dao.findPageHalfYearAfters(userAccountchange));
		return page;
	}

	public void save(UserAccountchange userAccountchange) throws ValidationException {
		super.save(userAccountchange);
	}

	public void delete(UserAccountchange userAccountchange)throws ValidationException {
		super.delete(userAccountchange);
	}

    public BigDecimal sumAddMoneyByMoneyType(String moneyType, Date date) {
		return dao.sumAddMoneyByMoneyType(moneyType,date);
    }
	public BigDecimal sumAddMoneyByChangeType(String userName,String changeType,String moneyType,Date date) {
		return dao.sumAddMoneyByChangeType(userName,changeType,moneyType,date);
	}
	public BigDecimal changeTypeSumMoney(UserAccountchange userAccountchange) {
		return dao.changeTypeSumMoney(userAccountchange);
	}

    public List<UserAccountchange> sumMoneyByTypeAndDateGroupByName(String changeType, String moneyType, Date createDate) {
		return dao.sumMoneyByTypeAndDateGroupByName(changeType,moneyType,createDate);
    }


	public Page<UserAccountchange> groupByChangeAndMoneyType(Page<UserAccountchange> page, UserAccountchange userAccountchange) {
		userAccountchange.setPage(page);
		page.setList(dao.groupByChangeAndMoneyType(userAccountchange));
		return page;
	}

	public BigDecimal sumMoneyByType(String changeType,String moneyType){
		return dao.sumMoneyByType(changeType,moneyType);
	}

	public BigDecimal sumMoneyByTypeAndDate(String userName,String changeType,String moneyType){
		return dao.sumMoneyByTypeAndDate(userName,changeType,moneyType,new Date());
	}


}