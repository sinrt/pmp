package com.pmp.nwms.web.rest.errors;

public class SessionNotStartedException extends BadRequestAlertException {
    public SessionNotStartedException() {
        super(ErrorConstants.SESSION_NOT_STARTED_TYPE, "Session not started.", "sessionNotStarted", "sessionNotStarted");
    }
}
