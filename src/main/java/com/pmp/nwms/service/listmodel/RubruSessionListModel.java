package com.pmp.nwms.service.listmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;

import java.io.Serializable;
import java.util.Date;

public interface RubruSessionListModel extends Serializable {
    Long getId();

    String getSessionId();

    String getSessionName();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    Date getStartDate();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    Date getEndDate();

    Long getDuration();

    Long getClassroomId();
}
