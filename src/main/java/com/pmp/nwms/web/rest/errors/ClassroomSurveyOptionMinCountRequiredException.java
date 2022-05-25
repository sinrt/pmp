package com.pmp.nwms.web.rest.errors;

public class ClassroomSurveyOptionMinCountRequiredException extends BadRequestAlertException {
    public ClassroomSurveyOptionMinCountRequiredException(int expectedMinCount, int actualCount) {
        super(ErrorConstants.CLASSROOM_SURVEY_OPTION_MIN_COUNT_REQUIRED_TYPE, "Classroom survey option min count required is " + expectedMinCount + ", but sent "+ actualCount, "ClassroomSurveyOptionMinCount", "ClassroomSurveyOptionMinCount");
    }
}
