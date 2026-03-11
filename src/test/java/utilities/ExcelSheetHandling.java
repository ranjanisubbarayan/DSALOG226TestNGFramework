
package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelSheetHandling {

	private String path;
    private Workbook workbook;
    

    public ExcelSheetHandling(String path) {
    	this.path = path;
        try (InputStream is = new FileInputStream(path)) {
            workbook = new XSSFWorkbook(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to open Excel file: " + path, e);
        }
    }
    public String getPath() {
        return path;
    }

    public List<Map<String, String>> getSheetData(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) throw new RuntimeException("Sheet not found: " + sheetName);

        Iterator<Row> rows = sheet.iterator();
        if (!rows.hasNext()) return Collections.emptyList();

        Row headerRow = rows.next();
        List<String> headers = new ArrayList<>();
        for (Cell c : headerRow) headers.add(getCellString(c));

        List<Map<String, String>> data = new ArrayList<>();
        while (rows.hasNext()) {
            Row r = rows.next();
            Map<String, String> rowMap = new LinkedHashMap<>();
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = r.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                rowMap.put(headers.get(i), getCellString(cell));
            }
            data.add(rowMap);
        }
        return data;
    }

    public Map<String, String> getRowData(String sheetName, String testId) {
        List<Map<String, String>> allRows = getSheetData(sheetName);
        for (Map<String, String> row : allRows) {
            String id = row.get("testId");
            if (id != null && id.equalsIgnoreCase(testId)) {
                return row;
            }
        }
        throw new RuntimeException("No data found for testId: " + testId + " in sheet: " + sheetName);
    }

    private String getCellString(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }
	
    public static String getCellData(String sheetName, int rowNum, int colNum) {
        String value = "";
        try (FileInputStream fis = new FileInputStream(ConfigReader.getProperty("excelPath"));
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);
            value = cell.getStringCellValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
    
    public List<String> getCodeLines(String sheetName, String testCaseName) {
    	 List<String> codeLines = new ArrayList<>();
    	 List<Map<String, String>> allRows = getSheetData(sheetName);
    	 for (Map<String, String> row : allRows) {
    		 String testcaseValue = row.get("testcase");
    		 if (testCaseName.equalsIgnoreCase(testcaseValue)) {
    			 for (Map.Entry<String, String> cell : row.entrySet()) {

    	                String columnName = cell.getKey(); 
    	                String cellValue  = cell.getValue();
    	                if (!columnName.equalsIgnoreCase("testcase") && !cellValue.isBlank()) {
    	                    codeLines.add(cellValue);
    	                }
    	            }
    	        }
    	    }
    	 return codeLines;
    }
    
    @SuppressWarnings("resource")
    public List<String> getCodeByColumn(String sheetName, String columnName) {

        List<String> codeLines = new ArrayList<>();

        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Sheet not found: " + sheetName);
        }

        Row headerRow = sheet.getRow(0);
        int columnIndex = -1;

        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                columnIndex = cell.getColumnIndex();
                break;
            }
        }

        if (columnIndex == -1) {
            throw new RuntimeException("Column not found: " + columnName);
        }

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                codeLines.add(getCellString(cell));
            }
        }

        return codeLines;
    }


    public void writeCellData(String sheetName, int rowNum, int colNum, String value) {
        Workbook workbook = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis); 
            fis.close(); 

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) sheet = workbook.createSheet(sheetName);

            Row row = sheet.getRow(rowNum);
            if (row == null) row = sheet.createRow(rowNum);

            Cell cell = row.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellValue(value);

            fos = new FileOutputStream(path); 
            workbook.write(fos); 

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) workbook.close();
                if (fis != null) fis.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
