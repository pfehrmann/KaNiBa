package de.kaniba.presenter;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

/**
 * Interface for the View when communicating with the presenter.
 * @author Philipp
 *
 */
public interface SurveyPresenterInterface extends SecuredPresenter {

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