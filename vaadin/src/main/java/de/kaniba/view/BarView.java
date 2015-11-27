package de.kaniba.view;

import com.vaadin.navigator.View;

public interface BarView extends View{

	public void setDisplay(double value);
	interface BarViewListener {
		 void buttonClick(char operation);
		 }
	public void addListener(BarViewListener listener);
	
}
