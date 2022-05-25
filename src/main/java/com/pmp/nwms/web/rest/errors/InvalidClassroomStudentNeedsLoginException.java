package com.pmp.nwms.web.rest.errors;

public class InvalidClassroomStudentNeedsLoginException extends BadRequestAlertException {
    public InvalidClassroomStudentNeedsLoginException(int needsLogin) {
        super(ErrorConstants.INVALID_CLASSROOM_STUDENT_NEEDS_LOGIN, "invalid needsLogin value " + needsLogin + ", expected values are 0 and 1", "FileUpload", "file.invalid.needsLogin");
    }
}
