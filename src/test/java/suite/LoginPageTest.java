package suite;

import driver.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pageObjects.LaunchPage;
import pageObjects.LoginPage;
import pageObjects.homePage;
import utilities.ExcelSheetHandling;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginPageTest extends BaseTest {
	
	@DataProvider(name = "loginExcelData")
	public Object[][] getLoginData() {
	    String path = Paths.get("src/main/resources/ExcelSheet/DsAlgoTestData.xlsx").toString();
	    ExcelSheetHandling excel = new ExcelSheetHandling(path);
	    List<Map<String, String>> allRows = excel.getSheetData("Login");

	   
	    List<Map<String, String>> nonEmptyRows = new ArrayList<>();
	    for (Map<String, String> row : allRows) {
	        String testId = row.get("testId");
	        String username = row.get("username");
	        String password = row.get("password");

	        if ((testId == null || testId.trim().isEmpty()) &&
	            (username == null || username.trim().isEmpty()) &&
	            (password == null || password.trim().isEmpty())) {
	            break; 
	        }
	        nonEmptyRows.add(row);
	    }

	   
	    Object[][] data = new Object[nonEmptyRows.size()][1];
	    for (int i = 0; i < nonEmptyRows.size(); i++) {
	        data[i][0] = nonEmptyRows.get(i); 
	        System.out.println("Row " + i +
	                " | testId: " + nonEmptyRows.get(i).get("testId") +
	                " | username: " + nonEmptyRows.get(i).get("username") +
	                " | password: " + nonEmptyRows.get(i).get("password"));
	    }

	    return data;
	}
	@Test(dataProvider = "loginExcelData", priority = 1)
	public void verifyLogin(Map<String, String> rowData) {
	    String testId = rowData.get("testId");
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