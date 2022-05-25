package com.pmp.nwms.service.util;

import com.pmp.nwms.NwmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class EndSessionCloseTimer extends AbstractEndSessionTimer {

    private final Logger log = LoggerFactory.getLogger(EndSessionCloseTimer.class);

    private String closeSessionUrl;

    public EndSessionCloseTimer(String sessionId, Date finishDateTime, String rubruWsUrl, RestTemplate restTemplate, NwmsConfig nwmsConfig) {
        super(nwmsConfig, restTemplate, finishDateTime);
        this.closeSessionUrl = "https://" + rubruWsUrl + "/secured-api/sessions/" + sessionId + "/sessionClosedDueToEndTime";
    }

    @Override
    public boolean setFinishDateTime(Date finishDateTime) {
        boolean changed = super.setFinishDateTime(finishDateTime);
        if (changed) {
            Date now = new Date();
            if (this.finishDateTime.before(now)) {
                callEndSessionApi();
            }
        }
        return changed;
    }

    @Override
    public void run() {
        Date now = new Date();
        if (this.finishDateTime.before(now)) {
            try {
                callEndSessionApi();
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
            }
        }
    }

    private void callEndSessionApi() {
        HttpHeaders headers = SignalServerRestCallUtil.makeHttpHeaders(MediaType.APPLICATION_FORM_URLENCODED, nwmsConfig);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        restTemplate.exchange(closeSessionUrl, HttpMethod.DELETE, requestEntity, Void.class);
    }

}
