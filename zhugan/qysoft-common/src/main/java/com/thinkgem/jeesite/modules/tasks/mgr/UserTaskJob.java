package com.thinkgem.jeesite.modules.tasks.mgr;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.tasks.entity.UserTaskBusiness;
import com.thinkgem.jeesite.modules.tasks.entity.UserTasks;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by yankai on 2017/6/18.
 */
@DisallowConcurrentExecution    //避免当次任务未执行完就开始下一次调度
public class UserTaskJob implements Job {
    public static final String JOB_DETAIL_KEY = "scheduleJob";

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        UserTasks userTask = (UserTasks) context.getMergedJobDataMap().get(JOB_DETAIL_KEY);
        if (userTask == null) {
            return;
        }

        String beanName = userTask.getBeanName();
        if (StringUtils2.isBlank(beanName)) {
            return;
        }

        Object bean = SpringContextHolder.getBean(beanName);
        if (bean == null || !(bean instanceof UserTaskBusiness)) {
            return;
        }

        Object mgr = SpringContextHolder.getBean("taskManager");
        if (mgr != null && mgr instanceof TaskManager) {
            userTask.addPropertyChangeListener((TaskManager) mgr);
        }

        //设置上次执行时间
        userTask.setLastTime(new Date());

        //执行业务方法
        try {
            boolean retValue = ((UserTaskBusiness) bean).doBusiness();
            if (retValue) {
                userTask.setLastState(UserTasks.TASK_RUN_SUCCESS);
            } else {
                userTask.setLastState(UserTasks.TASK_RUN_FAILED);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        }
    }
}
