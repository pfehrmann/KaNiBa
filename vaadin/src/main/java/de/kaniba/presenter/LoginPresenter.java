package de.kaniba.presenter;

import com.vaadin.navigator.View;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import de.kaniba.model.Admin;
import de.kaniba.model.InternalUser;
import de.kaniba.navigator.NavigatorUI;
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
	 * @param event Das Event, das beim Klicken ausgelöst wurde
	 */
	@Override
	public void loginButtonClick(ClickEvent event) {
		// Session holen
		VaadinSession session = ((NavigatorUI) UI.getCurrent()).getSession();

		// Wenn der User bereits eingeloggt ist, kann er sich nicht erneut
		// einloggen, sondern ausloggen
		Object loggedInObj = session.getAttribute("loggedIn");
		
		if (loggedInObj != null && loggedInObj.getClass() == Boolean.class && (boolean) loggedInObj) {
			session.setAttribute("loggedIn", false);
			session.setAttribute("model", new User());
			view.getSubmitButton().setCaption("Einloggen");
			return;
		}

		// Passwort und Benutzernamen auslesen
		String username = view.getUsernameText().getValue();
		String password = view.getPasswordText().getValue();

		//Passwort Löschen
		view.getPasswordText().setValue("");
		
		// Versuchen einzuloggen
		boolean loggedIn = false;
		InternalUser user = null;
		
		try {
			InternalUser temp = model.login(username, password);
			if(temp != null) {
				model = temp;
				user = temp;
				loggedIn = true;
			} else {
				loggedIn = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			loggedIn = false;
		}
		
		// Prüfen, ob das einloggen geklappt hat und ob der User admin ist
		boolean admin = false;
		if (loggedIn) {
			view.getSubmitButton().setCaption("Ausloggen");
			if (model.getClass().equals(Admin.class)) {
				admin = true;
			}
			//Bestätigung für den User anzeigen
			Notification.show("Erfolgreich eingeloggt.");
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
			view.getUsernameText().setComponentError(null);
		}

		// Werte in die Session schreiben
		session.setAttribute("user", user);
		session.setAttribute("admin", admin);
		session.setAttribute("loggedIn", loggedIn);
	}

}
