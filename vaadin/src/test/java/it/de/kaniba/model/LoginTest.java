/**
 * 
 */
package it.de.kaniba.model;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.jetty.jetty.Server;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.vaadin.ui.Window;

import de.kaniba.components.LoginPopupImpl;
import de.kaniba.utils.ScreenShotRule;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;

/**
 * @author phili
 *
 */
public class LoginTest {
	private LoginPopupImpl loginPopup;
	private PhantomJSDriver driver;
	
	@BeforeClass
	public static void initialize() {
		PhantomJsDriverManager.getInstance().setup();
	}
	
	@Before
	public void setUp() throws Exception {
		driver = new PhantomJSDriver();
		screenShootRule.setDriver(driver);
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		loginPopup = new LoginPopupImpl(new Window());
	}

	@Rule public ScreenShotRule screenShootRule = new ScreenShotRule(driver);
	
	/**
	 * Test method for
	 * {@link de.kaniba.components.LoginPopupImpl#LoginPopupImpl(com.vaadin.ui.Window)}
	 * .
	 */
	@Test
	public void testLoginPopupImpl() {
		driver.get("localhost:9764");
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		System.out.println("first test");
		System.out.println("Going to sleep");
		
		driver.findElement(By.id("login-button")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		driver.findElement(By.id("usernameTextfield")).clear();
		driver.findElement(By.id("usernameTextfield")).sendKeys("philipp");

		driver.findElement(By.id("passwordField")).clear();
		driver.findElement(By.id("passwordField")).sendKeys("test");
		
		driver.findElement(By.id("loginPopup-submit")).click();
		System.out.println("Everything found");
		assertNotNull("No logout button found, not logged in?", driver.findElement(By.id("logout-button")));
	}

}
