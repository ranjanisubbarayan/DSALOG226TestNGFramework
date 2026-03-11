package pageObjects;

import java.time.Duration;
import java.util.Map;

import java.nio.file.Paths;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import utilities.ConfigReader;
import utilities.ExcelSheetHandling;

public class StackPage {

    private WebDriver driver;
    private WebDriverWait wait;
    LaunchPage launchPage;
    private homePage homepage;
    StackPage stackPage;
    private Map<String, String> stackphyTryEditData;
    
    public StackPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@role='alert']")
    WebElement verifyHomepage;

    @FindBy(xpath = "//h4[text()='Stack']")
    WebElement verifyStackPageHeader;

    @FindBy(xpath = "//a[normalize-space()='Operations in Stack']")
    WebElement verifyOpertioninStack;

    @FindBy(xpath = "//p[text()='Implementation']")
    WebElement verifyImplementinStack;

    @FindBy(xpath = "//p[text()='Applications']")
    WebElement verifyApplicationStack;
    @FindBy(xpath = "//button[text()='Run']")
    WebElement verifyTryEditorPage;

    @FindBy(xpath = "//h5[text()='Stack']/following-sibling::a")
    WebElement stackGetStartedBtn;

    @FindBy(xpath = "//a[text()='Operations in Stack']")
    WebElement linkOperationInStack;

    @FindBy(xpath = "//a[text()='Implementation']")
    WebElement linkImplementStack;

    @FindBy(xpath = "//a[text()='Applications']")
    WebElement linkApplicationStack;

    @FindBy(xpath = "//a[text()='Try here>>>']")
    WebElement tryHereButton;

    @FindBy(xpath = "//div[@class='CodeMirror-scroll']")
    WebElement codeEditor;

    @FindBy(xpath = "//button[text()='Run']")
    WebElement run;
    @FindBy(xpath = "//h4[text()='Stack']")
    WebElement stackHeader;
    @FindBy(id = "output")
    WebElement outputConsole;

    @FindBy(xpath = "//a[text()='Practice Questions']")
    WebElement practiceQuestionsLink;
    
    

	public void clickStackGetStarted() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    WebElement stackBtn = wait.until(
	        ExpectedConditions.elementToBeClickable(stackGetStartedBtn)
	    );

	    ((JavascriptExecutor) driver).executeScript(
	        "arguments[0].scrollIntoView(true);", stackBtn
	    );
	    ((JavascriptExecutor) driver).executeScript(
	        "arguments[0].click();", stackBtn
	    );
	}	
	 public boolean isHomePageDisplayed() { 
		 return verifyHomepage.isDisplayed(); 
		 }
	
   	public boolean isStackPageDisplayed() {
        return verifyStackPageHeader.isDisplayed();
    }

    public boolean isOperationInStackDisplayed() {
        return verifyOpertioninStack.isDisplayed();
    }
    public boolean isImplementInStackDisplayed() {
        return verifyImplementinStack.isDisplayed();
    }

    public boolean isApplicationInStackDisplayed() {
        return verifyApplicationStack.isDisplayed();
    }
    public boolean isTryEditorDisplayed() {
        return verifyTryEditorPage.isDisplayed();
    }
					

		public void clickOperationsInStack() { 
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		    try {
		        WebElement operationsLink = wait.until(ExpectedConditions.elementToBeClickable(linkOperationInStack));
		       
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", operationsLink);
		        
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", operationsLink);
		    } catch (TimeoutException e) {
		        throw new RuntimeException("'Operations in Stack' link not found or not clickable!", e);
		    }			
				} 
			
			public void clickImplementStackLink() { 				 
				wait.until(ExpectedConditions.elementToBeClickable(linkImplementStack)).click();				
				} 
			public void clickApplicationStackLink() {				
				wait.until(ExpectedConditions.elementToBeClickable(linkApplicationStack)).click(); 
				}
			
			public void clickPracticeQuestions() { 
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); 				
				WebElement practiceLink = wait.until(ExpectedConditions.elementToBeClickable(practiceQuestionsLink ));				
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", practiceLink); 
				practiceLink.click();				 
			}	

			    public void clickTryHere() {
			        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tryHereButton);
			    }
				public void clickRunButton() {				   
					run.click();
				}
				public void enterCodeInEditor(String code) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			    try {			       
			    	wait.until(ExpectedConditions.visibilityOf(codeEditor)); 
			        WebElement cm = codeEditor.findElement(By.cssSelector(".CodeMirror-code"));
			        
			        new Actions(driver).click(cm).perform();
			        new Actions(driver)
			                .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
			                .sendKeys(Keys.DELETE)
			                .sendKeys(code)
			                .perform();			        
			        run.click();			        
			        try {
			            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			            String alertText = alert.getText();
			            alert.accept();
			            System.out.println("Alert displayed: " + alertText);
			        } catch (TimeoutException e) {			           
			            wait.until(ExpectedConditions.visibilityOf(outputConsole));
			            System.out.println("Code executed successfully. Output: " + outputConsole.getText());
			        }
			    } catch (Exception e) {
			        
			    }
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

				public String seeOutput() {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
					  wait.until(ExpectedConditions.visibilityOf(outputConsole));
					    String output = outputConsole.getText();
					    System.out.println("Console Output: " + output);
			       	return output;
				}
				public void waitForStackPage() {
					wait.until(ExpectedConditions.visibilityOf(stackHeader));
					
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

				 public void navigateToStackPage() {
				        loginToApplication();
				        clickStackGetStarted();
				       
				    }

				    public void navigateToOperationsInStackPage() {
				        navigateToStackPage();
				        clickOperationsInStack();
				     
				    }

				    public void navigateToTryEditorFromOperationsInStack() {
				        navigateToOperationsInStackPage();
				        clickTryHere();
				       
				    }

}
