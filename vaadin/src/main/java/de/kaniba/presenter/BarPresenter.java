package de.kaniba.presenter;

import java.sql.SQLException;

import com.google.gwt.dom.client.ModElement;
import com.vaadin.navigator.View;
import com.vaadin.ui.Notification;

import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.model.Rating;
import de.kaniba.view.BarView;

public class BarPresenter implements BarView.BarViewListener{

	Bar barModel;
	Rating ratingModel;
	BarView view;

	public View getView() {
		return view;
	}
	
	 public BarPresenter(Bar bmodel,BarView view/*,Rating rmodel*/) {
		 this.barModel = bmodel;
		 /*this.ratingModel = rmodel;*/
		 this.view = view;
		 
		 view.addRatingButtonClickListener(this);
		
		 view.setBarDescription(barModel.getDescription());
		 System.out.println(barModel);
		 System.out.println(barModel.getPinboard());
		 System.out.println(barModel.getPinboard().getMessages());
		 view.setMessageBoardStrings(barModel.getPinboard().getMessages());
	}


	@Override
	public void ratingButtonClick(Rating rating) {
		rating.saveRating();
	}}
