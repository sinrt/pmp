package com.pmp.nwms.web.rest.errors;

public class InvalidFileNameException extends BadRequestAlertException {
    public InvalidFileNameException(String fileName, String expectedFileName) {
        super(ErrorConstants.INVALID_FILE_NAME_TYPE, "invalid file name, expected file name is " + expectedFileName + ", but actual file name is " + fileName, "FileUpload", "file.invalid.name");
    }
}
