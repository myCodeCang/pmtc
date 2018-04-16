/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.dao.TransPriceDaylogDao;
import com.thinkgem.jeesite.modules.user.dao.TranscodeBuyDao;
import com.thinkgem.jeesite.modules.user.entity.TransPriceDaylog;
import com.thinkgem.jeesite.modules.user.entity.TranscodeBuy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 撮合交易业务层
 * @author luo
 * @version 2018-02-10
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class TransCodeService extends CrudService<TranscodeBuyDao, TranscodeBuy> {


	/**
	 * 买入或者卖出pmtc
	 * @param userName 用户名
	 * @param enumType 卖或者买
	 * @param price 单价
	 * @param num 数量
	 */
	public void buysell(String userName , EnumUtil.TransCodeBuyType enumType , BigDecimal price , BigDecimal num){

		//获取后台交易押金配置 默认收取10% 押金
		// 交易必须是50的倍数.
		//判断是购买还是卖出
		//判断用户押金余额是否充足
		//获取今日单价
		//扣除用户押金向和交易金额 transcode_buy 表插入购买记录
	}

	public TranscodeBuy get(String id) {
		return super.get(id);
	}
	
	public List<TranscodeBuy> findList(TranscodeBuy transcodeBuy) {
		return super.findList(transcodeBuy);
	}
	
	public Page<TranscodeBuy> findPage(Page<TranscodeBuy> page, TranscodeBuy transPriceDaylog) {
		return super.findPage(page, transPriceDaylog);
	}
	

	public void save(TranscodeBuy transPriceDaylog) {
		super.save(transPriceDaylog);
	}
	

	public void delete(TranscodeBuy transPriceDaylog) {
		super.delete(transPriceDaylog);
	}

}