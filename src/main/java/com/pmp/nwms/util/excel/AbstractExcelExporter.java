package com.pmp.nwms.util.excel;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.util.MessageUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;

public abstract class AbstractExcelExporter<T> {

    protected static final Locale FA = new Locale("fa");

    protected List<String> headers;
    @Autowired
    protected MessageSource messageSource;
    @Autowired
    protected NwmsConfig nwmsConfig;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public byte[] export(List<T> data, boolean addRowNumCol, Object... extraData) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet(MessageUtil.getMessage(messageSource, getSheetNameKey(), getSheetNameArgs(), FA));
            sheet.setRightToLeft(true);

            int rowNum = 0;

            int columnCount = createHeaderRow(wb, sheet, rowNum, addRowNumCol, extraData);
            rowNum++;
            CellStyle numericCellStyle = wb.createCellStyle();
            DataFormat dataFormat = wb.createDataFormat();
            numericCellStyle.setDataFormat(dataFormat.getFormat("#,##0.00;[Red](#,##0.00)"));


            CellStyle longNumericCellStyle = wb.createCellStyle();
            longNumericCellStyle.setDataFormat(dataFormat.getFormat("#,##0;[Red](#,##0)"));

            if (data != null && !data.isEmpty()) {
                for (T t : data) {
                    createDataRow(sheet, numericCellStyle, longNumericCellStyle, t, rowNum, addRowNumCol, extraData);
                    rowNum++;
                }
            }


            for (int i = 0; i < columnCount; i++) {
                try {
                    sheet.autoSizeColumn(i);
                } catch (Exception ignored) {
                }
            }

            wb.write(os);
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                os.close();
            } catch (Exception ignored) {
            }
        }
    }

    protected abstract Object[] getSheetNameArgs();

    protected abstract String getSheetNameKey();

    private int createHeaderRow(Workbook workbook, Sheet sheet, int rowNum, boolean addRowNumCol, Object[] extraData) {
        CellStyle headerTitleCellStyle = workbook.createCellStyle();
        headerTitleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerTitleCellStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        headerTitleCellStyle.setFont(font);

        Row titleRow = sheet.createRow(rowNum);

        List<String> titles = makeHeaderTitles(addRowNumCol);

        int cellCounter = 0;
        for (String title : titles) {
            Cell titleCell = titleRow.createCell(cellCounter);
            titleCell.setCellStyle(headerTitleCellStyle);
            titleCell.setCellValue(title);
            cellCounter++;
        }
        return cellCounter;
    }

    private void createDataRow(Sheet sheet, CellStyle numericCellStyle, CellStyle longNumericCellStyle, T t, int rowNum, boolean addRowNumCol, Object[] extraData) {
        Row dataRow = sheet.createRow(rowNum);
        List<ExportCellData> data = makeRowExportData(t, extraData);
        int cellCounter = 0;
        if (addRowNumCol) {
            Cell titleCell = dataRow.createCell(cellCounter);
            titleCell.setCellValue(rowNum);
            titleCell.setCellStyle(longNumericCellStyle);
            titleCell.setCellType(CellType.NUMERIC);
            cellCounter++;
        }
        for (ExportCellData cellData : data) {
            Cell titleCell = dataRow.createCell(cellCounter);

            if (cellData.isLongNumeric() && cellData.getValue() != null) {
                titleCell.setCellStyle(longNumericCellStyle);
                Long longValue = cellData.getLongValue();
                if (longValue != null) {
                    titleCell.setCellType(CellType.NUMERIC);
                    titleCell.setCellValue(longValue);
                }
            } else if (cellData.isDoubleNumeric() && cellData.getValue() != null) {
                titleCell.setCellStyle(numericCellStyle);
                Double doubleValue = cellData.getDoubleValue();
                if (doubleValue != null) {
                    titleCell.setCellType(CellType.NUMERIC);
                    titleCell.setCellValue(doubleValue);
                }
            } else {
                titleCell.setCellType(CellType.STRING);
                titleCell.setCellValue(cellData.getValue());
            }
            cellCounter++;
        }
    }

    protected abstract List<String> makeHeaderTitles(boolean addRowNumCol);

    protected abstract List<ExportCellData> makeRowExportData(T t, Object[] extraData);

}

