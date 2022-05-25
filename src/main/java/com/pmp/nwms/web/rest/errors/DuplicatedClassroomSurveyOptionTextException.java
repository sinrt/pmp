package com.pmp.nwms.web.rest.errors;

public class DuplicatedClassroomSurveyOptionTextException extends  BadRequestAlertException {
    public DuplicatedClassroomSurveyOptionTextException(String duplicatedOptionText) {
        super(ErrorConstants.DUPLICATED_CLASSROOM_SURVEY_OPTION_TEXT_TYPE, "Duplicated classroom survey option text " + duplicatedOptionText + " has been sent.", "classroomSurveyOption", "duplicatedClassroomSurveyOptionText");
    }
}
