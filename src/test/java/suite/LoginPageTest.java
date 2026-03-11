package suite;

import driver.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pageObjects.LaunchPage;
import pageObjects.LoginPage;
import pageObjects.homePage;
import utilities.TestDataProvider;
import java.util.Map;

public class LoginPageTest extends BaseTest {
	
	
	@Test(dataProvider = "loginExcelData",dataProviderClass = TestDataProvider.class, priority = 1)
	public void verifyLogin(Map<String, String> rowData) {
	   
	    String username = rowData.get("username");
	    String password = rowData.get("password");
	    String expectedResult = rowData.get("ExpectedResult");

	    LaunchPage launchPage = new LaunchPage(DriverFactory.getDriver());
	    homePage homepage = launchPage.clickGetStarted();
	    LoginPage loginPage = homepage.clickSignInLink();

	    
	    loginPage.login(username, password);

	    switch (expectedResult.trim()) {

	    case "You are logged in":
	        Assert.assertTrue(loginPage.isHomePageDisplayed(),
	                "Expected user to be logged in, but was NOT!");
	        break;

	    case "Please fill out this field":

	        String usernameMsg = loginPage.getUsernameValidationMessage();
	        String passwordMsg = loginPage.getPasswordValidationMessage();

	        Assert.assertTrue(
	                !usernameMsg.isEmpty() || !passwordMsg.isEmpty(),
	                "Expected browser validation message but found none."
	        );
	        break;

	    default:

	        String alertMessage = loginPage.getAlertMessage();

	        Assert.assertTrue(alertMessage.contains(expectedResult),
	                "Expected alert: " + expectedResult +
	                " but got: " + alertMessage);

	        break;
	}
	}
}