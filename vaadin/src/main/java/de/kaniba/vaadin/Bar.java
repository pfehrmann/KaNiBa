package de.kaniba.vaadin;

import java.util.List;

/**
 * This Class represents a bar.
 * @author philipp
 *
 */
public class Bar {
	private Admin barOwner;
	private Pinboard pinboard;
	private Rating avgRating;
	private Address address;
	private List<Special> specials;
	
	public Admin getBarOwner() {
		return barOwner;
	}
	
	public void setBarOwner(Admin barOwner) {
		this.barOwner = barOwner;
		throw new RuntimeException("implement me");
	}
	
	public Pinboard getPinboard() {
		return pinboard;
	}
	
	public void setPinboard(Pinboard pinboard) {
		this.pinboard = pinboard;
		throw new RuntimeException("implement me");
	}
	
	public Rating getAvgRating() {
		return avgRating;
	}
	
	public void setAvgRating(Rating avgRating) {
		this.avgRating = avgRating;
		throw new RuntimeException("implement me");
	}

	public List<Special> getSpecials() {
		return specials;
	}
	
	public void setSpecials(List<Special> specials) {
		this.specials = specials;
		throw new RuntimeException("implement me");
	}
	
	public void updateRating() {
		throw new RuntimeException("implement me");
	}
	
	
}
