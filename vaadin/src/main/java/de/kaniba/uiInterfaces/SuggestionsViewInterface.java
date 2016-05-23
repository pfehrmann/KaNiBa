package de.kaniba.uiInterfaces;

import java.util.List;

import com.vaadin.ui.Component;

public interface SuggestionsViewInterface extends SecuredView{

	/**
	 * Set the presenter for this view.
	 * @param presenter
	 */
	void setPresenter(SuggestionsPresenterInterface presenter);

	void setResults(List<Component> components);

}