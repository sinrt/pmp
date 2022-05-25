package com.pmp.nwms.web.rest.errors;

public class UnableToDeleteClassroomDueToActiveSessionException extends BadRequestAlertException {
    public UnableToDeleteClassroomDueToActiveSessionException() {
        super(ErrorConstants.UNABLE_TO_DELETE_CLASSROOM_DUE_TO_ACTIVE_SESSION_TYPE, "Unable To Delete Classroom Due To Active Session.", "Classroom", "unableToDeleteClassroomDueToActiveSession");
    }
}
