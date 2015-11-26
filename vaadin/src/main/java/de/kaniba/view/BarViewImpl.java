package de.kaniba.view;

import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Panel;

import de.kaniba.view.*;

public class BarViewImpl extends CustomComponent implements BarView, ClickListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5449042042425044956L;

	public BarViewImpl() {
		
				Panel mainPanel = createMainPanel();
			    RatingStars ratingStars = createRatingStars();
			    
			    
		        
			    mainPanel.setContent(ratingStars);
			    
			    setCompositionRoot(mainPanel);
			   
	}

	private Panel createMainPanel() {
		Panel mainPanel = new Panel();
		mainPanel.setWidth("550px");
		mainPanel.setHeight("500px");
		return mainPanel;
	}

	private RatingStars createRatingStars() {
		//Diese Methode legt die Anzeigeelemente für die Sterne an
		
		RatingStars ratinggeneral = new RatingStars();
		RatingStars ratingatmo = new RatingStars();
		RatingStars ratingmusic = new RatingStars();
		RatingStars ratingppr = new RatingStars();
		
		ratinggeneral.setMaxValue(5);
		ratingatmo.setMaxValue(5);
		ratingmusic.setMaxValue(5);
		ratingppr.setMaxValue(5);
		
		
		ratinggeneral.setCaption("Bar Rating");
		ratinggeneral.setImmediate(true);
		return ratinggeneral;
	}
	
	@Override
	public void setDisplay(double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener(BarViewListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}
	

}
