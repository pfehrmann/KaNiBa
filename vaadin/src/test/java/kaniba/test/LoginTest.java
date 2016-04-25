/**
 * 
 */
package kaniba.test;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.vaadin.ui.Window;

import de.kaniba.components.LoginPopupImpl;
import de.kaniba.presenter.Utils;

/**
 * @author phili
 *
 */
public class LoginTest {
	private LoginPopupImpl loginPopup;
	private FirefoxDriver driver;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		loginPopup = new LoginPopupImpl(new Window());
	}
	
	@After
	public void tearDown() throws Exception {
		driver.close();
	}

	/**
	 * Test method for
	 * {@link de.kaniba.components.LoginPopupImpl#LoginPopupImpl(com.vaadin.ui.Window)}
	 * .
	 */
	@Ignore
	@Test
	public void testLoginPopupImpl() {
		driver.get("localhost:8080");
		driver.findElement(By.id("login-button")).click();
		
		driver.findElement(By.id("usernameTextfield")).clear();
		driver.findElement(By.id("usernameTextfield")).sendKeys("philipp");

		driver.findElement(By.id("passwordField")).clear();
		driver.findElement(By.id("passwordField")).sendKeys("test");
		
		driver.findElement(By.id("loginPopup-submit")).click();

		driver.findElement(By.id("logout-button"));
	}

}
