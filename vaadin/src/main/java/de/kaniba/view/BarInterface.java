package de.kaniba.view;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import de.kaniba.model.Rating;

public interface BarInterface {

	void enter(ViewChangeEvent event);

	void saveRating(Rating rating);

	void sendMessage(String message);

	void clickedSurvey();

	void updateTagList();

}