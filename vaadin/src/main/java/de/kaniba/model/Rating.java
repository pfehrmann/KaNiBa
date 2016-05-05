package de.kaniba.model;

import java.sql.SQLException;
import java.sql.Timestamp;

import de.kaniba.utils.LoggingUtils;

/**
 * Represents a rating of a bar by a user.
 * 
 * @author Philipp
 *
 */
public class Rating {
	public static final int UNSET = 0;

	private int ratingID;
	private int userID;
	private int barID;
	private int generalRating;
	private int pprRating;
	private int musicRating;
	private int peopleRating;
	private int atmosphereRating;
	private Timestamp timestamp;

	/**
	 * The rating with all the information.
	 * 
	 * @param ratingID
	 * @param userID
	 * @param barID
	 * @param generalRating
	 * @param pprRating
	 * @param musicRating
	 * @param peopleRating
	 * @param atmosphereRating
	 * @param timestamp
	 */
	public Rating(int ratingID, int userID, int barID, int generalRating, int pprRating, int musicRating,
			int peopleRating, int atmosphereRating, Timestamp timestamp) {
		super();
		this.ratingID = ratingID;
		this.userID = userID;
		this.barID = barID;
		this.generalRating = generalRating;
		this.pprRating = pprRating;
		this.musicRating = musicRating;
		this.peopleRating = peopleRating;
		this.atmosphereRating = atmosphereRating;
		if (timestamp == null) {
			this.timestamp = null;
		} else {
			this.timestamp = new Timestamp(timestamp.getTime());
		}
	}

	public int getRatingID() {
		return ratingID;
	}

	public void setRatingID(int ratingID) {
		this.ratingID = ratingID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getBarID() {
		return barID;
	}

	public void setBarID(int barID) {
		this.barID = barID;
	}

	public int getGeneralRating() {
		return generalRating;
	}

	public void setGeneralRating(int generalRating) {
		this.generalRating = generalRating;
	}

	public int getPprRating() {
		return pprRating;
	}

	public void setPprRating(int pprRating) {
		this.pprRating = pprRating;
	}

	public int getMusicRating() {
		return musicRating;
	}

	public void setMusicRating(int musicRating) {
		this.musicRating = musicRating;
	}

	public int getPeopleRating() {
		return peopleRating;
	}

	public void setPeopleRating(int peopleRating) {
		this.peopleRating = peopleRating;
	}

	public int getAtmosphereRating() {
		return atmosphereRating;
	}

	public void setAtmosphereRating(int atmosphereRating) {
		this.atmosphereRating = atmosphereRating;
	}

	public Timestamp getTimestamp() {
		if (timestamp == null) {
			return null;
		}
		return new Timestamp(timestamp.getTime());
	}

	public void setTimestamp(Timestamp timestamp) {
		if(timestamp == null) {
			this.timestamp = null;
			return;
		}
		this.timestamp = new Timestamp(timestamp.getTime());
	}

	/**
	 * Save a rating to the Database
	 */
	public void saveRating() {
		try {
			Database.saveBarRating(this);
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}
	}

}
