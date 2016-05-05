package de.kaniba.presenter;

import java.io.Serializable;

/**
 * The interface for the Register View to Presenter communication
 * @author Philipp
 *
 */
public interface RegisterPresenterInterface extends SecuredPresenter, Serializable{

	/**
	 * This is calles, when the Register button is clicked.
	 * @param event
	 */
	void registerClick();

}