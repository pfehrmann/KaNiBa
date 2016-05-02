package kaniba.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.model.Email;
import de.kaniba.model.InternalUser;
import de.kaniba.model.Rating;

public class TestInternalUser {
	private InternalUser user;
	private InternalUser original;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Utils.prepareDatabaseForTests();
	}
	
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
	public void testUserID() {
		assertEquals(5, user.getUserID());
	}
	
	@Test
	public void testRateBar() throws SQLException {
		Bar bar = null;
		bar = Database.readBar(1);
		
		user.rateBar(bar, new Rating(-1, user.getUserID(), bar.getBarID(), 3, 3, 3, 3, 3, null));
		assertEquals(true, user.ratedBar(bar));
		
		Rating rating = user.getRating(bar.getBarID());
		assertEquals(3, rating.getAtmosphereRating());
		assertEquals(3, rating.getGeneralRating());
		assertEquals(3, rating.getMusicRating());
		assertEquals(3, rating.getPprRating());
		assertEquals(3, rating.getPeopleRating());
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
