/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.TranscodeBuy;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 交易表DAO接口
 * @author wyxiang
 * @version 2018-03-20
 */
@MyBatisDao
public interface TranscodeBuyDao extends CrudDao<TranscodeBuy> {

    List<TranscodeBuy> findListLock(TranscodeBuy transcodeBuy);

    void updateNowNumAndStatus(@Param("id") String id, @Param("nowNum")BigDecimal nowNum, @Param("status")String status);

    TranscodeBuy getLock(String id);

    void updateDownNum(@Param("id")String id, @Param("downNum") BigDecimal downNum);

    void updatRemarks(@Param("id")String id, @Param("remarks") String remarks);

    void updatGuarantees(@Param("id")String id, @Param("guarantees") BigDecimal guarantees);

    BigDecimal sumNowNumByTypeAndDate(@Param("type")String type,@Param("createDate") Date createDate);
}