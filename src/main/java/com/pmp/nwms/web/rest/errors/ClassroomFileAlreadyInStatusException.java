package com.pmp.nwms.web.rest.errors;

import com.pmp.nwms.domain.enums.VisibilityStatus;

public class ClassroomFileAlreadyInStatusException extends BadRequestAlertException {
    public ClassroomFileAlreadyInStatusException(Long id, VisibilityStatus status) {
        super(ErrorConstants.CLASSROOM_FILE_ALREADY_IN_STATUS_TYPE, "ClassroomFile by id " + id + " already is " + status, "classroomFileStatus", "classroomFileStatus");
    }
}
