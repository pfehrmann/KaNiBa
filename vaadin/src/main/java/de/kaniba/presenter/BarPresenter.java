package de.kaniba.presenter;

import java.sql.SQLException;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.model.DisplayRating;
import de.kaniba.model.InternalUser;
import de.kaniba.model.Message;
import de.kaniba.model.Rating;
import de.kaniba.model.User;
import de.kaniba.uiInterfaces.BarPresenterInterface;
import de.kaniba.uiInterfaces.BarViewInterface;
import de.kaniba.utils.LoggingUtils;
import de.kaniba.utils.NavigationUtils;
import de.kaniba.utils.NotificationUtils;
import de.kaniba.view.SurveyView;

/**
 * This class is the presenter of the BarView
 * 
 * @author Philipp
 *
 */
public class BarPresenter implements BarPresenterInterface {
	private static final long serialVersionUID = 1L;

	private Bar bar;
	private BarViewInterface view;
	private boolean settingUp;

	/**
	 * Initialize the Presenter with the correct view
	 * 
	 * @param view
	 */
	public BarPresenter(BarViewInterface view) {
		this.view = view;
		view.setPresenter(this);
		settingUp = false;
	}

	public BarViewInterface getView() {
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.kaniba.presenter.BarPresenterInterface#enter(com.vaadin.navigator.
	 * ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		settingUp = true;

		bar = Bar.getBarFromParams(event.getParameters());

		if (bar == null) {
			settingUp = false;

			NotificationUtils.showNotification(
					"Wir konnten die Bar leider nicht finden... " + "Vielleicht ist sie ja später da :D",
					Type.ERROR_MESSAGE);
			return;
		}
		view.setMapCoords(bar.getLatLon());
		view.setBarName(bar.getName());
		view.setBarAddress(bar.getOneLineAddress());
		view.setBarDescription(bar.getDescription());
		view.setBarMessageBoard(bar.getPinboard().getMessages());
		view.setBarLogo(bar);
		view.setTags(bar.getTags(), bar.getBarID());
		
		Page.getCurrent().setTitle(bar.getName());		
		DisplayRating rating = bar.getDisplayRating();

		if (User.isLoggedIn()) {
			Rating userRating = null;
			try {
				userRating = Database.getRating(InternalUser.getUser().getUserID(), bar.getBarID());
			} catch (SQLException e) {
				LoggingUtils.exception(e);
			}

			if (userRating != null) {
				rating = new DisplayRating(userRating);
			}
		}

		view.setBarRating(rating);
		settingUp = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.kaniba.presenter.BarPresenterInterface#saveRating(de.kaniba.model.
	 * Rating)
	 */
	@Override
	public void saveRating(Rating rating) {
		if (settingUp) {
			return;
		}
		boolean loggedIn = User.isLoggedIn();
		if (!loggedIn) {
			Notification.show("Um abzustimmen muss du eingeloggt sein.", Type.WARNING_MESSAGE);
			return;
		}

		InternalUser user = InternalUser.getUser();

		Rating fromDatabase = getRatingFromDatabase(user);

		rating.setUserID(user.getUserID());
		rating.setBarID(bar.getBarID());

		// Check if a value is 0. If so, replace that value with the last rating
		// of the user. If no former rating exists, set all to 0.
		checkChanges(rating, fromDatabase);

		try {
			Database.saveBarRating(rating);
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}
	}

	/**
	 * Check if the rating has changed. If a value has not changed, set it to
	 * something else than 0
	 * 
	 * @param rating
	 *            The new and possibly changed rating
	 * @param fromDatabase
	 *            The rating from the database
	 */
	private void checkChanges(Rating rating, Rating fromDatabase) {
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
	}

	/**
	 * Read a rating from the database using the users id and this bars id. If
	 * no rating was found, a new one will be created.
	 * 
	 * @param user
	 * @return
	 */
	private Rating getRatingFromDatabase(InternalUser user) {
		Rating fromDatabase = null;
		try {

			fromDatabase = Database.getRating(user.getUserID(), bar.getBarID());
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}

		if (fromDatabase == null) {
			fromDatabase = new Rating(-1, InternalUser.getUser().getUserID(), bar.getBarID(), 0, 0, 0, 0, 0, null);
		}
		return fromDatabase;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.kaniba.presenter.BarPresenterInterface#sendMessage(java.lang.String)
	 */
	@Override
	public void sendMessage(String message) {
		if (!User.isLoggedIn()) {
			Notification.show("Um eine Message zu senden muss du eingeloggt sein!");
			return;
		}
		InternalUser user = InternalUser.getUser();
		Message dbMessage = new Message(user.getUserID(), bar.getBarID(), message);
		dbMessage.save();
		view.setBarMessageBoard(bar.getPinboard().getMessages());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.kaniba.presenter.BarPresenterInterface#clickedSurvey()
	 */
	@Override
	public void clickedSurvey() {
		NavigationUtils.navigateTo(SurveyView.NAME + "/" + bar.getBarID());
	}

	@Override
	public void updateTagList() {
		view.setTags(bar.getTags(), bar.getBarID());
	}
}
