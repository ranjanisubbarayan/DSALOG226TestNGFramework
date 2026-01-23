package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LaunchPage {	
	 WebDriver driver;
	 WebDriverWait wait;
	
	@FindBy(xpath = "//button[text()='Get Started']")
    WebElement getStartedButton;

    public LaunchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    public homePage clickGetStarted() {
        try {
            wait.until(ExpectedConditions.visibilityOf(getStartedButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getStartedButton);
            wait.until(ExpectedConditions.elementToBeClickable(getStartedButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getStartedButton);
        } catch (TimeoutException e) {
            throw new RuntimeException("Get Started button not found or not clickable!", e);
        }
        return new homePage(driver);
    }
    
 
}
