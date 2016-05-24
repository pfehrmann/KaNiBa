package de.kaniba.utils;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class NotificationUtils {
	private static final int DEFAULT_NOTIFICATION_DELAY = 2000;

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
	 * Shows a notfication.
	 * 
	 * @param message
	 * @param type
	 */
	public static void showNotification(String message, Type type) {
		showNotification(message, type, DEFAULT_NOTIFICATION_DELAY);
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

}
