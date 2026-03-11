package suite;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseTest;
import pageObjects.LaunchPage;
import pageObjects.StackPage;
import pageObjects.homePage;
import utilities.TestDataProvider;

public class StacklistTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(StacklistTest.class);

    private WebDriver driver;
    LaunchPage launchPage;
    homePage homepage;
    private StackPage stackPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = getDriver();
        launchPage = new LaunchPage(driver);
        stackPage = new StackPage(driver);
        logger.info("Stack Test setup completed");
    }

   
   
    @Test(priority = 1, groups = {"smoke", "login"})
    public void LoginToDsAlgo() {
    	stackPage.loginToApplication();
    	logger.info("Successfully logged into dsAlgo application");
    }

    @Test(priority = 2)
    public void clickGetStartedStackPanel() {
    	stackPage.navigateToStackPage();
        logger.info("Clicked Stack Get Started");
    }

    @Test(priority = 3)
    public void verifyStackPageNavigation() {
    	stackPage.navigateToStackPage();
        logger.info("Navigated to Stack page");
        Assert.assertTrue(
                stackPage.isStackPageDisplayed(),
                "User is not on Stack Page"
        );
        logger.info("Stack page verified");
    }

    @Test(priority = 4)
    public void verifyStackPageLoadTime() {
    	stackPage.loginToApplication();

        long maxTime = 5;
        long startTime = System.currentTimeMillis();

        stackPage.clickStackGetStarted();
        stackPage.waitForStackPage();

        long loadTime = (System.currentTimeMillis() - startTime) / 1000;

        Assert.assertTrue(
                loadTime <= maxTime,
                "Stack page load time exceeded limit: " + loadTime + " seconds"
        );

        logger.info("Stack page loaded in " + loadTime + " seconds");
    }

    @Test(priority = 5)
    public void verifyStackPageHTTPS() {
    	stackPage.navigateToStackPage();

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.startsWith("https"),
                "Stack page is NOT loaded using HTTPS. URL: " + currentUrl
        );

        logger.info("Stack page loaded using HTTPS");
    }

    @Test(priority = 6)
    public void verifyStackMainLinksVisible() {
    	stackPage.navigateToStackPage();

        Assert.assertTrue(
                stackPage.isOperationInStackDisplayed(),
                "Operations in Stack not visible"
        );

        Assert.assertTrue(
                stackPage.isImplementInStackDisplayed(),
                "Implementation not visible"
        );

        Assert.assertTrue(
                stackPage.isApplicationInStackDisplayed(),
                "Applications not visible"
        );

        logger.info("All main stack links are visible");
    }

    @Test(priority = 7)
    public void clickOperationsInStack() {
    	stackPage.navigateToStackPage();
        stackPage.clickOperationsInStack();
        logger.info("Clicked Operations in Stack");
    }

    @Test(priority = 8)
    public void clickTryHereButton() {
    	stackPage.navigateToOperationsInStackPage();
    	   logger.info("Navigated to Operations in Stack page");
        stackPage.clickTryHere();

        Assert.assertTrue(
                stackPage.isTryEditorDisplayed(),
                "Try Editor not visible"
        );
        logger.info("Try Here page verified");
    }

    @Test(priority = 9)
    public void runInvalidCodeAndVerifyAlert() {
    	stackPage.navigateToTryEditorFromOperationsInStack();
    	 logger.info("Navigated to Try Editor from Operations in Stack page");
        stackPage.enterCodeInEditor("print(5 + )");
        stackPage.clickRunButton();
        String alertMsg = stackPage.errorMessageinAlertWindow();

        Assert.assertNotNull(alertMsg, "Expected alert but none appeared");
        logger.info("Alert detected: " + alertMsg);
    }

    @Test(priority = 10)
    public void runValidCodeAndVerifyOutput() {
    	stackPage.navigateToTryEditorFromOperationsInStack();

        stackPage.enterCodeInEditor("print(5 + 3)");
        String output = stackPage.seeOutput();

        Assert.assertFalse(output.isEmpty(), "Expected output but got empty");
        logger.info("Stack console output: " + output);
    }

    @Test(priority = 11, groups = {"regression", "editor"},
            dataProvider = "arrayCodeData",
            dataProviderClass = TestDataProvider.class)
    public void runCodeUsingExcelData(String line) throws IOException {
    	stackPage.navigateToTryEditorFromOperationsInStack();
            stackPage.enterCodeInEditor(line);
        
        String output = stackPage.seeOutput();
        Assert.assertFalse(output.isEmpty(), "No output displayed");
        logger.info("Data-driven Stack execution output: " + output);
    }

    @Test(priority = 12)
    public void refreshStackPageAndVerifyNoErrors() {
    	stackPage.navigateToStackPage();

        driver.navigate().refresh();

        String pageSource = driver.getPageSource().toLowerCase();

        Assert.assertFalse(pageSource.contains("404"), "Page contains '404'");
        Assert.assertFalse(pageSource.contains("500"), "Page contains '500'");
        Assert.assertNotNull(driver.getTitle(), "Stack page title is NULL after refresh");

        logger.info("Stack page refreshed without errors");
    }
}