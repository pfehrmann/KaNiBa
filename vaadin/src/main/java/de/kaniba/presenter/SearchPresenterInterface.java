package de.kaniba.presenter;

import java.io.Serializable;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

public interface SearchPresenterInterface extends Serializable {

	/**
	 * Searches the database
	 * @param searchValue The string to search for
	 */
	void updateSearchView(String searchValue);

	/**
	 * Forwards the enter event.
	 * @param event
	 */
	void enter(ViewChangeEvent event);

}