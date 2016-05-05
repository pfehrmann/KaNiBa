package de.kaniba.uiInterfaces;

import java.util.List;

import com.vaadin.navigator.View;

import de.kaniba.components.SearchElement;
import de.kaniba.model.Bar;

public interface SearchViewInterface extends View {

	/**
	 * Sets the markers for the bars on the map
	 * @param bars
	 */
	void displayBarsOnMap(List<Bar> bars);

	void setSearchResults(List<SearchElement> elements);

	/**
	 * Add an presenter to the list of presenters. It will be called on various events.
	 * @param presenter
	 */
	void setPresenter(SearchPresenterInterface presenter);

}