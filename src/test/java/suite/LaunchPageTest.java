package suite;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pageObjects.LaunchPage;
import pageObjects.homePage;

public class LaunchPageTest extends BaseTest {

    private LaunchPage launchPage;

    @BeforeMethod
    public void setUpPage() {
        launchPage = new LaunchPage(driver);
    }

    @Test(priority = 1)
    public void verifyLaunchPageTitle() {
        String expectedTitle = "Numpy Ninja";
        Assert.assertEquals(driver.getTitle(), expectedTitle, "Launch page title mismatch");
    }

    @Test(priority = 2)
    public void verifyGetStartedButtonAndNavigation() {
       
        Assert.assertTrue(launchPage.isGetStartedButtonDisplayed(), "Get Started button not displayed");

       
        homePage homePage = launchPage.clickGetStarted();
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page is not displayed after clicking Get Started");
    }
}