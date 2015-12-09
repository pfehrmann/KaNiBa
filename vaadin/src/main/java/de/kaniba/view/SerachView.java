package de.kaniba.view;

import com.vaadin.navigator.View;

import de.kaniba.model.Bar;

public interface SerachView extends View {
	interface SearchViewListener {
		public void visitBar(Bar bar);
	}
	
	public void addListener(SearchViewListener listener);
}
