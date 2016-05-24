package de.kaniba.view;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.kaniba.designs.MyBarsDesign;
import de.kaniba.model.Admin;
import de.kaniba.model.Bar;
import de.kaniba.uiInterfaces.MyBarsPresenterInterface;
import de.kaniba.uiInterfaces.MyBarsViewInterface;
import de.kaniba.utils.LoggingUtils;
import de.kaniba.utils.NavigationUtils;

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
		addNewBarButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				createNewBar();
			}
		});
	}
	
	private void createNewBar() {
		Bar bar = Bar.getDefaultBar();
		bar.setBarOwner((Admin) Admin.getUser());
		
		int barID = -1;
		try {
			barID = bar.saveBar();
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}
		
		NavigationUtils.navigateTo(EditBarView.NAME + "/" + barID);
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
