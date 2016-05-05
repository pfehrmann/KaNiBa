package de.kaniba.view;

import java.io.Serializable;

/**
 * The interface for the Register View to Presenter communication
 * @author Philipp
 *
 */
public interface RegisterInterface extends Serializable{

	/**
	 * This is calles, when the Register button is clicked.
	 * @param event
	 */
	void registerClick();

}