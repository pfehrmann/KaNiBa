package de.kaniba.presenter;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;

import de.kaniba.model.Bar;
import de.kaniba.model.Rating;
import de.kaniba.view.BarView;

public class BarPresenter implements BarView.BarViewListener{

	Bar barModel;
	Rating ratingModel;
	BarView view;

	
	 public BarPresenter(Bar bmodel,BarView view,Rating rmodel) {
		// TODO Auto-generated constructor stub
		 this.barModel = bmodel;
		 this.ratingModel = rmodel;
		 this.view = view;
		 
		 view.addRatingButtonClickListener(this);
		 
		 
		 
		
	}


	@Override
	public void ratingButtonClick(Rating rating) {
		// TODO Auto-generated method stub
		Notification.show("Es Lebt");
		
	}}
