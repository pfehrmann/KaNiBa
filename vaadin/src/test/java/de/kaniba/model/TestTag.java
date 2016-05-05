package de.kaniba.model;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import kaniba.test.Utils;

public class TestTag {

	@BeforeClass
	public static void prepareForTest() {
		Utils.prepareDatabaseForTests();
	}
	
	@Test
	public void testSaveTag() throws SQLException {
		Tag tag = new Tag();
		tag.setBarID(42);
		tag.setUserID(1);
		tag.setName("testTag");
		tag.saveTag();
		List<Tag> tags = Database.getTagsForBar(42);
		assertEquals("Wrong amount of tags read", 1, tags.size());
	}

}
