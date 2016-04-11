package de.kaniba.view;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import de.kaniba.model.Rating;

public interface BarViewListener {
	void ratingButtonClick(Rating rating);

	void sendMessage(String message);
	
	void enter(ViewChangeEvent event);
}