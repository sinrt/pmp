package com.pmp.nwms.web.rest.errors;

public class ClassroomSurveyAnswerTextCanNotBeEmptyException extends BadRequestAlertException {
    public ClassroomSurveyAnswerTextCanNotBeEmptyException() {
        super(ErrorConstants.CLASSROOM_SURVEY_ANSWER_TEXT_CAN_NOT_BE_EMPTY_TYPE, "Classroom survey answer text can not be empty.", "ClassroomSurveyAnswer", "classroom.survey.answer.text.can.not.be.empty");
    }
}
