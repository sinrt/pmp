package com.pmp.nwms.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;

import java.util.Date;

public interface RubruSessionParticipantsReportVM {
    String getParticipantName();

    Long getDuration();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    Date getJoinDateTime();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    Date getLeaveDateTime();

    String getLogin();

    String getParticipantKey();
}
