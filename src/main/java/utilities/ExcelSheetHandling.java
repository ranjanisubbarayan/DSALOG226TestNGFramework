package utilities;

import java.io.FileInputStream;
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
}
