package de.kaniba.model;

import java.sql.SQLException;

import com.vaadin.server.VaadinSession;

import de.kaniba.utils.LoggingUtils;
import de.kaniba.utils.NavigationUtils;
import de.kaniba.utils.NotificationUtils;
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
	 * @return Returns an internalUser, or null.
	 * @throws SQLException
	 */
	public static InternalUser loginDatabase(String username, String password) {
		try {
			return Database.logUserIn(username, password);
		} catch (SQLException e) {
			LoggingUtils.exception(e);
			return null;
		}
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
	
		return admin && User.isLoggedIn();
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

	/**
	 * Logs the user out. Triggers listeners.
	 */
	public static void logout() {
		VaadinSession session = Utils.getSession();
		session.setAttribute("loggedIn", false);
		session.setAttribute("model", null);
		session.setAttribute("admin", null);
		Utils.updateMenu();
		NotificationUtils.showNotification("Erfolgreich ausgeloggt.");
		
		// Navigate to a save state
		NavigationUtils.navigateTo("");
	}

	/**
	 * Loggs a user in with a password and a username
	 * @param username The username
	 * @param password The password.
	 * @return Returns the user, or null if no user could be retrieved.
	 */
	public static InternalUser login(String username, String password) {
	
		// Wenn der User bereits eingeloggt ist, kann er sich nicht erneut
		// einloggen
		boolean loggedIn = isLoggedIn();
		if(loggedIn) {
			return null;
		}
	
		// Versuchen einzuloggen
		InternalUser user = loginDatabase(username, password);
		if(user != null) {
			loggedIn = true;
		}
		
		// Prüfen, ob das einloggen geklappt hat und ob der User admin ist
		Admin admin = null;
		if (loggedIn) {
			if (user.getClass().equals(Admin.class)) {
				admin = (Admin) user;
			}
			
			// Bestätigung für den User anzeigen
			NotificationUtils.showNotification("Erfolgreich eingeloggt.");
		}
	
		// Werte in die Session schreiben
		VaadinSession session = Utils.getSession();
		session.setAttribute("user", user);
		session.setAttribute("admin", admin);
		session.setAttribute("loggedIn", loggedIn);
		
		// Update the menu
		Utils.updateMenu();
		
		// Return values
		if(admin != null) {
			return admin;
		}
		
		return user;
	}
}
