package de.kaniba.components;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.kaniba.model.Address;
import de.kaniba.model.Bar;

public class BarSearchResultTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testBarSearchResult() {
		Bar bar = new Bar();
		bar.setAddress(new Address("city", "street", "number", "zip"));
		bar.setName("Stövchen");
		bar.setBarID(10);
		
		BarSearchResult result = new BarSearchResult(bar);
		assertEquals("result-10", result.getId());
	}

}
