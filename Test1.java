// Created by Marianna Gurovich on 1/10/2020
// Test scenario: Navigate to suuchi.com; select Contact Us; Submit form

package suuchiTests;

import java.util.List;
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
import org.openqa.selenium.support.ui.Select;


public class Test1 {
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

	@Parameters({ "name", "email", "phone", "budget", "description" })
	@Test
	public void submitForm(String name, String email, String phone, String budget, String description) {
		
		// click Contact Us button
		WebElement contactUsButton = driver.findElement(By.xpath("//a[contains(text(),'CONTACT US')]"));
		contactUsButton.click();
		
		// Step 1 - validate you are on the right page
		WebElement informtiveMsg = driver.findElement(By.xpath("//p[contains(text(),'We are here to answer any questions you have about our services. Drop us a line and we’ll be in touch as soon as we can.')]"));
		String expectedMsg1 = "Drop us a line and we’ll be in touch as soon as we can.";
		String actualMsg1 = informtiveMsg.getText();
//		Assert.assertEquals(actualMsg1, expectedMsg1, "The message is correct");
///
		Assert.assertTrue(actualMsg1.contains(expectedMsg1),
				"The message is correct.\nActual Message: " + actualMsg1 + "\nExpected Message: " + expectedMsg1);

		
		// enter name
		WebElement nameElement = driver.findElement(By.name("your-name"));
		nameElement.sendKeys(name);
		
		// enter email
		WebElement emailElement = driver.findElement(By.name("your-email"));
		emailElement.sendKeys(email);

		// enter phone
		WebElement phoneNumberElement = driver.findElement(By.name("tel-134"));
		phoneNumberElement.sendKeys(phone);

		// select company size 
		new Select(driver.findElement(By.name("company-size"))).selectByIndex(2);
		   
		// enter budget
		WebElement budgetElement = driver.findElement(By.name("budget"));
		budgetElement.sendKeys(budget);
		
		// enter project description
		WebElement projectDescriptionElement = driver.findElement(By.name("ProjectDescription"));
		projectDescriptionElement.sendKeys(description);

		// mark check box 1
		boolean checkBox1 = driver.findElement(By.name("checkbox-609[]")).isSelected();
		
		//if check box is not selected. select it
		if (checkBox1 == false)
			driver.findElement(By.name("checkbox-609[]")).click();

		/// mark check box 2
		boolean checkBox2 = driver.findElement(By.name("Thisisthelegaldisclaimer:[]")).isSelected();
		
		//if check box is not selected. select it
		if (checkBox2 == false)
			driver.findElement(By.name("Thisisthelegaldisclaimer:[]")).click();
	
		
		// submit a form
		WebElement submitFormElement = driver.findElement(By.xpath("//input[@value='Send Message']"));
		submitFormElement.click();

		// wait for 1 seconds to page to load
		waitForPageToLoad(5000);
		

		// validate you are on the right page
		WebElement successMsg = driver.findElement(By.xpath("//p[contains(text(),'Someone will be in touch shortly.')]"));
		String expectedMsg = "Someone will be in touch shortly.";
		String actualMsg = successMsg.getText();
		Assert.assertEquals(actualMsg, expectedMsg, "The message is correct");

		// wait for 1 seconds to page to load
		waitForPageToLoad(1000);
		
	}
	
	// close browser
	@AfterMethod(alwaysRun = true) 
	private void closeBrowser() 
	{ 
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
