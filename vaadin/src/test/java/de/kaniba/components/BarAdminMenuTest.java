package de.kaniba.components;

import static org.junit.Assert.*;

import org.junit.Test;

public class BarAdminMenuTest {

	@Test
	public void testBarAdminMenuImpl() {
		BarAdminMenu menu = new BarAdminMenuImpl();
		assertNotNull("Error initializing menu", menu);
	}

}
