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
import pageObjects.HomePageObject;
import pageObjects.RegisterPageObjects;
import pageUIs.RegisterPageUI;

public class Level_03_Apply_Base_Object{
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String emailAddress, firstName, lastName, password;
	private HomePageObject homePage;
	private RegisterPageObjects registerPage;
  
	@BeforeClass
	public void beforeClass() {
	 // System.setProperty("webdriver.gecko.driver",  projectPath + "\\browerDrivers\\geckodriver.exe");
	 // driver = new FirefoxDriver();
	 
	 System.setProperty("webdriver.chrome.driver", projectPath + "\\browerDrivers\\chromedriver.exe");
	 driver = new ChromeDriver();
	 driver.manage().window().maximize();
	 driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	 driver.get("https://demo.nopcommerce.com/");
	 
	 homePage = new HomePageObject(driver);
	 registerPage = new RegisterPageObjects(driver);
	 firstName = "automation";
	 lastName = "fc";
	 emailAddress = "abc" + getRandomNumber() + "@gmail.com";
	 password = "123456";
  }
	 
  @Test
  public void Register_01_Empty_Data() {
	  homePage.clickToRegisterLink();
	  registerPage.clickRegisterButton();
	  
	  Assert.assertEquals(registerPage.getErrorMessageFirstNameTextbox(), "First name is required.");
	  Assert.assertEquals(registerPage.getErrorMessageLastNameTextbox(), "Last name is required.");
	  Assert.assertEquals(registerPage.getErrorMessageEmailTextbox(), "Email is required.");
	  Assert.assertEquals(registerPage.getErrorMessagePasswordTextbox(), "Password is required.");
	  Assert.assertEquals(registerPage.getErrorMessageConfirmPasswordTextbox(), "Password is required."); 
  }
  
  @Test
  public void Register_02_Invalid_Email() {
	  homePage.clickToRegisterLink();
	  registerPage.inputToFirstNameTextbox(firstName);
	  registerPage.inputToLastNameTextbox(lastName);
	  registerPage.inputToEmailTextbox("abc@abc");
	  registerPage.inputToPasswordTextbox(password);
	  registerPage.inputToConfirmPasswordTextbox(password);
	  registerPage.clickRegisterButton();
	  Assert.assertEquals(registerPage.getExistingErrorMessage(), "Wrong email");
  }
  
  @Test
  public void Register_03_Success() {
	  homePage.clickToRegisterLink();
	  registerPage.inputToFirstNameTextbox(firstName);
	  registerPage.inputToLastNameTextbox(lastName);
	  registerPage.inputToEmailTextbox(emailAddress);
	  registerPage.inputToPasswordTextbox(password);
	  registerPage.inputToConfirmPasswordTextbox(password);
	  registerPage.clickRegisterButton();
	  Assert.assertEquals(registerPage.getSuccessRegisterMessage(), "Your registration completed");
	  registerPage.clickToLogoutLink();
  }
  
  @Test
  public void Register_04_Existing_Email() {
	  homePage.clickToRegisterLink();
	  registerPage.inputToFirstNameTextbox(firstName);
	  registerPage.inputToLastNameTextbox(lastName);
	  registerPage.inputToEmailTextbox(emailAddress);
	  registerPage.inputToPasswordTextbox(password);
	  registerPage.inputToConfirmPasswordTextbox(password);
	  registerPage.clickRegisterButton();
	  Assert.assertEquals(registerPage.getExistingErrorMessage(), "The specified email already exists");
  }
  
  @Test
  public void Register_05_Password_Less_Than_6_Chars() {
	  homePage.clickToRegisterLink();
	  registerPage.inputToFirstNameTextbox(firstName);
	  registerPage.inputToLastNameTextbox(lastName);
	  registerPage.inputToEmailTextbox(emailAddress);
	  registerPage.inputToPasswordTextbox("123");
	  registerPage.inputToConfirmPasswordTextbox(password);
	  registerPage.clickRegisterButton();
	  Assert.assertEquals(registerPage.getErrorMessagePasswordTextbox(), 
			  "Password must meet the following rules:\nmust have at least 6 characters");
  }
  
  @Test
  public void Register_06_Wrong_Confirm_Password() {
	  homePage.clickToRegisterLink();
	  registerPage.inputToFirstNameTextbox(firstName);
	  registerPage.inputToLastNameTextbox(lastName);
	  registerPage.inputToEmailTextbox(emailAddress);
	  registerPage.inputToPasswordTextbox(password);
	  registerPage.inputToConfirmPasswordTextbox("1234555");
	  registerPage.clickRegisterButton();
	  Assert.assertEquals(registerPage.getErrorMessageConfirmPasswordTextbox(), "The password and confirmation password do not match.");
  }
 
  @AfterClass
  public void afterClass() {
	  driver.quit();
  }
  
  public int getRandomNumber() {
	  Random random = new Random();
	  return  random.nextInt(999);
  }
}
