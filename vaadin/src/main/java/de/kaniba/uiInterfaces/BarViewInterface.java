package de.kaniba.uiInterfaces;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.tapio.googlemaps.client.LatLon;

import de.kaniba.model.Bar;
import de.kaniba.model.DisplayRating;
import de.kaniba.model.Message;
import de.kaniba.model.Tag;

public interface BarViewInterface extends View {

	void setPresenter(BarPresenterInterface presenter);

	void setBarMessageBoard(List<Message> messages);

	void setBarRating(DisplayRating rating);

	void setBarLogo(Bar bar);

	void setMapCoords(LatLon coords);

	void setBarName(String name);

	void setBarAddress(String address);

	void setBarDescription(String description);

	void setTags(List<Tag> tags, int barID);

}