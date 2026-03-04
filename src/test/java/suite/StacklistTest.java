package suite;

import static driver.DriverFactory.getDriver;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import base.BaseTest;
import pageObjects.LaunchPage;
import pageObjects.LoginPage;
import pageObjects.StackPage;
import pageObjects.homePage;
import utilities.ConfigReader;
import utilities.ExcelSheetHandling;

public class StacklistTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(StacklistTest.class);

    private WebDriver driver;
    private LaunchPage launchPage;
    private homePage homepage;
    private StackPage stackPage;

    @BeforeClass
    public void setUp() {
        driver = getDriver();
        launchPage = new LaunchPage(driver);
        stackPage = new StackPage(driver);
        logger.info("Stack Test setup completed");
    }

    @Test(priority = 1)
    public void userLoginToDsAlgo() {

        homepage = launchPage.clickGetStarted();

        if (!homepage.isUserLoggedIn()) {
            homepage.clickSignInLinkIfPresent();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.enterUsername("TestNinja");
            loginPage.enterPassword("C5Mha6FkdSAVEN@");
            loginPage.clickLoginButton();
        }

        Assert.assertTrue(homepage.isUserLoggedIn(), "Login failed");
        logger.info("Successfully logged into dsAlgo application");
    }

    @Test(priority = 2)
    public void clickGetStartedStackPanel() {
        stackPage.clickStackGetStarted();
        logger.info("Clicked Stack Get Started");
    }

    @Test(priority = 3)
    public void verifyStackPageNavigation() {
        Assert.assertTrue(stackPage.isStackPageDisplayed(),
                "User is not on Stack Page");
    }

    @Test(priority = 4)
    public void verifyStackPageLoadTime() {

        long maxTime = 5; // seconds
        long startTime = System.currentTimeMillis();

        stackPage.waitForStackPage();

        long loadTime = (System.currentTimeMillis() - startTime) / 1000;

        Assert.assertTrue(loadTime <= maxTime,
                "Stack page load time exceeded limit: " + loadTime + " seconds");

        logger.info("Stack page loaded in " + loadTime + " seconds");
    }

    @Test(priority = 5)
    public void verifyStackPageHTTPS() {

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.startsWith("https"),
                "Stack page is NOT loaded using HTTPS. URL: " + currentUrl
        );

        logger.info("Stack page loaded using HTTPS");
    }

    @Test(priority = 6)
    public void verifyStackMainLinksVisible() {

        Assert.assertTrue(stackPage.isOperationInStackDisplayed(),
                "Operations in Stack not visible");

        Assert.assertTrue(stackPage.isImplementInStackDisplayed(),
                "Implementation not visible");

        Assert.assertTrue(stackPage.isApplicationInStackDisplayed(),
                "Applications not visible");

        logger.info("All main stack links are visible");
    }

    @Test(priority = 7)
    public void clickOperationsInStack() {
        stackPage.clickOperationsInStack();
        logger.info("Clicked Operations in Stack");
    }

    @Test(priority = 8)
    public void clickTryHereButton() {
        stackPage.clickTryHere();
        Assert.assertTrue(stackPage.isTryEditorDisplayed(),
                "Try Editor not visible");
    }

    @Test(priority = 9)
    public void runInvalidCodeAndVerifyAlert() {

        stackPage.enterCodeInEditor("print(5 + 8 )");

        String alertMsg = stackPage.errorMessageinAlertWindow();

        Assert.assertNotNull(alertMsg, "Expected alert but none appeared");
        logger.info("Alert detected: " + alertMsg);
    }

    @Test(priority = 10)
    public void runValidCodeAndVerifyOutput() {

        stackPage.enterCodeInEditor("print(5 + 3)");

        String output = stackPage.seeOutput();

        Assert.assertFalse(output.isEmpty(),
                "Expected output but got empty");

        logger.info("Stack console output: " + output);
    }

    @Test(priority = 11)
    public void runCodeUsingExcelData() throws IOException {

        String excelPath = ConfigReader.getProperty("excelPath");
        ExcelSheetHandling excel = new ExcelSheetHandling(excelPath);

        List<String> data = excel.getCodeByColumn("testdata", "Data1");

        for (String line : data) {
            stackPage.enterCodeInEditor(line);
        }

        String output = stackPage.seeOutput();
        Assert.assertFalse(output.isEmpty(), "No output displayed");

        logger.info("Data-driven Stack execution output: " + output);
    }

    @Test(priority = 12)
    public void refreshStackPageAndVerifyNoErrors() {

        driver.navigate().refresh();

        String pageSource = driver.getPageSource();

        Assert.assertFalse(pageSource.contains("error"), "Page contains 'error'");
        Assert.assertFalse(pageSource.contains("404"), "Page contains '404'");
        Assert.assertFalse(pageSource.contains("500"), "Page contains '500'");

        Assert.assertNotNull(driver.getTitle(),
                "Stack page title is NULL after refresh");

        logger.info("Stack page refreshed without errors");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Stack Test execution completed");
    }
}