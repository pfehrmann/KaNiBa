package de.kaniba.presenter;

import java.sql.SQLException;

import com.google.gwt.dom.client.ModElement;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.model.InternalUser;
import de.kaniba.model.Message;
import de.kaniba.model.Rating;
import de.kaniba.navigator.NavigatorUI;
import de.kaniba.view.BarView;
import de.kaniba.view.LoginView;

public class BarPresenter implements BarView.BarViewListener{

	Bar barModel;
	Rating ratingModel;
	BarView view;
	InternalUser iUModel;
	VaadinSession session;
	

	public View getView() {
		return view;
	}
	
	 public BarPresenter(Bar bmodel,BarView view/*,Rating rmodel*/) {
		 this.barModel = bmodel;
		 /*this.ratingModel = rmodel;*/
		 this.view = view;
		 
		 view.addRatingButtonClickListener(this);
		
		 view.setBarDescription(barModel.getDescription());
		 view.setMessageBoardStrings(barModel.getPinboard().getMessages());
		 session=UI.getCurrent().getSession();
	}


	@Override
	public void ratingButtonClick(Rating rating) {
		this.iUModel = (InternalUser) session.getAttribute("user");
		Object loggedInObj = session.getAttribute("loggedIn");
		boolean loggedIn = false;
		if (loggedInObj != null) {
			loggedIn = (boolean) loggedInObj;
		}
		if (!loggedIn) {
			Notification.show("Bitte logg dich ein um abzustimmen");
			
			return;
		}
		rating.setBarID(barModel.getBarID());
		rating.setUserID(iUModel.getUserID());
		rating.saveRating();
	}

	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		session = ((NavigatorUI) UI.getCurrent()).getSession();
	
		Object loggedInObj = session.getAttribute("loggedIn");
		boolean loggedIn = false;
		if (loggedInObj != null) {
			loggedIn = (boolean) loggedInObj;
		}
		if (!loggedIn) {
			Notification.show("Um eine Message zu senden muss du eingeloggt sein!");
			return;
		}
		this.iUModel = (InternalUser) session.getAttribute("user");
		Message dbMessage = new Message( iUModel.getUserID(), barModel.getBarID(), message);
		dbMessage.save();
		view.setMessageBoardStrings(barModel.forceGetPinboard().getMessages());
	}}
