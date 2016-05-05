package de.kaniba.presenter;

import java.sql.SQLException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification.Type;

import de.kaniba.model.Bar;
import de.kaniba.model.InternalUser;
import de.kaniba.model.User;
import de.kaniba.utils.LoggingUtils;
import de.kaniba.utils.NavigationUtils;
import de.kaniba.utils.NotificationUtils;
import de.kaniba.view.BarView;
import de.kaniba.view.EditBarView;

/**
 * Logic for editing bars
 * @author Philipp
 *
 */
public class EditBarPresenter implements EditBarPresenterInterface {
	private static final long serialVersionUID = 1L;
	
	private EditBarView view;
	private Bar bar;
	
	/**
	 * @param view The view to manage
	 */
	public EditBarPresenter(EditBarView view) {
		this.view = view;
		view.setPresenter(this);
	}

	public View getView() {
		return view;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.presenter.EditBarInterface#saveBar()
	 */
	@Override
	public void saveBar() {
		Bar read = view.getBar();
		bar.setAddress(read.getAddress());
		bar.setName(read.getName());
		bar.setDescription(read.getDescription());
		try {
			bar.saveBar();
			NavigationUtils.navigateTo(BarView.NAME + "/" + bar.getBarID());
			NotificationUtils.showNotification("Die Änderungen wurden erfolgreich gespeichert.");
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}
	}

	/* (non-Javadoc)
	 * @see de.kaniba.presenter.EditBarInterface#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public boolean enter(ViewChangeEvent event) {
		bar = Bar.getBarFromParams(event.getParameters());
		view.setBar(bar);
		return true;
	}

	@Override
	public boolean checkRights(String parameters) {
		bar = Bar.getBarFromParams(parameters);
		
		if(!User.isAdmin()) {
			NotificationUtils.showNotification("Um eine Bar bearbeiten zu können, musst du ein Bar Admin sein.",
					Type.ERROR_MESSAGE);
			return false;
		}
		if(bar.getBarOwner().getUserID() != InternalUser.getUser().getUserID()) {
			NotificationUtils.showNotification("Du darfst diese Bar nicht administrieren");
			return false;
		}
		
		return true;
	}
}
