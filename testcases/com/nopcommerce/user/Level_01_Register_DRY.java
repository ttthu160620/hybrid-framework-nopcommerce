package com.nopcommerce.user;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Level_01_Register_DRY {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String emailAddress = "abc" + getRandomNumber() + "@gmail.com";
  @BeforeClass
  public void beforeClass() {
//	 System.setProperty("webdriver.gecko.driver",  projectPath + "\\browerDrivers\\geckodriver.exe");
//	 driver = new FirefoxDriver();
	 
	 System.setProperty("webdriver.chrome.driver", projectPath + "\\browerDrivers\\chromedriver.exe");
	 driver = new ChromeDriver();
	 driver.manage().window().maximize();
	 driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	 driver.get("https://demo.nopcommerce.com/");
  }
	 
  @Test
  public void TC_01_Register_Empty_Data() {
	  driver.findElement(By.cssSelector(".ico-register")).click();
	  
	  driver.findElement(By.cssSelector("#register-button")).click();
	  
	  Assert.assertEquals(driver.findElement(By.cssSelector("#FirstName-error")).getText(), "First name is required.");
	  Assert.assertEquals(driver.findElement(By.cssSelector("#LastName-error")).getText(), "Last name is required.");
	  Assert.assertEquals(driver.findElement(By.cssSelector("#Email-error")).getText(), "Email is required.");
	  Assert.assertEquals(driver.findElement(By.cssSelector("#Password-error")).getText(), "Password is required.");
	  Assert.assertEquals(driver.findElement(By.cssSelector("#ConfirmPassword-error")).getText(), "Password is required.");
  }
  
  @Test
  public void TC_02_Register_Invalid_Email() {
	  driver.findElement(By.cssSelector(".ico-register")).click();
	  
	  driver.findElement(By.cssSelector("#FirstName")).sendKeys("automation");
	  driver.findElement(By.cssSelector("#LastName")).sendKeys("FC");
	  driver.findElement(By.cssSelector("#Email")).sendKeys("123@123");
	  driver.findElement(By.cssSelector("#Password")).sendKeys("123456");
	  driver.findElement(By.cssSelector("#ConfirmPassword")).sendKeys("123456");
	  
	  
	  driver.findElement(By.cssSelector("#register-button")).click();
	  Assert.assertEquals(driver.findElement(By.cssSelector(".message-error li")).getText(), "Wrong email");
  }
  
  @Test
  public void TC_03_Register_Success() {
	  driver.findElement(By.cssSelector(".ico-register")).click();
	  
	  driver.findElement(By.cssSelector("#FirstName")).sendKeys("automation");
	  driver.findElement(By.cssSelector("#LastName")).sendKeys("FC");
	  driver.findElement(By.cssSelector("#Email")).sendKeys(emailAddress);
	  driver.findElement(By.cssSelector("#Password")).sendKeys("123456");
	  driver.findElement(By.cssSelector("#ConfirmPassword")).sendKeys("123456");
	  
	  
	  driver.findElement(By.cssSelector("#register-button")).click();
	  sleepInSecond(2);
	  Assert.assertEquals(driver.findElement(By.cssSelector("div.result")).getText(), "Your registration completed");
	  //log out
	  driver.findElement(By.cssSelector(".ico-logout")).click();
  }
  
  @Test
  public void TC_04_Register_Existing_Email() {
	  driver.findElement(By.cssSelector(".ico-register")).click();
	  
	  driver.findElement(By.cssSelector("#FirstName")).sendKeys("automation");
	  driver.findElement(By.cssSelector("#LastName")).sendKeys("FC");
	  driver.findElement(By.cssSelector("#Email")).sendKeys("abc122@gmail.com");
	  driver.findElement(By.cssSelector("#Password")).sendKeys("123456");
	  driver.findElement(By.cssSelector("#ConfirmPassword")).sendKeys("123456");
	  
	  driver.findElement(By.cssSelector("#register-button")).click();
	  
	  Assert.assertEquals(driver.findElement(By.cssSelector(".message-error li")).getText(), "The specified email already exists");
  }
  
  @Test
  public void TC_05_Register_Password_Less_Than_6_Chars() {
	  driver.findElement(By.cssSelector(".ico-register")).click();
	  
	  driver.findElement(By.cssSelector("#FirstName")).sendKeys("automation");
	  driver.findElement(By.cssSelector("#LastName")).sendKeys("FC");
	  driver.findElement(By.cssSelector("#Email")).sendKeys(emailAddress);
	  driver.findElement(By.cssSelector("#Password")).sendKeys("123");
	  driver.findElement(By.cssSelector("#ConfirmPassword")).sendKeys("123456");
	  
	  driver.findElement(By.cssSelector("#register-button")).click();
	  
	  Assert.assertEquals(driver.findElement(By.cssSelector("#Password-error")).getText(), "Password must meet the following rules:\nmust have at least 6 characters");
  }
  
  @Test
  public void TC_06_Register_Wrong_Confirm_Password() {
	  driver.findElement(By.cssSelector(".ico-register")).click();
	  
	  driver.findElement(By.cssSelector("#FirstName")).sendKeys("automation");
	  driver.findElement(By.cssSelector("#LastName")).sendKeys("FC");
	  driver.findElement(By.cssSelector("#Email")).sendKeys(emailAddress);
	  driver.findElement(By.cssSelector("#Password")).sendKeys("123456");
	  driver.findElement(By.cssSelector("#ConfirmPassword")).sendKeys("123458");
	  
	  driver.findElement(By.cssSelector("#register-button")).click();
	  
	  Assert.assertEquals(driver.findElement(By.cssSelector("#ConfirmPassword-error")).getText(), "The password and confirmation password do not match.");
  }
 
  @AfterClass
  public void afterClass() {
	  driver.quit();
  }
  
  public int getRandomNumber() {
	  Random random = new Random();
	  return  random.nextInt(999);
  }
  
  public void sleepInSecond (long second) {
		try {
			Thread.sleep( second * 1000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}

}
