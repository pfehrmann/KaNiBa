package de.kaniba.utils;

import java.util.LinkedList;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import de.kaniba.uiInterfaces.SecuredView;

import com.vaadin.ui.UI;

/**
 * Utils for navigation.
 * 
 * @author Philipp
 *
 */
public final class NavigationUtils {
	/**
	 * Variable for keeping track of the navigation history, in case that moving back is neccesary.
	 */
	private static LinkedList<String> viewHistory = new LinkedList<>();
	
	public static final ViewChangeListener viewChangeListener = new ViewChangeListener() {
		private static final long serialVersionUID = 1L;

		@Override
		public boolean beforeViewChange(ViewChangeEvent event) {
			return checkView(event.getNewView(), event.getParameters());
		}

		@Override
		public void afterViewChange(ViewChangeEvent event) {
			viewHistory.add(event.getViewName() + "/" + event.getParameters());
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
