

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.List;

import org.junit.After;
import org.junit.Before;

//import java.beans.Statement;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.StandardCopyOption;
//import java.util.UUID;

import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.google.common.io.Files;

public abstract class BaseAutomationTest {
	
	public enum DriverType {
		CHROME,
		FIREFOX
		//ADD browsers here or headless here
	}
	
	protected static WebDriver driver;
	//Set chrome as default
	protected static DriverType driverType = DriverType.CHROME;
	protected final static String CHROME_PATH = "webdrivers/chromedriver.exe";
	protected final static String FIREFOX_PATH = "webdrivers/geckodriver.exe";
	protected static DesiredCapabilities capabilities = new DesiredCapabilities();
	//Test Case 1 variables
	protected final static String USERNAME = "tomsmith";
	protected final static String PASSWORD = "SuperSecretPassword!";
	
	@Before
	public  void preparedDriver() throws Exception {
		ChromeOptions chromeOptions;
		//Swtich between driver types -- add other types here
		switch (driverType) {
		case CHROME:
			System.setProperty("webdriver.chrome.driver", CHROME_PATH);
			chromeOptions = new ChromeOptions().merge(capabilities);
			driver = new ChromeDriver(chromeOptions);
			break;
		case FIREFOX:
			System.setProperty("webdriver.gecko.driver", FIREFOX_PATH);
			capabilities.setBrowserName(BrowserType.FIREFOX);
			driver = new FirefoxDriver(new FirefoxOptions(capabilities));
			break;
		}
	}
	
	@After
	public  void cleanup() {
		//driver.quit();
	}
	
	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
//	protected int getRandomNumber(int min, int max) {
//	    return (int) ((Math.random() * (max - min)) + min);
//	}
	
	
	//Test Case 3 - random key code for robot (HARDCODED)
//	protected int getRandomKeyCode() {
//		//see keycodes in link at - https://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
//		int preKeyCode = getRandomNumber(8, 222);
//		//some numbers to be removed i.e. no key is assigned for specific unicode value
//		if(!(isValidCode(preKeyCode))) {
//			//retry random number generator since key was invalid
//			return getRandomKeyCode();
//		} else {
//			return preKeyCode;
//		}
//	}
	
	int keyInput[] = {
		    KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_M, KeyEvent.VK_I, KeyEvent.VK_R, 
		    KeyEvent.VK_SPACE, KeyEvent.VK_T, KeyEvent.VK_A, KeyEvent.VK_J,
		    KeyEvent.VK_ENTER
		  };
	
	protected List<WebElement> emTag;
    WebElement loginBtn, logoutBtn;
	
    protected String getUsername() {
    	this.emTag = driver.findElements(By.tagName("em"));
    	return emTag.get(0).getText();
    }
    
	protected String getPassword() {
    	this.emTag = driver.findElements(By.tagName("em"));
    	return emTag.get(1).getText();
    }
    
    protected WebElement getUsernameID() {
    	return driver.findElement(By.id("username"));
    }
    protected WebElement getPasswordID() {
    	return driver.findElement(By.id("password"));
    }

    protected void setLoginDetails(String username, String userpass) {

        getUsernameID().sendKeys(username);
        getPasswordID().sendKeys(userpass);
    }
    
    protected void submitLoginBtn() {
    	  this.loginBtn = driver.findElement(By.className("radius"));
          loginBtn.submit();   
    }
    
    protected void clickLogoutBtn() {
  	this.logoutBtn = driver.findElement(By.cssSelector
   		("div#content [href='/logout']"));
        logoutBtn.click();
  }
}