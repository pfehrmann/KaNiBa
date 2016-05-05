package de.kaniba.model;

import java.sql.SQLException;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import de.kaniba.components.ExternalMenuImpl;
import de.kaniba.navigator.NavigatorUI;
import de.kaniba.utils.Utils;

/**
 * This class represents a user.
 * @author Philipp
 *
 */
public class User {
	
	/**
	 * Try to log a user in.
	 * @param username The username
	 * @param password The password
	 * @return Retuns a user, or null.
	 * @throws SQLException
	 */
	public User login(String username, String password) throws SQLException {
		return Database.logUserIn(username, password);
	}

	/**
	 * Logs the user out.
	 */
	public static void logout() {
		VaadinSession session = Utils.getSession();
		session.setAttribute("loggedIn", false);
		session.setAttribute("model", null);
		session.setAttribute("admin", null);
		((NavigatorUI) UI.getCurrent()).setMenu(new ExternalMenuImpl());
		Notification.show("Erfolgreich ausgeloggt.");
	}

	/**
	 * Has to be static, as it does not uses the information from each user.
	 * @return
	 */
	public static boolean isAdmin() {
		VaadinSession session = Utils.getSession();
		Object adminObj = session.getAttribute("admin");
		boolean admin = false;
		if (adminObj != null) {
			admin = true;
		}
	
		return admin && isLoggedIn();
	}

	/**
	 * Checks if a user is logged in.
	 * @return
	 */
	public static boolean isLoggedIn() {
		VaadinSession session = Utils.getSession();
		Object loggedInObj = session.getAttribute("loggedIn");
		boolean loggedIn = false;
		if (loggedInObj != null) {
			loggedIn = (boolean) loggedInObj;
		}
	
		return loggedIn;
	}
}
