package com.pmp.nwms.web.rest.errors;

public class InvalidColumnHeaderException extends BadRequestAlertException {
    public InvalidColumnHeaderException(int sheetNo, String sheetName, int colNo, String columnName, String expectedColumnName) {
        super(ErrorConstants.INVALID_COLUMN_HEADER_TYPE, "invalid column header at sheet " + sheetNo + " by name " + sheetName + ", expected column name is " + expectedColumnName + ", but actual column name is " + columnName, "FileUpload", "file.invalid.column.header");

    }
}
