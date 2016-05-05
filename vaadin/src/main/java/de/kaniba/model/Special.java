package de.kaniba.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Class that represents a special of a bar.
 * @author Philipp
 *
 */
public class Special implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int UNKNOWNSPECIALID = -1;
	private int specialID;
	private int barID;
	private int userID;
	private String message;
	private Timestamp begin;
	private Timestamp end;
	private Timestamp created;
	
	/**
	 * Initialize as invalid special
	 */
	public Special() {
		this.specialID = UNKNOWNSPECIALID;
	}
	
	/**
	 * Read a special from the database
	 * @param specialID
	 * @throws Exception
	 */
	public Special(int specialID) throws SQLException {
		//Das Special aus der Datenbank auslesen
		Special t = Database.readSpecial(specialID);
		
		if(t == null) {
			throw new IllegalArgumentException("Invalid special ID");
		}
		
		//Die ausgelesenen Werte übernehmen
		this.specialID = specialID;
		this.barID = t.getBarID();
		this.userID = t.getUserID();
		this.message = t.getMessage();
		this.begin = t.begin;
		this.end = t.getEnd();
		this.created = t.getCreated();
	}

	public int getSpecialID() {
		return specialID;
	}

	public void setSpecialID(int specialID) {
		this.specialID = specialID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getBegin() {
		return new Timestamp(begin.getTime());
	}

	public void setBegin(Timestamp begin) {
		this.begin = new Timestamp(begin.getTime());
	}

	public Timestamp getEnd() {
		return new Timestamp(end.getTime());
	}

	public void setEnd(Timestamp end) {
		this.end = new Timestamp(end.getTime());
	}

	public Timestamp getCreated() {
		return new Timestamp(created.getTime());
	}

	public void setCreated(Timestamp created) {
		this.created = new Timestamp(created.getTime());
	}

	public int getBarID() {
		return barID;
	}

	public void setBarID(int barID) {
		this.barID=barID;
	}
}
