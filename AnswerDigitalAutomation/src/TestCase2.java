import static org.junit.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestCase2 extends BaseAutomationTest {
	String infinScrollURL = "http://the-internet.herokuapp.com/infinite_scroll";
	
	
	@Before
	public void innit() {
		//driver.manage().window().maximize();
		driver.get(infinScrollURL);
		assertEquals(driver.getCurrentUrl(), infinScrollURL);
		//Allows operations to perform in time gaps
		driver.manage().timeouts().implicitlyWait(20000, TimeUnit.MILLISECONDS);
	}
	
	/*
	 * Test Case 2: Scroll to the bottom of the page twice
	 * and scroll back to the top of the page
	 * and assert "Infinite Scroll" text.
	 */
	@Test
	public void verifyInfiniteScroll() {
	    // Scroll Down using Actions class
		Actions actions = new Actions(driver);
	    // Wait for html element to appear 
		WebDriverWait wait = new WebDriverWait(driver, 5);
		
		int infiniteScrollNum = 3;
	    int scrollTwice = 2;
	    
	    //loops twice to repeat scroll down action 
	    for(int i = 0; i < scrollTwice; i++) {
	    	//infinit_scroll href number increments by 1 after every scroll
	    	wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
		   	   		("div#content [ href='/infinite_scroll/" + infiniteScrollNum + "']")));
	    	//increment by 1 for next loop iteration 
			infiniteScrollNum = infiniteScrollNum + 1;
		    actions.sendKeys(Keys.PAGE_DOWN).build().perform();
	    	} 
	    //scrollTwice and infiniteScrollNum becomes 5 
	    infiniteScrollNum = 5;
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
	   	   		("div#content [ href='/infinite_scroll/" 
	   	   				+ infiniteScrollNum +
	   	   				"']")));	
	    //Wait for scroll to complete
	    
	    WebElement infiniteScrollHeader = driver.findElement(By.tagName("h3"));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].scrollIntoView(true);", infiniteScrollHeader);
	    //actions.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).perform();
	    //Assert "infinite scroll" text with h3 text element
	    assertEquals("Infinite Scroll", driver.findElement(By.tagName("h3")).getText());
	}
}
