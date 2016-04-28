package de.kaniba.presenter;

import java.sql.SQLException;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.model.DisplayRating;
import de.kaniba.model.InternalUser;
import de.kaniba.model.Message;
import de.kaniba.model.Rating;
import de.kaniba.utils.BarUtils;
import de.kaniba.utils.Utils;
import de.kaniba.view.BarView;
import de.kaniba.view.SurveyView;

public class BarPresenter {
	private Bar bar;
	private BarView view;
	private boolean settingUp;

	public BarPresenter() {
		view = new BarView();
		view.setPresenter(this);
		settingUp = false;
	}

	public BarView getView() {
		return view;
	}

	public void enter(ViewChangeEvent event) {
		settingUp = true;
		
		bar = BarUtils.getBarFromParams(event.getParameters());
		
		if(bar == null) {
			settingUp = false;
			
			//TODO: Show 404 - Bar not found page
			return;
		}
		view.setMapCoords(BarUtils.getLatLon(bar));
		view.setBarName(bar.getName());
		view.setBarAddress(BarUtils.getOneLineAddress(bar));
		view.setBarDescription(bar.getDescription());
		view.setBarMessageBoard(bar.forceGetPinboard().getMessages());
		
		if (Utils.isLoggedIn()) {
			Rating userRating = null;
			try {
				userRating = Database.getRating(InternalUser.getUser().getUserID(), bar.getBarID());
			} catch (SQLException e) {
				Utils.exception(e);
			}

			if (userRating != null) {
				view.setBarRating(new DisplayRating(userRating));
			}
		} else {
			view.setBarRating(bar.getDisplayRating());
		}
		settingUp = false;
	}

	public void saveRating(Rating rating) {
		if (settingUp) {
			return;
		}
		boolean loggedIn = Utils.isLoggedIn();
		if (!loggedIn) {
			Notification.show("Um abzustimmen muss du eingeloggt sein.", Type.WARNING_MESSAGE);
			return;
		}

		InternalUser user = InternalUser.getUser();

		Rating fromDatabase = null;
		try {
			
			fromDatabase = Database.getRating(user.getUserID(), bar.getBarID());
		} catch (SQLException e) {
			Utils.exception(e);
		}
		
		if(fromDatabase == null) {
			fromDatabase = new Rating(-1, InternalUser.getUser().getUserID(), bar.getBarID(), 0, 0, 0, 0, 0, null);
		}

		rating.setUserID(user.getUserID());
		rating.setBarID(bar.getBarID());

		// Check if a value is 0. If so, replace that value with the last rating
		// of the user. If no former rating exists, set all to 0.
		if (rating.getAtmosphereRating() == 0) {
			rating.setAtmosphereRating(fromDatabase.getAtmosphereRating());
		}

		if (rating.getPeopleRating() == 0) {
			rating.setPeopleRating(fromDatabase.getPeopleRating());
		}

		if (rating.getPprRating() == 0) {
			rating.setPprRating(fromDatabase.getPprRating());
		}

		if (rating.getGeneralRating() == 0) {
			rating.setGeneralRating(fromDatabase.getGeneralRating());
		}

		if (rating.getMusicRating() == 0) {
			rating.setMusicRating(fromDatabase.getMusicRating());
		}

		try {
			Database.saveBarRating(rating);
		} catch (SQLException e) {
			Utils.exception(e);
		}
	}

	public void sendMessage(String message) {
		if (!Utils.isLoggedIn()) {
			Notification.show("Um eine Message zu senden muss du eingeloggt sein!");
			return;
		}
		InternalUser user = InternalUser.getUser();
		Message dbMessage = new Message(user.getUserID(), bar.getBarID(), message);
		dbMessage.save();
		view.setBarMessageBoard(bar.forceGetPinboard().getMessages());
	}

	public void clickedSurvey() {
		UI.getCurrent().getNavigator().navigateTo(SurveyView.NAME + "/" + bar.getBarID());
	}
}
