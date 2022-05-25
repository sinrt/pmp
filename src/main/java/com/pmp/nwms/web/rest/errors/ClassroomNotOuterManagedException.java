package com.pmp.nwms.web.rest.errors;

public class ClassroomNotOuterManagedException extends BadRequestAlertException {
    public ClassroomNotOuterManagedException() {
        super(ErrorConstants.CLASSROOM_NOT_OUTER_MANAGED_TYPE, "Classroom not outer managed.", "classroomNotOuterManaged", "classroomNotOuterManaged");
    }
}
