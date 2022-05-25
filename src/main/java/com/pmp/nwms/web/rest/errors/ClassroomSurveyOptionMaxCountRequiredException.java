package com.pmp.nwms.web.rest.errors;

public class ClassroomSurveyOptionMaxCountRequiredException extends BadRequestAlertException {
    public ClassroomSurveyOptionMaxCountRequiredException(int expectedMaxCount, int actualCount) {
        super(ErrorConstants.CLASSROOM_SURVEY_OPTION_MAX_COUNT_REQUIRED_TYPE, "Classroom survey option max count required is " + expectedMaxCount + ", but sent "+ actualCount, "ClassroomSurveyOptionMaxCount", "ClassroomSurveyOptionMaxCount");
    }
}
