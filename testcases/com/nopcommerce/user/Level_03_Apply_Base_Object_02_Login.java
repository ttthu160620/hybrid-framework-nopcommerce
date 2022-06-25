package com.nopcommerce.user;

import org.testng.annotations.Test;

import pageObjects.HomePageObject;
import pageObjects.LoginPageObjects;
import pageObjects.RegisterPageObjects;

import org.testng.annotations.BeforeClass;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Level_03_Apply_Base_Object_02_Login {
  WebDriver driver;
  String projectPath = System.getProperty("user.dir");
  private HomePageObject homePage;
  private LoginPageObjects loginPage;
  private RegisterPageObjects registerPage;
  String firstName, lastName, password, invalidEmail, notFoundEmail, existingEmail;
  @BeforeClass
  public void beforeClass() {
	  System.setProperty("webdriver.chrome.driver", projectPath + "\\browerDrivers\\chromedriver.exe");
	  driver = new ChromeDriver();
	  driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  driver.get("https://demo.nopcommerce.com/");
	  
	  homePage = new HomePageObject(driver);
	  loginPage = new LoginPageObjects(driver);
	  firstName = "automation";
	  lastName = "fc";
	  existingEmail = "abc" + getRandomNumber() + "@gmail.com";
	  notFoundEmail = "abc" + getRandomNumber() + "@gmail.com";
	  invalidEmail = "123";
	  password = "123456";
	  registerPage = new RegisterPageObjects(driver);
	  System.out.println("Pre-condition_Register");
	  homePage.clickToRegisterLink();
	  registerPage.inputToFirstNameTextbox(firstName);
	  registerPage.inputToLastNameTextbox(lastName);
	  registerPage.inputToEmailTextbox(existingEmail);
	  registerPage.inputToPasswordTextbox(password);
	  registerPage.inputToConfirmPasswordTextbox(password);
	  registerPage.clickRegisterButton();
	  registerPage.clickToLogoutLink(); 
  }
  
  @Test
  public void Login_01_Empty_Data() {
	  homePage.clickToLoginLink();
	  loginPage.clickToLoginButton();
	  Assert.assertEquals(loginPage.getEmailErrorMessage(), "Please enter your email");
  }
  
  @Test
  public void Login_02_Invalid_Email() {
	  homePage.clickToLoginLink();
	  loginPage.inputToEmailTextbox(invalidEmail);
	  loginPage.clickToLoginButton();
	  Assert.assertEquals(loginPage.getEmailErrorMessage(), "Wrong email");
  }
  
  @Test
  public void Login_03_Not_Existing_Email() {
	  homePage.clickToLoginLink();
	  loginPage.inputToEmailTextbox(notFoundEmail);
	  loginPage.clickToLoginButton();
	  Assert.assertEquals(loginPage.getUnsuccessErrorMessage(), "Login was unsuccessful. Please correct the errors and try again.\n"
		  		+ "No customer account found");
  }
  
  @Test
  public void Login_04_Existing_Email_Empty_Password() {
	  homePage.clickToLoginLink();
	  loginPage.inputToEmailTextbox(existingEmail);
	  loginPage.clickToLoginButton();
	  Assert.assertEquals(loginPage.getUnsuccessErrorMessage(), "Login was unsuccessful. Please correct the errors and try again.\n"
		  		+ "The credentials provided are incorrect");
  }
  
  @Test
  public void Login_05_Existing_Email_Wrong_Password() {
	  homePage.clickToLoginLink();
	  loginPage.inputToEmailTextbox(existingEmail);
	  loginPage.inputToPasswordTextbox("123");
	  loginPage.clickToLoginButton();
	  Assert.assertEquals(loginPage.getUnsuccessErrorMessage(), "Login was unsuccessful. Please correct the errors and try again.\n"
	  		+ "The credentials provided are incorrect");
  }
  
  @Test
  public void Login_06_Login_Success() {
	  homePage.clickToLoginLink();
	  loginPage.inputToEmailTextbox(existingEmail);
	  loginPage.inputToPasswordTextbox(password);
	  loginPage.clickToLoginButton();
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
