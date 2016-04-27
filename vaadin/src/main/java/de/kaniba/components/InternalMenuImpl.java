package de.kaniba.components;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;

import de.kaniba.model.User;
import de.kaniba.navigator.NavigatorUI;
import de.kaniba.utils.Utils;
import de.kaniba.view.SearchView;
import de.kaniba.view.UpdateInformationView;

public class InternalMenuImpl extends InternalMenu {
	public InternalMenuImpl () {
		searchButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(SearchView.NAME);
			}
		});
		
		logoutButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				VaadinSession session = Utils.getSession();
				session.setAttribute("loggedIn", false);
				session.setAttribute("model", new User());
				session.setAttribute("admin", false);
				((NavigatorUI) UI.getCurrent()).setMenu(new ExternalMenuImpl());
				Notification.show("Erfolgreich ausgeloggt.");
			}
		});
		
		profilButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(UpdateInformationView.NAME);
			}
		});
	}
}
