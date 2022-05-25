package com.pmp.nwms.web.rest.errors;

public class ClassroomNotFoundByNameException extends BadRequestAlertException {
    public ClassroomNotFoundByNameException() {
        super(ErrorConstants.CLASSROOM_NOT_FOUND_BY_NAME_TYPE, "Classroom not found by name", "classroomNotFound", "classroomNotFound");
    }

}
