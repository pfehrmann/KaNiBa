package de.kaniba.view;

import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Component;

import de.kaniba.designs.MyBarsDesign;
import de.kaniba.uiInterfaces.MyBarsPresenterInterface;
import de.kaniba.uiInterfaces.MyBarsViewInterface;

/**
 * This view is used to display all the bars, one admin can administrate.
 * @author Philipp
 *
 */
public class MyBarsView extends MyBarsDesign implements MyBarsViewInterface {
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "myBars";
	private MyBarsPresenterInterface presenter;
	
	/**
	 * Does nothing, setting the view up is done when entering is
	 */
	public MyBarsView() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see de.kaniba.view.MBarsViewInterface#setPresenter(de.kaniba.presenter.MyBarsPresenterInterface)
	 */
	@Override
	public void setPresenter(MyBarsPresenterInterface presenter) {
		this.presenter = presenter;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("My Bars");
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

	@Override
	public boolean checkRights(String parameters) {
		return presenter.checkRights(parameters);
	}

}
