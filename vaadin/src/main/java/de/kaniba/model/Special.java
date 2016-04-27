package de.kaniba.model;

import java.sql.Timestamp;

public class Special {
	public static final int UNKNOWNSPECIALID = -1;
	private int specialID;
	private int barID;
	private int userID;
	private String message;
	private Timestamp begin;
	private Timestamp end;
	private Timestamp created;
	
	public Special() {
		this.specialID = UNKNOWNSPECIALID;
	}
	
	public Special(int specialID) throws Exception {
		//Das Special aus der Datenbank auslesen
		Special t = Database.readSpecial(specialID);
		
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
		return begin;
	}

	public void setBegin(Timestamp begin) {
		this.begin = begin;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public int getBarID() {
		return barID;
	}

	public void setBarID(int barID) {
		this.barID=barID;
		
	}
}
