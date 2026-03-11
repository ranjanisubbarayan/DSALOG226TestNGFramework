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
import pageObjects.LinkedListPage;
import pageObjects.homePage;
import utilities.TestDataProvider;

public class LinkedListTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LinkedListTest.class);

    private WebDriver driver;
    LaunchPage launchPage;
     homePage homepage;
    private LinkedListPage linkedlistPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.getDriver();
        launchPage = new LaunchPage(driver);
        linkedlistPage = new LinkedListPage(driver);
        logger.info("LinkedList Test setup completed");
    }
   
    @Test( priority = 1,
    	    groups = {"smoke", "login"})
    public void LoginToDsAlgo() {
    	linkedlistPage.loginToApplication();
    }

    @Test(priority = 2)
    public void clickGetStartedLinkedListPanel() {
    	linkedlistPage.loginToApplication();
        linkedlistPage.getstartedLinkedList();
        logger.info("Clicked Get Started for LinkedList");
    }

    @Test(priority = 3)
    public void verifyLinkedListPageNavigation() {
    	linkedlistPage.loginToApplication();
    	 linkedlistPage.getstartedLinkedList();
        Assert.assertEquals(
                linkedlistPage.getLinkedListPageText(),
                "Linked List",
                "User is not on Linked List page"
        );
        logger.info("Linked List page verified");
    }

    @Test(priority = 4, groups = {"linkedlistlinks", "editor"},
            dataProvider = "linkedlistlinks",
            dataProviderClass = TestDataProvider.class)
    public void clickIntroductionLink(String LinkedLinks) {
    	linkedlistPage.loginToApplication();
    	linkedlistPage.getstartedLinkedList();
    	if (LinkedLinks.equalsIgnoreCase("Introduction")) {
	        linkedlistPage.clickIntroductionLink();

	    } else if (LinkedLinks.equalsIgnoreCase("Creating Linked List")) {
	        linkedlistPage.clickCreatingLink();

	    } else if (LinkedLinks.equalsIgnoreCase("Types of Linked List")) {
	        linkedlistPage.clicktypesLink();

	    } else if (LinkedLinks.equalsIgnoreCase("Implement Linked List in Python")) {
	        linkedlistPage.clickImplementingLink();

	    } else if (LinkedLinks.equalsIgnoreCase("Traversal")) {
	        linkedlistPage.clickTraversalLink();

	    } else if (LinkedLinks.equalsIgnoreCase("Insertion")) {
	        linkedlistPage.clickInsertionLink();

	    } else if (LinkedLinks.equalsIgnoreCase("Deletion")) {
	        linkedlistPage.clickDeletionLink();

	    } else {
	        throw new IllegalArgumentException("Invalid LinkedList link: " + LinkedLinks);
	    }
    	
    }


    @Test(priority = 6)
    public void runInvalidCodeAndVerifyAlert() throws IOException {
    	linkedlistPage.loginToApplication();
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
    	linkedlistPage.loginToApplication();
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

    	linkedlistPage.loginToApplication();
    	linkedlistPage.getstartedLinkedList();
    	linkedlistPage.clickIntroductionLink();
    	linkedlistPage.clickTryHere();
    	linkedlistPage.writeAndRunLinkedListCode(Code);
        
        String output = linkedlistPage.getOutput();
        Assert.assertFalse(output.isEmpty(), "No output displayed");
        logger.info("Data-driven execution output: " + output);
    }

  
}