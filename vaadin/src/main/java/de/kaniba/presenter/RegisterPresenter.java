package de.kaniba.presenter;

import java.sql.SQLException;

import com.vaadin.navigator.View;
import com.vaadin.server.UserError;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import de.kaniba.components.LoginPopupImpl;
import de.kaniba.model.InternalUser;
import de.kaniba.model.User;
import de.kaniba.uiInterfaces.RegisterPresenterInterface;
import de.kaniba.uiInterfaces.RegisterViewInterface;
import de.kaniba.utils.LoggingUtils;
import de.kaniba.utils.NavigationUtils;

public class RegisterPresenter implements RegisterPresenterInterface {
	private static final long serialVersionUID = 1L;
	private RegisterViewInterface view;

	/**
	 * Sets everything up.
	 * @param model
	 * @param view
	 */
	public RegisterPresenter(RegisterViewInterface view) {
		this.view = view;

		view.setPresenter(this);
	}
	
	public View getView() {
		return view;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.presenter.RegisterInterface#registerClick()
	 */
	@Override
	public void registerClick() {
		User user = view.getUser();
		try {
			((InternalUser) user).saveUser();
			view.getSubmit().setComponentError(null);
			
			NavigationUtils.navigateTo("", "Erfolgreich registriert");
			
			Window loginWindow = new Window("Login");
			LoginPopupImpl popup = new LoginPopupImpl(loginWindow);
			loginWindow.setContent(popup);
			loginWindow.setWidth("450px");
			loginWindow.setResizable(false);
			loginWindow.setModal(true);
			loginWindow.setDraggable(false);
			loginWindow.setContent(popup);
			popup.setLoginName(((InternalUser) user).getEmail().getMail());
			UI.getCurrent().addWindow(loginWindow);
			
		} catch (SQLException e) {
			view.getSubmit().setComponentError(new UserError("Fehler beim speichern"));
			LoggingUtils.exception(e);
		}
	}

	@Override
	public boolean checkRights(String parameters) {
		return !User.isLoggedIn();
	}

}
