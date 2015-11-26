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
		
				Panel mainPanel = new Panel();
			    mainPanel.setWidth("550px");
			    mainPanel.setHeight("500px");
			    RatingStars ratingStars = new RatingStars();
			    ratingStars.setMaxValue(5);
			    ratingStars.setCaption("Bar Rating");
			    ratingStars.setValue(4.0 + 1.3);
			    ratingStars.setImmediate(true);
			    
			    //ratingStars.addValueChangeListener(ratingStars.valueChange
		        
			    mainPanel.setContent(ratingStars);
			    
			    setCompositionRoot(mainPanel);
			   
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
