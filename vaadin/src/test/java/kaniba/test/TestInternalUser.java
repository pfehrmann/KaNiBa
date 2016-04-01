package kaniba.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.kaniba.model.Database;
import de.kaniba.model.Email;
import de.kaniba.model.InternalUser;

public class TestInternalUser {
	private InternalUser user;
	private InternalUser original;
	
	@Before
	public void setUp() throws Exception {
		original = Database.giveUser(5);
		user = Database.giveUser(5);
	}

	@After
	public void tearDown() throws Exception {
		original.saveUser();
	}

	@Test
	public void testGetEmail() {
		Email mail = new Email("test@live.de");
		user.setEmail(mail);
		
		Email db = user.getEmail();
		
		boolean succsess = db.getMail().equals(mail.getMail());
		if(!succsess) {
			fail("Mails stimmen nicht überein.");
		}
	}

	@Test
	public void testGetName() {
		String name = "testUser";
		user.setName(name);
		
		String db = user.getName();
		
		boolean succsess = db.equals(name);
		if(!succsess) {
			fail("Namen stimmen nicht überein.");
		}
	}

}
