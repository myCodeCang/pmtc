package com.thinkgem.jeesite.modules.tasks.mgr;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.modules.tasks.dao.UserTasksDao;
import com.thinkgem.jeesite.modules.tasks.entity.UserTasks;
import org.quartz.*;
import org.quartz.impl.jdbcjobstore.JobStoreSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 任务调度管理类
 * Created by yankai on 2017/6/18.
 */
public class TaskManager implements PropertyChangeListener {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Resource
    private UserTasksDao userTasksDao;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, UserTasks> jobs = null;

    @PostConstruct
    public void initTasks() {
        List<UserTasks> allTasks = userTasksDao.findAllList(new UserTasks());
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        if (Collections3.isEmpty(allTasks)) {
            jobs = Maps.newHashMap();
        } else {
            jobs = allTasks.stream().collect(Collectors.toMap((userTasks) -> userTasks.getName() + "-" + userTasks.getTaskGroup(), Function.identity()));
            jobs.forEach((nameAndGroup, task) -> {
                try {
                    //所有任务默认为启动状态
                    if (UserTasks.TASK_STATUS_STOP.equals(task.getStatus())) {
                        userTasksDao.updateStatus(task.getId(), UserTasks.TASK_STATUS_RUN);
                    }

                    //获取Trigger
                    TriggerKey triggerKey = TriggerKey.triggerKey(task.getName(), task.getTaskGroup());
                    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

                    boolean isPersistent = schedulerFactoryBean.getScheduler().getMetaData().isJobStoreSupportsPersistence();
                    //如果不存在，创建一个Trigger
                    if (trigger == null) {
                        addAndStartJob(scheduler, task);
                    } else {
                        if (!isPersistent) {
                            updateAndRestartJob(scheduler, task, triggerKey, trigger);
                        }
                    }
                } catch (Exception ex) {
                    logger.error(ex.getLocalizedMessage());
                }
            });
        }

        try {
            if (scheduler.isShutdown()) {
                scheduler.start();
                scheduler.standby();
            }
        } catch (SchedulerException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    private void updateAndRestartJob(Scheduler scheduler, UserTasks task, TriggerKey triggerKey, CronTrigger trigger) throws SchedulerException {
        //创建表达式调度器，不立即触发，等到cron表达式时间满足后再触发
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(task.getScheduleCron()).withMisfireHandlingInstructionDoNothing();
        //按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .withSchedule(cronSchedule).build();

        JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(task.getName(), task.getTaskGroup()));
        if (jobDetail != null) {
            //注册属性监听器
            task.addPropertyChangeListener(this);

            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            jobDataMap.put(UserTaskJob.JOB_DETAIL_KEY, task);
            jobDetail.getJobBuilder().usingJobData(jobDataMap);
        }

        JobDataMap triggerJobDataMap = trigger.getJobDataMap();
        triggerJobDataMap.put(UserTaskJob.JOB_DETAIL_KEY, task);
        trigger.getTriggerBuilder().usingJobData(triggerJobDataMap);

        //按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    private void addAndStartJob(Scheduler scheduler, UserTasks task) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(UserTaskJob.class).withIdentity(task.getName(), task.getTaskGroup()).build();

        //注册属性监听器
        task.addPropertyChangeListener(this);
        jobDetail.getJobDataMap().put(UserTaskJob.JOB_DETAIL_KEY, task);

        //创建表达式调度器，不立即触发，等到cron表达式时间满足后再触发
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(task.getScheduleCron()).withMisfireHandlingInstructionDoNothing();
        //构建Trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getName(), task.getTaskGroup()).withSchedule(cronSchedule).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 添加任务
     *
     * @param userTask
     */
    public void addJob(UserTasks userTask) {
        if (userTask == null) {
            return;
        }

        //注册属性监听器
        //userTask.addPropertyChangeListener(this);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            addAndStartJob(scheduler, userTask);
            jobs.put(userTask.getName() + "-" + userTask.getTaskGroup(), userTask);
        } catch (SchedulerException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    /**
     * 更新任务
     *
     * @param userTask
     */
    public void updateJob(UserTasks userTask) {
        if (userTask == null) {
            return;
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            //获取Trigger
            TriggerKey triggerKey = TriggerKey.triggerKey(userTask.getName(), userTask.getTaskGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            updateAndRestartJob(scheduler, userTask, triggerKey, trigger);
            jobs.put(userTask.getName() + "-" + userTask.getTaskGroup(), userTask);
        } catch (SchedulerException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    /**
     * 删除任务
     *
     * @param userTask
     */
    public void deleteJob(UserTasks userTask) {
        if (userTask == null) {
            return;
        }

        //删除属性监听器
        userTask.removePropertyChangeListener(this);

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(userTask.getName(), userTask.getTaskGroup());
        try {
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
            jobs.remove(userTask.getName() + "-" + userTask.getTaskGroup());
        } catch (SchedulerException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    /**
     * 恢复任务
     *
     * @param userTask
     */
    public boolean resumeJob(UserTasks userTask) {
        if (userTask == null) {
            return false;
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(userTask.getName(), userTask.getTaskGroup());
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    /**
     * 停止任务
     *
     * @param userTask
     */
    public boolean stopJob(UserTasks userTask) {
        if (userTask == null) {
            return false;
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(userTask.getName(), userTask.getTaskGroup());
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }

        return true;
    }

    /**
     * 立即执行任务
     *
     * @param userTask
     */
    public boolean startJob(UserTasks userTask) {
        if (userTask == null) {
            return false;
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(userTask.getName(), userTask.getTaskGroup());
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }

        return true;
    }

    public boolean startAll() {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.resumeAll();
        } catch (SchedulerException e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public boolean stopAll() {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        Object newValue = evt.getNewValue();
        Object source = evt.getSource();
        if (!(source instanceof UserTasks)) {
            return;
        }

        if ("status".equals(propertyName)) {
            userTasksDao.updateStatus(((UserTasks) source).getId(), (String) newValue);
        } else if ("scheduleCron".equals(propertyName)) {
            userTasksDao.updateScheduleCron(((UserTasks) source).getId(), (String) newValue);
        } else if ("lastTime".equals(propertyName)) {
            userTasksDao.updateLastTime(((UserTasks) source).getId(), (Date) newValue);
        } else if ("lastState".equals(propertyName)) {
            userTasksDao.updateLastState(((UserTasks) source).getId(), (String) newValue);
        } else {
            userTasksDao.update((UserTasks) source);
        }
    }

    public Collection<UserTasks> getUserTasks() {
        return jobs.values();
    }
}
