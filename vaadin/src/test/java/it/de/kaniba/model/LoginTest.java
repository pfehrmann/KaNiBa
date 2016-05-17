/**
 * 
 */
package it.de.kaniba.model;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import de.kaniba.utils.ScreenShotRule;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import kaniba.test.Utils;

/**
 * @author Philipp
 *
 */
public class LoginTest {
	private WebDriver driver;
	
	@BeforeClass
	public static void initialize() {
		PhantomJsDriverManager.getInstance().setup();
		Utils.prepareDatabaseForTests();
	}
	
	@Before
	public void setUp() throws Exception {
		driver = new PhantomJSDriver();
		driver.manage().window().setSize(new Dimension(1024, 768));
		screenShootRule.setDriver(driver);
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Utils.prepareDatabaseForTests();
	}
	
	@Rule public ScreenShotRule screenShootRule = new ScreenShotRule(driver);
	
	/**
	 * Test method for
	 * {@link de.kaniba.components.LoginPopupImpl#LoginPopupImpl(com.vaadin.ui.Window)}
	 * .
	 */
	@Test
	public void testLoginPopupImpl() {
		driver.get("http://localhost:9764/");
		
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		
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
