package com.pmp.nwms.web.rest.errors;

public class InvalidCellTypeException extends BadRequestAlertException {
    public InvalidCellTypeException(int colIndex, int rowIndex) {
        super(ErrorConstants.INVALID_CELL_TYPE_TYPE, "invalid cell type in row " + rowIndex + " and column " + colIndex, "ExcelFile", "excel.file.invalid.cell.type");
    }
}
