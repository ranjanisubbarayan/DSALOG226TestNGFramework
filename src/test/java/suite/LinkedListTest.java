package suite;


import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import base.BaseTest;
import driver.DriverFactory;
import pageObjects.LaunchPage;
import pageObjects.LoginPage;
import pageObjects.LinkedListPage;
import pageObjects.homePage;
import utilities.ConfigReader;
import utilities.ExcelSheetHandling;

public class LinkedListTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LinkedListTest.class);

    private WebDriver driver;
    private LaunchPage launchPage;
    private homePage homepage;
    private LinkedListPage linkedlistPage;

    @BeforeClass
    public void setUp() {
    	String browser = ConfigReader.getProperty("browser");
    	DriverFactory.initDriver(browser);  
    	  driver = DriverFactory.getDriver();
        launchPage = new LaunchPage(driver);
        linkedlistPage = new LinkedListPage(driver);
        logger.info("LinkedList Test setup completed");
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
    public void clickGetStartedLinkedListPanel() {
        linkedlistPage.getstartedLinkedList();
        logger.info("Clicked Get Started for LinkedList");
    }

    @Test(priority = 3)
    public void verifyLinkedListPageNavigation() {
        Assert.assertEquals(
                linkedlistPage.getLinkedListPageText(),
                "Linked List",
                "User is not on Linked List page"
        );
        logger.info("Linked List page verified");
    }

    @Test(priority = 4)
    public void clickIntroductionLink() {
        linkedlistPage.clickIntroductionLink();
        logger.info("Clicked Introduction link");
    }


    @Test(priority = 6)
    public void runInvalidCodeAndVerifyAlert() throws IOException {

        linkedlistPage.writeAndRunLinkedListCode("print(5 + )");
        String alertMsg = linkedlistPage.errorMessageinAlertWindow();

        Assert.assertNotNull(alertMsg, "Expected alert but none appeared");
        logger.info("Alert message displayed: " + alertMsg);
    }

    @Test(priority = 7)
    public void runValidCodeAndVerifyOutput() throws IOException {

        linkedlistPage.writeAndRunLinkedListCode("print(5 + 3)");
        String output = linkedlistPage.seeOutput();

        Assert.assertFalse(output.isEmpty(),
                "Expected output in console but found none");

        logger.info("Console output verified: " + output);
    }

    @Test(priority = 8)
    public void runCodeUsingExcelData() throws IOException {

        String excelPath = ConfigReader.getProperty("excelPath");
        ExcelSheetHandling excel = new ExcelSheetHandling(excelPath);

        List<String> data = excel.getCodeByColumn("testdata","Data1");

        for (String line : data) {
            linkedlistPage.writeAndRunLinkedListCode(line);
        }

        String output = linkedlistPage.seeOutput();
        Assert.assertFalse(output.isEmpty(), "No output displayed");

        logger.info("Data-driven LinkedList execution output: " + output);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("LinkedList Test execution completed");
    }
}