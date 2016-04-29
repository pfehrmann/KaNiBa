package de.kaniba.utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.tapio.googlemaps.client.LatLon;

import de.kaniba.model.Bar;
import de.kaniba.model.Database;

/**
 * Util Class that uses bars.
 * @author Philipp
 *
 */
public class BarUtils {
	
	private BarUtils() {
		// May not be instanciated.
	}

	/**
	 * Tries to find a bar from a parameter. The paramater is expected to be one single number.
	 * @param params The param string.
	 * @return Returns a bar or null if none was found.
	 */
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
					bar = Database.readBar(id);
				} catch (SQLException e) {
					// don't do anything.
					// An invalid ID was supplied
				}
			}
		}
		return bar;
	}

	static String prepareAddress(Bar bar) {
		if(bar == null) {
			return null;
		}
		
		String address = "";
		address += bar.getAddress().getStreet();
		address += " " + bar.getAddress().getNumber();
		address += "," + bar.getAddress().getZip();
		address += " " + bar.getAddress().getCity();
	
		try {
			address = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Utils.exception(e);
			return null;
		}
	
		return address;
	}

	/**
	 * Returns the coordinates of a bar.
	 * @param bar The bar to search for
	 * @return Returns the coordinates.
	 */
	public static LatLon getLatLon(Bar bar) {
		String jsonString;
		String url = "http://maps.google.com/maps/api/geocode/json?address=" + prepareAddress(bar) + "&sensor=false";
		try {
			jsonString = Utils.downloadURL(url);
		} catch (MalformedURLException e) {
			jsonString = "";
			Utils.exception(e);
		}
		
		try {
			JSONArray results = new JSONObject(jsonString).getJSONArray("results");
			JSONObject result = (JSONObject) results.get(0);
	
			double lat = result.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
			double lon = result.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
			return new LatLon(lat, lon);
		} catch (JSONException e) {
			Utils.exception(e);
			Utils.log(url);
			Utils.log(jsonString);
			return null;
		}
	}

	/**
	 * Returns a one line representation of a bar.
	 * @param bar The bar to format
	 * @return Returns the formatted address.
	 */
	public static String getOneLineAddress(Bar bar) {
		String address = "";
	
		address += bar.getAddress().getStreet();
		address += " " + bar.getAddress().getNumber();
		address += ", " + bar.getAddress().getZip();
		address += " " + bar.getAddress().getCity();
	
		return address;
	}

}
