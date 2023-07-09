package selenium_tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class Chrome113 {
	
	@Test
	public void chromeLatest() throws MalformedURLException, InterruptedException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
    	capabilities.setBrowserName("chrome");
    	capabilities.setVersion("114.0");

        // Set the URL of the Selenium Grid Hub
        String seleniumGridUrl = "http://localhost:4444/wd/hub";

        // Create a new instance of the Chrome driver
        RemoteWebDriver driver = new RemoteWebDriver(new URL(seleniumGridUrl), 
        		capabilities);

        // Open the website
        driver.get("https://www.google.com");
        
        Thread.sleep(30000);
        
        // Print the title of the website
        System.out.println("Page title is: " + driver.getTitle());

        String browserVersion = driver.getCapabilities().getBrowserVersion();
        Platform platformName = driver.getCapabilities().getPlatformName();
        String browserName = driver.getCapabilities().getBrowserName();
        System.out.println("Browser Version is:"+ browserVersion);
        System.out.println("Platform Name is:"+ platformName);
        System.out.println("Browser Name is:"+ browserName);
        
        // Close the browser
        driver.quit();
	}

}
