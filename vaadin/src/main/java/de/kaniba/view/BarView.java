package de.kaniba.view;

import java.util.List;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.Panel;

import de.kaniba.model.Message;
import de.kaniba.model.Rating;

public interface BarView extends View {

	public void setMessageBoardStrings(List<Message> messages);

	public void setBarDescription(String barDescription);

	public void updateBarMessageBoard();

	interface BarViewListener {
		void ratingButtonClick(Rating rating);

		void sendMessage(String message);
	}

	public void addRatingButtonClickListener(BarViewListener listener);
}
