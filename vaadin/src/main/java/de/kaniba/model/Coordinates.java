package de.kaniba.model;

/**
 * Coordinates for the Map class
 * 
 * @author Philipp
 *
 */
public class Coordinates {
	private double lat;
	private double lon;

	/**
	 * Initialize the object with the coordinates
	 * 
	 * @param lat
	 * @param lon
	 */
	public Coordinates(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}
}