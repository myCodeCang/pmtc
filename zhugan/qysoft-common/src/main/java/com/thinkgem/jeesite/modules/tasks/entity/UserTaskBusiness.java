package com.thinkgem.jeesite.modules.tasks.entity;

import com.thinkgem.jeesite.common.utils.LogAppendHelper;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

import java.util.Optional;

/**
 * Created by yankai on 2017/6/18.
 */
public interface UserTaskBusiness {
    public static final String BUSINESS_METHOD_NAME = "doBusiness";

    /**
     * 需要调度的业务方法
     *
     * @return
     */
    boolean doBusiness();

    default Logger getLogger() {
        Logger logger = Logger.getLogger(getClass());
        //FileAppender appender = (FileAppender)Logger.getRootLogger().getAppender("RollingFile");
        /*RollingFileAppender fileAppender = new RollingFileAppender();
        fileAppender.setName(getClass().getName());
        fileAppender.setThreshold(Priority.ERROR);
        fileAppender.setEncoding("UTF-8");
        fileAppender.setMaxBackupIndex(5);
        PatternLayout layout = new PatternLayout();
        layout.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss} %-5p [%c] - %m %L%n");
        fileAppender.setLayout(layout);

        String name = getClass().getName();
        String path = Arrays.stream(name.split("\\.")).collect(joining(File.separator));

        String url = SpringContextHolder.ROOT_PATH + "/WEB-INF/logs/" + File.separator + path + ".log";
        fileAppender.setFile(url);
        fileAppender.activateOptions();*/

        Optional<RollingFileAppender> rollingFileAppender = LogAppendHelper.getRollingFileAppender(getClass().getName());
        if (rollingFileAppender.isPresent()) {
            logger.addAppender(rollingFileAppender.get());
        }
        return logger;
    }
}
