package com.pmp.nwms.web.rest.errors;

public class InvalidFileSheetNumbersException extends BadRequestAlertException {
    public InvalidFileSheetNumbersException(int numberOfSheets, int expectedNumberOfSheets) {
        super(ErrorConstants.INVALID_FILE_SHEET_NUMBERS_TYPE, "invalid file sheets number. expected sheet count is " + expectedNumberOfSheets + ", but file has " + numberOfSheets + " sheets.", "FileUpload", "file.invalid.format");
    }
}
