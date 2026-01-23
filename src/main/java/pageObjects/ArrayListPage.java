package pageObjects;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ArrayListPage {
	
	WebDriver driver;
    WebDriverWait wait;
    Actions actions;

	public ArrayListPage(WebDriver driver) {
		this.driver=driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.actions = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	
	@FindBy (xpath="//div[contains(text(),'You are logged in')]")
	WebElement VerifyHomepage;
	
	@FindBy (xpath="//a[@href='array']")
	WebElement btnArrayGetstarted;
	
	@FindBy (xpath="//div/h4[text()='Array']")
	WebElement verifyArrayspage;
	
	@FindBy (xpath="//a[contains(text(),'Arrays in Python')]")
	WebElement lnkArraysInPython;
	
	@FindBy (xpath="//a[@href='/tryEditor']")
	WebElement btnTryEditor;
	
	@FindBy (xpath="//button[contains(text(),'Run')]")
	WebElement btnRun;
	
	@FindBy (xpath="//p[contains(text(),'Arrays in Python')]")
	WebElement verifyArraysInPython;
	
	@FindBy (xpath="//pre[@role='presentation']")
	WebElement codeEditor;
	
	public void writeCodeAndRun(String code) {
		Actions actions = new Actions(driver);
		actions.click(codeEditor).perform();
		actions.sendKeys(code).perform();
		btnRun.click();
	}
	  public String getHomePageText() {
	        return VerifyHomepage.getText();
	    }

	    public String getArrayPageText() {
	        return verifyArrayspage.getText();
	    }
	

	public void getstartedArray() {
		btnArrayGetstarted.click();
	}
	public void clickArraysInPython() {
		lnkArraysInPython.click();
	}
	public void clickTryHere() {
		btnTryEditor.click();
	}

	public boolean isRunButtonDisplayed() {
        return btnRun.isDisplayed();
    }

   
	   public String getArraysInPythonText() {
	        return verifyArraysInPython.getText();
	    }
	public void writeAndRunLinkedListCode(String code) throws IOException {
		Actions action=new Actions(driver);
		action.click(codeEditor).perform();
		action.sendKeys(code).perform();
		btnRun.click();
	}
	public void waitForArrayPage() {
        wait.until(ExpectedConditions.visibilityOf(verifyArrayspage));
    }

	
	public void clickTryHereIfVisible() {
        try {
        	clickArraysInPython();
            wait.until(ExpectedConditions.elementToBeClickable(btnTryEditor)).click();
            wait.until(ExpectedConditions.visibilityOf(btnRun));
        } catch (Exception e) {
            throw new RuntimeException("Try Here button not visible: " + e.getMessage());
        }
    }

	public String waitForAlertIfPresent() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
	        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
	        String text = alert.getText();
	        alert.accept();
	        return text;
	    } catch (Exception e) {
	        return null; 
	    }
	}
}
