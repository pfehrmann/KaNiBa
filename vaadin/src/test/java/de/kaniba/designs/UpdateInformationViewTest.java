package de.kaniba.designs;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import de.kaniba.model.Address;
import de.kaniba.model.Email;
import de.kaniba.model.InternalUser;
import de.kaniba.view.UpdateInformationView;

public class UpdateInformationViewTest {
	private UpdateInformationView view;
	
	@Before
	public void setUp() {
		view = new UpdateInformationView();
	}
	
	@Test
	public void testUpdateInformationView() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOldPasswordField() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPasswordField() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRepeatPasswordField() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSubmit() {
		assertNotEquals("No submit button was created", null, view.getSubmit());
	}

	@Test
	public void testEnter() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSetUser() {
		InternalUser user = new InternalUser();
		user.setAddress(new Address("testCity", "testStreet", "testNumber", "testZip"));
		user.setBirthdate(new Date(1942, 5, 17));
		user.setEmail(new Email("test@host.de"));
		user.setFirstname("Klaus");
		user.setName("Otto");
		user.setPassword("testPass");
		user.setUserID(5);
		
		view.setUser(user);
		
		InternalUser read = view.getUser();

		// Address check
		assertEquals("Address not equal", user.getAddress().getCity(), read.getAddress().getCity());
		assertEquals("Address not equal", user.getAddress().getStreet(), read.getAddress().getStreet());
		assertEquals("Address not equal", user.getAddress().getNumber(), read.getAddress().getNumber());
		assertEquals("Address not equal", user.getAddress().getZip(), read.getAddress().getZip());
		
		assertEquals("User not equal", user.getBirthdate(), read.getBirthdate());
		assertEquals("User not equal", user.getEmail().getMail(), read.getEmail().getMail());
		assertEquals("User not equal", user.getFirstname(), read.getFirstname());
		assertEquals("User not equal", user.getName(), read.getName());
		assertEquals("User not equal", user.getPassword(), read.getPassword());
	}

	@Test
	public void testAddPresenter() {
		fail("Not yet implemented");
	}

}
