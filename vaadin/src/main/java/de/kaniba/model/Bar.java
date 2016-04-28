package de.kaniba.model;

import java.sql.SQLException;
import java.util.List;

import de.kaniba.utils.Utils;

/**
 * This Class represents a bar.
 * 
 * @author philipp
 *
 */
public class Bar {
	public static final int UNKNOWNBARID = -1;

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
	private String description;
	private String name;

	public Bar() {
		barID = UNKNOWNBARID;
	}

	public Bar(int barID) throws SQLException {
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
		this.name = t.getName();
		this.description = t.getDescription();
	}

	public boolean saveBar() throws SQLException {
		return Database.saveBar(this);
	}

	public int getBarID() {
		return barID;
	}
	
	public void setBarID(int barID){
		this.barID =barID;
	}
	
	public DisplayRating getDisplayRating() {
		double general = getGeneralRating();
		double price = getPprRating();
		double music = getMusicRating();
		double people = getPeopleRating();
		double atmosphere = getAtmosphereRating();
		
		return new DisplayRating(general, price, music, people, atmosphere);
	}

	public double getGeneralRating() {
		if (countRating == 0) {
			return 0;
		}
		return (double) sumGeneralRating / (double) countRating;
	}

	public double getPprRating() {
		if (countRating == 0) {
			return 0;
		}
		return (double) sumPprRating / (double) countRating;
	}

	public double getMusicRating() {
		if (countRating == 0) {
			return 0;
		}
		return (double) sumMusicRating / (double) countRating;
	}

	public double getPeopleRating() {
		if (countRating == 0) {
			return 0;
		}
		return (double) sumPeopleRating / (double) countRating;
	}

	public double getAtmosphereRating() {
		if (countRating == 0) {
			return 0;
		}
		return (double) sumAtmosphereRating / (double) countRating;
	}

	public Admin getBarOwner() {
		return barOwner;
	}

	public void setBarOwner(Admin barOwner) {
		this.barOwner = barOwner;
	}

	public Pinboard getPinboard() {
		if (pinboard == null) {
			try {
				pinboard=Database.givePinboard(barID);
			} catch (SQLException e) {
				Utils.exception(e);
			}
		}
		return pinboard;
	}
	
	public Pinboard forceGetPinboard() {
		try {
			pinboard=Database.givePinboard(barID);
		} catch (SQLException e) {
			Utils.exception(e);
		}
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
	public List<Special> getCurrentSpecials() throws SQLException {
		if (currentSpecials != null) {
			return Utils.copyList(currentSpecials);
		} else {
			currentSpecials = Database.readCurrentSpecials(barID);
			return Utils.copyList(currentSpecials);
		}
	}

	// Zeigt alle Specials an, hilfreich für Admins
	public List<Special> getAllSpecials() throws SQLException {
		if (allSpecials != null) {
			return Utils.copyList(allSpecials);
		} else {
			allSpecials = Database.readCurrentSpecials(barID);
			return Utils.copyList(allSpecials);
		}
	}

	// Ein Special hinzufügen.
	/*
	 * TODO: Das Special nur auf die aktuelle Liste setzen, wenn es da wirklich
	 * hingehört, also auf das Datum prüfen
	 */
	public void addSpecial(Special special) {
		currentSpecials.add(special);
		allSpecials.add(special);
	}

	/**
	 * Muss vielleicht von Zeit zu Zeit ausgeführt werden, falls es Probleme mit
	 * der kontinuierlichen Zählung der Ratings gibt
	 */
	public void updateRating() {
		try {
			Database.checkRatings(barID);
		} catch (SQLException e) {
			Utils.exception(e);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return name + ", " + description + ", Ratings: " + countRating;
	}
}
