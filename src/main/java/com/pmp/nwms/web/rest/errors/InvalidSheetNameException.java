package com.pmp.nwms.web.rest.errors;

public class InvalidSheetNameException extends BadRequestAlertException {
    public InvalidSheetNameException(int sheetNo, String sheetName, String expectedSheetName) {
        super(ErrorConstants.INVALID_SHEET_NAME_TYPE, "Sheet by number " + sheetNo + " must be named " + expectedSheetName + " but its name is " + sheetName, "FileUpload", "file.invalid.sheet.name");

    }
}
