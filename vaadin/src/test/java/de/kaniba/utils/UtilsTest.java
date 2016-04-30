package de.kaniba.utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;


public class UtilsTest {

	@Test
	public void testCopyList() {
		List<String> original = Arrays.asList("Hallo", "Bla", "Blub");
		List<String> copy = Utils.copyList(original);
		
		assertFalse("Lists are equal", original == copy);
		assertEquals("Lists differ in size", original.size(), copy.size());
		for(int i = 0; i < original.size(); i++) {
			assertEquals("List elements are not equal", original.get(i), copy.get(i));
		}
	}

	@Test
	@Ignore
	public void testDownloadURL() {
		fail("Not yet implemented");
	}
}
