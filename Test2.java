// Created by Marianna Gurovich on 1/10/2020
// Test scenario: Navigate to suuchi.com; select Startup Brands; Select Listen to Podcast

package suuchiTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.AfterMethod;


	public class Test2 {
		private WebDriver driver;

		@Parameters({ "browser" })
		@BeforeMethod(alwaysRun = true)
		public void openBrowser(@Optional("chrome") String browser) {

			switch (browser) {
			case "crome":
				System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
				driver = new ChromeDriver();
				break;
			case "firefox":
				System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
				driver = new FirefoxDriver();
				break;

			default:
				System.out.println("The browser is not specified. Start Chrome browser");
				System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
				driver = new ChromeDriver();
				break;
			}
			// wait for 3 seconds to page to load
			waitForPageToLoad(1000);

			// maximize window
			driver.manage().window().maximize();

			// implicit wait for the elements that not immediately available on page
			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// open page
			String url = "https://www.suuchi.com/";
			driver.get(url);
			System.out.println("Page is opened");

		}

		@Test
		public void openPodcast() {
			
			// click startup brands
			WebElement startUpBrands = driver.findElement(By.xpath("//a[@class='lnk_s_bx']")); 
			startUpBrands.click();
			
			// validate you are on the right page
			WebElement nextPage = driver.findElement(By.xpath("//h2[contains(text(),'BUILD YOUR BRAND THE RIGHT WAY')]"));
			String expectedSentence = "BUILD YOUR BRAND THE RIGHT WAY";
			String actualSentence = nextPage.getText();
			Assert.assertEquals(actualSentence, expectedSentence, "The message is correct");

			waitForPageToLoad(1000);
			
			// click on listen to podcast
			WebElement nameElement = driver.findElement(By.xpath("//*[contains(text(),'Listen to Podcast')]"));
			nameElement.click();
	
			waitForPageToLoad(1000);
    		        	
			// validate you are on the right page  
			WebElement nextPage2 = driver.findElement(By.xpath("//a[contains(normalize-space(.), 'The Suuchi Podcast')]"));
			String expectedSentence2 = "The Suuchi Podcast";
			String actualSentence2 = nextPage2.getText();
			Assert.assertEquals(actualSentence2, expectedSentence2, "The message is correct");
			
					
		}
		
		@AfterMethod(alwaysRun = true) 
		  private void closeBrowser() { 
			  // close browser
			  driver.quit(); 
		  }
		 
		private void waitForPageToLoad(long m) {
			try {
				Thread.sleep(m);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


}
