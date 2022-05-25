package com.pmp.nwms.web.rest.errors;

public class InvalidClassroomSurveyOptionException extends BadRequestAlertException {
    public InvalidClassroomSurveyOptionException() {
        super(ErrorConstants.INVALID_CLASSROOM_SURVEY_OPTION_TYPE, "Invalid Classroom Survey Option.", "invalidClassroomSurveyOption", "invalidClassroomSurveyOption");
    }
}
