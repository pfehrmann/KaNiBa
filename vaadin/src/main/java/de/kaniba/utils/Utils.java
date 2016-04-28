package de.kaniba.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

public final class Utils {
	private final static Logger logger = Logger.getLogger("KaNiBa");
	
	private Utils() {
		// May not be instanciated
	}
	
	public static <T> List<T> copyList(List<T> list) {
		List<T> copy = new ArrayList<>();
		
		for(T element : list) {
			copy.add(element);
		}
		
		return copy;
	}
	
	public static void log(String msg) {
		logger.info(msg);
	}
	
	public static void exception(Exception e) {
		logger.log(Level.WARNING, e.getMessage(), e);
	}
	
	public static String basepath() {
		return VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
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

	public static void navigateTo(String navigationState) {
		UI.getCurrent().getNavigator().navigateTo(navigationState);
	}
	
	public static void showNotification(String message) {
		showNotification(message, Type.HUMANIZED_MESSAGE);
	}
	
	public static void showNotification(String message, Type type) {
		showNotification(message, type, 2000);
	}

	public static void showNotification(String message, Type type, int durationMs) {
		Notification note = new Notification(message, type);
		note.setDelayMsec(durationMs);
		note.setPosition(Position.MIDDLE_CENTER);
		note.show(Page.getCurrent());
	}
	
	public static void navigateBack() {
		Page.getCurrent().getJavaScript().execute("window.history.back()");
	}

	public static void navigateBack(String message) {
		navigateBack();
		showNotification(message);
	}

	public static void navigateBack(String message, Type type) {
		navigateBack();
		showNotification(message, type);
	}
}
