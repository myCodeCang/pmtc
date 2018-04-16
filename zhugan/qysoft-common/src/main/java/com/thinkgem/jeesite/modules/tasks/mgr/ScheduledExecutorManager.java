package com.thinkgem.jeesite.modules.tasks.mgr;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.modules.tasks.dao.UserTasksDao;
import com.thinkgem.jeesite.modules.tasks.entity.UserTasks;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 任务调度管理类
 * Created by yankai on 2017/6/18.
 */
public class ScheduledExecutorManager{



    protected Logger logger = LoggerFactory.getLogger(getClass());

    private ScheduledExecutorService scheduExec;


    @PostConstruct
    public void initTasks() {
        this.scheduExec =  Executors.newScheduledThreadPool(10);
    }

    public void createScheduledExecutor(Runnable task,int minutes) throws Exception {
         scheduExec.schedule(task, minutes, TimeUnit.MINUTES);
    }


}
