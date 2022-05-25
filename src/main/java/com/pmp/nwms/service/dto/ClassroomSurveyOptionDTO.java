package com.pmp.nwms.service.dto;

import java.io.Serializable;

public interface ClassroomSurveyOptionDTO extends Serializable {
    Long getId();

    Long getClassroomSurveyId();

    String getOptionText();

    Integer getOptionOrder();
}
