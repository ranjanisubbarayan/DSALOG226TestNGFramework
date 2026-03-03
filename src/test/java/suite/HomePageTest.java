package suite;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.LaunchPage;
import pageObjects.homePage;

import java.util.Arrays;
import java.util.List;

public class HomePageTest extends BaseTest {

    @Test(priority = 1)
    public void verifyNavigationToHomePage() {

        LaunchPage launchPage = new LaunchPage(driver);
        homePage homepage = launchPage.clickGetStarted();

        Assert.assertTrue(homepage.isHomePageDisplayed(),
                "Home page not displayed");
    }

    @Test(priority = 2)
    public void verifyDropdownModules() {

        LaunchPage launchPage = new LaunchPage(driver);
        homePage homepage = launchPage.clickGetStarted();

        homepage.openDropdownOnly();

        List<String> expectedModules = Arrays.asList(
                "Arrays",
                "Linked List",
                "Stack",
                "Queue",
                "Tree",
                "Graph"
        );

        List<String> actualModules = homepage.getAllDropdownModules();

        Assert.assertEquals(actualModules, expectedModules,
                "Dropdown modules mismatch");
    }

    @DataProvider(name = "moduleData")
    public Object[][] moduleData() {
        return new Object[][]{
                {"Arrays"},
                {"Linked List"},
                {"Stack"},
                {"Queue"},
                {"Tree"},
                {"Graph"}
        };
    }

    @Test(dataProvider = "moduleData", priority = 3)
    public void verifyWarningMessageForModules(String moduleName) {

        LaunchPage launchPage = new LaunchPage(driver);
        homePage homepage = launchPage.clickGetStarted();

        System.out.println("Print Module Name  :" + moduleName);
        homepage.clickModuleFromDropdown(moduleName);

        String expectedMessage = "You are not logged in";
        String actualMessage = homepage.getWarningMessageText();

        Assert.assertEquals(actualMessage, expectedMessage,
                "Warning message mismatch");
    }

    @Test(dataProvider = "moduleData", priority = 4)
    public void verifyWarningMessageForModulesGetstarted(String moduleName) {

        LaunchPage launchPage = new LaunchPage(driver);
        homePage homepage = launchPage.clickGetStarted();

        homepage.waitForHomePageToLoad();

        System.out.println("Print Module Name  :" + moduleName);
        homepage.clickGetStartedForModule(moduleName); 

        String expectedMessage = "You are not logged in";
        String actualMessage = homepage.getWarningMessageText();

        Assert.assertEquals(actualMessage, expectedMessage,
                "Warning message mismatch");
    }
}