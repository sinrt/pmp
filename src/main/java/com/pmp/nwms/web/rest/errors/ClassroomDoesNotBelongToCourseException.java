package com.pmp.nwms.web.rest.errors;

public class ClassroomDoesNotBelongToCourseException extends BadRequestAlertException {
    public ClassroomDoesNotBelongToCourseException(Long classroomId, Long courseId) {
        super(ErrorConstants.CLASSROOM_DOES_NOT_BELONG_TO_COURSE_TYPE, "Classroom by id " + classroomId + " does not belong to course by id " + courseId, "classroom", "classroom.does.not.belong.to.course");
    }
}
