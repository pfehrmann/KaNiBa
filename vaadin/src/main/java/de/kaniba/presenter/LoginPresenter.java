package de.kaniba.presenter;

import com.vaadin.navigator.View;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import de.kaniba.model.Admin;
import de.kaniba.model.MyUI;
import de.kaniba.model.User;
import de.kaniba.view.LoginView;

public class LoginPresenter implements LoginView.LoginViewListener {
	private User model;
	private LoginView view;

	public LoginPresenter(User model, LoginView view) {
		this.model = model;
		this.view = view;

		view.addListener(this);
	}

	public View getView() {
		return view;
	}

	/**
	 * Einloggen
	 * 
	 * @param event
	 */
	@Override
	public void click(ClickEvent event) {
		// Session holen
		VaadinSession session = ((MyUI) UI.getCurrent()).getSession();

		// Passwort und Benutzernamen auslesen
		String username = view.getUsernameText().getValue();
		String password = view.getPasswordText().getValue();

		// Versuchen einzuloggen
		try {
			model = model.login(username, password);
		} catch (Exception e) {
			e.printStackTrace();
			model = null;
		}
		// Prüfen, ob das einloggen geklappt hat und ob der User admin ist
		boolean loggedIn = false;
		boolean admin = false;
		if (model != null) {
			loggedIn = true;
			if (model.getClass().equals(Admin.class)) {
				admin = true;
			}
		}

		// Im Fehlerfall eine Warnung ausgeben
		if (!loggedIn) {
			Notification
					.show("Fehler beim Anmelden. Benutzername oder Passwort sind falsch.",
							Notification.Type.WARNING_MESSAGE);
			view.getUsernameText().setComponentError(
					new UserError("Falscher Benutzername"));
			view.getPasswordText().setComponentError(
					new UserError("Falsches Passwort"));
		} else {
			view.getPasswordText().setComponentError(null);
		}

		// Werte in die Session schreiben
		session.setAttribute("user", model);
		session.setAttribute("admin", admin);
		session.setAttribute("loggedIn", loggedIn);
	}

}
