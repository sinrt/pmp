package com.pmp.nwms.util.excel;

import com.pmp.nwms.util.ExcelUtil;
import com.pmp.nwms.web.rest.errors.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public abstract class AbstractExcelFileStructureValidator {
    public Workbook validateFile(String fileName, InputStream fileInputStream) {
        try {
            validateFileName(fileName);
            OPCPackage pkg = OPCPackage.open(fileInputStream);
            XSSFWorkbook workbook = new XSSFWorkbook(pkg);
            validateFileSheets(workbook);
            return workbook;
        } catch (InvalidFormatException | IOException e) {
            //todo handle properly
            throw new RuntimeException(e);
        }
    }

    private void validateFileName(String fileName) {
        String expectedFileName = getExpectedFileName();
        if (StringUtils.isNotEmpty(expectedFileName) && !fileName.equals(expectedFileName)) {
            throw new InvalidFileNameException(fileName, expectedFileName);
        }
    }

    private void validateFileSheets(XSSFWorkbook workbook) {
        List<String> expectedSheetNames = getExpectedSheetNames();
        if (workbook.getNumberOfSheets() != expectedSheetNames.size()) {
            throw new InvalidFileSheetNumbersException(workbook.getNumberOfSheets(), expectedSheetNames.size());
        }
        int counter = 0;
        Map<Integer, List<String>> sheetsExpectedColumnNames = getSheetsExpectedColumnNames();
        for (String expectedSheetName : expectedSheetNames) {
            XSSFSheet sheet = workbook.getSheetAt(counter);
            if (StringUtils.isNotEmpty(expectedSheetName) && !sheet.getSheetName().equals(expectedSheetName)) {
                throw new InvalidSheetNameException(counter + 1, sheet.getSheetName(), expectedSheetName);
            }
            if(!sheet.isRightToLeft()){
                throw new InvalidSheetDirectionException(counter + 1, sheet.getSheetName());
            }
            XSSFRow row = sheet.getRow(0);
            List<String> expectedColumnNames = sheetsExpectedColumnNames.get(counter);
            int colCounter = 0;
            for (String expectedColumnName : expectedColumnNames) {
                XSSFCell cell = row.getCell(colCounter);
                String cellValue = ExcelUtil.getCellStringValue(cell);
                if (!expectedColumnName.equals(cellValue.trim())) {
                    throw new InvalidColumnHeaderException(counter + 1, sheet.getSheetName(), colCounter + 1, cellValue, expectedColumnName);
                }
                colCounter++;
            }
            counter++;
        }
    }

    protected abstract String getExpectedFileName();

    protected abstract Map<Integer, List<String>> getSheetsExpectedColumnNames();

    protected abstract List<String> getExpectedSheetNames();
}
