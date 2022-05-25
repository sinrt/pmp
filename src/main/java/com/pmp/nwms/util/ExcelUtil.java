package com.pmp.nwms.util;

import com.pmp.nwms.web.rest.errors.InvalidCellTypeException;
import com.pmp.nwms.web.rest.errors.InvalidCellValueException;
import com.pmp.nwms.web.rest.errors.InvalidFileFormatException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.text.DecimalFormat;

public class ExcelUtil {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#");

    public static long getCellLongValue(Cell cell) {
        try {
            long result;
            if (cell.getCellType().equals(CellType.STRING)) {
                result = Long.parseLong(cell.getStringCellValue());
            } else if (cell.getCellType().equals(CellType.NUMERIC)) {
                double numericCellValue = cell.getNumericCellValue();
                result = (long) numericCellValue;
            } else {
                throw new InvalidCellTypeException(cell.getColumnIndex() + 1, cell.getRowIndex() + 1);
            }
            return result;
        } catch (NumberFormatException e) {
            throw new InvalidFileFormatException();
        }
    }

    public static String getCellNumericStringValue(Cell cell) {
        String result;
        if (cell.getCellType().equals(CellType.STRING)) {
            result = cell.getStringCellValue();
            if (!NumberUtil.isAllDigits(result)) {
                throw new InvalidCellValueException(cell.getColumnIndex() + 1, cell.getRowIndex() + 1);
            }
        } else if (cell.getCellType().equals(CellType.NUMERIC)) {
            result = DECIMAL_FORMAT.format(cell.getNumericCellValue());
        } else {
            throw new InvalidCellTypeException(cell.getColumnIndex() + 1, cell.getRowIndex() + 1);
        }
        return result;
    }

    public static String getCellStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        if (cell.getCellType().equals(CellType.STRING)) {
            return cell.getStringCellValue();
        }
        if (cell.getCellType().equals(CellType.NUMERIC)) {
            return DECIMAL_FORMAT.format(cell.getNumericCellValue());
        }

        if (cell.getCellType().equals(CellType.BLANK)) {
            return null;
        }
        throw new InvalidCellTypeException(cell.getColumnIndex(), cell.getRowIndex());
    }

    public static boolean isCellEmpty(Cell cell) {
        return cell == null || cell.getCellType().equals(CellType.BLANK);
    }
}
