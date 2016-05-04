package de.kaniba.model;

import java.sql.Date;

/**
 * Class to represent a tag.
 * @author Philipp
 *
 */
public class Tag {
	public static final int INVALID_TAG_ID = -1;
	private int tagID;
	private int userID;
	private int barID;
	private String name;
	private Date created;
	
	/**
	 * Create a tag and initialize it to an unknown id.
	 */
	public Tag() {
		this.tagID = INVALID_TAG_ID;
	}
	
	public int getBarID() {
		return barID;
	}

	public void setBarID(int barID) {
		this.barID = barID;
	}



	public void setTagID(int tagID) {
		this.tagID = tagID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getTagID() {
		return tagID;
	}

	public int getUserID() {
		return userID;
	}

	public String getName() {
		return name;
	}

	public Date getCreated() {
		return created;
	}
	
	public void saveTag() {
		Database.saveTag(this);
	}
}
