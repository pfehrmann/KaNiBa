package de.kaniba.presenter;

import java.sql.SQLException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.model.InternalUser;
import de.kaniba.model.Message;
import de.kaniba.model.Rating;
import de.kaniba.navigator.NavigatorUI;
import de.kaniba.view.BarView;

public class BarPresenter implements BarView.BarViewListener {

	Bar barModel;
	Rating ratingModel;
	BarView view;
	InternalUser iUModel;
	VaadinSession session;

	public View getView() {
		return view;
	}

	public BarPresenter(BarView view/* ,Rating rmodel */) {
		/* this.ratingModel = rmodel; */
		this.view = view;

		view.addRatingButtonClickListener(this);
		session = UI.getCurrent().getSession();
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
		Notification.show("Danke das du abgestimmt hast");
		
		//TODO: Remove this.
		try {
			barModel = Database.readBar(barModel.getBarID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		view.setRating(barModel);
	}

	@Override
	public void sendMessage(String message) {
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
		Message dbMessage = new Message(iUModel.getUserID(), barModel.getBarID(), message);
		dbMessage.save();
		view.setMessageBoardStrings(barModel.forceGetPinboard().getMessages());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// Get the barID and the corresponding bar
		barModel = null;
		if (event.getParameters() != null) {
			String idParameter = event.getParameters();

			int id = -1;
			try {
				id = Integer.parseInt(idParameter);
			} catch (NumberFormatException e) {
				// don't do anything.
			}

			if (id != -1) {
				try {
					barModel = new Bar(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		// set the coordinates of the bar
		LatLon coords = Utils.getLatLon(barModel);
		view.setMapCoords(coords);
		
		// initialize the view
		if (barModel != null) {

			//TODO: if the user is logged in, use his rating.
			view.setRating(barModel);
			view.setBarDescription(barModel.getDescription());
			view.setBarTitle(barModel.getName());
			view.setBarAddress(barModel.getAddress());
			if(barModel.getPinboard() != null) {
				view.setMessageBoardStrings(barModel.getPinboard().getMessages());
			}
			UI.getCurrent().getPage().setTitle(barModel.getName());
		}
	}
}
