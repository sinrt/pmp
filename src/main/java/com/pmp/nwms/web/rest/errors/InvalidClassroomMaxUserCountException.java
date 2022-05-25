package com.pmp.nwms.web.rest.errors;

public class InvalidClassroomMaxUserCountException extends BadRequestAlertException {
    public InvalidClassroomMaxUserCountException(Integer sentValue, Integer expectedMaxValue) {
        super(ErrorConstants.INVALID_CLASSROOM_MAX_USER_COUNT_TYPE, "invalid classroom max user count, max value can be " + expectedMaxValue + ", but " + sentValue + " was sent.", "ClassroomS", "classroom.invalid.max.user.count");
    }
}
