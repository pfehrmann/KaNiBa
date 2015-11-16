package de.kaniba.vaadin;

import java.sql.Timestamp;

public class Rating {
	private int ratingID;
	private int userID;
	private int barID;
	private int generalRating;
	private int pprRating;
	private int musicRating;
	private int peopleRating;
	private int atmosphereRating;
	private Timestamp timestamp;
	
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
		this.timestamp = timestamp;
	}
	
	public int getRatingID() {
		return ratingID;
	}
	public void setRatingID(int ratingID) {
		this.ratingID = ratingID;
		throw new RuntimeException("implement me");
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
		throw new RuntimeException("implement me");
	}
	
	public int getBarID() {
		return barID;
	}
	
	public void setBarID(int barID) {
		this.barID = barID;
		throw new RuntimeException("implement me");
	}
	
	public int getGeneralRating() {
		return generalRating;
	}
	
	public void setGeneralRating(int generalRating) {
		this.generalRating = generalRating;
		throw new RuntimeException("implement me");
	}
	
	public int getPprRating() {
		return pprRating;
	}
	
	public void setPprRating(int pprRating) {
		this.pprRating = pprRating;
		throw new RuntimeException("implement me");
	}
	
	public int getMusicRating() {
		return musicRating;
	}
	
	public void setMusicRating(int musicRating) {
		this.musicRating = musicRating;
		throw new RuntimeException("implement me");
	}
	
	public int getPeopleRating() {
		return peopleRating;
	}
	
	public void setPeopleRating(int peopleRating) {
		this.peopleRating = peopleRating;
		throw new RuntimeException("implement me");
	}
	
	public int getAtmosphereRating() {
		return atmosphereRating;
	}
	
	public void setAtmosphereRating(int atmosphereRating) {
		this.atmosphereRating = atmosphereRating;
		throw new RuntimeException("implement me");
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
}
