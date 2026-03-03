package suite;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pageObjects.LaunchPage;
import pageObjects.homePage;
import pageObjects.registerPage;
import utilities.ExcelSheetHandling;

import java.util.List;
import java.util.Map;

public class RegisterDataTest extends BaseTest {

    private final String excelPath = "src/main/resources/ExcelSheet/DsAlgoTestData.xlsx";
    private ExcelSheetHandling excel = new ExcelSheetHandling(excelPath);

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
    @Test(dataProvider = "registerExcelData")
    public void verifyRegister(Map<String, String> rowData) {

        String testId = rowData.get("testId");
        String username = rowData.get("username");
        String password = rowData.get("password");
        String confirmPassword = rowData.get("confirmpassword");
        String expectedResult = rowData.get("ExpectedResult");

        LaunchPage launchPage = new LaunchPage(driver);
        homePage homepage = launchPage.clickGetStarted();
       // homepage.signOutIfLoggedIn();
        registerPage registerPage = homepage.clickRegisterLink();

        registerPage.registerUsingExcel(username, password, confirmPassword);


        switch (expectedResult.trim()) {

            case "You are logged in":
            	homePage home = new homePage(driver);

                Assert.assertTrue(home.isHomePageDisplayed(),
                        "Expected user to be logged in, but was NOT!");
                if (username == null || username.isEmpty()) {
                 
                    username = registerPage.generateUsernameAndWriteToExcel(excel, 7, 1); 
                }
                break;

            case "Please fill out this field":
                String usernameMsg = registerPage.getValidationMessage();
                String passwordMsg = registerPage.getPasswordValidationMessage();
                String confirmMsg = registerPage.getConfirmPasswordValidationMessage();

                Assert.assertTrue(
                        !usernameMsg.isEmpty() ||
                        !passwordMsg.isEmpty() ||
                        !confirmMsg.isEmpty(),
                        "Expected browser validation message but found none."
                );
                break;

            default:
                String alertMessage = registerPage.getAlertMessage();
                Assert.assertTrue(alertMessage.contains(expectedResult),
                        "Expected alert: [" + expectedResult + "] but got: [" + alertMessage + "]");
                break;
        }
    }
}
