package com.pmp.nwms.web.rest.errors;

public class UserHasNoActivePlanException extends BadRequestAlertException {
    public UserHasNoActivePlanException(){
        super(ErrorConstants.USER_HAS_NO_ACTIVE_PLAN, "User has no active plan!", "classroomManagement", "no.active.plan");;
    }
}
