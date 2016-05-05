package de.kaniba.view;

import java.io.Serializable;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import de.kaniba.model.Rating;

public interface BarInterface extends Serializable {

	void enter(ViewChangeEvent event);

	void saveRating(Rating rating);

	void sendMessage(String message);

	void clickedSurvey();

	void updateTagList();

}