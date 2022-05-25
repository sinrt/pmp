package com.pmp.nwms.service.so;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;

import java.util.Date;

public class LogSo extends BaseSearchObject {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date fromCallDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date toCallDateTime;

    private String userName;
    private String ipAddress;
    private String logUri;
    private Boolean success;

    public Date getFromCallDateTime() {
        return fromCallDateTime;
    }

    public void setFromCallDateTime(Date fromCallDateTime) {
        this.fromCallDateTime = fromCallDateTime;
    }

    public Date getToCallDateTime() {
        return toCallDateTime;
    }

    public void setToCallDateTime(Date toCallDateTime) {
        this.toCallDateTime = toCallDateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLogUri() {
        return logUri;
    }

    public void setLogUri(String logUri) {
        this.logUri = logUri;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public boolean isClean() {
        return fromCallDateTime == null
            && toCallDateTime == null
            && (userName == null || userName.isEmpty())
            && (ipAddress == null || ipAddress.isEmpty())
            && (logUri == null|| logUri.isEmpty())
            && success == null;
    }
}
