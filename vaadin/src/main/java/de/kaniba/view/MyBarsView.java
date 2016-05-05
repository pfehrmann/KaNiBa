package de.kaniba.view;

import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;

import de.kaniba.designs.MyBarsDesign;
import de.kaniba.presenter.MyBarsPresenterInterface;

/**
 * This view is used to display all the bars, one admin can administrate.
 * @author Philipp
 *
 */
public class MyBarsView extends MyBarsDesign implements SecuredView {
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "myBars";
	private MyBarsPresenterInterface presenter;
	
	/**
	 * Does nothing, setting the view up is done when entering is
	 */
	public MyBarsView() {
		super();
	}
	
	/**
	 * Set the presenter for this view.
	 * @param presenter
	 */
	public void setPresenter(MyBarsPresenterInterface presenter) {
		this.presenter = presenter;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		presenter.enter(event);
	}
	
	public void setResults(List<Component> components) {
		barResultContainer.removeAllComponents();
		
		for(Component component : components) {
			barResultContainer.addComponent(component);
		}
	}

	@Override
	public boolean checkRights(String parameters) {
		return presenter.checkRights(parameters);
	}

}
