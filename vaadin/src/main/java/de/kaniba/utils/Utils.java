package de.kaniba.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

import de.kaniba.model.Bar;
import de.kaniba.model.InternalUser;

public class Utils {
	private final static Logger logger = Logger.getLogger("KaNiBa");
	
	private Utils() {
		// May not be instanciated
	}
	
	public static void log(String msg) {
		logger.info(msg);
	}
	
	public static void exception(Exception e) {
		logger.log(Level.WARNING, e.getMessage(), e);
	}
	
	public static String getOneLineAddress(Bar bar) {
		String address = "";

		address += bar.getAddress().getStreet();
		address += " " + bar.getAddress().getNumber();
		address += ", " + bar.getAddress().getZip();
		address += " " + bar.getAddress().getCity();

		return address;
	}
	
	public static String basepath() {
		return VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	}
	
	public static VaadinSession getSession() {
		return UI.getCurrent().getSession();
	}
	
	public static InternalUser getUser() {
		VaadinSession session = getSession();
		return (InternalUser) session.getAttribute("user");
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
	
	private static String prepareAddress(Bar bar) {
		String address = "";

		if(bar == null) {
			return null;
		}
		
		address += bar.getAddress().getStreet();
		address += " " + bar.getAddress().getNumber();
		address += "," + bar.getAddress().getZip();
		address += " " + bar.getAddress().getCity();

		try {
			address = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			exception(e);
			return null;
		}

		return address;
	}

	public static LatLon getLatLon(Bar bar) {
		String jsonString;
		String url = "http://maps.google.com/maps/api/geocode/json?address=" + prepareAddress(bar) + "&sensor=false";
		try {
			jsonString = downloadURL(url);
		} catch (MalformedURLException e) {
			jsonString = "";
			exception(e);
		}
		
		try {
			JSONArray results = new JSONObject(jsonString).getJSONArray("results");
			JSONObject result = (JSONObject) results.get(0);

			double lat = result.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
			double lon = result.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
			return new LatLon(lat, lon);
		} catch (JSONException e) {
			exception(e);
			log(url);
			log(jsonString);
			return null;
		}
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

	public static Bar getBarFromParams(String params) {
		Bar bar = null;
	
		if (params != null) {
	
			int id = -1;
			try {
				id = Integer.parseInt(params);
			} catch (NumberFormatException e) {
				// don't do anything.
				// An invalid ID was supplied
			}
	
			if (id != -1) {
				try {
					bar = new Bar(id);
				} catch (SQLException e) {
					exception(e);
				}
			}
		}
	
		return bar;
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
