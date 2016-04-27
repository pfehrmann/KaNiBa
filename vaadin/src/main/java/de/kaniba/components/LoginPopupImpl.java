package de.kaniba.components;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.kaniba.model.Admin;
import de.kaniba.model.InternalUser;
import de.kaniba.model.User;
import de.kaniba.navigator.NavigatorUI;
import de.kaniba.utils.Utils;

public class LoginPopupImpl extends LoginPopup {

	public LoginPopupImpl(final Window window) {
		loginButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				loginButtonClick();
				window.close();
			}
		});
		
		registerLink.setResource(new ExternalResource("#!register"));
	}

	private void loginButtonClick() {
		// Session holen
		VaadinSession session = Utils.getSession();

		// Wenn der User bereits eingeloggt ist, kann er sich nicht erneut
		// einloggen
		boolean loggedIn = Utils.isLoggedIn();
		if(loggedIn) {
			return;
		}

		// Passwort und Benutzernamen auslesen
		String username = usernameTextfield.getValue();
		String password = passwordField.getValue();

		// Passwort Löschen
		passwordField.setValue("");

		// Versuchen einzuloggen
		InternalUser user = null;

		User model = new User();
		try {
			InternalUser temp = model.login(username, password);
			if (temp != null) {
				model = temp;
				user = temp;
				loggedIn = true;
			} else {
				loggedIn = false;
			}
		} catch (Exception e) {
			Utils.exception(e);
			loggedIn = false;
		}

		// Prüfen, ob das einloggen geklappt hat und ob der User admin ist
		boolean admin = false;
		if (loggedIn) {
			((NavigatorUI) UI.getCurrent()).setMenu(new InternalMenuImpl());
			if (model.getClass().equals(Admin.class)) {
				admin = true;
			}
			// Bestätigung für den User anzeigen
			Notification.show("Erfolgreich eingeloggt.");
		}

		// Im Fehlerfall eine Warnung ausgeben
		if (!loggedIn) {
			Notification.show("Fehler beim Anmelden. Benutzername oder Passwort sind falsch.",
					Notification.Type.WARNING_MESSAGE);
			usernameTextfield.setComponentError(new UserError("Falscher Benutzername"));
			passwordField.setComponentError(new UserError("Falsches Passwort"));
		} else {
			passwordField.setComponentError(null);
			usernameTextfield.setComponentError(null);
		}

		// Werte in die Session schreiben
		session.setAttribute("user", user);
		session.setAttribute("admin", admin);
		session.setAttribute("loggedIn", loggedIn);
	}

	public void setLoginName(String username) {
		usernameTextfield.setValue(username);
		
	}
}
