package de.kaniba.view;

import java.util.ArrayList;

import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import de.kaniba.model.Database;
import de.kaniba.model.Rating;

public class BarViewImpl extends CustomComponent implements BarView {

	public java.util.List<BarViewListener> listenerList = new ArrayList<BarViewListener>();

	public int generalRating;
	public int pprRating;
	public int musicRating;
	public int atmoRating;
	public int peopleRating = 0;
	public RatingStars ratinggeneral = new RatingStars();
	public RatingStars ratingatmo = new RatingStars();
	public RatingStars ratingmusic = new RatingStars();
	public RatingStars ratingppr = new RatingStars();

	public BarViewImpl() {

		Panel mainPanel = createMainPanel();

		Panel ratingStars = createRatingStars();
		
		GridLayout mainLayout = new GridLayout(2, 2);
		mainLayout.addComponent(ratingStars, 1, 1);
		mainPanel.setContent(mainLayout);

		setCompositionRoot(mainPanel);
	}

	private Panel createMainPanel() {
		Panel mainPanel = new Panel();

		mainPanel.setWidth("900px");
		mainPanel.setHeight("500px");
		return mainPanel;
	}

	private Panel createRatingStars() {
		// Diese Methode legt die Anzeigeelemente für die Sterne an
		Panel ratingpanel = new Panel();
		Button ratingButton = new Button("Speichern");
		ratingpanel.setWidth("300px");
		ratingpanel.setHeight("250px");
		ratingpanel.addStyleName("ratingpanel");
		GridLayout starLayout = new GridLayout(1, 6);
		starLayout.setSizeFull();

		ratinggeneral.setMaxValue(5);
		ratingatmo.setMaxValue(5);
		ratingmusic.setMaxValue(5);
		ratingppr.setMaxValue(5);

		ratinggeneral.setCaption("Gesamtbewertung Bar: ");
		ratingatmo.setCaption("Bewertung Atmosphäre: ");
		ratingmusic.setCaption("Bewertung der Musik: ");
		ratingppr.setCaption("Bewertung Preis-/Leistung: ");

		ratinggeneral.setImmediate(true);
		ratingatmo.setImmediate(true);
		ratingmusic.setImmediate(true);
		ratingppr.setImmediate(true);

		starLayout.addComponent(ratingButton, 0, 5);
		starLayout.addComponent(ratinggeneral, 0, 0);
		starLayout.addComponent(ratingatmo, 0, 1);
		starLayout.addComponent(ratingmusic, 0, 2);
		starLayout.addComponent(ratingppr, 0, 3);

		ratingpanel.setContent(starLayout);

		ratingButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				// TODO Auto-generated method stub
				generalRating = ratinggeneral.getValue().intValue();
				pprRating = ratingppr.getValue().intValue();
				atmoRating = ratingatmo.getValue().intValue();
				musicRating = ratingmusic.getValue().intValue();
				/* Platzhalter Integer */
				int barID = 0;
				int ratingID = 0;
				int userID = 0;

				Rating rating = new Rating(ratingID, userID, barID, generalRating, pprRating, musicRating, peopleRating,
						atmoRating, null);
				for (BarViewListener listener : listenerList) {
					listener.ratingButtonClick(rating);
				}

			}
		});

		return ratingpanel;
	}

	@Override
	public void addRatingButtonClickListener(BarViewListener listener) {
		// TODO Auto-generated method stub
		listenerList.add(listener);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
