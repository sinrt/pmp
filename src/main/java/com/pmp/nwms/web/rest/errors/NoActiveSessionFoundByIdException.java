package com.pmp.nwms.web.rest.errors;

public class NoActiveSessionFoundByIdException extends BadRequestAlertException {
    public NoActiveSessionFoundByIdException(String sessionId) {
        super(ErrorConstants.NO_ACTIVE_SESSION_FOUND_BY_ID_TYPE, "Active session by id " + sessionId + " noy found", "rubruwebhook", "sessionnotfount");
    }
}
