package com.pmp.nwms.web.rest.errors;

public class InvalidFileSizeException extends BadRequestAlertException {
    public InvalidFileSizeException(double fileSize, int maxFileSize) {
        super(ErrorConstants.INVALID_FILE_SIZE_TYPE, "File size is " + fileSize + " but max file size is " + maxFileSize, "invalidFileSize", "invalidFileSize");
    }
}
