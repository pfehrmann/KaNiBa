package de.kaniba.presenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.server.VaadinService;
import com.vaadin.tapio.googlemaps.client.LatLon;

import de.kaniba.model.Bar;

public class Utils {
	public static String basepath() {
		return VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	}
	
	private static String prepareAddress(Bar bar) {
		String address = "";

		address += bar.getAddress().getStreet();
		address += " " + bar.getAddress().getNumber();
		address += "," + bar.getAddress().getZip();
		address += " " + bar.getAddress().getCity();

		try {
			address = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		
		try {
			JSONArray results = new JSONObject(jsonString).getJSONArray("results");
			JSONObject result = (JSONObject) results.get(0);

			double lat = result.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
			double lon = result.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
			return new LatLon(lat, lon);
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println(url);
			System.out.println(jsonString);
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
			e.printStackTrace();
		}
		return sb.toString();
	}
}
