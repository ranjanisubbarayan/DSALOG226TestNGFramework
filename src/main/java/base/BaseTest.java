package base;

import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.annotations.*;

import driver.DriverFactory;
import utilities.ConfigReader;
import utilities.ExtentReportManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;

    @Parameters("browser")
    @BeforeSuite
    public void setupExtentReports() {
        extent = ExtentReportManager.getExtentReports();
    }

    @Parameters("browser")
    @BeforeClass
    public void setup(@Optional("chrome") String browser) {
        driver = DriverFactory.initDriver(browser);

        String url = ConfigReader.getProperty("baseUrl");
        if (url == null || url.isEmpty()) {
            throw new RuntimeException("baseUrl not found in config.properties");
        }
        driver.get(url);
    }

    @AfterClass
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus() && driver != null) {
            takeScreenshot(result.getName());
        }

        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void tearDownExtentReports() {
        if (extent != null) {
            extent.flush();
        }
    }

    private void takeScreenshot(String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            Path destination = Paths.get(
                    System.getProperty("user.dir"),
                    "screenshots",
                    testName + ".png"
            );

            Files.createDirectories(destination.getParent());
            Files.copy(source.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            System.err.println("Screenshot failed: " + e.getMessage());
        }
    }
}