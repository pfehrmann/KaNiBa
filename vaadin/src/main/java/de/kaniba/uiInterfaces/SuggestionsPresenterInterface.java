package de.kaniba.uiInterfaces;

import java.io.Serializable;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

public interface SuggestionsPresenterInterface extends Serializable {

	void enter(ViewChangeEvent event);

}
