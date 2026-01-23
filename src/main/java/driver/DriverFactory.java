package driver;

import java.time.Duration;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static ThreadLocal<String> browserName = new ThreadLocal<>();

    
    public static void setBrowser(String browser) {
        browserName.set(browser);
    }

    public static WebDriver getDriver() {
        if (webDriver.get() == null) {
            String browser = browserName.get();

            if (browser == null || browser.trim().isEmpty()) {
                browser = System.getProperty("browser", "chrome");
            }

            webDriver.set(createDriver(browser));
        }
        return webDriver.get();
    }

    private static WebDriver createDriver(String browser) {

        browser = browser.trim().toLowerCase();
        System.out.println("Launching browser: " + browser);

        WebDriver driver;

        switch (browser) {

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-infobars");
                chromeOptions.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driver = new FirefoxDriver(firefoxOptions);
                driver.manage().window().maximize();
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driver = new EdgeDriver(edgeOptions);
                driver.manage().window().maximize();
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

       
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        return driver;
    }

    public static void cleanupDriver() {
        if (webDriver.get() != null) {
            webDriver.get().quit();
            webDriver.remove();
            browserName.remove();
        }
    }
}
