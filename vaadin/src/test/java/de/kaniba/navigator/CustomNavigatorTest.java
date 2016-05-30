package de.kaniba.navigator;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

public class CustomNavigatorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Test
	public void testCustomNavigator() {
		UI ui = Mockito.mock(UI.class);
		
		ComponentContainer componentContainer = Mockito.mock(ComponentContainer.class);
		CustomNavigator navigator = new CustomNavigator(ui, componentContainer);
		
		assertNotNull("Initialization of the Navigator failed.", navigator);
	}

}
