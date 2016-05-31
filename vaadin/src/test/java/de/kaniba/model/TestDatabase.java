package de.kaniba.model;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import kaniba.test.Utils;

public class TestDatabase {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Utils.prepareDatabaseForTests();
	}

	@Test
	public void testVerbindung() throws SQLException {
		Connection con = Database.verbindung();
		assertNotNull("No connection created", con);
	}

	@Test
	public void testReadBar() throws SQLException {
		Bar bar = Database.readBar(1);
		assertEquals("Descriptions differ", bar.getDescription(), "Eine hübsche kleine Bar im Herzen Karlsruhes.");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testReadBarNegative() throws SQLException {
		Database.readBar(-1);
	}
	
	@Test
	public void testReadBarTooHigh() throws SQLException {
		Bar bar = Database.readBar(100000);
		assertNull("Invalid bar read", bar);
	}
	
	@Test
	public void testReadAllSpecials() throws SQLException {

		assertEquals(1, Database.readAllSpecials(1).size());
	}
	
	@Test
	public void testGivePinboard() throws SQLException {
		Pinboard pinboard = Database.givePinboard(1);
		assertEquals(12, pinboard.getMessages().size());
	}
	
	@Test
	public void testGetSuggestions() throws SQLException {
		List<Bar> actualResult = Database.getSuggestions(11);
		
		assertEquals("Wrong amount of bars returned", 2, actualResult.size());
		
		boolean bar1Found = false;
		boolean bar2Found = false;
		
		// Check for bar with ID 1
		for(Bar bar : actualResult) {
			if(bar.getBarID() == 1) {
				bar1Found = true;
			}
		}
		
		// Check for bar with ID 2
		for(Bar bar : actualResult) {
			if(bar.getBarID() == 2) {
				bar2Found = true;
			}
		}

		assertTrue("Bar with ID 1 not found", bar1Found);
		assertTrue("Bar with ID 2 not found", bar2Found);
	}
}
