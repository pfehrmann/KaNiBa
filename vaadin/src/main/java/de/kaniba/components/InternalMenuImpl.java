package de.kaniba.components;

import com.vaadin.server.Page;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.kaniba.model.User;
import de.kaniba.view.SearchView;
import de.kaniba.view.UpdateInformationView;

/**
 * The menu for logged in people
 * @author Philipp
 *
 */
public class InternalMenuImpl extends InternalMenu {
	private static final long serialVersionUID = 1L;

	/**
	 * Sets up the internal menu
	 */
	public InternalMenuImpl () {
		searchButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(SearchView.NAME);
			}
		});
		
		logoutButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				User.logout();
			}
		});
		
		profilButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(UpdateInformationView.NAME);
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
