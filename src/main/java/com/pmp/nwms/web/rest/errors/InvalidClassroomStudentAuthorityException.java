package com.pmp.nwms.web.rest.errors;

public class InvalidClassroomStudentAuthorityException extends BadRequestAlertException {
    public InvalidClassroomStudentAuthorityException(String authorityName) {
        super(ErrorConstants.INVALID_CLASSROOM_STUDENT_AUTHORITY_TYPE, authorityName +" is not accepted as authorityName ", "ClassroomStudent", "classroom.student.invalid.authority.name");
    }
}
