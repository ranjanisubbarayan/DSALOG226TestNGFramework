package suite;

import driver.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.LaunchPage;
import pageObjects.LoginPage;
import pageObjects.homePage;
import base.BaseTest;
import utilities.TestDataProvider;

import java.util.Map;

public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        LaunchPage launchPage = new LaunchPage(DriverFactory.getDriver());
        homePage homepage = launchPage.clickGetStarted();
        loginPage = homepage.clickSignInLink();
    }

    @Test(priority = 1, dataProvider = "validLoginData", dataProviderClass = TestDataProvider.class)
    public void testValidLogin(Map<String, String> rowData) {
        loginPage.login(rowData.get("username"), rowData.get("password"));
        Assert.assertTrue(loginPage.isHomePageDisplayed(),
                "Expected user to be logged in, but login failed!");
    }

    @Test(priority = 2, dataProvider = "invalidLoginData", dataProviderClass = TestDataProvider.class)
    public void testInvalidLogin(Map<String, String> rowData) {
        loginPage.login(rowData.get("username"), rowData.get("password"));       
        String alertMessage = loginPage.getAlertMessage();
        Assert.assertTrue(alertMessage.contains("Invalid Username and Password"),
                "Expected alert: Invalid Username and Password but got: " + alertMessage);
    }

    @Test(priority = 3, dataProvider = "emptyLoginData", dataProviderClass = TestDataProvider.class)
    public void testEmptyCredentials(Map<String, String> rowData) {
        loginPage.login(rowData.get("username"), rowData.get("password"));
        String usernameMsg = loginPage.getUsernameValidationMessage();
        String passwordMsg = loginPage.getPasswordValidationMessage();
        Assert.assertTrue(!usernameMsg.isEmpty() || !passwordMsg.isEmpty(),
                "Expected browser validation message for empty fields but found none.");
    }

    @Test(priority = 4, dataProvider = "invalidPasswordData", dataProviderClass = TestDataProvider.class)
    public void testInvalidPassword(Map<String, String> rowData) {
        loginPage.login(rowData.get("username"), rowData.get("password"));
        String alertMessage = loginPage.getAlertMessage();
        Assert.assertTrue(alertMessage.contains("Invalid Username and Password"),
                "Expected alert: Invalid Username and Password but got: " + alertMessage);
    }

    @Test(priority = 5, dataProvider = "invalidUsernameData", dataProviderClass = TestDataProvider.class)
    public void testInvalidUsername(Map<String, String> rowData) {
        loginPage.login(rowData.get("username"), rowData.get("password"));
        String alertMessage = loginPage.getAlertMessage();
        Assert.assertTrue(alertMessage.contains("Invalid Username and Password"),
                "Expected alert: Invalid Username and Password but got: " + alertMessage);
    }
}