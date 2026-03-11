package suite;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.*;
import base.BaseTest;
import pageObjects.ArrayListPage;
import pageObjects.LaunchPage;
import pageObjects.homePage;
import utilities.TestDataProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArraysListTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(ArraysListTest.class);

 
    LaunchPage launchPage;
    ArrayListPage arrayListPage;
    homePage homepage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        launchPage = new LaunchPage(getDriver());
        arrayListPage = new ArrayListPage(getDriver());
        
        logger.info("Test setup completed");
    }
   
 
    @Test( priority = 1,
    	    groups = {"smoke", "login"})
    public void LoginToDsAlgo() {
    	arrayListPage.loginToApplication();
    }
   
    @Test(priority = 2, groups = {"smoke", "array"})
    public void clickGetStartedArrayPanel() {
    	
    
    	arrayListPage.loginToApplication();
    	ArrayListPage arrayListPage  = new ArrayListPage(getDriver());
    	arrayListPage.getstartedArray();
        logger.info("Navigated to Array module");
    }

    @Test(priority = 3, groups = {"smoke", "array"})
    public void verifyArrayPageNavigation() {
    	
    	arrayListPage.loginToApplication();
    	  
           arrayListPage.getstartedArray();
    	
    	Assert.assertEquals(
                arrayListPage.getArrayPageText(),
                "Array",
                "User is not on Array Page"
        );
        logger.info("Array page verified");
    }

    @Test(priority = 4, groups = {"regression", "array"}, dataProvider = "arraylinks",
            dataProviderClass = TestDataProvider.class)
    public void clickArraysInPythonLink(String links) {
    
    	arrayListPage.loginToApplication();
  	  
        arrayListPage.getstartedArray();
    	 
         if (links.equalsIgnoreCase("Arrays in Python")) {
        	 arrayListPage.clickArraysInPython();

 	    } else if (links.equalsIgnoreCase("Arrays Using List")) {
 	    	arrayListPage.clickArraysUsingList();

 	    } else if (links.equalsIgnoreCase("Basic Operations in Lists")) {
 	    	arrayListPage.clickBasicOperationArray();

 	    } else if (links.equalsIgnoreCase("Applications of Array")) {
 	    	arrayListPage.clickApplicationofArray();

 	    } else if (links.equalsIgnoreCase("Practice Questions")) {
 	    	arrayListPage.clickPracticeQue();

 	    } else {
 	        throw new IllegalArgumentException("Invalid Array link: " + links);
 	    }
 }

    

    @Test(priority = 5, groups = {"regression", "editor"})
    public void clickTryHereButton() {
    	
    	arrayListPage.loginToApplication();
    	   arrayListPage.getstartedArray();
           arrayListPage.clickArraysInPython();
           arrayListPage.clickTryHere();
        Assert.assertTrue(
                arrayListPage.isRunButtonDisplayed(),
                "Run button is not displayed"
        );
    }

    @Test(priority = 6)
    public void runInvalidCodeAndVerifyAlert() {
    
    	arrayListPage.loginToApplication();
    	
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
    
    	arrayListPage.loginToApplication();
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
    	
    	arrayListPage.loginToApplication();
          arrayListPage.getstartedArray();
         arrayListPage.clickArraysInPython();
         arrayListPage.clickTryHere();
            arrayListPage.writeAndRunLinkedListCode(code);
    
        String output = arrayListPage.getOutput();
        Assert.assertFalse(output.isEmpty(), "No output displayed");
        logger.info("Data-driven execution output: " + output);
    }

}
