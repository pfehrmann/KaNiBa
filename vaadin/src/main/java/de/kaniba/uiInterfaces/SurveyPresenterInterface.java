package de.kaniba.uiInterfaces;

import java.io.Serializable;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

/**
 * Interface for the View when communicating with the presenter.
 * @author Philipp
 *
 */
public interface SurveyPresenterInterface extends SecuredPresenter, Serializable {

	/**
	 * Called when the page is accessed
	 * @param event
	 */
	void enter(ViewChangeEvent event);

	/**
	 * Called when the submit button is clicked.
	 */
	void submitForm();

}