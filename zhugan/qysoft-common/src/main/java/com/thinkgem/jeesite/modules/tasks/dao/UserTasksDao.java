/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tasks.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.tasks.entity.UserTasks;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 任务调度DAO接口
 * @author yankai
 * @version 2017-06-17
 */
@MyBatisDao
public interface UserTasksDao extends CrudDao<UserTasks> {
    /**
     * 更新任务状态
     * @param id
     * @param status
     */
	public void updateStatus(@Param("id")String id, @Param("status")String status);

    /**
     * 更新任务调度表达式
     * @param id
     * @param scheduleCron
     */
	public void updateScheduleCron(@Param("id")String id, @Param("scheduleCron")String scheduleCron);

    /**
     * 更新任务调度表达式
     * @param id
     * @param lastState
     */
    public void updateLastState(@Param("id")String id, @Param("lastState")String lastState);

    /**
     * 更新任务调度表达式
     * @param id
     * @param lastState
     */
    public void updateLastTime(@Param("id")String id, @Param("lastTime")Date lastState);
}