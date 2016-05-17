package de.kaniba.view;

import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;

import de.kaniba.designs.MyBarsDesign;
import de.kaniba.uiInterfaces.SuggestionsPresenterInterface;
import de.kaniba.uiInterfaces.SuggestionsViewInterface;

/**
 * This view is used to display all the bars, one admin can administrate.
 * @author Philipp
 *
 */
public class SuggestionsView extends MyBarsDesign implements SuggestionsViewInterface {
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "suggestions";
	private SuggestionsPresenterInterface presenter;
	
	/**
	 * Does nothing, setting the view up is done when entering is
	 */
	public SuggestionsView() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see de.kaniba.view.MBarsViewInterface#setPresenter(de.kaniba.presenter.MyBarsPresenterInterface)
	 */
	@Override
	public void setPresenter(SuggestionsPresenterInterface presenter) {
		this.presenter = presenter;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		presenter.enter(event);
	}
	
	/* (non-Javadoc)
	 * @see de.kaniba.view.MBarsViewInterface#setResults(java.util.List)
	 */
	@Override
	public void setResults(List<Component> components) {
		barResultContainer.removeAllComponents();
		
		for(Component component : components) {
			barResultContainer.addComponent(component);
		}
	}

}
