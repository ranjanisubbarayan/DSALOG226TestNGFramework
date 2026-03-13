package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ExcelSheetHandling;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;

public class registerPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private String generatedUsername;
    private Map<String, String> registerData;

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

    @FindBy(xpath = "//div[@role='alert']")
    WebElement printErrormsg;
    
    @FindBy(xpath = "//div[@role='alert']")
    WebElement alertMessage;

    @FindBy(xpath = "//a[@href='/login']")
    WebElement signin;

   
    public void click_register_link() {
        wait.until(ExpectedConditions.elementToBeClickable(register_link)).click();
    }

    public boolean isRegisterPageDisplayed() {
        return register_button.isDisplayed();
    }

    public void clickRegisterButton() {
        safeClick(register_button);
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

    public void enter_registerUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(register_username)).clear();
        register_username.sendKeys(username);
    }

    public void enter_regPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(register_password)).clear();
        register_password.sendKeys(password);
    }

    public void enter_regPwdConfirm(String confirmPassword) {
        wait.until(ExpectedConditions.visibilityOf(register_confirm_password)).clear();
        register_confirm_password.sendKeys(confirmPassword);
    }

    public String getFieldValidation(WebElement element) {
        return (String)((JavascriptExecutor)driver).executeScript("return arguments[0].validationMessage;", element);
    }

    public String getValidationMessage() { 
    	return getFieldValidation(register_username);
    	}
    
    public String getPasswordValidationMessage() { 
    	return getFieldValidation(register_password); 
    	}
    public String getConfirmPasswordValidationMessage() { 
    	return getFieldValidation(register_confirm_password); 
    	}

    public void generate_newUsername() {
        generatedUsername = return_generateNewUsername();
        register_username.sendKeys(generatedUsername);
        System.out.println("Generated Username: " + generatedUsername);
    }

    public static String return_generateNewUsername() {
        return "user_name" + UUID.randomUUID().toString().substring(0, 8);
    }

    public void registerUsingExcel(String username, String password, String confirmPassword) {

        if (username == null || username.isEmpty()) {
            generate_newUsername();   
        } else {
            enter_registerUsername(username);
        }

        if (password != null && !password.isEmpty()) {
            enter_regPassword(password);
        }

        if (confirmPassword != null && !confirmPassword.isEmpty()) {
            enter_regPwdConfirm(confirmPassword);
        }

        clickRegisterButton();
    }
    public String generateUsernameAndWriteToExcel(ExcelSheetHandling excel, int rowNum, int colNum) {
        
        generatedUsername = return_generateNewUsername();

         enter_registerUsername(generatedUsername);
        System.out.println("Generated Username: " + generatedUsername);

        excel.writeCellData("Register", rowNum, colNum, generatedUsername);
        System.out.println("Username written to Excel at row " + rowNum + ", column " + colNum);

        return generatedUsername;
    }

    public String getAlertMessage() {
        try {
        	   return printErrormsg.getText();         
        } catch (Exception e) {
            return "";
        }
    }
    public boolean isAccountCreated() {
        try {
            String successMsg = getAlertMessage(); 
            return successMsg.contains("New Account Created");
        } catch (Exception e) {
            return false;
        }
    }
}