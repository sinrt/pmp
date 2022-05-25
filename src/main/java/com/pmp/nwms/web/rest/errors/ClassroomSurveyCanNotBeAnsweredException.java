package com.pmp.nwms.web.rest.errors;

public class ClassroomSurveyCanNotBeAnsweredException extends BadRequestAlertException {
    public ClassroomSurveyCanNotBeAnsweredException() {
        super(ErrorConstants.CLASSROOM_SURVEY_CAN_NOT_BE_ANSWERED_TYPE, "Classroom survey can not be answered", "ClassroomSurveyAnswer", "classroom.survey.can.not.be.answered");
    }
}
