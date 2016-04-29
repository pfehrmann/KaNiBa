package de.kaniba.components;

import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

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
				Utils.logout();
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
