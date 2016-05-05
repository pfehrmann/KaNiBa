package de.kaniba.presenter;

import java.io.Serializable;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import de.kaniba.model.Rating;

public interface BarPresenterInterface extends Serializable {

	void enter(ViewChangeEvent event);

	void saveRating(Rating rating);

	void sendMessage(String message);

	void clickedSurvey();

	void updateTagList();

}