package pageObjects;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




public class LinkedListPage {
	private WebDriver driver;

	public LinkedListPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	
	@FindBy (xpath="//a[@href='linked-list']")
	WebElement btnLinkedListGetstarted;
	
	@FindBy (xpath="//div/h4[text()='Linked List']")
	WebElement verifyLinkedListPage;
	
	@FindBy (xpath="//a[contains(text(),'Introduction')]")
	WebElement linkIntroduction;
	
	@FindBy (xpath="//a[@href='/tryEditor']")
	WebElement btnTryEditor;
	
	@FindBy (xpath="//pre[@role='presentation']")
	WebElement codeEditor;
	
	@FindBy (xpath="//button[contains(text(),'Run')]")
	WebElement btnRun;
	
	 @FindBy(id = "output")
	    WebElement outputConsole;
	public void getstartedLinkedList() {
		btnLinkedListGetstarted.click();
	}
	  public String getLinkedListPageText() {
	        return verifyLinkedListPage.getText();
	    }
	public void clickIntroductionLink() {
		linkIntroduction.click();
	}
	public void clickTryHere() {
		btnTryEditor.click();
	}
	
	public void writeAndRunLinkedListCode(String code) throws IOException {
		Actions action=new Actions(driver);
		action.click(codeEditor).perform();
		action.sendKeys(code).perform();
		btnRun.click();
	}
	public String seeOutput() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		  wait.until(ExpectedConditions.visibilityOf(outputConsole));
		    String output = outputConsole.getText();
		    System.out.println("Console Output: " + output);
       	return output;
	}
	public String errorMessageinAlertWindow() {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			Alert alert = null;
			try {						   
			    alert = wait.until(ExpectedConditions.alertIsPresent());						   
			    String alertMsg = alert.getText();
			    System.out.println("Alert Message: " + alertMsg); 
			    alert.accept();	
			    return alertMsg;
			} catch (TimeoutException e) {						    
			    System.out.println("No native alert appeared.");	
			    return null; 
			}
	}
	
	
	
	
}
