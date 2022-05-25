package com.pmp.nwms.web.rest.errors;

public class InvalidSheetDirectionException extends BadRequestAlertException {
    public InvalidSheetDirectionException(int sheetNo, String sheetName) {
        super(ErrorConstants.INVALID_SHEET_DIRECTION_TYPE, "Sheet " + sheetNo + " by name " + sheetName + " is not right to left.", "FileUpload", "file.sheet.invalid.direction");
    }
}
