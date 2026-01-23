package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.UUID;

public class registerPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private String generatedUsername;
  
    public registerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
  
    @FindBy(xpath = "//a[@href='/register']")
    WebElement register_link;

    @FindBy(id = "id_username")
   public WebElement register_username;

    @FindBy(id = "id_password1")
   public WebElement register_password;

    @FindBy(id = "id_password2")
   public WebElement register_confirm_password;

    @FindBy(xpath = "//input[@value='Register']")
   public WebElement register_button;

    @FindBy(xpath = "//input[@value='Register']")
    WebElement registerpage_displayed;

    @FindBy(xpath = "//div[@role='alert']")
    WebElement printErrormsg;

    @FindBy(xpath = "//div[@role='alert']")
    WebElement registeredsuccess;

   @FindBy(xpath = "//a[@href='/logout']")
   WebElement signout;
   
   @FindBy(xpath = "//a[@href='/login']")
   WebElement signin;

    public void click_register_link() {
        wait.until(ExpectedConditions.elementToBeClickable(register_link)).click();
    }

    public boolean isRegisterPageDisplayed() {
        return register_button.isDisplayed();
    }
    
    public void safeClick(WebElement element) {
        for (int i = 0; i < 3; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("Retrying click (attempt " + (i + 1) + ")");
            }
        }
    }

    public void clickRegisterButton() {
        safeClick(register_button);
    }

    public void clickWithEmptyFields() {
        register_button.click();
    }

    public void show_ErrorMsg_EmptyUsername() {
        showValidationMessage(register_username);
    }

    public void show_ErrorMsg_EmptyPassword() {
        showValidationMessage(register_password);
    }

    public void show_ErrorMsg_EmptyConfirmPassword() {
        showValidationMessage(register_confirm_password);
    }

    private void showValidationMessage(WebElement element) {
        String message = (String) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].validationMessage;", element);
        System.out.println("Validation message: " + message);
    }

    public void print_ErrorMessage() {
        WebElement alert = wait.until(ExpectedConditions.visibilityOf(printErrormsg));
        System.out.println("Error Message: " + alert.getText());
    }

    public void enter_registerUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(register_username)).sendKeys(username);
       
    }

    public void enter_regPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(register_password)).sendKeys(password);
        
    }

    public void enter_regPwdConfirm(String confirmPassword) {
        wait.until(ExpectedConditions.visibilityOf(register_confirm_password)).sendKeys(confirmPassword);
       
    }

    public String getValidationMessage() {
        return getFieldValidation(register_username);
    }

    public String getPasswordValidationMessage() {
        return getFieldValidation(register_password);
    }

    private String getFieldValidation(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String message = (String) js.executeScript("return arguments[0].validationMessage;", element);
        System.out.println("Validation message: " + message);
        return message;
    }
    
    public void generate_newUsername() {
        generatedUsername = return_generateNewUsername();
        register_username.sendKeys(generatedUsername);
        System.out.println("Generated Username: " + generatedUsername);
    }

    public static String return_generateNewUsername() {    	
        return "user_name" + UUID.randomUUID().toString().substring(0, 8);
    }

    public String getGeneratedUsername() {
        return generatedUsername;
    }

    public void print_successfullyRegistered() {
        System.out.println("Registered Successfully: " + registeredsuccess.getText());
    }
    
    public LoginPage clickSigninLink() {
        signin.click();
        return new LoginPage(driver); 
    }
    
    public boolean isUsernameFieldVisible() {
        return register_username.isDisplayed();
    }

    public boolean isPasswordFieldVisible() {
        return register_password.isDisplayed();
    }

    public boolean isConfirmPasswordFieldVisible() {
        return register_confirm_password.isDisplayed();
    }

    public boolean isRegisterButtonVisible() {
        return register_button.isDisplayed();
    }
}
