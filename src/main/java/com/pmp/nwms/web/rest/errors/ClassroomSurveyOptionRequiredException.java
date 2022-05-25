package com.pmp.nwms.web.rest.errors;

public class ClassroomSurveyOptionRequiredException extends BadRequestAlertException {
    public ClassroomSurveyOptionRequiredException() {
        super(ErrorConstants.CLASSROOM_SURVEY_OPTION_REQUIRED_TYPE, "classroom survey option required", "classroomSurveyOptionRequired", "classroomSurveyOptionRequired");
    }
}
