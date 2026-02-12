package suite;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import driver.DriverFactory;
import pageObjects.LoginPage;
import pageObjects.homePage;
import pageObjects.GraphListPage;
import pageObjects.LaunchPage;
import utilities.ConfigReader;
import utilities.ExcelSheetHandling;

public class GraphListTest {

    private static final Logger logger = LogManager.getLogger(GraphListTest.class);

    private WebDriver driver;
    private LaunchPage launchPage;
    private GraphListPage graphPage;
    private homePage homepage;
    private String alertMsg = null;

    @BeforeClass
    public void setUp() {
        driver = DriverFactory.getDriver();
        launchPage = new LaunchPage(driver);
        graphPage = new GraphListPage(driver);
        logger.info("Graph Test setup completed");
    }

    @Test(priority = 1)
    public void loginToDsAlgo() {
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
    public void navigateToGraphSection() {
        graphPage.getstartedGraph();

        Assert.assertEquals(
                graphPage.getGraphLandingPageText(),
                "Graph",
                "Graph landing page is not displayed"
        );
    }

    @Test(priority = 3)
    public void openGraphTopicPage() {
        graphPage.clickGraphTopic();

        Assert.assertTrue(
                graphPage.isGraphTopicTextDisplayed(),
                "Graph Topic page text not visible"
        );
    }

    @Test(priority = 4)
    public void openGraphTopicTryEditor() {
        graphPage.clickTryHere();
        logger.info("Navigated to Try Editor for Graph Topic");
    }

    @Test(priority = 5)
    public void runInvalidGraphTopicCode() {
        graphPage.writeCodeAndRun("print(5 + )");

        alertMsg = graphPage.waitForAlertIfPresent();
        Assert.assertNotNull(alertMsg, "Expected alert for invalid Graph Topic code");
        logger.info("Alert detected: " + alertMsg);
    }

    @Test(priority = 6)
    public void openGraphRepresentationsPage() {
        graphPage.getstartedGraph();
        graphPage.clickGraphRepresentations();

        Assert.assertTrue(
                graphPage.isGraphRepresentationsTextDisplayed(),
                "Graph Representations page text not visible"
        );
    }

    @Test(priority = 7)
    public void openGraphRepresentationsTryEditor() {
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

    @Test(priority = 9)
    public void runValidGraphCodeUsingDataDriven() throws IOException {
    	String excelPath = ConfigReader.getProperty("excelPath");

    	ExcelSheetHandling excel = new ExcelSheetHandling(excelPath);

		List<String> data = excel.getCodeByColumn("testdata","Graph");
	
				for (int i = 0; i < data.size(); i++) {
		    String line = data.get(i);
		    graphPage.writeAndRunLinkedListCode(line);
		}

        String output = driver.findElement(By.xpath("//pre[@id='output']")).getText();
        Assert.assertFalse(output.isEmpty(), "No output displayed in console");
        logger.info("Graph output verified: " + output);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Graph Test execution completed");
    }
}
