import static org.junit.Assert.assertEquals;
import java.util.regex.*;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestCase3 extends BaseAutomationTest {
	
	String keyPresses = "http://the-internet.herokuapp.com/key_presses";

	@Before
	public void innit() {
		driver.get(keyPresses);
		assertEquals(driver.getCurrentUrl(), keyPresses);
		//driver.manage().window().maximize();
		//Allows operations to perform in time gaps
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
	}
	/*
	 * Test Case 3: Click on 4 keys and assert the text displayed 
	 * after every key press.
	 */
	@Test
	public void verifyKeyPressTxt() throws AWTException {
		//Here our robot will begin to enter keys 
		Robot robot = new Robot();
		for (int i = 0; i < 4; i++) {
			robot.delay(25);
			robot.keyPress(keyInput[i]);
			WebElement resultText = driver.findElement(By.id("result"));
			assertEquals(KeyEvent.getKeyText(keyInput[i]), resultText.getText().replaceAll("You entered: ", ""));
			robot.delay(25);
			robot.keyRelease(keyInput[i]);
			robot.delay(25);
		}
	}
}
