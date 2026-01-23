package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class homePage {

    private WebDriver driver;
    private WebDriverWait wait;
    JavascriptExecutor js;
    
    public homePage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    @FindBy(xpath = "//a[@href='/register']")
     WebElement registerLink;

    @FindBy(xpath = "//a[@href='/login']")
     WebElement loginLink;

    @FindBy(xpath = "//a[@href='/logout']")
     WebElement logoutLink;

    @FindBy(xpath = "//a[@href='/home']")
     WebElement homeTitle;

    @FindBy(xpath = ".//a[normalize-space()='Get Started']")
    private List<WebElement> getStartedButton;

    @FindBy(xpath = "//div[contains(text(),'You are not logged in')]")
     WebElement errorMsg;

    @FindBy(xpath = "//a[contains(text(),'Data Structures')]")
     WebElement dropdownMenu;

    @FindBy(xpath = "//div[contains(@class,'dropdown-menu')]//a")
     List<WebElement> dropdownItems;

    @FindBy(xpath = "//div[contains(@class,'card')]")
     private List<WebElement> dataStructureCard;

    @FindBy(xpath = ".//h5[contains(@class,'card-title')]")
    private List<WebElement> cardTitle;

    @FindBy(xpath = "//a[normalize-space()='Sign in']")
     WebElement signinlink;

    

    public void openDropdownOnly() {
        wait.until(ExpectedConditions.elementToBeClickable(dropdownMenu));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdownMenu);
        wait.until(ExpectedConditions.visibilityOfAllElements(dropdownItems));
    }
    
    public List<String> getAllDropdownModules() {
    	return dropdownItems
                .stream()
                .map(e -> e.getText().trim())
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }

    public void clickModuleFromDropdown(String moduleName) {
       
        List<WebElement> items = wait.until(
                ExpectedConditions.visibilityOfAllElements(dropdownItems));
        for (WebElement item : items) {
            if (item.getText().trim().equalsIgnoreCase(moduleName.trim())) {
                item.click();
                return;
            }
        }
        throw new RuntimeException(" Module '" + moduleName + "' not found in dropdown");
        }
    
    private void scrollAndClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
    
    public void waitForHomePageToLoad() {
        wait.until(ExpectedConditions.visibilityOf(homeTitle));
    }
   
    public boolean isHomePageDisplayed() {
    	try {
            return  homeTitle.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isRegisterLinkDisplayed() {
        return registerLink.isDisplayed();
    }

    public boolean isSignInLinkDisplayed() {
        return loginLink.isDisplayed();
    }

    public boolean isUserLoggedIn() {
        try {
            return logoutLink.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public registerPage clickRegisterLink() {
        scrollAndClick(registerLink);
        return new registerPage(driver);
    }

    public LoginPage clickSignInLink() {
        scrollAndClick(loginLink);
        return new LoginPage(driver);
    }

    public LoginPage clickSignOut() {
        try {
            if (logoutLink.isDisplayed()) {
                scrollAndClick(logoutLink);
            }
        } catch (NoSuchElementException e) {
            
        }
        return new LoginPage(driver);
    }

    public void clickAllDropdownModulesSafely() {
        String[] modules = {"Arrays", "Linked List", "Stack", "Queue", "Tree", "Graph"};
        for (String moduleName : modules) {
            clickModuleFromDropdown(moduleName);
            driver.navigate().back();
            waitForHomePageToLoad();
        }
    }
    
    public void clickGetStartedForModule(String moduleName) {

    	 wait.until(ExpectedConditions.visibilityOfAllElements(dataStructureCard));
    	 for (int i = 0; i < dataStructureCard.size(); i++) {

    		  String actualTitle = normalize(cardTitle.get(i).getText());
    	        String expectedTitle = normalize(moduleName);
    	        if (actualTitle.contains(expectedTitle)
    	                || expectedTitle.contains(actualTitle)) {

    	            scrollAndClick(getStartedButton.get(i));
    	            return;
    	        }
    	    }

    	    throw new RuntimeException(
    	            "Module '" + moduleName + "' not found in Data Structures cards.");
    	}

    private String normalize(String text) {
        return text
                .toLowerCase()
                .replace("–", "-")   
                .replace("—", "-")   
                .replaceAll("\\s+", " ")
                .trim();
    }

    public String getWarningMessageText() {
    	try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement alert = wait.until(ExpectedConditions.visibilityOf(errorMsg));
            String text = alert.getText();
            System.out.println("Alert displayed: " + text);
            return text;
        } catch (TimeoutException e) {
           
            try {
                           
            	String text = errorMsg.getText();
                System.out.println("Alert found without waiting: " + text);
                return text;  
            } catch (NoSuchElementException ex) {
                System.out.println("Warning message still not found!");
                return "";  
            }
        }
    }
    
    public void clickSignInLinkIfPresent() {
        try {
            if (loginLink.isDisplayed()) {
                loginLink.click();
            }
        } catch (NoSuchElementException e) {
            
        } catch (ElementClickInterceptedException e) {
           
            System.out.println("Sign in link could not be clicked: " + e.getMessage());
        }
    }

    public void navigateToHomePage() {
        driver.get("https://dsportalapp.herokuapp.com/home");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElements(getStartedButton));
    }
    public boolean areImportantOptionsVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(getStartedButton));
            wait.until(ExpectedConditions.visibilityOf(signinlink));

            return getStartedButton.get(0).isDisplayed()
                    && signinlink.isDisplayed();
        } catch (Exception e) {
            System.out.println("Important home page options not visible: " + e.getMessage());
            return false;
        }
    }
    public void signOutIfLoggedIn() {
        try {
            if (logoutLink.isDisplayed()) {
                System.out.println("User is logged in. Signing out...");
                logoutLink.click();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.visibilityOf(signinlink));

                System.out.println("Sign out completed.");
            } else {
                System.out.println("User is not logged in. No sign out needed.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("User is not logged in. No sign out needed.");
        } catch (Exception e) {
            System.out.println("Exception while trying to sign out: " + e.getMessage());
        }
    }

}