package com.pmp.nwms.service.so;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;
import com.pmp.nwms.service.enums.AuthenticateLogReportMode;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class AuthenticateLogReportSo extends BaseSearchObject {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date fromCallDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date toCallDateTime;

    @NotNull
    private AuthenticateLogReportMode mode;

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

    public AuthenticateLogReportMode getMode() {
        return mode;
    }

    public void setMode(AuthenticateLogReportMode mode) {
        this.mode = mode;
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
            && mode == null
            && success == null;

    }
}
