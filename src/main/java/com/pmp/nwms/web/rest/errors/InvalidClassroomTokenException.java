package com.pmp.nwms.web.rest.errors;

public class InvalidClassroomTokenException extends BadRequestAlertException {
    public InvalidClassroomTokenException() {
        super(ErrorConstants.INVALID_CLASSROOM_TOKEN_TYPE, "Invalid classroom token.", "ClassroomStudent", "invalid.classroom.token");
    }
}
