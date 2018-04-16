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
import com.thinkgem.jeesite.common.utils.DateUtils2;
import com.thinkgem.jeesite.common.utils.VerifyUtils;
import com.thinkgem.jeesite.config.EnumTransUtil;
import com.thinkgem.jeesite.modules.user.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.dao.TranscodeBuyDao;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 交易表Service
 * @author wyxiang
 * @version 2018-03-20
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class TranscodeBuyService extends CrudService<TranscodeBuyDao, TranscodeBuy> {

	@Resource
	private UserUserinfoService userUserinfoService;

	@Resource
	private TranscodePriceDaylogService transcodePriceDaylogService;

	@Resource
	private UserUserinfoBankService userUserinfoBankService;

	@Resource
	private UserUCApiService userUCApiService;


	/**
	 * 买入或者卖出pmtc
	 * @param userName 用户名
	 * @param enumType 卖或者买
	 * @param num 数量
	 */
	public void buysell(HttpServletRequest request,String userName , EnumUtil.TransCodeBuyType enumType , BigDecimal num){
		if (!this.isTrans()){
			throw new ValidationException("交易休市中");
		}
		if (!this.validateIsTransTime()){
			throw new ValidationException("不在交易时间");
		}
		UserUserinfo user = userUserinfoService.getByNameLock(userName);
		if (user == null){
			throw new ValidationException("用户不存在");
		}
		if ("2".equals(user.getIsShop())){
			throw new ValidationException("对不起!您的账户已被冻结,禁止该操作");
		}
		//验证验证码
		userUserinfoService.checkValidateCode(request);


		//获取后台交易押金配置 默认收取10% 押金 交易必须是50的倍数.
		BigDecimal guarantyMoney;
		BigDecimal transMultiple;
		try {
			guarantyMoney = new BigDecimal(Global.getOption("system_trans","auction_money","0.1"));
			transMultiple = new BigDecimal(Global.getOption("system_trans", "trans_multiple","50"));
		} catch (Exception e) {
			throw new ValidationException("保证金配置错误!");
		}
		BigDecimal[] decimals = num.divideAndRemainder(transMultiple);
		if(!BigDecimal.ZERO.equals(decimals[1])){
			throw new ValidationException("交易数量必须为"+transMultiple+"的倍数");
		}
		if (!VerifyUtils.isInteger(num)){
			throw new ValidationException("交易数量必须为正整数");
		}
		//获取今日单价
		TranscodePriceDaylog nowLog = transcodePriceDaylogService.getNowLog();
		if (nowLog == null){
			throw new ValidationException("最新价格获取失败");
		}

		String message = "发布买交易";
		EnumUtil.MoneyChangeType moneyType = EnumUtil.MoneyChangeType.transBuy;
		BigDecimal expendMoney = num;
		BigDecimal guaranty = num.multiply(guarantyMoney);
		if (EnumUtil.TransCodeBuyType.buy == enumType){
			expendMoney = BigDecimal.ZERO;
			if ("-1".equals(user.getActiveStatus())){
				guaranty =BigDecimal.ZERO;
			}
			BigDecimal checkNum = this.checkBuyNum(userName);
			BigDecimal	pushConfine;
			try {
				pushConfine = new BigDecimal(Global.getOption("system_trans","push_buy_confine","0.2"));
			} catch (Exception e) {
				throw new ValidationException("购买限制配置有误");
			}
			if (num.compareTo(checkNum) > 0){
				throw new ValidationException("超过每日限额,每日购买数量不可超过"+pushConfine+",今日剩余可发布:"+checkNum+"枚");
			}
		}
		//判断是购买还是卖出
		if (EnumUtil.TransCodeBuyType.sell == enumType){
			message = "发布卖交易";
			moneyType = EnumUtil.MoneyChangeType.transSell;
			UserUserinfoBank bank = userUserinfoBankService.getByUserName(userName);
			if (bank == null){
				throw new ValidationException("请您先去绑定银行账户");
			}
			if (EnumUtil.YesNO.NO.toString().equals(user.getUsercenterAddress())){
				throw new ValidationException("请您先去绑定会员端钱包地址");
			}
			BigDecimal checkNum = this.checkPushNum(userName, user.getUsercenterAddress());
			BigDecimal checkPushNumByMonuth = this.checkPushNumByMonuth(userName, user.getUsercenterAddress());
			//判断矿机钱包限制 1 每日限制
			BigDecimal	pushConfine;
			BigDecimal  pushConfineByMonth;
			try {
				pushConfine = new BigDecimal(Global.getOption("system_trans","user_push_confine","0.2"));
				pushConfineByMonth = new BigDecimal(Global.getOption("system_trans","user_month_push_confine","0.2"));

			} catch (Exception e) {
				throw new ValidationException("矿机钱包限制配置有误");
			}
			if (num.compareTo(checkNum) > 0){
				throw new ValidationException("超过每日限额,每日可出售数量不可超过矿机钱包的"+pushConfine.multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_HALF_UP)+"%,今日剩余可发布:"+checkNum+"枚");
			}
			if (num.compareTo(checkPushNumByMonuth) > 0){
				throw new ValidationException("超过每月限额,每月可出售数量不可超过矿机钱包的"+pushConfineByMonth+"倍,本月剩余可发布:"+checkPushNumByMonuth+"枚");
			}
			//每月限制

		}
		//判断用户押金余额是否充足
		if (user.getMoney().compareTo(expendMoney.add(guaranty)) < 0){
			throw new ValidationException("所剩币余额不足,本次交易需要:"+expendMoney.add(guaranty)+"枚币,担保金为交易额的"+guarantyMoney.multiply(BigDecimal.valueOf(100))+"%");
		}
		//扣除用户押金向和交易金额 transcode_buy 表插入购买记录
		userUserinfoService.updateUserMoney(userName,expendMoney.multiply(new BigDecimal(-1)),message+"扣除", moneyType);
		userUserinfoService.updateUserMoney(userName,guaranty.multiply(new BigDecimal(-1)),"发布保证金扣除", EnumUtil.MoneyChangeType.AUCTION_GUARANTEE);
		TranscodeBuy transcodeBuy = new TranscodeBuy();
		transcodeBuy.setUserName(userName);
		transcodeBuy.setMoney(nowLog.getNowMoney());
		transcodeBuy.setDownNum(BigDecimal.ZERO);
		transcodeBuy.setNowNum(num);
		transcodeBuy.setSellNum(num);
		transcodeBuy.setStatus(EnumTransUtil.TransBuyStatus.Selling.toString());
		transcodeBuy.setType(enumType.toString());
		transcodeBuy.setSort(0);
		transcodeBuy.setGuarantees(guaranty); //手续费
		transcodeBuy.setAddDate(new Date());
		this.save(transcodeBuy);

		//如果是第一次 则修改用户状态
		if ("-1".equals(user.getActiveStatus()) && EnumUtil.TransCodeBuyType.buy == enumType){
			userUserinfoService.updateActiveStatus(userName,"0");
		}
		//销毁验证码
		userUserinfoService.disableValidateCode(request);
	}



	public TranscodeBuy getLock(String id) {
		return dao.getLock(id);
	}

	public TranscodeBuy get(String id) {
		return super.get(id);
	}
	
	public List<TranscodeBuy> findList(TranscodeBuy transcodeBuy) {
		return super.findList(transcodeBuy);
	}
	
	public Page<TranscodeBuy> findPage(Page<TranscodeBuy> page, TranscodeBuy transcodeBuy) {
		return super.findPage(page, transcodeBuy);
	}
	

	public void save(TranscodeBuy transcodeBuy) {
		super.save(transcodeBuy);
	}
	

	public void delete(TranscodeBuy transcodeBuy) {
		super.delete(transcodeBuy);
	}

	public boolean isTrans() {
		if ("on".equals(Global.getOption("system_trans","trans_open"))){
			return true;
		}
		return false;
	}

	public boolean validateIsTransTime() throws ValidationException {
		//判断是否在交易时间内
		Date beginTime ;
		Date endTime;

		try {
			beginTime = DateUtils2.formatStrTime(Global.getOption("system_trans", "trans_time_begin"));
			endTime = DateUtils2.formatStrTime(Global.getOption("system_trans", "trans_time_end"));
		} catch (ValidationException e) {
			throw new ValidationException("交易时间配置错误。");
		}
		Date nowtime = DateUtils2.formatStrTime(DateUtils2.getTime());
		if (!(nowtime.getTime() > beginTime.getTime() && nowtime.getTime() < endTime.getTime())) {
			return false;
		}else {
			return true;
		}
	}

	public List<TranscodeBuy> findListLock(TranscodeBuy transcodeBuy) {
		return dao.findListLock(transcodeBuy);
	}


	public void updateNowNumAndStatus(String id, BigDecimal nowNum, String status) {
		dao.updateNowNumAndStatus(id,nowNum,status);
	}

	public void saleOut(String userName, String id,EnumTransUtil.TransBuyStatus status) {
		TranscodeBuy transcodeBuy = this.getLock(id);
		if (transcodeBuy == null){
			throw new ValidationException("下架订单不存在");
		}
		if (!transcodeBuy.getUserName().equals(userName)){
			throw new ValidationException("该下架订单不存在");
		}
		if (!EnumTransUtil.TransBuyStatus.Selling.toString().equals(transcodeBuy.getStatus())){
			throw new ValidationException("下架失败,该订单已交易结束.请刷新页面重试!");
		}
		this.updateNowNumAndStatus(id,BigDecimal.ZERO,status.toString());
		this.updateDownNum(id ,transcodeBuy.getNowNum());
		BigDecimal guarantyMoney = new BigDecimal(Global.getOption("system_trans","auction_money","0.1"));
		if (transcodeBuy.getGuarantees().compareTo(BigDecimal.ZERO) > 0) {
			userUserinfoService.updateUserMoney(transcodeBuy.getUserName(),transcodeBuy.getNowNum().multiply(guarantyMoney),"撤单保证金退还,撤单交易编号:"+transcodeBuy.getId(), EnumUtil.MoneyChangeType.AUCTION_GUARANTEE);
		}
		//换币给卖家

		if (EnumUtil.TransCodeBuyType.sell.toString().equals(transcodeBuy.getType())){
			userUserinfoService.updateUserMoney(transcodeBuy.getUserName(),transcodeBuy.getNowNum(),"退还未交易的币", EnumUtil.MoneyChangeType.MD_CANCEL_ORDER);
		}

	}

	public void updateDownNum(String id, BigDecimal downNum) {
		 dao.updateDownNum(id , downNum);
	}


    public void updatGuarantees(String id, BigDecimal guarantees) {
		dao.updatGuarantees(id ,guarantees);
    }
	private BigDecimal checkPushNum(String userName,String address) {
		TranscodeBuy select = new TranscodeBuy();
		select.setUserName(userName);
		select.setType(EnumUtil.TransCodeBuyType.sell.toString());
		select.setCreateDate(new Date());
		BigDecimal sellNum = this.findList(select).stream().map(TranscodeBuy::getSellNum).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal pushConfine = null;
		try {
			pushConfine = new BigDecimal(Global.getOption("system_trans","user_push_confine","0.2"));
		} catch (Exception e) {
			throw new ValidationException("会员端钱包限制配置有误");
		}
		UserUserinfo apiInfo = userUCApiService.getUserInfoByAdd(address);
		if (apiInfo == null){
			throw new ValidationException("会员端钱包地址有误");
		}
		BigDecimal num = pushConfine.multiply(apiInfo.getMoney2()).subtract(sellNum).setScale(2,BigDecimal.ROUND_HALF_UP);
		if (num.compareTo(BigDecimal.ZERO) <= 0){
			num = BigDecimal.ZERO;
		}
		return num;
	}

	private BigDecimal checkBuyNum(String userName) {
		TranscodeBuy select = new TranscodeBuy();
		select.setUserName(userName);
		select.setType(EnumUtil.TransCodeBuyType.buy.toString());
		select.setCreateDate(new Date());
		BigDecimal buyNum = this.findList(select).stream().map(TranscodeBuy::getSellNum).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal pushConfine = null;
		try {
			pushConfine = new BigDecimal(Global.getOption("system_trans","push_buy_confine"));
		} catch (Exception e) {
			throw new ValidationException("购买限制配置有误");
		}

		BigDecimal num =pushConfine.subtract(buyNum).setScale(2,BigDecimal.ROUND_HALF_UP);
		if (num.compareTo(BigDecimal.ZERO) <= 0){
			num = BigDecimal.ZERO;
		}
		return num;
	}

	private BigDecimal checkPushNumByMonuth(String userName,String address) {
		TranscodeBuy select = new TranscodeBuy();
		select.setUserName(userName);
		select.setType(EnumUtil.TransCodeBuyType.sell.toString());
		select.setCreateDateByMonth(new Date());
		BigDecimal sellNum = this.findList(select).stream().map(TranscodeBuy::getSellNum).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal pushConfine = null;
		try {
			pushConfine = new BigDecimal(Global.getOption("system_trans","user_month_push_confine","2"));
		} catch (Exception e) {
			throw new ValidationException("会员端钱包限制(月)配置有误");
		}
		UserUserinfo apiInfo = userUCApiService.getUserInfoByAdd(address);
		if (apiInfo == null){
			throw new ValidationException("会员端钱包地址有误");
		}
		BigDecimal num = pushConfine.multiply(apiInfo.getMoney2()).subtract(sellNum).setScale(2,BigDecimal.ROUND_HALF_UP);
		if (num.compareTo(BigDecimal.ZERO) <= 0){
			num = BigDecimal.ZERO;
		}
		return num;
	}

	public BigDecimal sumNowNumByTypeAndDate(String type,Date createDate) {
		return dao.sumNowNumByTypeAndDate( type,createDate);
	}

	public void adminPushBuy(String userName, BigDecimal num) {
		if (!this.isTrans()){
			throw new ValidationException("交易休市中");
		}
		if (!this.validateIsTransTime()){
			throw new ValidationException("不在交易时间");
		}
		UserUserinfo user = userUserinfoService.getByNameLock(userName);
		if (user == null){
			throw new ValidationException("用户不存在");
		}

		//获取后台交易押金配置 默认收取10% 押金 交易必须是50的倍数.
		BigDecimal guarantyMoney;
		try {
			guarantyMoney = new BigDecimal(Global.getOption("system_trans","auction_money","0.1"));
		} catch (Exception e) {
			throw new ValidationException("保证金配置错误!");
		}
		if (!VerifyUtils.isInteger(num)){
			throw new ValidationException("交易数量必须为正整数");
		}
		//获取今日单价
		TranscodePriceDaylog nowLog = transcodePriceDaylogService.getNowLog();
		if (nowLog == null){
			throw new ValidationException("最新价格获取失败");
		}

		String message = "发布买交易";
		EnumUtil.MoneyChangeType moneyType = EnumUtil.MoneyChangeType.transBuy;
		BigDecimal expendMoney = BigDecimal.ZERO;
		BigDecimal guaranty = num.multiply(guarantyMoney);
		if ("-1".equals(user.getActiveStatus())){
			guaranty =BigDecimal.ZERO;
		}
		//判断用户押金余额是否充足
		if (user.getMoney().compareTo(expendMoney.add(guaranty)) < 0){
			throw new ValidationException("所剩币余额不足,本次交易需要:"+expendMoney.add(guaranty)+"枚币,担保金为交易额的"+guarantyMoney.multiply(BigDecimal.valueOf(100))+"%");
		}
		//扣除用户押金向和交易金额 transcode_buy 表插入购买记录
		userUserinfoService.updateUserMoney(userName,expendMoney.multiply(new BigDecimal(-1)),message+"扣除", moneyType);
		userUserinfoService.updateUserMoney(userName,guaranty.multiply(new BigDecimal(-1)),"发布保证金扣除", EnumUtil.MoneyChangeType.AUCTION_GUARANTEE);
		TranscodeBuy transcodeBuy = new TranscodeBuy();
		transcodeBuy.setUserName(userName);
		transcodeBuy.setMoney(nowLog.getNowMoney());
		transcodeBuy.setDownNum(BigDecimal.ZERO);
		transcodeBuy.setNowNum(num);
		transcodeBuy.setSellNum(num);
		transcodeBuy.setStatus(EnumTransUtil.TransBuyStatus.Selling.toString());
		transcodeBuy.setType(EnumUtil.TransCodeBuyType.buy.toString());
		transcodeBuy.setSort(1);
		transcodeBuy.setGuarantees(guaranty); //手续费
		transcodeBuy.setAddDate(new Date());
		this.save(transcodeBuy);

		//如果是第一次 则修改用户状态
		if ("-1".equals(user.getActiveStatus())){
			userUserinfoService.updateActiveStatus(userName,"0");
		}
	}
}