package suite;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import driver.DriverFactory;
import pageObjects.LaunchPage;
import pageObjects.LoginPage;
import pageObjects.homePage;
import utilities.ScreenshotUtil;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private LaunchPage launchPage;
    private homePage homepage;

    @BeforeClass
    public void setUp() {
        driver = DriverFactory.getDriver();
        launchPage = new LaunchPage(driver);
    }

    @Test(priority = 1)
    public void navigateToLoginPage() {
        homepage = launchPage.clickGetStarted();

       
        try {
            loginPage = homepage.clickSignOut();
        } catch (Exception e) {
          
        }

        loginPage = homepage.clickSignInLink();
        Assert.assertNotNull(loginPage, "Login page not opened");
    }

    @Test(priority = 2)
    public void loginWithEmptyCredentials() {
        loginPage.clickLoginButton();

        String usernameMsg = loginPage.getUsernameValidationMessage();
        String passwordMsg = loginPage.getPasswordValidationMessage();

        Assert.assertTrue(
                !usernameMsg.isEmpty() || !passwordMsg.isEmpty(),
                "Expected browser validation message but found none"
        );
    }

    @Test(priority = 3)
    public void loginWithInvalidCredentials() {
        loginPage.enterUsername("invalidUser");
        loginPage.enterPassword("invalidPass");
        loginPage.clickLoginButton();

        String alert = loginPage.getAlertMessage();
        Assert.assertTrue(
                alert.contains("Invalid"),
                "Expected invalid login message, but got: " + alert
        );
    }

    @Test(priority = 4)
    public void loginWithValidCredentials() {
        loginPage.enterUsername("validUsername");  
        loginPage.enterPassword("validPassword");
        loginPage.clickLoginButton();

        Assert.assertTrue(
                loginPage.isHomePageDisplayed(),
                "Home page should be displayed but was NOT!"
        );
    }

    @Test(priority = 5)
    public void loginUsingExcelData() {
        homepage = launchPage.clickGetStarted();
        loginPage = homepage.clickSignInLink();

        loginPage.loginUsingTestData("TC_Login_01");
        loginPage.getDataFromExcel();

        Assert.assertTrue(
                loginPage.isHomePageDisplayed(),
                "Login failed using Excel data"
        );
    }

    @Test(priority = 6)
    public void captureScreenshotAfterLogin() {
        ScreenshotUtil.takeScreenshot(driver, "LoginTest_Screenshot");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
