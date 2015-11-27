package de.kaniba.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import de.kaniba.view.LoginView.LoginViewListener;

public interface LoginView extends View{
	public TextField getUsernameText();
	public PasswordField getPasswordText();
	public void setFormWidth(String width);
	public String getFormWidth();
	
	interface LoginViewListener {
		void click(ClickEvent event);
	}

	public void addListener(LoginViewListener listener);
}
