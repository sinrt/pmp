package com.pmp.nwms.web.rest.errors;

public class NotWebinarCourseException extends BadRequestAlertException {
    public NotWebinarCourseException() {
        super(ErrorConstants.NOT_WEBINAR_COURSE_TYPE, "not webinar course.", "ClassroomManagement", "not.webinar.course");
    }
}
