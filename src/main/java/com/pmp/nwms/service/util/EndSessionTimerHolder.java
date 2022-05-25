package com.pmp.nwms.service.util;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

@Component("endSessionTimerHolder")
public class EndSessionTimerHolder {
    private final Logger log = LoggerFactory.getLogger(EndSessionTimerHolder.class);

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NwmsConfig nwmsConfig;

    private Map<String, EndSessionCloseTimer> endSessionCloseTasks = new HashMap<>();
    private Map<String, Timer> endSessionCloseTimers = new HashMap<>();
    private Map<String, EndSessionSendSignalTimer> endSessionSendSignalTasks = new HashMap<>();
    private Map<String, Timer> endSessionSendSignalTimers = new HashMap<>();

    public void addSessionTimer(String sessionId, Date finishDateTime, boolean signalSessionEnd, String rubruWsUrl) {
        if (signalSessionEnd) {
            try {
                if (!endSessionCloseTimers.containsKey(sessionId) && nwmsConfig.isEndSessionCloseTimerEnabled()) {
                    Date checkDate = DateUtil.plusHours(new Date(), nwmsConfig.getFinishClassroomInformMaxTime());
                    if (finishDateTime.before(checkDate)) {
                        EndSessionCloseTimer task = new EndSessionCloseTimer(sessionId, finishDateTime, rubruWsUrl, restTemplate, nwmsConfig);
                        Timer timer = new Timer();
                        timer.scheduleAtFixedRate(task, nwmsConfig.getFinishClassroomInformTimerDelay() * 60L * 1000L, nwmsConfig.getFinishClassroomInformTimerPeriod() * 60L * 1000L);
                        endSessionCloseTasks.put(sessionId, task);
                        endSessionCloseTimers.put(sessionId, timer);
                    }
                }
                if (!endSessionSendSignalTimers.containsKey(sessionId) && nwmsConfig.isEndSessionSendSignalTimerEnabled()) {
                    Date checkDate = DateUtil.plusHours(new Date(), nwmsConfig.getFinishClassroomInformMaxTime());
                    if (finishDateTime.before(checkDate)) {
                        EndSessionSendSignalTimer task = new EndSessionSendSignalTimer(sessionId, finishDateTime, rubruWsUrl, messageSource, restTemplate, nwmsConfig);
                        Timer timer = new Timer();
                        timer.scheduleAtFixedRate(task, nwmsConfig.getFinishClassroomInformTimerDelay() * 60L * 1000L, nwmsConfig.getFinishClassroomInformTimerPeriod() * 60L * 1000L);
                        endSessionSendSignalTasks.put(sessionId, task);
                        endSessionSendSignalTimers.put(sessionId, timer);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public void cancelSessionTimer(String sessionId) {
        if (endSessionCloseTimers.containsKey(sessionId)) {
            endSessionCloseTasks.get(sessionId).cancel();
            endSessionCloseTimers.get(sessionId).cancel();
            endSessionCloseTimers.remove(sessionId);
            endSessionCloseTasks.remove(sessionId);
        }
        if (endSessionSendSignalTimers.containsKey(sessionId)) {
            endSessionSendSignalTasks.get(sessionId).cancel();
            endSessionSendSignalTimers.get(sessionId).cancel();
            endSessionSendSignalTimers.remove(sessionId);
            endSessionSendSignalTasks.remove(sessionId);
        }
    }

    public void updateSessionTimer(String sessionId, Date finishDateTime) {
        if (endSessionCloseTimers.containsKey(sessionId)) {
            endSessionCloseTasks.get(sessionId).setFinishDateTime(finishDateTime);
        }
        if (endSessionSendSignalTimers.containsKey(sessionId)) {
            endSessionSendSignalTasks.get(sessionId).setFinishDateTime(finishDateTime);
        }
    }


}
