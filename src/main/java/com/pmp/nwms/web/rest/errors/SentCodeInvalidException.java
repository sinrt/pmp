package com.pmp.nwms.web.rest.errors;

public class SentCodeInvalidException extends BadRequestAlertException {
    private static final long serialVersionUID = 1L;

    public SentCodeInvalidException() {
        super(ErrorConstants.SENTCODE_INVALID_TYPE, "SentCode is Invalid!", "enrollment", "sentcodeinvalid");
    }
}
