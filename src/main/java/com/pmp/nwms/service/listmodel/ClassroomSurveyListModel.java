package com.pmp.nwms.service.listmodel;

import com.pmp.nwms.domain.enums.SurveyStatus;
import com.pmp.nwms.domain.enums.SurveyType;

import java.io.Serializable;

public interface ClassroomSurveyListModel extends Serializable {
    Long getId();

    String getQuestion();

    SurveyType getSurveyType();

    SurveyStatus getStatus();
}
