package com.pmp.nwms.web.rest.errors;

public class ClassroomSurveyCanNotBeReansweredException extends BadRequestAlertException {
    public ClassroomSurveyCanNotBeReansweredException() {
        super(ErrorConstants.CLASSROOM_SURVEY_CAN_NOT_BE_REANSWERED_TYPE, "Classroom survey can not be reanswered", "ClassroomSurveyAnswer", "classroom.survey.can.not.be.reanswered.type");
    }
}
