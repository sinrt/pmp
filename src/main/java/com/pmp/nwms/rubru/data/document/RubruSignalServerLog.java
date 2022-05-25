package com.pmp.nwms.rubru.data.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Document(collection = "openvidu-logs")
public class RubruSignalServerLog implements Serializable {
    @Id
    public String id;
    private String action;
    private Boolean check;
    private String description;
    @Indexed
    private String eventName;
    private Map<String, Object> logInfo;
    private Date logDate;
    private String ovwsUrl;
    private String appUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Map<String, Object> getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(Map<String, Object> logInfo) {
        this.logInfo = logInfo;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getOvwsUrl() {
        return ovwsUrl;
    }

    public void setOvwsUrl(String ovwsUrl) {
        this.ovwsUrl = ovwsUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
}
