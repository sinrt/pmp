package com.pmp.nwms.web.rest.errors;

import com.pmp.nwms.domain.enums.SurveyStatus;

import java.util.List;

public class InvalidClassroomSurveyStatusException extends BadRequestAlertException {
    public InvalidClassroomSurveyStatusException(SurveyStatus actualStatus, List<SurveyStatus> expectedStatuses) {
        super(ErrorConstants.INVALID_CLASSROOM_SURVEY_STATUS_TYPE, "ClassroomSurvey status must be one of " + expectedStatuses + ", but it is " + actualStatus, "ClassroomSurvey", "invalidClassroomSurveyStatus");
    }
}
