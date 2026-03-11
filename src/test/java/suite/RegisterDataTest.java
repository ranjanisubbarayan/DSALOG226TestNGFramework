package suite;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pageObjects.LaunchPage;
import pageObjects.homePage;
import pageObjects.registerPage;
import utilities.ExcelSheetHandling;
import utilities.TestDataProvider;

import java.util.Map;

public class RegisterDataTest extends BaseTest {

    @Test(dataProvider = "registerExcelData", dataProviderClass = TestDataProvider.class)
    public void verifyRegister(Map<String, String> rowData) {

    	ExcelSheetHandling excel =
                new ExcelSheetHandling("src/main/resources/ExcelSheet/DsAlgoTestData.xlsx");

        String username = rowData.get("username");
        String password = rowData.get("password");
        String confirmPassword = rowData.get("confirmpassword");
        String expectedResult = rowData.get("ExpectedResult");

        LaunchPage launchPage = new LaunchPage(getDriver());
        homePage homepage = launchPage.clickGetStarted();
     
       registerPage registerPage = homepage.clickRegisterLink();

        registerPage.registerUsingExcel(username, password, confirmPassword);


        switch (expectedResult.trim()) {

            case "You are logged in":
            	homePage home = new homePage(getDriver());

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
