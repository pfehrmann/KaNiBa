package de.kaniba.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

/**
 * Utility class for various purposes.
 * @author Philipp
 *
 */
public final class Utils {
	private static final int DEFAULT_NOTIFICATION_DELAY = 2000;
	
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
			LoggingUtils.exception(e);
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
}
