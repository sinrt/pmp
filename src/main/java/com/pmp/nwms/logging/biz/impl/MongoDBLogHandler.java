package com.pmp.nwms.logging.biz.impl;

import com.pmp.nwms.logging.biz.LogHandler;
import com.pmp.nwms.logging.data.entity.Log;
import com.pmp.nwms.logging.data.repository.LogRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class MongoDBLogHandler implements LogHandler {
    @Autowired
    private LogRepository logRepository;

    private Queue<Log> logObjects = new ConcurrentLinkedQueue<>();

    private static boolean busy = false;


    @Override
    public void addLogToQueue(Log log) {
        logObjects.offer(log);
    }

    @Override
    @Scheduled(fixedDelayString = "${log.handler.delay}")
    public void saveLogs() {
        if (!busy) {
            busy = true;
            try {
                if(!logObjects.isEmpty()){
                    Log log = logObjects.poll();
                    while (ObjectUtils.anyNotNull(log)){
                        log.setHashCodeValue(log.hashCode());
                        logRepository.insert(log);
                        log = logObjects.poll();
                    }
                }
            } finally {
                busy = false;
            }
        }
    }
}
