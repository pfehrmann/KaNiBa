package com.example.designertest;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.kaniba.components.LoginPopup;

import com.vaadin.ui.Notification;

public class ExternalMenuImpl extends ExternalMenu {
	
	public ExternalMenuImpl() {
		loginButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("test");
				Window loginWindow = new Window("Login");
				loginWindow.setContent(new LoginPopup());
				loginWindow.setWidth("450px");
				//loginWindow.setHeight("600px");
				UI.getCurrent().addWindow(loginWindow);
			}
		});
	}
}
