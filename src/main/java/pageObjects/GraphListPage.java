package pageObjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class GraphListPage {
	
	WebDriver driver;
	   WebDriverWait wait;

	public GraphListPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		PageFactory.initElements(driver, this);
	}

	
	@FindBy(xpath = "//div[contains(text(),'You are logged in')]")
	WebElement verifyHomepage;

	
	@FindBy(xpath = "//a[@href='graph']")
	WebElement btnGraphGetstarted;

	@FindBy(xpath = "//div/h4[text()='Graph']")
	WebElement verifyGraphPage;



	@FindBy(xpath = "//div//a[@href='graph']")
	

	WebElement lnkGraphTopic;

	@FindBy(xpath = "//a[normalize-space()='Graph Representations']")
	WebElement lnkGraphRepresentations;

	@FindBy(xpath = "//a[@href='/tryEditor']")
	WebElement btnTryEditor;

	@FindBy(xpath = "//button[contains(text(),'Run')]")
	WebElement btnRun;

	
	@FindBy(xpath = "//p[contains(text(),'Graph') and not(contains(text(),'Representations'))]")
	WebElement verifyGraphTopicText;

	@FindBy(xpath = "//p[contains(text(),'Graph Representations')]")
	WebElement verifyGraphRepresentationsText;

	
	@FindBy(xpath = "//pre[@role='presentation']")
	WebElement writeCode;


	public String getHomePageText() {
        return verifyHomepage.getText();
    }
	public void getstartedGraph() {
		btnGraphGetstarted.click();
	}

    public String getGraphLandingPageText() {
        return verifyGraphPage.getText();
    }

	public void clickGraphTopic() {
		lnkGraphTopic.click();
	}

	public void clickGraphRepresentations() {
		lnkGraphRepresentations.click();
	}

	public void clickTryHere() {
		btnTryEditor.click();
	}
	
	public void clickRunButton() {
		btnRun.click();
	}

	  public boolean isRunButtonDisplayed() {
	        return btnRun.isDisplayed();
	    }

	    public boolean isGraphTopicTextDisplayed() {
	        return verifyGraphTopicText.isDisplayed();
	    }

	    public boolean isGraphRepresentationsTextDisplayed() {
	        return verifyGraphRepresentationsText.isDisplayed();
	    }
	    
	    public void clickTryHereIfVisible() {
	        try {
	        	clickGraphTopic();
	            wait.until(ExpectedConditions.elementToBeClickable(btnTryEditor)).click();
	            wait.until(ExpectedConditions.visibilityOf(btnRun));
	        } catch (Exception e) {
	            throw new RuntimeException("Try Here button not visible: " + e.getMessage());
	        }
	    }
	
	public void writeCodeAndRun(String code) {
		Actions actions = new Actions(driver);
		actions.click(writeCode).perform();
		actions.sendKeys(code).perform();
		btnRun.click();
	}

	
	public String getAlertTextAndAccept() {
		Alert a = driver.switchTo().alert();
		String text = a.getText();
		a.accept();
		return text;
	}
	public String waitForAlertIfPresent() {
	    try {
	       
	        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
	        String text = alert.getText();
	        alert.accept();
	        return text;
	    } catch (Exception e) {
	        return null; 
	    }
	}

	
	public String getConsoleOutput() {
		
		WebElement output = driver.findElement(By.id("output"));
		return output.getText().trim();
	}
	
	public void writeAndRunLinkedListCode(String code) throws IOException {
		Actions action=new Actions(driver);
		action.click(writeCode).perform();
		action.sendKeys(code).perform();
		btnRun.click();
	}
	
}
