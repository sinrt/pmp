package com.pmp.nwms.web.rest.errors;

public class UserMaxClassroomCountExceededException extends BadRequestAlertException {
    public UserMaxClassroomCountExceededException(int allowedSessionCount, int actualSessionCount) {
        super(ErrorConstants.CLASSROOM_MAX_COUNT_EXCEEDED_TYPE, "User max classroom exceeded!", "classroomManagement", "classroom.max.count.exceeded");
    }
}
