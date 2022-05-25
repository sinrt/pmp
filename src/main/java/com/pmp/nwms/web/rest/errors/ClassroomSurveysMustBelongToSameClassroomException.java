package com.pmp.nwms.web.rest.errors;

public class ClassroomSurveysMustBelongToSameClassroomException extends BadRequestAlertException {
    public ClassroomSurveysMustBelongToSameClassroomException() {
        super(ErrorConstants.CLASSROOM_SURVEYS_MUST_BELONG_TO_SAME_CLASSROOM_TYPE, "Classroom surveys must belong to same classroom.", "ClassroomSurveyAnswer", "classroom.surveys.must.belong.to.same.classroom");
    }
}
