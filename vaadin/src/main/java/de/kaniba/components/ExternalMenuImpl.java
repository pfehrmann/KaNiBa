package de.kaniba.components;

import com.vaadin.server.Page;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.kaniba.navigator.NavigatorUI;
import de.kaniba.utils.NavigationUtils;
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
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				// Hide the menu
				((NavigatorUI) UI.getCurrent()).setMenuVisibility(false);
				
				Window loginWindow = new Window("Login");
				LoginPopupImpl popup = new LoginPopupImpl(loginWindow); 
				loginWindow.setContent(popup);
				loginWindow.setWidth("450px");
				loginWindow.setResizable(false);
				loginWindow.setModal(true);
				loginWindow.setDraggable(false);
				UI.getCurrent().getNavigator().addViewChangeListener(popup);
				UI.getCurrent().addWindow(loginWindow);
			}
		});
		searchButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				NavigationUtils.navigateTo(SearchView.NAME);
			}
		});
		
		aboutButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Page.getCurrent().open("https://kanibablog.wordpress.com/", "KaNiBa - Blog");
			}
		});
	}
}
