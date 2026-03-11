package suite;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.*;
import base.BaseTest;
import pageObjects.ArrayListPage;
import pageObjects.LaunchPage;
import pageObjects.LoginPage;
import pageObjects.homePage;
import utilities.ConfigReader;
import utilities.TestDataProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArraysListTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(ArraysListTest.class);

 
    LaunchPage launchPage;
    ArrayListPage arrayListPage;
    private homePage homepage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        launchPage = new LaunchPage(getDriver());
        arrayListPage = new ArrayListPage(getDriver());
        
        logger.info("Test setup completed");
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
   
    @Test(priority = 2, groups = {"smoke", "array"})
    public void clickGetStartedArrayPanel() {
    	
    
     	  loginToApplication();
    	ArrayListPage arrayListPage  = new ArrayListPage(getDriver());
    	arrayListPage.getstartedArray();
        logger.info("Navigated to Array module");
    }

    @Test(priority = 3, groups = {"smoke", "array"})
    public void verifyArrayPageNavigation() {
    	
    	loginToApplication();
    	   ArrayListPage arrayListPage  = new ArrayListPage(getDriver());
           arrayListPage.getstartedArray();
    	
    	Assert.assertEquals(
                arrayListPage.getArrayPageText(),
                "Array",
                "User is not on Array Page"
        );
        logger.info("Array page verified");
    }

    @Test(priority = 4, groups = {"regression", "array"})
    public void clickArraysInPythonLink() {
    
    	 loginToApplication();
    	 ArrayListPage arrayListPage = new ArrayListPage(getDriver());
         arrayListPage.getstartedArray();
         arrayListPage.clickArraysInPython();
        arrayListPage.clickArraysInPython();
        Assert.assertEquals(
                arrayListPage.getArraysInPythonText(),
                "Arrays in Python",
                "Arrays in Python page not displayed"
        );
    }

    @Test(priority = 5, groups = {"regression", "editor"})
    public void clickTryHereButton() {
    	
    	loginToApplication();
    	   ArrayListPage arrayListPage = new ArrayListPage(getDriver());
           arrayListPage.getstartedArray();
           arrayListPage.clickArraysInPython();
           arrayListPage.clickTryHere();
        arrayListPage.clickTryHere();
        Assert.assertTrue(
                arrayListPage.isRunButtonDisplayed(),
                "Run button is not displayed"
        );
    }

    @Test(priority = 6)
    public void runInvalidCodeAndVerifyAlert() {
    
    	loginToApplication();
    	ArrayListPage arrayListPage = new ArrayListPage(getDriver());
          arrayListPage.getstartedArray();
          arrayListPage.clickArraysInPython();
          arrayListPage.clickTryHere();
    	arrayListPage.writeCodeAndRun("print(5 + )");
        String alertMsg = arrayListPage.waitForAlertIfPresent();

        Assert.assertNotNull(alertMsg, "Expected alert but none appeared");
        logger.info("Alert message displayed: " + alertMsg);
    }

    @Test(priority = 7)
    public void runValidCodeAndVerifyOutput() {
    
    	loginToApplication();
    	 ArrayListPage arrayListPage = new ArrayListPage(getDriver());
         arrayListPage.getstartedArray();
         arrayListPage.clickArraysInPython();
         arrayListPage.clickTryHere();
    	
    	arrayListPage.writeCodeAndRun("print(5 + 3)");

        String output = arrayListPage.getOutput();
        logger.info("Console output verified: " + output);
    }

    @Test(priority = 8, groups = {"regression", "editor"},
            dataProvider = "arrayCodeData",
            dataProviderClass = TestDataProvider.class)
    public void runCodeUsingDataDriven(String code) throws IOException {
    	
    	loginToApplication();
    	 ArrayListPage arrayListPage = new ArrayListPage(getDriver());
         arrayListPage.getstartedArray();
         arrayListPage.clickArraysInPython();
         arrayListPage.clickTryHere();
            arrayListPage.writeAndRunLinkedListCode(code);
    
        String output = arrayListPage.getOutput();
        Assert.assertFalse(output.isEmpty(), "No output displayed");
        logger.info("Data-driven execution output: " + output);
    }

}
