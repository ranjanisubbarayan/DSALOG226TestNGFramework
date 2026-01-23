package base;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;

import driver.DriverFactory;
import utilities.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class BaseTest {

    protected WebDriver driver;
    
    @Parameters("browser")
    @BeforeMethod
    public void setup(@Optional("chrome") String browser) {
        DriverFactory.setBrowser(browser);
        driver = DriverFactory.getDriver();
        driver.get(ConfigReader.getProperty("baseUrl"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

      
        if (ITestResult.FAILURE == result.getStatus()) {
            takeScreenshot(result.getName());
        }

        DriverFactory.cleanupDriver();
    }

    private void takeScreenshot(String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
            File source = ts.getScreenshotAs(OutputType.FILE);

            Path destination = Path.of(
                    System.getProperty("user.dir"),
                    "screenshots",
                    testName + ".png"
            );

            Files.createDirectories(destination.getParent());
            Files.copy(source.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
