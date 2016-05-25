package de.kaniba.components;

import com.vaadin.server.Page;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.kaniba.model.User;
import de.kaniba.utils.NavigationUtils;
import de.kaniba.view.SearchView;
import de.kaniba.view.SuggestionsView;
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
				NavigationUtils.navigateTo(SearchView.NAME);
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
				NavigationUtils.navigateTo(UpdateInformationView.NAME);
			}
		});
		
		aboutButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Page.getCurrent().open("https://kanibablog.wordpress.com/", "KaNiBa - Blog");
			}
		});
		
		suggestionsButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				NavigationUtils.navigateTo(SuggestionsView.NAME);
			}
		});
	}
}
