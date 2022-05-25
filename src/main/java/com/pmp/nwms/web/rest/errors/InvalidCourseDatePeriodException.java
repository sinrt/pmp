package com.pmp.nwms.web.rest.errors;

public class InvalidCourseDatePeriodException extends BadRequestAlertException {
    public InvalidCourseDatePeriodException() {
        super(ErrorConstants.INVALID_COURSE_DATE_PERIOD_TYPE, "Course startTime must be before its finish time!", "courseManagement", "course.invalid.date.period");
    }
}
