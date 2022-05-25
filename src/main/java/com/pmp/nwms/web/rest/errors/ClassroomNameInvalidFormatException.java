package com.pmp.nwms.web.rest.errors;

public class ClassroomNameInvalidFormatException extends BadRequestAlertException {
    public ClassroomNameInvalidFormatException(){
        super(ErrorConstants.CLASSROOM_NAME_BAD_FORMAT_TYPE, "Classroom name format must be any combination of lower case characters, numeric characters and persian letters!", "classroomManagement", "classroom.name.bad.format");
    }
}
