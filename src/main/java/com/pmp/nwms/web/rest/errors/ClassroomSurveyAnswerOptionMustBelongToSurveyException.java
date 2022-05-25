package com.pmp.nwms.web.rest.errors;

public class ClassroomSurveyAnswerOptionMustBelongToSurveyException extends BadRequestAlertException {
    public ClassroomSurveyAnswerOptionMustBelongToSurveyException() {
        super(ErrorConstants.CLASSROOM_SURVEY_ANSWER_OPTION_MUST_BELONG_TO_SURVEY_TYPE, "Classroom survey answer option must belong to survey", "ClassroomSurveyAnswer", "classroom-survey-answer-option-must-belong-to-survey");
    }
}
