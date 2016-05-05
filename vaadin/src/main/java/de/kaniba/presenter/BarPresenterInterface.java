package de.kaniba.presenter;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import de.kaniba.model.Rating;

public interface BarPresenterInterface {

	void enter(ViewChangeEvent event);

	void saveRating(Rating rating);

	void sendMessage(String message);

	void clickedSurvey();

}