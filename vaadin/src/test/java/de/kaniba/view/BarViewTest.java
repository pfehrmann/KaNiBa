package de.kaniba.view;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.kaniba.model.Bar;
import de.kaniba.presenter.BarPresenter;

public class BarViewTest {
	private BarView view;
	
	@Before
	public void tearDownAfterClass() throws Exception {
		view = new BarView();
	}

	@Test
	public void testBarView() {
		BarView view = new BarView();
		assertNotEquals("Initialization error", null, view);
	}

	@Test
	public void testSetPresenter() {
		BarPresenter presenter = new BarPresenter();
		assertNotEquals("No view in the presenter.", null, presenter.getView());
	}

	@Test
	public void testSetBarMessageBoardNull() {
		view.setBarMessageBoard(null);
	}

	@Test
	public void testSetBarRatingNull() {
		view.setBarRating(null);
	}

	@Test
	public void testSetBarLogoNull() {
		view.setBarLogo(null);
	}
	
	@Test
	public void testSetBarLogo() {
		Bar bar = new Bar();
		bar.setBarID(1);
		view.setBarLogo(bar);
	}
	
	@Test
	public void testSetBarLogoInvalidID() {
		Bar bar = new Bar();
		bar.setBarID(-5);
		view.setBarLogo(bar);
	}

	@Test
	public void testSetMapCoordsNull() {
		view.setMapCoords(null);
	}

}
