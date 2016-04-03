package de.kaniba.view;

import java.util.List;

import com.vaadin.navigator.View;

import de.kaniba.model.Bar;

public interface BarFinderView extends View {
	public void displayBars(List<Bar> bars);

	interface BarFinderViewListener {
		public void searchBar(String search);
	}

	public void addListener(BarFinderViewListener listener);
}
