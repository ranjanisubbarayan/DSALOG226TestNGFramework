package utilities;

import driver.DriverFactory;

public class ElementUtils {

    public static String getCurrentTitle(){
        return DriverFactory.getDriver().getTitle();
    }
    public static String getCurrentURL(){
        return DriverFactory.getDriver().getCurrentUrl();
    }
    public static boolean getCurrenURLEndswith() {
    	String url= DriverFactory.getDriver().getCurrentUrl();
    	return url.endsWith(getCurrentURL());
    }
}
