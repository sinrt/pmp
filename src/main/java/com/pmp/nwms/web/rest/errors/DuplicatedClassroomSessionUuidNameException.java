package com.pmp.nwms.web.rest.errors;

public class DuplicatedClassroomSessionUuidNameException extends BadRequestAlertException{
    public DuplicatedClassroomSessionUuidNameException() {
        super(ErrorConstants.CLASSROOM_SESSION_UUID_NAME_ALREADY_USED_TYPE, "Classroom name is already in use!", "classroomManagement", "classroom.name.exists");
    }
}
