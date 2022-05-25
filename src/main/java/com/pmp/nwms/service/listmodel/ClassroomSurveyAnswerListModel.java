package com.pmp.nwms.service.listmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;

import java.io.Serializable;
import java.util.Date;

public interface ClassroomSurveyAnswerListModel extends Serializable {
    Long getUserId();

    String getClientId();

    String getToken();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    Date getAnswerDateTime();

    String getAnswerText();

    Long getSelectedOptionId();

    String getSelectedOptionText();
}
