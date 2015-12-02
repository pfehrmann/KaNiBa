package de.kaniba.cucumber;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefs {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();
	
	public StepDefs() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Given("^I am logged in as \"(.*?)\"$")
	public void i_am_logged_in_as(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^I am on the \"(.*?)\" site$")
	public void i_am_on_the_site(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I insert \"(.*?)\" in the field \"(.*?)\"$")
	public void i_insert_in_the_field(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I insert \"(.*?)\" in the field Description$")
	public void i_insert_in_the_field_Description(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I submit a correct image$")
	public void i_submit_a_correct_image() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I see the message \"(.*?)\"$")
	public void i_see_the_message(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
	
	@Given("^I am in \"(.*?)\" of the bar \"(.*?)\"$")
	public void i_am_in_of_the_bar(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^I have not rated it$")
	public void i_have_not_rated_it() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I click on (\\d+) stars$")
	public void i_click_on_stars(int arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I see the new rating of (\\d+) stars$")
	public void i_see_the_new_rating_of_stars(int arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^I have already rated it with (\\d+) stars$")
	public void i_have_already_rated_it_with_stars(int arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
	
	@Given("^I open the url \"(.*?)\"$")
	public void i_open_the_url(String arg1) throws Throwable {
	    driver.get(arg1);
	}

	@When("^I input my user name and password with \"(.*?)\" and \"(.*?)\"$")
	public void i_input_my_user_name_and_password_with_and(String arg1, String arg2) throws Throwable {
		driver.findElement(By.id("login-view-username-input")).clear();
		driver.findElement(By.id("login-view-username-input")).sendKeys(arg1);
		driver.findElement(By.id("login-view-password-input")).clear();
		driver.findElement(By.id("login-view-password-input")).sendKeys(arg2);
		driver.findElement(By.id("login-view-submit-button")).click();
	}

	@Then("^I should see an error$")
	public void i_should_see_an_error() throws Throwable {
		boolean error = isElementPresent(By.cssSelector("span.v-errorindicator"));
		if(!error) {
			throw new Exception("No error present");
		}
	}

	@Then("^I should not see an error$")
	public void i_should_not_see_an_error() throws Throwable {
		boolean error = isElementPresent(By.cssSelector("span.v-errorindicator"));
		if(error) {
			throw new Exception("Error present");
		}
	}
	
	public void tearDown() throws Exception {
		driver.quit();
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
	
}
