package com.pmp.nwms.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    //todo check why this is not working correctly
    private static Logger createLoggerFor(Class clazz, String logPath, Level level) {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder ple = new PatternLayoutEncoder();

        ple.setPattern("%d{HH:mm:ss.SSS} %-5level %logger{36} - %msg%n");
        ple.setContext(lc);
        ple.start();
        RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
        TimeBasedRollingPolicy<ILoggingEvent> policy = new TimeBasedRollingPolicy<>();
        policy.setFileNamePattern(logPath + "/" + clazz.getSimpleName() + ".%d{yyyy-MM-dd}.log");
        policy.setMaxHistory(14);
        fileAppender.setRollingPolicy(policy);
        fileAppender.setFile(logPath + "/" + clazz.getSimpleName() + ".log");
        fileAppender.setEncoder(ple);
        fileAppender.setContext(lc);
        fileAppender.start();

        Logger logger = (Logger) LoggerFactory.getLogger(clazz);
        logger.addAppender(fileAppender);
        logger.setLevel(level);
        logger.setAdditive(false); /* set to true if root should log too */

        return logger;
    }

}
