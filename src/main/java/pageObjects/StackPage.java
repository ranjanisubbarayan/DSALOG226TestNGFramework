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

import utilities.ExcelSheetHandling;

public class StackPage {

    private WebDriver driver;
    private WebDriverWait wait;

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

    @FindBy(xpath = "//p[text()='Operations in Stack']")
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
	
					
		public void clickstack_Getstarted_btn() {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); 
			WebElement stackBtn = wait.until(ExpectedConditions.elementToBeClickable(stackGetStartedBtn));
			 
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", stackBtn); 
			
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
			 public void clickTryhereofoperation() throws InterruptedException {
			        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", tryHereButton);
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
				public void errorMessageinAlertWindow() {
					 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
						Alert alert = null;
						try {						   
						    alert = wait.until(ExpectedConditions.alertIsPresent());						   
						    String alertMsg = alert.getText();
						    System.out.println("Alert Message: " + alertMsg); 
						    alert.accept();						   				    
						} catch (TimeoutException e) {						    
						    System.out.println("No native alert appeared.");						   
						}
				}
				public void readDataFromExcel(String testId) {
					String path = Paths.get("src/test/resources/ExcelSheet/DsAlgoTestData.xlsx").toString();
				    ExcelSheetHandling excel = new ExcelSheetHandling(path);

				    System.out.println("Reading Excel Sheet: phythonTryEditor, testId=" + testId);

				    stackphyTryEditData = excel.getRowData("phythonTryEditor", testId);

				    if (stackphyTryEditData == null || stackphyTryEditData.isEmpty()) {
				        throw new RuntimeException("Excel returned EMPTY/NULL data for sheet 'phythonTryEditor' and testId: " + testId);
				    }
				    System.out.println("Loaded Excel row: " + stackphyTryEditData);
				}

				public void getDataFromExcel() {
				    String valid = stackphyTryEditData.get("Valid Input");
				    String invalid = stackphyTryEditData.get("Invalid Input");				    
				    if (valid != null && !valid.isEmpty()) {
				        enterCodeInEditor(valid);
				    }
				    else if (invalid != null && !invalid.isEmpty()) {
				        enterCodeInEditor(invalid);
				    }
				    else {
				        throw new RuntimeException("Both Valid Input and Invalid Input columns are empty in Excel!");
				    }
				}
						
				public void seeOutput() {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			    	wait.until(ExpectedConditions.visibilityOf(outputConsole)).getText();
			       	System.out.println(outputConsole);
				}	
}
