package suite;


import java.io.IOException;
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
import utilities.TestDataProvider;

public class LinkedListTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LinkedListTest.class);

    private WebDriver driver;
    LaunchPage launchPage;
    private homePage homepage;
    private LinkedListPage linkedlistPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.getDriver();
        launchPage = new LaunchPage(driver);
        linkedlistPage = new LinkedListPage(driver);
        logger.info("LinkedList Test setup completed");
    }
    
    public void loginToApplication() {
      	 String username = ConfigReader.getProperty("username");
           String password = ConfigReader.getProperty("password");
           LaunchPage launchPage = new LaunchPage(getDriver());
           homepage = launchPage.clickGetStarted();

           if (!homepage.isUserLoggedIn()) {
               homepage.clickSignInLinkIfPresent();
               LoginPage loginPage = new LoginPage(getDriver());
               loginPage.enterUsername(username);
               loginPage.enterPassword(password);
               loginPage.clickLoginButton();
           }
           

           Assert.assertTrue(homepage.isUserLoggedIn(), "User login failed");
           logger.info("Successfully logged into dsAlgo application");
        }

    @Test( priority = 1,
    	    groups = {"smoke", "login"})
    public void LoginToDsAlgo() {
    	loginToApplication();
    }

    @Test(priority = 2)
    public void clickGetStartedLinkedListPanel() {
    	 loginToApplication();
        linkedlistPage.getstartedLinkedList();
        logger.info("Clicked Get Started for LinkedList");
    }

    @Test(priority = 3)
    public void verifyLinkedListPageNavigation() {
    	 loginToApplication();
    	 linkedlistPage.getstartedLinkedList();
        Assert.assertEquals(
                linkedlistPage.getLinkedListPageText(),
                "Linked List",
                "User is not on Linked List page"
        );
        logger.info("Linked List page verified");
    }

    @Test(priority = 4)
    public void clickIntroductionLink() {
    	loginToApplication();
    	linkedlistPage.getstartedLinkedList();
        linkedlistPage.clickIntroductionLink();
        logger.info("Clicked Introduction link");
    }


    @Test(priority = 6)
    public void runInvalidCodeAndVerifyAlert() throws IOException {
    	  loginToApplication();
          linkedlistPage.getstartedLinkedList();
          linkedlistPage.clickIntroductionLink();
          linkedlistPage.clickTryHere();

        linkedlistPage.writeAndRunLinkedListCode("print(5 + )");
        String alertMsg = linkedlistPage.waitForAlertIfPresent();

        Assert.assertNotNull(alertMsg, "Expected alert but none appeared");
        logger.info("Alert message displayed: " + alertMsg);
    }

    @Test(priority = 7)
    public void runValidCodeAndVerifyOutput() throws IOException {
    	 loginToApplication();
         linkedlistPage.getstartedLinkedList();
         linkedlistPage.clickIntroductionLink();
         linkedlistPage.clickTryHere();
        linkedlistPage.writeAndRunLinkedListCode("print(5 + 3)");
        String output = linkedlistPage.getOutput();

        Assert.assertFalse(output.isEmpty(),
                "Expected output in console but found none");

        logger.info("Console output verified: " + output);
    }

    @Test(priority = 8, groups = {"regression", "editor"},
            dataProvider = "arrayCodeData",
            dataProviderClass = TestDataProvider.class)
    public void runCodeUsingExcelData(String Code) throws IOException {

    	loginToApplication();
    	linkedlistPage.getstartedLinkedList();
    	linkedlistPage.clickIntroductionLink();
    	linkedlistPage.clickTryHere();
    	linkedlistPage.writeAndRunLinkedListCode(Code);
        
        String output = linkedlistPage.getOutput();
        Assert.assertFalse(output.isEmpty(), "No output displayed");
        logger.info("Data-driven execution output: " + output);
    }

  
}