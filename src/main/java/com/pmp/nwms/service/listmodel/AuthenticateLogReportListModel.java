package com.pmp.nwms.service.listmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;

import java.io.Serializable;
import java.util.Date;

public class AuthenticateLogReportListModel implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date callDateTime;
    private String userName;
    private String ipAddress;
    private boolean success;

    public AuthenticateLogReportListModel(Date callDateTime, String userName, String ipAddress, boolean success) {
        this.callDateTime = callDateTime;
        this.userName = userName;
        this.ipAddress = ipAddress;
        this.success = success;
    }

    public Date getCallDateTime() {
        return callDateTime;
    }

    public void setCallDateTime(Date callDateTime) {
        this.callDateTime = callDateTime;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
