package de.kaniba.model;

import java.util.List;

/**
 * This Class represents a bar.
 * 
 * @author philipp
 *
 */
public class Bar {
	public final static int UNKNOWNBARID = -1;

	private int barID;
	private Admin barOwner;
	private Pinboard pinboard;
	private Address address;
	private List<Special> currentSpecials;
	private List<Special> allSpecials;
	private int sumGeneralRating;
	private int sumPprRating;
	private int sumMusicRating;
	private int sumPeopleRating;
	private int sumAtmosphereRating;
	private int countRating;

	public Bar() {
		barID = UNKNOWNBARID;
	}

	public Bar(int barID) {
		Bar t = Database.readBar(barID);

		this.barID = barID;
		this.barOwner = t.getBarOwner();
		this.pinboard = t.getPinboard();
		this.address = t.getAddress();
		this.sumAtmosphereRating = t.getSumAtmosphereRating();
		this.sumGeneralRating = t.getSumGeneralRating();
		this.sumMusicRating = t.getSumMusicRating();
		this.sumPeopleRating = t.getSumPeopleRating();
		this.sumPprRating = t.getSumPprRating();
		this.countRating = t.getCountRating();
	}
	
	public boolean saveBar() {
		return Database.saveBar(this);
	}

	public int getBarID() {
		return barID;
	}

	public double getGeneralRating() {
		return (double) sumGeneralRating / countRating;
	}

	public double getPprRating() {
		return (double) sumPprRating / countRating;
	}

	public double getMusicRating() {
		return (double) sumMusicRating / countRating;
	}

	public double getPeopleRating() {
		return (double) sumPeopleRating / countRating;
	}

	public double getAtmosphereRating() {
		return (double) sumAtmosphereRating / countRating;
	}

	public Admin getBarOwner() {
		return barOwner;
	}

	public void setBarOwner(Admin barOwner) {
		this.barOwner = barOwner;
	}

	public Pinboard getPinboard() {
		return pinboard;
	}

	public void setPinboard(Pinboard pinboard) {
		this.pinboard = pinboard;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getSumGeneralRating() {
		return sumGeneralRating;
	}

	public void setSumGeneralRating(int sumGeneralRating) {
		this.sumGeneralRating = sumGeneralRating;
	}

	public int getSumPprRating() {
		return sumPprRating;
	}

	public void setSumPprRating(int sumPprRating) {
		this.sumPprRating = sumPprRating;
	}

	public int getSumMusicRating() {
		return sumMusicRating;
	}

	public void setSumMusicRating(int sumMusicRating) {
		this.sumMusicRating = sumMusicRating;
	}

	public int getSumPeopleRating() {
		return sumPeopleRating;
	}

	public void setSumPeopleRating(int sumPeopleRating) {
		this.sumPeopleRating = sumPeopleRating;
	}

	public int getSumAtmosphereRating() {
		return sumAtmosphereRating;
	}

	public void setSumAtmosphereRating(int sumAtmosphereRating) {
		this.sumAtmosphereRating = sumAtmosphereRating;
	}

	public int getCountRating() {
		return countRating;
	}

	public void setCountRating(int countRating) {
		this.countRating = countRating;
	}

	// Es macht unter Umständen Sinn, nur die Specials anzuzeigen, die aktuell
	// laufen, z.B. beim anzeigen der specials für den normalen User.
	public List<Special> getCurrentSpecials() {
		if (currentSpecials != null) {
			return currentSpecials;
		} else {
			currentSpecials = Database.readCurrentSpecials(barID);
			return currentSpecials;
		}
	}

	// Zeigt alle Specials an, hilfreich für Admins
	public List<Special> getAllSpecials() {
		if (allSpecials != null) {
			return allSpecials;
		} else {allSpecials = Database.readCurrentSpecials(barID);
		return allSpecials;
		}
	}

	// Ein Special hinzufügen.
	/* TODO: Das Special nur auf die aktuelle Liste setzen, wenn es da wirklich
	 *  hingehört, also auf das Datum prüfen
	 */
	public void addSpecial(Special special) {
		currentSpecials.add(special);
		allSpecials.add(special);
		throw new RuntimeException("implement me");
	}

	/**
	 * Muss vielleicht von Zeit zu Zeit ausgeführt werden, falls es Probleme mit
	 * der kontinuierlichen Zählung der Ratings gibt
	 */
	public void updateRating() {
		throw new RuntimeException("implement me");
	}

}
