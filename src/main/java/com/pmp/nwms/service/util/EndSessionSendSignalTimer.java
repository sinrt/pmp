package com.pmp.nwms.service.util;

import com.google.gson.Gson;
import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.util.MessageUtil;
import com.pmp.nwms.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EndSessionSendSignalTimer extends AbstractEndSessionTimer {

    private final Logger log = LoggerFactory.getLogger(EndSessionSendSignalTimer.class);

    protected static final Locale FA = new Locale("fa");
    private MessageSource messageSource;
    private String sessionId;
    private String sendSignalUrl;

    public EndSessionSendSignalTimer(String sessionId, Date finishDateTime, String rubruWsUrl, MessageSource messageSource, RestTemplate restTemplate, NwmsConfig nwmsConfig) {
        super(nwmsConfig, restTemplate, finishDateTime);
        this.sessionId = sessionId;
        this.messageSource = messageSource;
        this.sendSignalUrl = "https://" + rubruWsUrl + "/secured-api/signal";
    }


    @Override
    public void run() {
        Date now = new Date();
        if (this.finishDateTime.after(now)) {
            int diff = DateUtil.getDatesDiffInMinutes(finishDateTime, now);
            if (nwmsConfig.getFinishClassroomInformTimes().contains(diff)) {
                try {
                    sendSignalMessage(diff);
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
    }

    private void sendSignalMessage(int diff) {
        HttpHeaders headers = SignalServerRestCallUtil.makeHttpHeaders(MediaType.APPLICATION_JSON_UTF8, nwmsConfig);
        Gson gson = new Gson();
        Map<String, Object> src = new HashMap<>();
        src.put("session", sessionId);
        src.put("type", nwmsConfig.getFinishClassroomInformSignalType());
        String data = diff + "||" + MessageUtil.getMessage(messageSource, "finish.classroom.inform.message", new Object[]{diff}, FA);
        src.put("data", data);
        String body = gson.toJson(src);
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        restTemplate.exchange(sendSignalUrl, HttpMethod.POST, requestEntity, Void.class);
    }

}
