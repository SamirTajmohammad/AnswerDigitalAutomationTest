import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.openqa.selenium.By;
//import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestCase1 extends BaseAutomationTest{
	
	String loginURL = "http://the-internet.herokuapp.com/login";
	
	@Before
	public void innit() {
		driver.get(loginURL);
		assertEquals(driver.getCurrentUrl(), loginURL);
		//driver.manage().window().setSize(new Dimension(1600, 1000));
		//Allows operations to perform in within below time gaps
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
	}
	/**
	 * Scenario 1:
	 * Try to login with correct username and wrong password
     * and assert login validation.
     * 
	 */
	@Test
	public void verifyPassword() {
		//set wrong password here
		String wrongPassword = "123";
    	setLoginDetails(getUsername(), wrongPassword);
		//wait for user account login details
		WebDriverWait wait = new WebDriverWait(driver, 5);	
    	wait.until(ExpectedConditions.textToBePresentInElementValue(By.id("username"), USERNAME));
    	//testing if actual password is not the same as wrongPassword
    	wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementValue(By.id("password"), PASSWORD)));
    	//wait until login button and click it
		wait.until(ExpectedConditions.elementToBeClickable(By.className("radius"))).click();
    	//Test if user was not sent to any page other than login
    	assertEquals(loginURL, driver.getCurrentUrl());
        //Test if notification works
        WebElement passwrdFlashMsg = driver.findElement(By.id("flash"));
        // get text of flash notification id and check if it contains below
        assertTrue(passwrdFlashMsg.getText().contains("Your password is invalid!")); 
        
        //take a screenshot of end result here
	}
	/**
	 * Scenario 2:
	 * Try to login with incorrect username and correct password
     * and assert login validation.
     * 
	 */
	@Test
	public void verifyUsername() {
		String incorrectUsername = "bob";
    	setLoginDetails(incorrectUsername, getPassword());
    	WebDriverWait wait = new WebDriverWait(driver, 5);	
    	//Testing if actual username is not the same as what was entered e.g. tomsmith != bob
    	wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementValue(By.id("username"), USERNAME)));
    	wait.until(ExpectedConditions.textToBePresentInElementValue(By.id("password"), PASSWORD));
    	//wait until login button clickable and then click
		wait.until(ExpectedConditions.elementToBeClickable(By.className("radius"))).click();
    	//Test if user remains in login page
    	assertEquals(loginURL, driver.getCurrentUrl());
        //Test if notification works
        WebElement usernameFlashMsg = driver.findElement(By.id("flash"));
        // get text of flash notification id and check if it contains below
        assertTrue(usernameFlashMsg.getText().contains("Your username is invalid!")); 
        
        //take a screenshot of end result here

	}
	/**
	 * Scenario 3:
	 * Try to login with correct username and password
     * and then logout.
     * 
	 */
	@Test
	public void verifyLogout() {
		//login with correct username and password
    	setLoginDetails(getUsername(), getPassword());
    	
    	WebDriverWait wait = new WebDriverWait(driver, 5);	
    	//Wait for text input to be exact match of username and password
    	wait.until(ExpectedConditions.textToBePresentInElementValue(By.id("username"), USERNAME));
    	wait.until(ExpectedConditions.textToBePresentInElementValue(By.id("password"), PASSWORD));
		wait.until(ExpectedConditions.elementToBeClickable(By.className("radius"))).click();
    	
		//test to see if notification works
    	WebElement loggedInMsg = driver.findElement(By.id("flash"));
        assertTrue(loggedInMsg.getText().contains("You logged into a secure area!")); 
    	//test to see if page loaded correct url
    	assertEquals("http://the-internet.herokuapp.com/secure", driver.getCurrentUrl()); 
    	
    	//take a screenshot of user logged in
    	//code here...
    	
    	//Wait until logout is clickable and then click
    	(new WebDriverWait(driver,5)).until(ExpectedConditions.elementToBeClickable(By.cssSelector
   	   		("div#content [href='/logout']"))).click();
    	//Test to see if we are back in login page after clicking log out
    	assertEquals(loginURL, driver.getCurrentUrl());
        //Test to see if notification works
    	WebElement loggedOutMsg = driver.findElement(By.id("flash"));
    	assertTrue(loggedOutMsg.getText().contains("You logged out of the secure area!"));  
    	
    	//take another screenshot of user logged out
    	//code here...
    	
	}
}
