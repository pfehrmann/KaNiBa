package de.kaniba.components;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.kaniba.view.SearchView;

/**
 * The menu for external visitors
 * @author Philipp
 *
 */
public class ExternalMenuImpl extends ExternalMenu {
	private static final long serialVersionUID = 1L;

	/**
	 * Sets up the menu
	 */
	public ExternalMenuImpl() {
		loginButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Window loginWindow = new Window("Login");
				loginWindow.setContent(new LoginPopupImpl(loginWindow));
				loginWindow.setWidth("450px");
				loginWindow.setResizable(false);
				loginWindow.setModal(true);
				loginWindow.setDraggable(false);
				UI.getCurrent().addWindow(loginWindow);
			}
		});
		searchButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(SearchView.NAME);
			}
		});
	}
}
