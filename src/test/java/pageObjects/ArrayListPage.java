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

import utilities.ConfigReader;

import org.openqa.selenium.TimeoutException;
import java.util.List;



public class ArrayListPage {
	
	WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    LaunchPage launchPage;
    private homePage homepage;
    
    
	public ArrayListPage(WebDriver driver) {
		this.driver=driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.actions = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//pre[@id='output']")
	WebElement console;
	@FindBy (xpath="//div[contains(text(),'You are logged in')]")
	WebElement VerifyHomepage;
	
	@FindBy (xpath="//a[@href='array']")
	WebElement btnArrayGetstarted;
	
	@FindBy (xpath="//div/h4[normalize-space()='Array']")
	WebElement verifyArrayspage;
	
	@FindBy (xpath="//a[normalize-space()='Arrays in Python']")
	WebElement lnkArraysInPython;
	
	@FindBy (xpath="//a[normalize-space()='Basic Operations in Lists']")
	WebElement lnkBasicOperationofArray;
	
	@FindBy (xpath="//a[normalize-space()='Applications of Array']")
	WebElement lnkApplicationofArray;
	
	@FindBy (xpath="//a[normalize-space()='Arrays Using List']")
	WebElement lnkArraysusinglist;
	
	@FindBy (xpath="//a[normalize-space()='Practice Questions']")
	WebElement lnkPracticeQue;
	
	@FindBy(xpath = "//a[contains(@href,'array')]")
	private List<WebElement> arraypagelements;

	
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
	public void clickArraysUsingList() {
		lnkArraysusinglist.click();
	}
	public void clickBasicOperationArray() {
		lnkBasicOperationofArray.click();
	}
	public void clickApplicationofArray() {
		lnkApplicationofArray.click();
	}
	public void clickPracticeQue() {
		lnkPracticeQue.click();
	}
	
	public void clickTryHere() {
		btnTryEditor.click();
	}
	public boolean isTryHereButtonVisible() {
	    return btnTryEditor.isDisplayed();
	}
	public boolean isTryHereButtonClickable() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    try {
	        wait.until(ExpectedConditions.elementToBeClickable(btnTryEditor));
	        return true;
	    } catch (TimeoutException e) {
	        return false;
	    }
	}
	public boolean isRunButtonDisplayed() {
        return btnRun.isDisplayed();
    }

	public void writeAndRunLinkedListCode(String code) throws IOException {
		Actions action=new Actions(driver);
		action.click(codeEditor).perform();
		action.sendKeys(code).perform();
		btnRun.click();
	}

	   public String getArraysInPythonText() {
	        return verifyArraysInPython.getText();
	    }
	public void waitForArrayPage() {
        wait.until(ExpectedConditions.visibilityOf(verifyArrayspage));
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
	
	 public void loginToApplication() {
	        String username = ConfigReader.getProperty("username");
	        String password = ConfigReader.getProperty("password");
	        launchPage = new LaunchPage(driver);
	        homepage = launchPage.clickGetStarted();

	        if (!homepage.isUserLoggedIn()) {
	            homepage.clickSignInLinkIfPresent();
	            LoginPage loginPage = new LoginPage(driver);
	            loginPage.enterUsername(username);
	            loginPage.enterPassword(password);
	            loginPage.clickLoginButton();
	        }
	 }

}
