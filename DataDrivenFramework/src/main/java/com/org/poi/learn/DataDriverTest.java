package com.org.poi.learn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDriverTest {

	WebDriver driver;

	@BeforeClass
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://admin-demo.nopcommerce.com/login");

	}

	@Test(priority = 1, dataProvider = "LoginData")
	public void loginTest(String userName, String passWord, String exp) {

		WebElement usertextbox = driver.findElement(By.id("Email"));
		usertextbox.clear();
		usertextbox.sendKeys(userName);

		WebElement passwordtextbox = driver.findElement(By.id("Password"));
		passwordtextbox.clear();
		passwordtextbox.sendKeys(passWord);

		WebElement loginButton = driver.findElement(By.xpath("//button[@type = 'submit']"));
		loginButton.click();

		String exp_title = "Dashboard / nopCommerce administration";
		String actual_title = driver.getTitle();
		System.out.println(actual_title);
		if (exp.equals("Valid")) {

			if (exp_title.equals(actual_title)) {
				driver.findElement(By.linkText("Logout")).click();
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}

		} else if (exp.equals("Invalid")) {
			if (exp_title.equals(actual_title)) {
				driver.findElement(By.linkText("Logout")).click();
				Assert.assertTrue(false);
			} else {
				Assert.assertTrue(true);
			}
		}

	}

	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {

		/*
		 * String loginData[][] = {
		 * 
		 * { "admin@yourstore.com", "admin","Valid"}, { "adm@yourstore.com",
		 * "admin","Invalid"}, { "admin@yourstore.com", "adm", "Invalid" } };
		 */
		// Get the data from Excel document
		String filepath = ".\\datafiles\\login.xlsx";
		XLUtility xlutil = new XLUtility(filepath);

		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1", 1);

		String loginData[][] = new String[totalrows][totalcols];

		for (int i = 1; i <= totalrows; i++) {
			for (int j = 0; j < totalcols; j++) {
				loginData[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
			}
		}

		return loginData;

	}

	@AfterClass
	public void tearDown() {
		driver.close();
	}

}
