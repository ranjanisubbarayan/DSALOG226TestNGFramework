package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.PageLoadStrategy;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
   
    public static void initDriver(String browser) {
        if (driver.get() != null) {
            return ;
        }

        if (browser == null || browser.isEmpty()) {
            browser = "chrome"; 
        }

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                driver.set(new FirefoxDriver());
                break;

            case "edge":
                driver.set(new EdgeDriver());
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }

        driver.get().manage().window().maximize();
        
    }
    
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            throw new RuntimeException("Driver not initialized! Call initDriver() first.");
        }
        return driver.get();
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}