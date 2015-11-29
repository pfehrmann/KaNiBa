package de.kaniba.view;

import com.vaadin.ui.Panel;

public interface BarView {

	public void setDisplay(Panel panel);
	interface BarViewListener {
		 void buttonClick(char operation);
		 }
	public void addListener(BarViewListener listener);
	
}
