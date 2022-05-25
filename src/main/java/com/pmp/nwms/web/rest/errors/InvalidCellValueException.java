package com.pmp.nwms.web.rest.errors;

public class InvalidCellValueException extends BadRequestAlertException{
    public InvalidCellValueException(int colIndex, int rowIndex) {
        super(ErrorConstants.INVALID_CELL_VALUE_TYPE, "invalid cell value in row " + rowIndex + " and column " + colIndex, "ExcelFile", "excel.file.invalid.cell.value");
    }
}
