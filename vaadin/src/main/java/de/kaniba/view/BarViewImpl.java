package de.kaniba.view;

import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;

import de.kaniba.view.*;

public class BarViewImpl extends CustomComponent implements BarView, ClickListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5449042042425044956L;

	public BarViewImpl() {
		
				Panel mainPanel = createMainPanel();
			    Panel ratingStars = createRatingStars();
			    GridLayout mainLayout = new GridLayout(2,2);
			    mainLayout.addComponent(ratingStars,1,1);
			    mainPanel.setContent(mainLayout);
			    
			    setCompositionRoot(mainPanel);
			   
	}

	private Panel createMainPanel() {
		Panel mainPanel = new Panel();
		
		mainPanel.setWidth("900px");
		mainPanel.setHeight("500px");
		return mainPanel;
	}

	private Panel createRatingStars() {
		//Diese Methode legt die Anzeigeelemente für die Sterne an
		Panel ratingpanel = new Panel();
		ratingpanel.setWidth("300px");
		ratingpanel.setHeight("250px");
		ratingpanel.addStyleName("ratingpanel");
		GridLayout starLayout = new GridLayout(1, 4);
		starLayout.setSizeFull();
		
		RatingStars ratinggeneral = new RatingStars();
		RatingStars ratingatmo = new RatingStars();
		RatingStars ratingmusic = new RatingStars();
		RatingStars ratingppr = new RatingStars();
		
		ratinggeneral.setMaxValue(5);
		ratingatmo.setMaxValue(5);
		ratingmusic.setMaxValue(5);
		ratingppr.setMaxValue(5);
		
		
		ratinggeneral.setCaption("Gesamtbewertung Bar: ");
		ratingatmo.setCaption("Bewertung Atmosphäre: ");
		ratingmusic.setCaption("Bewertung der Musik: ");
		ratingppr.setCaption("Bewertung Preis-/Leistung: ");
		
		ratinggeneral.setImmediate(true);
		ratingatmo.setImmediate(true);
		ratingmusic.setImmediate(true);
		ratingppr.setImmediate(true);
		
		starLayout.addComponent(ratinggeneral, 0, 0);
		starLayout.addComponent(ratingatmo,0,1);
		starLayout.addComponent(ratingmusic, 0, 2);
		starLayout.addComponent(ratingppr,0,3);
		
		ratingpanel.setContent(starLayout);
	
		
		return ratingpanel;
	}
	


	@Override
	public void addListener(BarViewListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDisplay(Panel panel) {
		// TODO Auto-generated method stub
		
	}
	

}
