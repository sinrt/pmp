package com.pmp.nwms.web.rest.errors;

public class ClassroomSurveyTypeNotSupportedException extends BadRequestAlertException {
    public ClassroomSurveyTypeNotSupportedException() {
        super(ErrorConstants.CLASSROOM_SURVEY_TYPE_NOT_SUPPORTED_TYPE, "Classroom survey type not supported.", "ClassroomSurvey", "classroomSurveyTypeNotSupported");
    }
}
