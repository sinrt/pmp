package com.pmp.nwms.web.rest.errors;

public class MaxFileCountExceededException extends BadRequestAlertException {
    public MaxFileCountExceededException(int maxFileCountPerSession) {
        super(ErrorConstants.MAX_FILE_COUNT_EXCEEDED_TYPE, "Max acceptable File Count is " + maxFileCountPerSession,
            "maxFileCountExceeded", "maxFileCountExceeded");
    }
}
