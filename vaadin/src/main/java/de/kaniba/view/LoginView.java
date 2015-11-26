package de.kaniba.view;

import de.kaniba.view.LoginView.LoginViewListener;

public interface LoginView {
	public void setDisplay(double value);

	interface LoginViewListener {
		void buttonClick(char operation);
	}

	public void addListener(LoginViewListener listener);
	public void login(String username, String password);
}
