package de.kaniba.model;

/**
 * Class that represents a rating for display
 * @author Philipp
 *
 */
public class DisplayRating {
	private double generalRating;
	private double priceRating;
	private double musicRating;
	private double peopleRating;
	private double atmosphereRating;
	
	/**
	 * Innitialize the display rating with a usual rating
	 * @param rating
	 */
	public DisplayRating(Rating rating) {
		this.generalRating = rating.getGeneralRating();
		this.priceRating = rating.getPprRating();
		this.musicRating = rating.getMusicRating();
		this.peopleRating = rating.getPeopleRating();
		this.atmosphereRating = rating.getAtmosphereRating();
	}
	
	/**
	 * Initialize the rating with all the single ratings
	 * @param generalRating
	 * @param priceRating
	 * @param musicRating
	 * @param peopleRating
	 * @param atmosphereRating
	 */
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
