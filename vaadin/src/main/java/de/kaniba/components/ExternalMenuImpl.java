package de.kaniba.components;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;

public class ExternalMenuImpl extends ExternalMenu {
	
	public ExternalMenuImpl() {
		loginButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Window loginWindow = new Window("Login");
				loginWindow.setContent(new LoginPopup());
				loginWindow.setWidth("450px");
				loginWindow.setResizable(false);
				UI.getCurrent().addWindow(loginWindow);
			}
		});
	}
}
