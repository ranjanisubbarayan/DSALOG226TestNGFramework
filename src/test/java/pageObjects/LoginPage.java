package pageObjects;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
    private WebDriver driver;
    WebDriverWait wait;
   
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
        PageFactory.initElements(driver, this); 
    }

    @FindBy(id = "id_username")
    public WebElement loginUsername;

    @FindBy(id = "id_password")
    public WebElement loginPassword;

    @FindBy(xpath = "//input[@value='Login']")
     public WebElement loginBtn;

    @FindBy(xpath = "//div[@role='alert']")
     WebElement alertMsg;
 
    @FindBy(xpath = "//a[text()='NumpyNinja']")
     WebElement homePageLink;
   
    public void clickLoginButton() {
    	  wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
          	loginBtn.click();
    }    

    public void enterUsername(String username) {
    	 wait.until(ExpectedConditions.visibilityOf(loginUsername));
         loginUsername.clear();
         loginUsername.sendKeys(username);       
    }

    public void enterPassword(String password) {    	
      wait.until(ExpectedConditions.visibilityOf(loginPassword));
      loginPassword.clear();
        loginPassword.sendKeys(password);
    }    
       
    public String getAlertMessage() {
        try {
        	   return alertMsg.getText();         
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isHomePageDisplayed() {
    	
        try {
            wait.until(ExpectedConditions.visibilityOf(homePageLink));             
            return true;
        } catch (Exception e) {
            return false;
        }       
    }    
    
    public String getUsernameValidationMessage() {
        
        return (String)((JavascriptExecutor)driver).executeScript(
                "return arguments[0].validationMessage;", loginUsername);
    }

    public String getPasswordValidationMessage() {
       
        return (String)((JavascriptExecutor)driver).executeScript(
                "return arguments[0].validationMessage;", loginPassword);
    }   
    
    public void login(String user, String pass) {
        enterUsername(user);
        enterPassword(pass);
        clickLoginButton();
    }
}
