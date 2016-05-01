package de.kaniba.view;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

public interface EditBarInterface {

	/**
	 * Save a bar from the view
	 */
	void saveBar();

	/**
	 * To be called on enter
	 * @param event The event containing the parmeters
	 * @return Returns true, if everything is fine.
	 */
	boolean enter(ViewChangeEvent event);

}