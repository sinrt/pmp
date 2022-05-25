package com.pmp.nwms.web.rest.errors;

public class DuplicatedCourseTitleException extends BadRequestAlertException {
    public DuplicatedCourseTitleException() {
        super(ErrorConstants.COURSE_TITLE_ALREADY_USED_TYPE, "Course title is already in use!", "courseManagement", "course.name.exists");
    }
}
