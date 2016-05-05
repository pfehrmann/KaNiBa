package de.kaniba.utils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification.Type;

import de.kaniba.uiInterfaces.SecuredView;

import com.vaadin.ui.UI;

/**
 * Utils for navigation.
 * 
 * @author Philipp
 *
 */
public final class NavigationUtils {
	public static final ViewChangeListener viewChangeListener = new ViewChangeListener() {
		private static final long serialVersionUID = 1L;

		@Override
		public boolean beforeViewChange(ViewChangeEvent event) {
			return checkView(event.getNewView(), event.getParameters());
		}

		@Override
		public void afterViewChange(ViewChangeEvent event) {
			// Nothing to do, only interested in the before event
		}

	};

	private NavigationUtils() {
		// May not be instantiated
	}

	private static boolean checkView(View view, String parameters) {
		if (view instanceof SecuredView) {
			return checkSecuredView((SecuredView) view, parameters);
		}
		return true;
	}

	private static boolean checkSecuredView(SecuredView view, String parameters) {
		return view.checkRights(parameters);
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
		NotificationUtils.showNotification(message);
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
		NotificationUtils.showNotification(message, type);
	}

	/**
	 * Navigates to a state displaying a message
	 * 
	 * @param state
	 * @param message
	 */
	public static void navigateTo(String state, String message) {
		navigateTo(state);
		NotificationUtils.showNotification(message);
	}

}
