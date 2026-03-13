package suite;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseTest;
import pageObjects.LaunchPage;
import pageObjects.homePage;
import pageObjects.registerPage;
import utilities.TestDataProvider;

import java.util.Map;

public class RegisterDataTest extends BaseTest {

    private registerPage registerPage;  

    @BeforeMethod
    public void setUp() {
       
        LaunchPage launchPage = new LaunchPage(getDriver());
        homePage homepage = launchPage.clickGetStarted();
        registerPage = homepage.clickRegisterLink();
    }

    @Test(priority = 1, dataProvider = "validRegisterData", dataProviderClass = TestDataProvider.class)
    public void testValidRegister(Map<String, String> rowData) {
        registerPage.registerUsingExcel(
                rowData.get("username"),
                rowData.get("password"),
                rowData.get("confirmpassword")
        );

        String expectedResult = rowData.get("ExpectedResult");
        Assert.assertTrue(expectedResult.contains("New Account Created") && registerPage.isAccountCreated(),
                "Expected: New Account Created, but registration failed.");
    }

    @Test(priority = 2, dataProvider = "emptyRegisterData", dataProviderClass = TestDataProvider.class)
    public void testEmptyFieldsRegister(Map<String, String> rowData) {
        registerPage.registerUsingExcel(
                rowData.get("username"),
                rowData.get("password"),
                rowData.get("confirmpassword")
        );

        String usernameMsg = registerPage.getValidationMessage();
        String passwordMsg = registerPage.getPasswordValidationMessage();
        String confirmMsg = registerPage.getConfirmPasswordValidationMessage();

        Assert.assertTrue(!usernameMsg.isEmpty() || !passwordMsg.isEmpty() || !confirmMsg.isEmpty(),
                "Expected browser validation message for empty fields but found none.");
    }

    @Test(priority = 3, dataProvider = "passwordMismatchData", dataProviderClass = TestDataProvider.class)
    public void testPasswordMismatchRegister(Map<String, String> rowData) {
        registerPage.registerUsingExcel(
                rowData.get("username"),
                rowData.get("password"),
                rowData.get("confirmpassword")
        );

        String alertMessage = registerPage.getAlertMessage();
        Assert.assertTrue(alertMessage.contains("The two password fields didn’t match"),
                "Expected alert: password mismatch but got: " + alertMessage);
    }
}