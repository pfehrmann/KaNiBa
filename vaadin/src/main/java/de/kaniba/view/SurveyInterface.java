package de.kaniba.view;

import java.io.Serializable;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

public interface SurveyInterface extends Serializable {

	void enter(ViewChangeEvent event);

	void submitForm();

}