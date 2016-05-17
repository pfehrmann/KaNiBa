package de.kaniba.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import de.kaniba.components.BarAdminMenu;
import de.kaniba.components.ExternalMenuImpl;
import de.kaniba.components.InternalMenuImpl;
import de.kaniba.model.User;
import de.kaniba.navigator.NavigatorUI;

/**
 * Utility class for various purposes.
 * 
 * @author Philipp
 *
 */
public final class Utils {

	private Utils() {
		// May not be instanciated
	}

	/**
	 * Copys a list. Note that the elements are not duplicated, so all the
	 * elements are still equal (listA.get(1) == listB.get(1)), but the lists
	 * are not equal (listA != listB).
	 * 
	 * @param list
	 *            The list to copy.
	 * @return Returns a copy of the list.
	 */
	public static <T> List<T> copyList(List<T> list) {
		List<T> copy = new ArrayList<>();

		if (list == null) {
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
				sb.append(br.readLine() + "\n");
			}
		} catch (IOException e) {
			LoggingUtils.exception(e);
		}
		return sb.toString();
	}

	/**
	 * Set the menu
	 */
	public static void updateMenu() {
		((NavigatorUI) UI.getCurrent()).setMenu(getMenu());
	}

	private static Component getMenu() {
		if (User.isAdmin()) {
			return new BarAdminMenu();
		}
		if (User.isLoggedIn()) {
			return new InternalMenuImpl();
		}

		return new ExternalMenuImpl();
	}
}
