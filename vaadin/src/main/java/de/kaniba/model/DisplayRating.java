package de.kaniba.model;

public class DisplayRating {
	private double generalRating;
	private double priceRating;
	private double musicRating;
	private double peopleRating;
	private double atmosphereRating;
	
	public DisplayRating(Rating rating) {
		this.generalRating = rating.getGeneralRating();
		this.priceRating = rating.getPprRating();
		this.musicRating = rating.getMusicRating();
		this.peopleRating = rating.getPeopleRating();
		this.atmosphereRating = rating.getAtmosphereRating();
	}
	
	public DisplayRating(double generalRating, double priceRating, double musicRating, double peopleRating,
			double atmosphereRating) {
		super();
		this.generalRating = generalRating;
		this.priceRating = priceRating;
		this.musicRating = musicRating;
		this.peopleRating = peopleRating;
		this.atmosphereRating = atmosphereRating;
	}
	
	public double getGeneralRating() {
		return generalRating;
	}
	
	public double getPriceRating() {
		return priceRating;
	}
	
	public double getMusicRating() {
		return musicRating;
	}
	
	public double getPeopleRating() {
		return peopleRating;
	}
	
	public double getAtmosphereRating() {
		return atmosphereRating;
	}
	
	
}
