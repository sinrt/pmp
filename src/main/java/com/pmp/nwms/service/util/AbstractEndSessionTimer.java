package com.pmp.nwms.service.util;

import com.pmp.nwms.NwmsConfig;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.TimerTask;

public abstract class AbstractEndSessionTimer extends TimerTask {
    protected NwmsConfig nwmsConfig;
    protected RestTemplate restTemplate;
    protected Date finishDateTime;

    public AbstractEndSessionTimer(NwmsConfig nwmsConfig, RestTemplate restTemplate, Date finishDateTime) {
        this.nwmsConfig = nwmsConfig;
        this.restTemplate = restTemplate;
        this.finishDateTime = finishDateTime;
    }

    public boolean setFinishDateTime(Date finishDateTime) {
        if (!this.finishDateTime.equals(finishDateTime)) {
            this.finishDateTime = finishDateTime;
            return true;
        }
        return false;
    }
}
