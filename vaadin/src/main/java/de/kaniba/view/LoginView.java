package de.kaniba.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public interface LoginView extends View{
	public TextField getUsernameText();
	public PasswordField getPasswordText();
	public Button getSubmitButton();
	public void setFormWidth(String width);
	public String getFormWidth();
	
	interface LoginViewListener {
		void loginButtonClick(ClickEvent event);
	}

	public void addListener(LoginViewListener listener);
}
