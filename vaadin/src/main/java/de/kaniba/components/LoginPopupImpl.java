package de.kaniba.components;

import java.sql.SQLException;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.kaniba.model.InternalUser;
import de.kaniba.model.User;

/**
 * Popup for the login.
 * 
 * @author Philipp
 *
 */
public class LoginPopupImpl extends LoginPopup implements ViewChangeListener {
	private static final long serialVersionUID = 1L;
	private Window window;

	/**
	 * Creates a loginpopup with a window, that is closed on login.
	 * 
	 * @param window
	 */
	public LoginPopupImpl(final Window window) {
		this.window = window;
		loginButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				loginButtonClick();
				window.close();
			}
		});

		registerLink.setResource(new ExternalResource("#!register"));
	}

	private void loginButtonClick() {
		// Passwort und Benutzernamen auslesen
		String username = usernameTextfield.getValue();
		String password = passwordField.getValue();

		// Passwort Löschen
		passwordField.setValue("");
		
		InternalUser user = User.login(username, password);
		
		// Im Fehlerfall eine Warnung ausgeben
		if (user == null) {
			Notification.show("Fehler beim Anmelden. Benutzername oder Passwort sind falsch.",
					Notification.Type.WARNING_MESSAGE);
			usernameTextfield.setComponentError(new UserError("Falscher Benutzername"));
			passwordField.setComponentError(new UserError("Falsches Passwort"));
		} else {
			passwordField.setComponentError(null);
			usernameTextfield.setComponentError(null);
		}
	}

	public void setLoginName(String username) {
		usernameTextfield.setValue(username);
	}

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		return true;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {
		window.close();
	}
}
