package de.kaniba.presenter;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;

import de.kaniba.model.User;
import de.kaniba.view.LoginView;

public class LoginPresenter implements LoginView.LoginViewListener{
	private User model;
	private LoginView view;
	
	public LoginPresenter(User model, LoginView view) {
		this.model = model;
		this.view = view;
	
		view.addListener(this);
	}
	
	public CustomComponent getView() {
		return (CustomComponent) view;
	}
	
	/**
	 * Einloggen
	 * @param event
	 */
	@Override
	public void click(ClickEvent event) {
		//model = model.login(view.getUsernameText().getValue(), view.getPasswordText().getValue());
		view.getUsernameText().setValue("Hallo");
	}
	
}
