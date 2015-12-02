package de.kaniba.view;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Panel;

import de.kaniba.model.Rating;

public interface BarView {

	
	
	interface BarViewListener{
		void ratingButtonClick(Rating rating);
	}

	public void addRatingButtonClickListener(BarViewListener listener);
}
