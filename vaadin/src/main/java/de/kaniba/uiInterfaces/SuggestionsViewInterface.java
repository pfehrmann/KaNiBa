package de.kaniba.uiInterfaces;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;

public interface SuggestionsViewInterface extends View{

	/**
	 * Set the presenter for this view.
	 * @param presenter
	 */
	void setPresenter(SuggestionsPresenterInterface presenter);

	void setResults(List<Component> components);

}