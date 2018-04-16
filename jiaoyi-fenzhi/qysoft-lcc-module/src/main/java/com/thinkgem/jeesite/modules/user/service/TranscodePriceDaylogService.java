/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.TranscodePriceDaylog;
import com.thinkgem.jeesite.modules.user.dao.TranscodePriceDaylogDao;

import javax.annotation.Resource;

/**
 * k线走势Service
 * @author wyxiang
 * @version 2018-03-20
 */
@Service
@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception","ValidationException"})
public class TranscodePriceDaylogService extends CrudService<TranscodePriceDaylogDao, TranscodePriceDaylog> {

	@Resource
	private UserUCApiService userUCApiService ;

	public TranscodePriceDaylog get(String id) {
		return super.get(id);
	}
	
	public List<TranscodePriceDaylog> findList(TranscodePriceDaylog transcodePriceDaylog) {
		return super.findList(transcodePriceDaylog);
	}
	
	public Page<TranscodePriceDaylog> findPage(Page<TranscodePriceDaylog> page, TranscodePriceDaylog transcodePriceDaylog) {
		return super.findPage(page, transcodePriceDaylog);
	}
	

	public void save(TranscodePriceDaylog transcodePriceDaylog) {

		if (transcodePriceDaylog.getIsNewRecord()){

			List<TranscodePriceDaylog> date = findByCreateDate(transcodePriceDaylog.getCreateDate());
			if(date != null && date.size()>0){
				throw new ValidationException("本日价格已经添加,请去修改.");
			}
			transcodePriceDaylog.preInsert();
			dao.insert(transcodePriceDaylog);

			try {
				userUCApiService.updateNowPrice(transcodePriceDaylog.getNowMoney());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
            Date oldDate = this.get(transcodePriceDaylog.getId()).getCreateDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String dateO = dateFormat.format(oldDate);
            String dateN = dateFormat.format(transcodePriceDaylog.getCreateDate());
            if (dateO.equals(dateN)) {
                transcodePriceDaylog.preUpdate();
                dao.update(transcodePriceDaylog);
				try {
					userUCApiService.updateNowPrice(transcodePriceDaylog.getNowMoney());
				} catch (Exception e) {
					e.printStackTrace();
				}
            } else {
                List<TranscodePriceDaylog> date = findByCreateDate(transcodePriceDaylog.getCreateDate());
                if (date != null && date.size() > 0) {
                    throw new ValidationException("本日价格已经添加,请去修改.");
                }
                transcodePriceDaylog.preUpdate();
                dao.update(transcodePriceDaylog);
            }
        }
	}
	

	public void delete(TranscodePriceDaylog transcodePriceDaylog) {
		super.delete(transcodePriceDaylog);
	}

	public TranscodePriceDaylog getNowLog() {
		return dao.getNowLog();
	}

	public List<TranscodePriceDaylog> findWeekData(){
		return dao.findWeekData();
	}

	public List<TranscodePriceDaylog> findMonthData(){
		return dao.findMonthData();
	}

	public void updateAmount(BigDecimal nowNum) {
		TranscodePriceDaylog nowLog = getNowLog();
		if (nowLog != null){
			dao.updateAmount(nowLog.getId(),nowNum);
		}
	}

	public List<TranscodePriceDaylog> findByCreateDate(Date date){
		return dao.findByCreateDate(date);
	}


}