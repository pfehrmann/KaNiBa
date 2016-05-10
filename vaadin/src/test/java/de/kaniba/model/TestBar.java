package de.kaniba.model;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import kaniba.test.Utils;

public class TestBar {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Utils.prepareDatabaseForTests();
	}

	@Test
	public void testBar() {
		Bar bar = new Bar();
		assertEquals("Wrong bar id", Bar.UNKNOWNBARID, bar.getBarID());
	}

	@Test
	public void testLoadSaveBar() throws SQLException {
		Bar original = Database.readBar(1);
		Bar bar = Database.readBar(1);
		bar.setName("TestBar99");
		bar.saveBar();
		
		Bar afterSave = Database.readBar(1);
		assertEquals("Barnames not equal", "TestBar99", afterSave.getName());

		original.saveBar();
	}

	@Test
	public void testGetBarAttributes() throws SQLException {
		Bar bar = Database.readBar(1);
	}

	@Test
	public void testGetBarFromParams() {
		Bar bar = Bar.getBarFromParams("1");
		assertEquals("Wrong bar read from database", 1, bar.getBarID());
	}
	
	@Test
	public void testGetBarFromParamsNull() {
		Bar bar = Bar.getBarFromParams(null);
		assertNull("Wrong bar read from database", bar);
	}
	
	@Test
	public void testGetBarFromParamsNegative() {
		Bar bar = Bar.getBarFromParams("-1");
		assertNull("Wrong bar read from database", bar);
	}
	
	@Test
	public void testGetTags() throws SQLException {
		Bar bar = Database.readBar(1);
		List<Tag> tags = bar.getTags();
		assertEquals("Wrong amount of tags read", 3, tags.size());
	}

}
