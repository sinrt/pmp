package com.pmp.nwms.web.rest.errors;

public class DuplicatedClassroomSurveyOptionOrderException extends BadRequestAlertException {
    public DuplicatedClassroomSurveyOptionOrderException(Integer duplicatedOrder) {
        super(ErrorConstants.DUPLICATED_CLASSROOM_SURVEY_OPTION_ORDER_TYPE, "Duplicated classroom survey option order " + duplicatedOrder + " has been sent.", "classroomSurveyOption", "duplicatedClassroomSurveyOptionOrder");
    }
}
