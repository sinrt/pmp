package com.pmp.nwms.web.rest.errors;

public class ClassroomSurveyAnswerOptionMustBeSelectedException extends BadRequestAlertException {
    public ClassroomSurveyAnswerOptionMustBeSelectedException() {
        super(ErrorConstants.CLASSROOM_SURVEY_ANSWER_OPTION_MUST_BE_SELECTED_TYPE, "Classroom survey answer option must be selected.", "ClassroomSurveyAnswer", "classroom.survey.answer.option.must.be.selected.type");
    }
}
