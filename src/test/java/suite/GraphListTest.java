package suite;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import driver.DriverFactory;
import pageObjects.LoginPage;
import pageObjects.homePage;
import pageObjects.GraphListPage;
import pageObjects.LaunchPage;
import utilities.ConfigReader;
import utilities.TestDataProvider;

public class GraphListTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(GraphListTest.class);

    private WebDriver driver;
    LaunchPage launchPage;
    private GraphListPage graphPage;
    private homePage homepage;
    private String alertMsg = null;
 
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.getDriver();
        launchPage = new LaunchPage(driver);
        graphPage = new GraphListPage(driver);
        logger.info("Graph Test setup completed");
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
    public void navigateToGraphSection() {
       
    	  loginToApplication();
    	graphPage.getstartedGraph();

        Assert.assertEquals(
                graphPage.getGraphLandingPageText(),
                "Graph",
                "Graph landing page is not displayed"
        );
    }

    @Test(priority = 3)
    public void openGraphTopicPage() {
    	  loginToApplication();
    	  graphPage.getstartedGraph();
        graphPage.clickGraphTopic();

        Assert.assertTrue(
                graphPage.isGraphTopicTextDisplayed(),
                "Graph Topic page text not visible"
        );
    }

    @Test(priority = 4)
    public void openGraphTopicTryEditor() {
    	  loginToApplication();
          graphPage.getstartedGraph();
          graphPage.clickGraphTopic();
        graphPage.clickTryHere();
        logger.info("Navigated to Try Editor for Graph Topic");
    }

    @Test(priority = 5)
    public void runInvalidGraphTopicCode() {
    	  loginToApplication();
          graphPage.getstartedGraph();
          graphPage.clickGraphTopic();
          graphPage.clickTryHere();
        graphPage.writeCodeAndRun("print(5 + )");

        alertMsg = graphPage.waitForAlertIfPresent();
        Assert.assertNotNull(alertMsg, "Expected alert for invalid Graph Topic code");
        logger.info("Alert detected: " + alertMsg);
    }

    @Test(priority = 6)
    public void openGraphRepresentationsPage() {
    	  loginToApplication();
        graphPage.getstartedGraph();
        graphPage.clickGraphRepresentations();

        Assert.assertTrue(
                graphPage.isGraphRepresentationsTextDisplayed(),
                "Graph Representations page text not visible"
        );
    }

    @Test(priority = 7)
    public void openGraphRepresentationsTryEditor() {
    	  loginToApplication();
    	  graphPage.getstartedGraph();
          graphPage.clickGraphRepresentations();
        graphPage.clickTryHere();
        logger.info("Navigated to Try Editor for Graph Representations");
    }

    @Test(priority = 8)
    public void runInvalidGraphRepresentationsCode() {
        
    	graphPage.writeCodeAndRun("print(5 + )");

        alertMsg = graphPage.waitForAlertIfPresent();
        Assert.assertNotNull(alertMsg, "Expected alert for invalid Graph Representations code");
        logger.info("Alert detected: " + alertMsg);
    }

    @Test(priority = 9, groups = {"regression", "editor"},
            dataProvider = "arrayCodeData",
            dataProviderClass = TestDataProvider.class)
    public void runValidGraphCodeUsingDataDriven(String code) throws IOException {
    	loginToApplication();
        graphPage.getstartedGraph();
        graphPage.clickGraphRepresentations();
        graphPage.clickTryHere();
        graphPage.writeAndRunLinkedListCode(code);
        
        String output = graphPage.getConsoleOutput();
        Assert.assertFalse(output.isEmpty(), "No output displayed");
        logger.info("Data-driven execution output: " + output);
    }

    
}
