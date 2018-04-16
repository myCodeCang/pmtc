/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tasks.service;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.tasks.dao.UserTasksDao;
import com.thinkgem.jeesite.modules.tasks.entity.UserTaskBusiness;
import com.thinkgem.jeesite.modules.tasks.entity.UserTasks;
import com.thinkgem.jeesite.modules.tasks.mgr.TaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 任务调度Service
 *
 * @author yankai
 * @version 2017-06-17
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackForClassName = {"RuntimeException", "Exception", "ValidationException"})
public class UserTasksService extends CrudService<UserTasksDao, UserTasks> {

    @Autowired
    private TaskManager taskManager;

    public UserTasks get(String id) {
        return super.get(id);
    }

    public List<UserTasks> findList(UserTasks userTasks) {
        return super.findList(userTasks);
    }

    public Page<UserTasks> findPage(Page<UserTasks> page, UserTasks userTasks) {
        return super.findPage(page, userTasks);
    }


    public void save(UserTasks userTasks) throws ValidationException{
        String beanName = userTasks.getBeanName();
        Object bean = SpringContextHolder.getBean(beanName);
        if (bean == null || !(bean instanceof UserTaskBusiness)) {
            throw  new ValidationException("业务Bean必须实现UserTaskBusiness接口");
        }

        boolean isNewRecord = userTasks.getIsNewRecord();
        super.save(userTasks);

        if (isNewRecord) {
            taskManager.addJob(userTasks);
        } else {
            taskManager.updateJob(userTasks);
        }
    }

    public void delete(UserTasks userTask) {
        super.delete(userTask);
        taskManager.deleteJob(userTask);
    }

    public void updateStatus(String id, String status) {
        if (StringUtils2.isBlank(id, status)) {
            return;
        }

        dao.updateStatus(id, status);
    }

    public boolean stop(UserTasks userTask) {
        updateStatus(userTask.getId(), UserTasks.TASK_STATUS_STOP);
        return taskManager.stopJob(userTask);
    }

    public boolean resume(UserTasks userTask) {
        updateStatus(userTask.getId(), UserTasks.TASK_STATUS_RUN);
        return taskManager.resumeJob(userTask);
    }

    public boolean start(UserTasks userTask) {
        return taskManager.startJob(userTask);
    }

    public boolean startAll() {
        Collection<UserTasks> tasks = taskManager.getUserTasks();
        tasks.stream().forEach((task) -> task.setStatus(UserTasks.TASK_STATUS_RUN));
        return taskManager.startAll();
    }

    public boolean stopAll() {
        Collection<UserTasks> tasks = taskManager.getUserTasks();
        tasks.stream().forEach((task) -> task.setStatus(UserTasks.TASK_STATUS_STOP));
        return taskManager.stopAll();
    }
}