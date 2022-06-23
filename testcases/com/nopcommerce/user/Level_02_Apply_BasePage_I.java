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

import commons.BasePage;

public class Level_02_Apply_BasePage_I {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	BasePage basePage;
	String emailAddress;
  @BeforeClass
  public void beforeClass() {
//	 System.setProperty("webdriver.gecko.driver",  projectPath + "\\browerDrivers\\geckodriver.exe");
//	 driver = new FirefoxDriver();
	 
	 System.setProperty("webdriver.chrome.driver", projectPath + "\\browerDrivers\\chromedriver.exe");
	 driver = new ChromeDriver();
	 
	 //Che giau viec khoi tao doi tuong - tinh dong goi
	 // basePage = new BasePage();
	 basePage = BasePage.getBasePageObject();
	 
	 emailAddress = "abc" + getRandomNumber() + "@gmail.com";
	 
	 driver.manage().window().maximize();
	 driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	 driver.get("https://demo.nopcommerce.com/");
  }
	 
  @Test
  public void TC_01_Register_Empty_Data() {
	  basePage.waitForClickable(driver, "//a[@class='ico-register']");
	  basePage.clickToElement(driver, "//a[@class='ico-register']");
	  
	  basePage.waitForClickable(driver, "//button[@id='register-button']");
	  basePage.clickToElement(driver, "//button[@id='register-button']");
	  
	  Assert.assertEquals(basePage.getElementText(driver, "//span[@id='FirstName-error']"), "First name is required.");
	  Assert.assertEquals(basePage.getElementText(driver, "//span[@id='LastName-error']"), "Last name is required.");
	  Assert.assertEquals(basePage.getElementText(driver, "//span[@id='Email-error']"), "Email is required.");
	  Assert.assertEquals(basePage.getElementText(driver, "//span[@id='Password-error']"), "Password is required.");
	  Assert.assertEquals(basePage.getElementText(driver, "//span[@id='ConfirmPassword-error']"), "Password is required."); 
  }
  
  @Test
  public void TC_02_Register_Invalid_Email() {
	  basePage.waitForClickable(driver, "//a[@class='ico-register']");
	  basePage.clickToElement(driver, "//a[@class='ico-register']");
	  
	  basePage.senkeyToElement(driver, "//input[@id='FirstName']", "automation");
	  basePage.senkeyToElement(driver, "//input[@id='LastName']", "FC");
	  basePage.senkeyToElement(driver, "//input[@id='Email']", "123@123");
	  basePage.senkeyToElement(driver, "//input[@id='Password']", "123456");
	  basePage.senkeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");
	  
	  basePage.waitForClickable(driver, "//button[@id='register-button']");
	  basePage.clickToElement(driver, "//button[@id='register-button']");
	  Assert.assertEquals(basePage.getElementText(driver, "//div[@class='message-error validation-summary-errors']//li"), "Wrong email");
  }
  
  @Test
  public void TC_03_Register_Success() {
	  basePage.waitForClickable(driver, "//a[@class='ico-register']");
	  basePage.clickToElement(driver, "//a[@class='ico-register']");
	  
	  basePage.senkeyToElement(driver, "//input[@id='FirstName']", "automation");
	  basePage.senkeyToElement(driver, "//input[@id='LastName']", "FC");
	  basePage.senkeyToElement(driver, "//input[@id='Email']", emailAddress);
	  basePage.senkeyToElement(driver, "//input[@id='Password']", "123456");
	  basePage.senkeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");
	  
	  
	  basePage.waitForClickable(driver, "//button[@id='register-button']");
	  basePage.clickToElement(driver, "//button[@id='register-button']");
	  
	  Assert.assertEquals(basePage.getElementText(driver, "//div[@class='result']"), "Your registration completed");
	  //log out
	  basePage.waitForClickable(driver, "//a[@class='ico-logout']");
	  basePage.clickToElement(driver, "//a[@class='ico-logout']");
  }
  
  @Test
  public void TC_04_Register_Existing_Email() {
	  basePage.waitForClickable(driver, "//a[@class='ico-register']");
	  basePage.clickToElement(driver, "//a[@class='ico-register']");
	  
	  basePage.senkeyToElement(driver, "//input[@id='FirstName']", "automation");
	  basePage.senkeyToElement(driver, "//input[@id='LastName']", "FC");
	  basePage.senkeyToElement(driver, "//input[@id='Email']", emailAddress);
	  basePage.senkeyToElement(driver, "//input[@id='Password']", "123456");
	  basePage.senkeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");
	  
	  basePage.waitForClickable(driver, "//button[@id='register-button']");
	  basePage.clickToElement(driver, "//button[@id='register-button']");
	  
	  Assert.assertEquals(basePage.getElementText(driver, "//div[@class='message-error validation-summary-errors']//li"), "The specified email already exists");
  }
  
  @Test
  public void TC_05_Register_Password_Less_Than_6_Chars() {
	  basePage.waitForClickable(driver, "//a[@class='ico-register']");
	  basePage.clickToElement(driver, "//a[@class='ico-register']");
	  
	  basePage.senkeyToElement(driver, "//input[@id='FirstName']", "automation");
	  basePage.senkeyToElement(driver, "//input[@id='LastName']", "FC");
	  basePage.senkeyToElement(driver, "//input[@id='Email']", emailAddress);
	  basePage.senkeyToElement(driver, "//input[@id='Password']", "123");
	  basePage.senkeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");
	  
	  basePage.waitForClickable(driver, "//button[@id='register-button']");
	  basePage.clickToElement(driver, "//button[@id='register-button']");
	  
	  Assert.assertEquals(basePage.getElementText(driver, "//span[@id='Password-error']"), 
			  "Password must meet the following rules:\nmust have at least 6 characters");
  }
  
  @Test
  public void TC_06_Register_Wrong_Confirm_Password() {
	  basePage.waitForClickable(driver, "//a[@class='ico-register']");
	  basePage.clickToElement(driver, "//a[@class='ico-register']");
	  
	  basePage.senkeyToElement(driver, "//input[@id='FirstName']", "automation");
	  basePage.senkeyToElement(driver, "//input[@id='LastName']", "FC");
	  basePage.senkeyToElement(driver, "//input[@id='Email']", emailAddress);
	  basePage.senkeyToElement(driver, "//input[@id='Password']", "123456");
	  basePage.senkeyToElement(driver, "//input[@id='ConfirmPassword']", "123458");
	  
	  basePage.waitForClickable(driver, "//button[@id='register-button']");
	  basePage.clickToElement(driver, "//button[@id='register-button']");
	  
	  Assert.assertEquals(basePage.getElementText(driver, "//span[@id='ConfirmPassword-error']"), "The password and confirmation password do not match.");
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
