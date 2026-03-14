package utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.testng.annotations.DataProvider;

public class TestDataProvider {
	
    private final String excelPath = "src/test/resources/ExcelSheet/DsAlgoTestData.xlsx";
    private ExcelSheetHandling excel = new ExcelSheetHandling(excelPath);

    @DataProvider(name = "validPythonCode")
    public Object[][] validPythonCode() {
        return new Object[][]{
            {"print(5 + 3)", "8"},
            {"print(10)", "10"},
            {"print('Hello')", "Hello"}
        };
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
    
    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() {
        return filterLoginRowsByTestId("valid");
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return filterLoginRowsByTestId("invalid");
    }

    @DataProvider(name = "emptyLoginData")
    public Object[][] emptyLoginData() {
        return filterLoginRowsByTestId("empty");
    }

    @DataProvider(name = "invalidPasswordData")
    public Object[][] invalidPasswordData() {
        return filterLoginRowsByTestId("invalidPassword");
    }

    @DataProvider(name = "invalidUsernameData")
    public Object[][] invalidUsernameData() {
        return filterLoginRowsByTestId("invalidUsername");
    }

    private Object[][] filterLoginRowsByTestId(String testId) {
        List<Map<String, String>> allRows = excel.getSheetData("Login");
        List<Map<String, String>> filteredRows = new ArrayList<>();

        for (Map<String, String> row : allRows) {
            if (testId.equalsIgnoreCase(row.get("testId"))) {
                filteredRows.add(row);
            }
        }

        Object[][] data = new Object[filteredRows.size()][1];
        for (int i = 0; i < filteredRows.size(); i++) {
            data[i][0] = filteredRows.get(i);
        }

        return data;
    }
    
    @DataProvider(name = "validRegisterData")
    public Object[][] validRegisterData() {
        return filterRegisterRowsByScenario("valid");
    }

    @DataProvider(name = "emptyRegisterData")
    public Object[][] emptyRegisterData() {
        return filterRegisterRowsByScenario("empty");
    }

    @DataProvider(name = "passwordMismatchData")
    public Object[][] passwordMismatchData() {
        return filterRegisterRowsByScenario("password_mismatch");
    }
    private Object[][] filterRegisterRowsByScenario(String scenario) {
        List<Map<String, String>> allRows = excel.getSheetData("Register");
        List<Map<String, String>> filteredRows = new ArrayList<>();

        for (Map<String, String> row : allRows) {
            String expected = row.get("ExpectedResult");
            if (expected == null) continue;

            switch (scenario) {
                case "valid":
                    if (expected.contains("New Account Created")) {
                        filteredRows.add(row);
                    }
                    break;
                case "empty":
                    if (expected.contains("Please fill out this field")) {
                        filteredRows.add(row);
                    }
                    break;
                case "password_mismatch":
                    if (expected.contains("password_mismatch")) {
                        filteredRows.add(row);
                    }
                    break;
            }
        }

        Object[][] data = new Object[filteredRows.size()][1];
        for (int i = 0; i < filteredRows.size(); i++) {
            data[i][0] = filteredRows.get(i);
        }
        return data;
    }
}
