package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyser implements IRetryAnalyzer {

	private int retryCount = 0;
    private int maxRetryCount = 1;

    @Override
    public boolean retry(ITestResult result) {

        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("Retrying " + result.getName() + " again. Retry " + retryCount);
            return true;
        }
        return false;
    }
	
}
