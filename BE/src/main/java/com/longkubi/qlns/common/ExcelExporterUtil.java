package com.longkubi.qlns.common;


import com.longkubi.qlns.model.dto.EmployeeDto;
import com.longkubi.qlns.model.dto.PositionDto;
import com.longkubi.qlns.model.dto.ResponseData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class ExcelExporterUtil {

    public static <T> ResponseEntity<byte[]> exportToExcel(List<T> dataList, String[] title, String fileName) throws IOException {
        // Create workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        // Create header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i); // dãn cột cho vừa với nội dung ô

        }

        // Create data rows
        for (int rowIndex = 0; rowIndex < dataList.size(); rowIndex++) {
            T obj = dataList.get(rowIndex);
            Row row = sheet.createRow(rowIndex + 1);
            for (int colIndex = 0; colIndex < title.length; colIndex++) {
                if (colIndex == 0) {
                    CellType cellType = getCellType(CellType.NUMERIC);
                    Cell cell = row.createCell(colIndex, cellType);
                    cell.setCellValue(rowIndex + 1);
                    continue;
                }
                String fieldName = title[colIndex];
                Object value = getValue(obj, fieldName);
                CellType cellType = getCellType(value);
                Cell cell = row.createCell(colIndex, cellType);
                if (value instanceof Date) {
                    cell.setCellValue((Date) value);
                    cell.setCellStyle(dateCellStyle);
                } else {
                    setCellValue(cell, value);
                    //cell.setCellStyle(style);//set căn giữa
                }
                sheet.autoSizeColumn(colIndex);
            }
        }

        // Write workbook to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        byte[] bytes = baos.toByteArray();

        // Set content type and headers for response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        headers.setContentDispositionFormData(fileName, fileName);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");


        // Create response entity with byte array and headers
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);

        return responseEntity;
    }

    private static CellType getCellType(Object value) {
        if (value == null) {
            return CellType.BLANK;
        } else if (value instanceof String) {
            return CellType.STRING;
        } else if (value instanceof Integer || value instanceof Long) {
            return CellType.NUMERIC;
        } else if (value instanceof Double || value instanceof Float) {
            return CellType.NUMERIC;
        } else if (value instanceof Boolean) {
            return CellType.BOOLEAN;
        } else if (value instanceof Enum) {
            return CellType.STRING;
        } else {
            return CellType.STRING;
        }
    }

    //    private static <T> Object getValue(T obj, String fieldName) {
//        try {
//            Field field = obj.getClass().getDeclaredField(fieldName);
//            field.setAccessible(true);
//            return field.get(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
    private static <T> Object getValue(T obj, String fieldName) {
        try {
            String positionName = null;
            if (obj instanceof EmployeeDto) {
                EmployeeDto employee = (EmployeeDto) obj;
                for (PositionDto position : employee.getPositions()) {
                    positionName = position.getName();
                }
            }
            String[] fieldNames = fieldName.split("\\.");
            Object value = obj;
            for (String name : fieldNames) {
                if (name.equals("position")) {
                    value = positionName;
                } else {
                    assert value != null;
                    Field field = value.getClass().getDeclaredField(name);
                    field.setAccessible(true);
                    value = field.get(value);
                }
            }
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setCellValue(Cell cell, Object value) {
        if (value == null) {
            cell.setCellType(CellType.BLANK);
        } else if (value instanceof String) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue((Long) value);
        } else if (value instanceof Double) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue((Double) value);
        } else if (value instanceof Float) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue((Float) value);
        } else if (value instanceof Boolean) {
            cell.setCellType(CellType.BOOLEAN);
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Enum) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(value.toString());
        } else {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(value.toString());
        }
    }
}