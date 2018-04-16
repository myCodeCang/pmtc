/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.support.json.JSONUtils;
import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.config.EnumTransUtil;
import com.thinkgem.jeesite.modules.user.dao.TranscodeBuyDao;
import com.thinkgem.jeesite.modules.user.entity.TranscodeBuy;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfoBank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.TranscodeBuyLog;
import com.thinkgem.jeesite.modules.user.dao.TranscodeBuyLogDao;

import javax.annotation.Resource;

/**
 * 撮合成功记录Service
 * @author wyxiang
 * @version 2018-03-20
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class TranscodeBuyLogService extends CrudService<TranscodeBuyLogDao, TranscodeBuyLog> {

	@Resource
	private UserUserinfoBankService userinfoBankService;
	@Resource
	private UserUserinfoService userUserinfoService;
	@Resource
	private TranscodePriceDaylogService transcodePriceDaylogService;
	@Resource
	private UserMsmService userMsmService;
	@Resource
	private TranscodeBuyDao transcodeBuyDao;

	public TranscodeBuyLog get(String id) {
		return super.get(id);
	}
	
	public List<TranscodeBuyLog> findList(TranscodeBuyLog transcodeBuyLog) {
		return super.findList(transcodeBuyLog);
	}
	
	public Page<TranscodeBuyLog> findPage(Page<TranscodeBuyLog> page, TranscodeBuyLog transcodeBuyLog) {
		return super.findPage(page, transcodeBuyLog);
	}
	

	public void save(TranscodeBuyLog transcodeBuyLog) {
		super.save(transcodeBuyLog);
	}
	

	public void delete(TranscodeBuyLog transcodeBuyLog) {
		super.delete(transcodeBuyLog);
	}

    public void createBuyLog(String sellName, String sellid, String buyName, String buyid, BigDecimal num, BigDecimal money) {
		UserUserinfoBank sellBank = userinfoBankService.getByUserName(sellName);
		UserUserinfo seller= userUserinfoService.getByName(sellName);
		UserUserinfo buyer= userUserinfoService.getByName(buyName);
		if (sellBank == null || seller == null || buyer == null) {
			throw new ValidationException("信息未完善");
		}
		HashMap<String, String> map = new HashMap<>();
		map.put("buyMobile",buyer.getMobile());
		map.put("sellMobile",seller.getMobile());
		map.put("bankName",sellBank.getBankName());
		map.put("bankAdd",sellBank.getBankUserAddress());
		map.put("name",sellBank.getBankUserName());
		map.put("bankNum",sellBank.getBankUserNum());

		TranscodeBuyLog transcodeBuyLog = new TranscodeBuyLog();
		transcodeBuyLog.setBuyId(buyid);
		transcodeBuyLog.setSellId(sellid);
		transcodeBuyLog.setBuyUserName(buyName);
		transcodeBuyLog.setSellUserName(sellName);
		transcodeBuyLog.setMoney(money.toString());
		transcodeBuyLog.setNum(num.toString());
		transcodeBuyLog.setIsCheck("0");
		transcodeBuyLog.setType("0");
		transcodeBuyLog.setRemarks(JSONUtils.toJSONString(map));
		transcodeBuyLog.setStatus(EnumTransUtil.TransCodeLogStatus.WaitBuyer.toString());
		this.save(transcodeBuyLog);
    }

	public void buyerConfirmaction(String userName, String recordId, String imageUrl) {
		TranscodeBuyLog buyLog = this.getLock(recordId);
		if (buyLog == null){
			throw new ValidationException("交易订单不存在");
		}
		if (StringUtils2.isBlank(imageUrl)){
			throw new ValidationException("请上传凭证");
		}
		if (!buyLog.getBuyUserName().equals(userName)){
			throw new ValidationException("该交易订单不存在");
		}
		if (!EnumTransUtil.TransCodeLogStatus.WaitBuyer.toString().equals(buyLog.getStatus())){
			throw new ValidationException("不可操作该订单");
		}

		buyLog.setStatus(EnumTransUtil.TransCodeLogStatus.WaitSeller.toString());
		buyLog.setImages(imageUrl);
		this.save(buyLog);
		//发送短信
//		UserUserinfo byName = userUserinfoService.getByName(buyLog.getSellUserName());
//		String msgTemplate = Global.getOption("system_sms", "lk_msg_registe");
//		if (StringUtils2.isNotBlank(msgTemplate)) {
//			userMsmService.pushMessage(byName.getMobile(), byName.getUserName(), msgTemplate);
//		}
	}

    public void sellerConfirmaction(String userName, String recordId) {
		TranscodeBuyLog buyLog = this.getLock(recordId);
		UserUserinfo buyUser =  userUserinfoService.getByNameLock(buyLog.getBuyUserName());
		UserUserinfo sellUser =  userUserinfoService.getByNameLock(buyLog.getSellUserName());
		if (buyLog == null){
			throw new ValidationException("交易订单不存在");
		}
		if (!buyLog.getSellUserName().equals(userName)){
			throw new ValidationException("该交易订单不存在");
		}
		if (!EnumTransUtil.TransCodeLogStatus.WaitSeller.toString().equals(buyLog.getStatus())){
			throw new ValidationException("不可操作该订单");
		}
		//获取买家订单信息
		TranscodeBuy buy = transcodeBuyDao.get(buyLog.getBuyId());
		if (buy == null){
			throw new ValidationException("买家发布订单不存在!");
		}
		buyLog.setStatus(EnumTransUtil.TransCodeLogStatus.TransSuccess.toString());
		buyLog.setType(EnumTransUtil.TransCodeLogType.normal.toString());
		this.save(buyLog);
		//给买家增加币
		userUserinfoService.updateUserMoney(buyLog.getBuyUserName(),new BigDecimal(buyLog.getNum()),"交易完成,交易编号:"+buyLog.getId(), EnumUtil.MoneyChangeType.MDTradeBuy);

		//返回双方保证金
		BigDecimal guarantyMoney = new BigDecimal(Global.getOption("system_trans","auction_money","0.1"));
		if (buy.getGuarantees().compareTo(BigDecimal.ZERO) > 0) {
			userUserinfoService.updateUserMoney(buyLog.getBuyUserName(),new BigDecimal(buyLog.getNum()).multiply(guarantyMoney),"交易成功保证金退还,成交记录编号:"+buyLog.getId(), EnumUtil.MoneyChangeType.AUCTION_GUARANTEE);
		}
		userUserinfoService.updateUserMoney(buyLog.getSellUserName(),new BigDecimal(buyLog.getNum()).multiply(guarantyMoney),"交易成功保证金退还,成交记录编号:"+buyLog.getId(), EnumUtil.MoneyChangeType.AUCTION_GUARANTEE);
		//扣除手续费
        BigDecimal shouxuFee = new BigDecimal(Global.getOption("system_trans","sell_procedure_money","0.05"));
        userUserinfoService.updateUserMoney(buyLog.getSellUserName(),new BigDecimal(buyLog.getNum()).multiply(shouxuFee).multiply(BigDecimal.valueOf(-1).setScale(2,BigDecimal.ROUND_HALF_UP)),"交易成功扣除手续费,成交记录编号:"+buyLog.getId(),EnumUtil.MoneyChangeType.MdTrade_FEE);

		String transAdd = Global.getOption("system_trans", "trans_add","on");
		if("on".equals(transAdd)){
			//增加成交额
			transcodePriceDaylogService.updateAmount(new BigDecimal(buyLog.getNum()));
		}
	}

	private TranscodeBuyLog getLock(String recordId) {
		return dao.getLock(recordId);
	}


	//买家未确认操作
	public void buyUnconfirmed(String id) {
		TranscodeBuyLog buyLog = this.getLock(id);
		if (buyLog == null){
			throw new ValidationException("操作订单不存在!");
		}
		if (Integer.parseInt(buyLog.getStatus()) > EnumTransUtil.TransCodeLogStatus.WaitSeller.getCode()){
			throw new ValidationException("不可操作该订单!");
		}
		buyLog.setStatus(EnumTransUtil.TransCodeLogStatus.TransError.toString());
		buyLog.setType(EnumTransUtil.TransCodeLogType.service.toString());
		this.save(buyLog);

		//退还卖家保证金及币
		BigDecimal guarantyMoney = new BigDecimal(Global.getOption("system_trans","auction_money","0.1"));
		userUserinfoService.updateUserMoney(buyLog.getSellUserName(),new BigDecimal(buyLog.getNum()).multiply(guarantyMoney),"平台仲裁,买家未确认,保证金退还,记录编号:"+buyLog.getId(), EnumUtil.MoneyChangeType.AUCTION_GUARANTEE);
		userUserinfoService.updateUserMoney(buyLog.getSellUserName(),new BigDecimal(buyLog.getNum()),"平台仲裁,买家未确认,数量退还,记录编号:"+buyLog.getId(), EnumUtil.MoneyChangeType.Revoke);

	}

	public void sellUnconfirmed(String id) {
		TranscodeBuyLog buyLog = this.getLock(id);
		UserUserinfo buyUser =  userUserinfoService.getByNameLock(buyLog.getBuyUserName());
		UserUserinfo sellUser =  userUserinfoService.getByNameLock(buyLog.getSellUserName());
		if (buyLog == null){
			throw new ValidationException("交易订单不存在");
		}
		if (!EnumTransUtil.TransCodeLogStatus.WaitSeller.toString().equals(buyLog.getStatus())){
			throw new ValidationException("不可操作该订单");
		}
		//获取买家订单信息
		TranscodeBuy buy = transcodeBuyDao.get(buyLog.getBuyId());
		if (buy == null){
			throw new ValidationException("买家发布订单不存在!");
		}
		buyLog.setStatus(EnumTransUtil.TransCodeLogStatus.TransSuccess.toString());
		buyLog.setType(EnumTransUtil.TransCodeLogType.service.toString());
		this.save(buyLog);
		//给买家增加币
		userUserinfoService.updateUserMoney(buyLog.getBuyUserName(),new BigDecimal(buyLog.getNum()),"交易完成,交易编号:"+buyLog.getId(), EnumUtil.MoneyChangeType.MDTradeBuy);

		//返回保证金
		BigDecimal guarantyMoney = new BigDecimal(Global.getOption("system_trans","auction_money","0.1"));
		if (buy.getGuarantees().compareTo(BigDecimal.ZERO) > 0) {
			userUserinfoService.updateUserMoney(buyLog.getBuyUserName(),new BigDecimal(buyLog.getNum()).multiply(guarantyMoney),"平台仲裁,交易成功保证金退还,成交记录编号:"+buyLog.getId(), EnumUtil.MoneyChangeType.AUCTION_GUARANTEE);
		}
		//扣除手续费
		BigDecimal shouxuFee = new BigDecimal(Global.getOption("system_trans","sell_procedure_money","0.05"));
		userUserinfoService.updateUserMoney(buyLog.getSellUserName(),new BigDecimal(buyLog.getNum()).multiply(shouxuFee).multiply(BigDecimal.valueOf(-1).setScale(2,BigDecimal.ROUND_HALF_UP)),"交易成功扣除手续费,成交记录编号:"+buyLog.getId(),EnumUtil.MoneyChangeType.MdTrade_FEE);

		String transAdd = Global.getOption("system_trans", "trans_add","on");
		if("on".equals(transAdd)){
			//增加成交额
			transcodePriceDaylogService.updateAmount(new BigDecimal(buyLog.getNum()));
		}
	}
}