package com.pmp.nwms.web.rest.errors;

public class InvalidFileFormatException extends BadRequestAlertException {
    public InvalidFileFormatException() {
        super(ErrorConstants.INVALID_FILE_FORMAT, "invalid file format ", "FileUpload", "file.invalid.format");
    }
}
