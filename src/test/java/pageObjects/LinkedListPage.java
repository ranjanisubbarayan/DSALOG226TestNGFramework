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
	
	@FindBy (xpath="//a[normalize-space()='Introduction']")
	WebElement linkIntroduction;
	
	@FindBy (xpath="//a[normalize-space()='Creating Linked LIst']")
    WebElement lnkCreatingLinkedlist;
	
	@FindBy (xpath="//a[normalize-space()='Types of Linked List']")
    WebElement lnktypesLinkedlist;
	
	@FindBy (xpath="//a[normalize-space()='Implement Linked List in Python']")
    WebElement lnkImplementingLinkedlist;
	
	@FindBy (xpath="//a[normalize-space()='Traversal']")
    WebElement lnkTraversalinkedlist;
	
	@FindBy (xpath="//a[normalize-space()='Insertion']")
    WebElement lnkInsertionLinkedlist;
	
	@FindBy (xpath="//a[normalize-space()='Deletion']")
    WebElement lnkDeletioninkedlist;
			
	@FindBy (xpath="//a[@href='/tryEditor']")
	WebElement btnTryEditor;
	
	@FindBy (xpath="//pre[@role='presentation']")
	WebElement codeEditor;
	
	@FindBy (xpath="//button[contains(text(),'Run')]")
	WebElement btnRun;
	
	@FindBy(xpath="//pre[@id='output']")
	WebElement console;
	
	public void getstartedLinkedList() {
		btnLinkedListGetstarted.click();
	}
	  public String getLinkedListPageText() {
	        return verifyLinkedListPage.getText();
	    }
	public void clickIntroductionLink() {
		linkIntroduction.click();
	}
	
	public void clickCreatingLink() {
		lnkCreatingLinkedlist.click();
	}
	
	public void clickDeletionLink() {
		lnkDeletioninkedlist.click();
	}
	
	public void clickImplementingLink() {
		lnkImplementingLinkedlist.click();
	}
	
	public void clickInsertionLink() {
		lnkInsertionLinkedlist.click();
	}
	
	public void clickTraversalLink() {
		lnkTraversalinkedlist.click();
	}
	
	public void clicktypesLink() {
		lnktypesLinkedlist.click();
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
	
	public String getOutput(){
		return console.getText();
	}
}
