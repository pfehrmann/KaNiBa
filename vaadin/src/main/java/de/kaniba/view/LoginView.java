package de.kaniba.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import de.kaniba.view.LoginView.LoginViewListener;

public interface LoginView {
	public TextField getUsernameText();
	public PasswordField getPasswordText();

	interface LoginViewListener {
		void click(ClickEvent event);
	}

	public void addListener(LoginViewListener listener);
}
