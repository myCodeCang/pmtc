package com.thinkgem.jeesite.common.utils;

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.log4j.RollingFileAppender;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

/**
 * Created by kevin on 2017/8/28.
 */
public class LogAppendHelper {
    public static Map<String, RollingFileAppender> rollingFileAppender = Maps.newHashMap();

    public static Optional<RollingFileAppender> getRollingFileAppender(String className) {
        if (StringUtils2.isBlank(className)) {
            return Optional.empty();
        }

        if (rollingFileAppender.containsKey(className)) {
            return Optional.of(rollingFileAppender.get(className));
        }

        //FileAppender appender = (FileAppender)Logger.getRootLogger().getAppender("RollingFile");
        RollingFileAppender fileAppender = new RollingFileAppender();
        fileAppender.setName(className);
        fileAppender.setThreshold(Priority.ERROR);
        fileAppender.setEncoding("UTF-8");
        fileAppender.setMaxBackupIndex(5);
        PatternLayout layout = new PatternLayout();
        layout.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss} %-5p [%c] - %m %L%n");
        fileAppender.setLayout(layout);

        String path = Arrays.stream(className.split("\\.")).collect(joining(File.separator));
        String url = SpringContextHolder.ROOT_PATH + "/WEB-INF/logs/" + File.separator + path + ".log";
        fileAppender.setFile(url);
        fileAppender.activateOptions();

        rollingFileAppender.put(className, fileAppender);
        return Optional.of(fileAppender);
    }
}
