package com.pmp.nwms.web.rest.errors;

public class InvalidClassroomCountException extends BadRequestAlertException {
    public InvalidClassroomCountException() {
        super(ErrorConstants.INVALID_CLASSROOM_COUNT_TYPE, "invalid classroom count ", "ClassroomS", "classroom.invalid.count");
    }
}
