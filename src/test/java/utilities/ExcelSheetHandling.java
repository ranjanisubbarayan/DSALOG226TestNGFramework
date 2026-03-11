
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

    private String getCellString(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    
    @SuppressWarnings("resource")

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
