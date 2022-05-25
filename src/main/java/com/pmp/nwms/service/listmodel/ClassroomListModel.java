package com.pmp.nwms.service.listmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;

import java.io.Serializable;
import java.util.Date;

public interface ClassroomListModel extends Serializable {
    Long getId();

    String getName();

    String getSessionUuidName();

    boolean isGuestSession();

    boolean isGuestSessionReqPass();

    String getGuestPassword();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    Date getStartDateTime();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    Date getFinishDateTime();

    String getConnectionType();

    String getResolution();

    Integer getFrameRate();

    Boolean getLock();
}
