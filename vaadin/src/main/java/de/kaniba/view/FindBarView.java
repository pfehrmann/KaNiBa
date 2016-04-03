package de.kaniba.view;

import java.util.List;

import com.vaadin.navigator.View;

import de.kaniba.model.Bar;

public interface FindBarView extends View {
	public void displayBars(List<Bar> bars);

	interface FindBarViewListener {
		public void searchBar(String search);
	}

	public void addListener(FindBarViewListener listener);
}
