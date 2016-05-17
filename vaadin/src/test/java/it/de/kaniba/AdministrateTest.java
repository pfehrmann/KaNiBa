package it.de.kaniba;

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

public class AdministrateTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

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
		baseUrl = "http://localhost:9764/";
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Utils.prepareDatabaseForTests();
	}

	@Rule
	public ScreenShotRule screenShootRule = new ScreenShotRule(driver);

	@Test
	public void testAdministrate() throws Exception {
		driver.get(baseUrl + "/");

		driver.findElement(By.id("login-button")).click();

		driver.findElement(By.id("usernameTextfield")).clear();
		driver.findElement(By.id("usernameTextfield")).sendKeys("philipp");

		driver.findElement(By.id("passwordField")).clear();
		driver.findElement(By.id("passwordField")).sendKeys("test");

		driver.findElement(By.id("loginPopup-submit")).click();

		driver.findElement(By.id("administrate-button")).click();
		driver.findElement(By.cssSelector("#name-label")).click();

		driver.findElement(By.id("bar-name-field")).clear();
		driver.findElement(By.id("bar-name-field")).sendKeys("Die Fritte2");

		driver.findElement(By.id("submit-button")).click();

		assertEquals("Die Fritte2", driver.findElement(By.id("bar-name-label")).getText());

		// Revert
		driver.findElement(By.id("administrate-button")).click();
		driver.findElement(By.cssSelector("#name-label")).click();
		driver.findElement(By.id("bar-name-field")).clear();
		driver.findElement(By.id("bar-name-field")).sendKeys("Die Fritte");
		driver.findElement(By.id("submit-button")).click();

		driver.findElement(By.id("logout-button")).click();
	}

	@After
	public void tearDown() throws Exception {
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
