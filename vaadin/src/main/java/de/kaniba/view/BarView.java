package de.kaniba.view;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.tapio.googlemaps.client.LatLon;

import de.kaniba.model.Address;
import de.kaniba.model.Bar;
import de.kaniba.model.Message;
import de.kaniba.model.Rating;

public interface BarView extends View {

	public void setMessageBoardStrings(List<Message> messages);

	public void setBarDescription(String barDescription);

	public void updateBarMessageBoard();
	
	public void setRating(Bar bar);
	
	public void setMapCoords(LatLon coords);
	
	public void setBarTitle(String barTitle);
	
	public void setBarAddress(Address address);

	interface BarViewListener {
		void ratingButtonClick(Rating rating);

		void sendMessage(String message);
		void enter(ViewChangeEvent event);
	}

	public void addRatingButtonClickListener(BarViewListener listener);
}
