package suite;

import static driver.DriverFactory.getDriver;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.ArrayListPage;
import pageObjects.LaunchPage;
import pageObjects.LoginPage;
import pageObjects.homePage;
import utilities.ConfigReader;
import utilities.ExcelSheetHandling;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArraysListTest {

    private static final Logger logger = LogManager.getLogger(ArraysListTest.class);

    private WebDriver driver;
    private LaunchPage launchPage;
    private ArrayListPage arrayListPage;
    private homePage homepage;

    @BeforeClass
    public void setUp() {
        driver = getDriver();
        launchPage = new LaunchPage(driver);
        arrayListPage = new ArrayListPage(driver);
        logger.info("Test setup completed");
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

        Assert.assertTrue(homepage.isUserLoggedIn(), "User login failed");
        logger.info("Successfully logged into dsAlgo application");
    }

    @Test(priority = 2)
    public void clickGetStartedArrayPanel() {
        arrayListPage.getstartedArray();
        logger.info("Navigated to Array module");
    }

    @Test(priority = 3)
    public void verifyArrayPageNavigation() {
        Assert.assertEquals(
                arrayListPage.getArrayPageText(),
                "Array",
                "User is not on Array Page"
        );
        logger.info("Array page verified");
    }

    @Test(priority = 4)
    public void clickArraysInPythonLink() {
        arrayListPage.clickArraysInPython();
        Assert.assertEquals(
                arrayListPage.getArraysInPythonText(),
                "Arrays in Python",
                "Arrays in Python page not displayed"
        );
    }

    @Test(priority = 5)
    public void clickTryHereButton() {
        arrayListPage.clickTryHere();
        Assert.assertTrue(
                arrayListPage.isRunButtonDisplayed(),
                "Run button is not displayed"
        );
    }

    @Test(priority = 6)
    public void runInvalidCodeAndVerifyAlert() {
        arrayListPage.writeCodeAndRun("print(5 + )");
        String alertMsg = arrayListPage.waitForAlertIfPresent();

        Assert.assertNotNull(alertMsg, "Expected alert but none appeared");
        logger.info("Alert message displayed: " + alertMsg);
    }

    @Test(priority = 7)
    public void runValidCodeAndVerifyOutput() {
        arrayListPage.writeCodeAndRun("print(5 + 3)");

        String output = driver.findElement(By.xpath("//pre[@id='output']")).getText();
        Assert.assertTrue(output.contains("8"), "Output is incorrect");
        logger.info("Console output verified: " + output);
    }

    @Test(priority = 8)
    public void runCodeUsingDataDriven() throws IOException {
    	String excelPath = ConfigReader.getProperty("excelPath");

    	ExcelSheetHandling excel = new ExcelSheetHandling(excelPath);

		List<String> data = excel.getCodeByColumn("testdata","Graph");
	
				for (int i = 0; i < data.size(); i++) {
		    String line = data.get(i);
            arrayListPage.writeAndRunLinkedListCode(line);
		}


        String output = driver.findElement(By.xpath("//pre[@id='output']")).getText();
        Assert.assertFalse(output.isEmpty(), "No output displayed");
        logger.info("Data-driven execution output: " + output);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Test execution completed");
    }
}
