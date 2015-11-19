package de.kaniba.view;

public interface BarView {

	public void setDisplay(double value);
	interface BarViewListener {
		 void buttonClick(char operation);
		 }
	public void addListener(BarViewListener listener);
	
}
