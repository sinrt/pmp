package com.pmp.nwms.service.dto;

import java.io.Serializable;

public interface ClassroomSurveyAnswerReportDto extends Serializable {
    Long getSelectedOptionId();

    String getSelectedOptionText();

    Long getSelectedOptionCount();
}
