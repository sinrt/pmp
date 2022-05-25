package com.pmp.nwms.util;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ConnectionPoolLogScheduler {
    private final Logger fileLogger = LoggerFactory.getLogger(ConnectionPoolLogScheduler.class);

    private HikariPoolMXBean poolProxy;

    public ConnectionPoolLogScheduler(HikariDataSource hikariDataSource) {
        poolProxy = hikariDataSource.getHikariPoolMXBean();
    }

    @Scheduled(cron = "${connection.pool.scheduler.cron}")
    public void run() {
        fileLogger.info("TotalConnections : " + poolProxy.getTotalConnections() +
            ", ActiveConnections : " + poolProxy.getActiveConnections() +
            ", IdleConnections : " + poolProxy.getIdleConnections() +
            ", ThreadsAwaitingConnection : " + poolProxy.getThreadsAwaitingConnection());

    }


}
