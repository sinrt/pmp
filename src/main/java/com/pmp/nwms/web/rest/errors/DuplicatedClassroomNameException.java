package com.pmp.nwms.web.rest.errors;

public class DuplicatedClassroomNameException extends BadRequestAlertException{
    public DuplicatedClassroomNameException() {
        super(ErrorConstants.CLASSROOM_NAME_ALREADY_USED_TYPE, "Classroom name is already in use!", "classroomManagement", "classroom.name.exists");
    }
}
