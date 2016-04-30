package de.kaniba.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

import de.kaniba.components.ExternalMenuImpl;
import de.kaniba.model.User;
import de.kaniba.navigator.NavigatorUI;

/**
 * Utility class for various purposes.
 * @author Philipp
 *
 */
public final class Utils {
	private static final int LOG_FILES = 10;
	private static final int LOG_LINES = 200000;
	private static final int DEFAULT_NOTIFICATION_DELAY = 2000;
	private static final Logger LOGGER = Logger.getLogger("KaNiBa");

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS.%1$tL %4$-11s %5$s%6$s%n");
		SimpleFormatter formatter = new SimpleFormatter();
		try {
			String path = "kaniba_%u.log";
			FileHandler handler = new FileHandler(path,
					LOG_LINES, LOG_FILES, true);
			handler.setFormatter(formatter);
			LOGGER.addHandler(handler);
		} catch (SecurityException | IOException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	private Utils() {
		// May not be instanciated
	}

	/**
	 * Copys a list. Note that the elements are not duplicated, so all the
	 * elements are still equal (listA.get(1) == listB.get(1)), but the lists
	 * are not equal (listA != listB).
	 * 
	 * @param list The list to copy.
	 * @return Returns a copy of the list.
	 */
	public static <T> List<T> copyList(List<T> list) {
		List<T> copy = new ArrayList<>();

		if(list == null) {
			return copy;
		}
		
		for (T element : list) {
			copy.add(element);
		}

		return copy;
	}

	/**
	 * Loggs a message.
	 * 
	 * @param msg
	 */
	public static void log(String msg) {
		LOGGER.info(msg);
	}

	/**
	 * Logs an exception.
	 * 
	 * @param e
	 */
	public static void exception(Exception e) {
		LOGGER.log(Level.WARNING, e.getMessage(), e);
	}

	/**
	 * @return Returns the basepath of the vaadin app.
	 */
	public static String basepath() {
		return VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	}
	
	public static String getBarLogoBasePath() {
		return basepath() + "/WEB-INF/images/";
	}

	public static VaadinSession getSession() {
		return UI.getCurrent().getSession();
	}

	public static boolean isLoggedIn() {
		VaadinSession session = getSession();
		Object loggedInObj = session.getAttribute("loggedIn");
		boolean loggedIn = false;
		if (loggedInObj != null) {
			loggedIn = (boolean) loggedInObj;
		}

		return loggedIn;
	}
	
	public static boolean isAdmin() {
		VaadinSession session = getSession();
		Object adminObj = session.getAttribute("admin");
		boolean admin = false;
		if (adminObj != null) {
			admin = true;
		}

		return admin && isLoggedIn();
	}

	/**
	 * Doenloads a given website
	 * 
	 * @param url
	 *            The website to download
	 * @return Returns the website as string
	 * @throws MalformedURLException
	 *             This is thrown, if the supplied url was invalid.
	 */
	public static String downloadURL(String url) throws MalformedURLException {
		URL website = new URL(url);
		StringBuilder sb = new StringBuilder();
		try (InputStream in = website.openStream(); BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			while (br.ready()) {
				while (br.ready()) {
					sb.append(br.readLine() + "\n");
				}
			}
		} catch (IOException e) {
			exception(e);
		}
		return sb.toString();
	}

	/**
	 * Navigates to a given state.
	 * 
	 * @param navigationState
	 */
	public static void navigateTo(String navigationState) {
		UI.getCurrent().getNavigator().navigateTo(navigationState);
	}

	/**
	 * Shows a notification with the given message. The notification will be
	 * visible for 2000ms.
	 * 
	 * @param message
	 */
	public static void showNotification(String message) {
		showNotification(message, Type.HUMANIZED_MESSAGE);
	}

	/**
	 * Shows a notfication.
	 * 
	 * @param message
	 * @param type
	 */
	public static void showNotification(String message, Type type) {
		showNotification(message, type, DEFAULT_NOTIFICATION_DELAY);
	}

	/**
	 * Shows a notification
	 * 
	 * @param message
	 * @param type
	 * @param durationMs
	 */
	public static void showNotification(String message, Type type, int durationMs) {
		Notification note = new Notification(message, type);
		note.setDelayMsec(durationMs);
		note.setPosition(Position.MIDDLE_CENTER);
		note.show(Page.getCurrent());
	}

	/**
	 * Navigates to the previous page.
	 */
	public static void navigateBack() {
		Page.getCurrent().getJavaScript().execute("window.history.back()");
	}

	/**
	 * Navigates to the previous page and shows a message.
	 * 
	 * @param message
	 */
	public static void navigateBack(String message) {
		navigateBack();
		showNotification(message);
	}

	/**
	 * Navigates back and shows a message of a specific type. Useful for error
	 * messages.
	 * 
	 * @param message
	 * @param type
	 */
	public static void navigateBack(String message, Type type) {
		navigateBack();
		showNotification(message, type);
	}

	public static void logout() {
		VaadinSession session = getSession();
		session.setAttribute("loggedIn", false);
		session.setAttribute("model", new User());
		session.setAttribute("admin", null);
		((NavigatorUI) UI.getCurrent()).setMenu(new ExternalMenuImpl());
		Notification.show("Erfolgreich ausgeloggt.");
	}
}
