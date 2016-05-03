package de.kaniba.presenter;

import com.vaadin.navigator.View;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;

import de.kaniba.model.Email;
import de.kaniba.model.InternalUser;
import de.kaniba.model.User;
import de.kaniba.utils.LoggingUtils;
import de.kaniba.utils.Utils;
import de.kaniba.view.UpdateInformationInterface;
import de.kaniba.view.UpdateInformationView;

public class UpdateInformationPresenter implements UpdateInformationInterface {

	InternalUser user;
	UpdateInformationView view;
	VaadinSession session;

	public UpdateInformationPresenter(UpdateInformationView view) {
		this.view = view;

		session = Utils.getSession();
		user = InternalUser.getUser();

		if(user != null) {
			view.setUser(user);
		}
		view.addPresenter(this);
	}
	
	public UpdateInformationView getView() {
		return view;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.presenter.UpdateInformationInterface#updateClickListener(com.vaadin.ui.Button.ClickEvent)
	 */
	@Override
	public void updateClickListener(ClickEvent event) {
		String passwordOld = user.getPassword();
		String passwordNew = view.getPasswordField().getValue();
		String passwordNewRepeat = view.getRepeatPasswordField().getValue();
		Email emailOld = user.getEmail();
		
		user = view.getUser();

		if ("".equals(passwordNew)) {
			user.setPassword(passwordOld);
		} else {
			if (passwordNew.equals(passwordNewRepeat)) {
				if (passwordOld.equals(view.getOldPasswordField().getValue())) {
					user.setPassword(passwordNew);
					view.getOldPasswordField().setComponentError(null);
				} else {
					view.getOldPasswordField().setComponentError(new UserError("Bitte das alte Passwort eingeben"));
				}
				view.getPasswordField().setComponentError(null);
				view.getRepeatPasswordField().setComponentError(null);
			} else {
				view.getPasswordField().setComponentError(new UserError("Die Passwörter müssen identisch sein"));
				view.getRepeatPasswordField().setComponentError(new UserError("Die Passwörter müssen identisch sein"));
			}
		}
		
		user.setEmail(emailOld);

		try {
			user.saveUser();
			view.getSubmit().setComponentError(null);
			
			session.setAttribute("user", user);
			Notification.show("Daten geändert.");
		} catch (Exception e) {
			view.getSubmit().setComponentError(new UserError("Fehler beim speichern"));
			LoggingUtils.exception(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see de.kaniba.presenter.UpdateInformationInterface#enter()
	 */
	@Override
	public void enter() {
		this.user = InternalUser.getUser();

		if (!User.isLoggedIn()) {
			Notification.show("Um deine Daten zu ändern, musst du eingeloggt sein.");
			Utils.navigateBack();
			return;
		}
		
		view.setUser(user);
	}

}
