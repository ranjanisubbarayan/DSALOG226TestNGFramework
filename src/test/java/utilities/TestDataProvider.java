package utilities;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.testng.annotations.DataProvider;

public class TestDataProvider {
	
    private final String excelPath = "src/main/resources/ExcelSheet/DsAlgoTestData.xlsx";
    private ExcelSheetHandling excel = new ExcelSheetHandling(excelPath);

    @DataProvider(name = "validPythonCode")
    public Object[][] validPythonCode() {
        return new Object[][]{
            {"print(5 + 3)", "8"},
            {"print(10)", "10"},
            {"print('Hello')", "Hello"}
        };
    }
    @DataProvider(name = "registerExcelData")
    public Object[][] getRegisterData() {

        List<Map<String, String>> allRows = excel.getSheetData("Register");

        Object[][] data = new Object[allRows.size()][1];

        for (int i = 0; i < allRows.size(); i++) {

            Map<String, String> row = allRows.get(i);

            System.out.println("Row " + i +
                    " | testId: " + row.get("testId") +
                    " | username: " + row.get("username") +
                    " | password: " + row.get("password") +
                    " | ExpectedResult: " + row.get("ExpectedResult"));

            data[i][0] = row;
        }

        return data;
    }
    @DataProvider(name = "invalidPythonCode")
    public Object[][] invalidPythonCode() {
        return new Object[][]{
            {"print(5 + )"},
            {"print("},
            {"abc xyz"}
        };
    }
    
    @DataProvider(name = "moduleData")
    public Object[][] moduleData() {
        return new Object[][]{
                {"Arrays"},
                {"Linked List"},
                {"Stack"},
                {"Queue"},
                {"Tree"},
                {"Graph"}
        };
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
            {"TestNinja", "C5Mha6FkdSAVEN@"}
        };
    }
    

    @DataProvider(name = "arrayCodeData")
    public Object[][] arrayCodeData() {

        return new Object[][]{
                {"print(5+3)"},
                {"print(10)"},
                {"print('Hello')"},
                {"print(100/2)"}
        };
        
    }
    
    @DataProvider(name = "loginExcelData")
	public Object[][] getLoginData() {
	    String path = Paths.get("src/main/resources/ExcelSheet/DsAlgoTestData.xlsx").toString();
	    ExcelSheetHandling excel = new ExcelSheetHandling(path);
	    List<Map<String, String>> allRows = excel.getSheetData("Login");

	   
	    List<Map<String, String>> nonEmptyRows = new ArrayList<>();
	    for (Map<String, String> row : allRows) {
	        String testId = row.get("testId");
	        String username = row.get("username");
	        String password = row.get("password");

	        if ((testId == null || testId.trim().isEmpty()) &&
	            (username == null || username.trim().isEmpty()) &&
	            (password == null || password.trim().isEmpty())) {
	            break; 
	        }
	        nonEmptyRows.add(row);
	    }

	   
	    Object[][] data = new Object[nonEmptyRows.size()][1];
	    for (int i = 0; i < nonEmptyRows.size(); i++) {
	        data[i][0] = nonEmptyRows.get(i); 
	        System.out.println("Row " + i +
	                " | testId: " + nonEmptyRows.get(i).get("testId") +
	                " | username: " + nonEmptyRows.get(i).get("username") +
	                " | password: " + nonEmptyRows.get(i).get("password"));
	    }

	    return data;
	}
}