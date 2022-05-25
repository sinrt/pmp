package com.pmp.nwms.web.rest.errors;

public class CourseAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public CourseAlreadyUsedException() {
        super(ErrorConstants.COURSE_ALREADY_USED_TYPE, "Course name already used!", "courseExists", "courseexists");
    }
}
