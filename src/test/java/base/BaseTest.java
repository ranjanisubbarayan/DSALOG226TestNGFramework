package base;

import org.testng.annotations.*;
import driver.DriverFactory;
import utilities.ConfigReader;
import org.openqa.selenium.WebDriver;


public class BaseTest {

   
  
    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("chrome") String browser) {
        DriverFactory.initDriver(browser);

        String url = ConfigReader.getProperty("baseUrl");
        if (url == null || url.isEmpty()) {
            throw new RuntimeException("baseUrl not found in config.properties");
        }
        DriverFactory.getDriver().get(url);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

 
    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
  
}