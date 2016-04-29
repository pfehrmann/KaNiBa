package de.kaniba.presenter;

import com.vaadin.navigator.View;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;

import de.kaniba.model.Email;
import de.kaniba.model.InternalUser;
import de.kaniba.utils.Utils;
import de.kaniba.view.UpdateInformationView;

public class UpdateInformationPresenter {

	InternalUser model;
	UpdateInformationView view;
	VaadinSession session;

	public UpdateInformationPresenter(UpdateInformationView view) {
		this.view = view;

		session = Utils.getSession();
		model = InternalUser.getUser();

		if(model != null) {
			view.setUser(model);
		}
		view.addPresenter(this);
	}
	
	public UpdateInformationView getView() {
		return view;
	}

	public void updateClickListener(ClickEvent event) {
		String passwordOld = model.getPassword();
		String passwordNew = view.getPasswordField().getValue();
		String passwordNewRepeat = view.getRepeatPasswordField().getValue();
		Email emailOld = model.getEmail();
		
		model = view.getUser();

		if ("".equals(passwordNew)) {
			model.setPassword(passwordOld);
		} else {
			if (passwordNew.equals(passwordNewRepeat)) {
				if (passwordOld.equals(view.getOldPasswordField().getValue())) {
					model.setPassword(passwordNew);
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
		
		model.setEmail(emailOld);

		try {
			model.saveUser();
			view.getSubmit().setComponentError(null);
			
			session.setAttribute("user", model);
			Notification.show("Daten geändert.");
		} catch (Exception e) {
			view.getSubmit().setComponentError(new UserError("Fehler beim speichern"));
			Utils.exception(e);
		}
	}
	
	public void enter() {
		this.model = InternalUser.getUser();

		if (!Utils.isLoggedIn()) {
			Notification.show("Um deine Daten zu ändern, musst du eingeloggt sein.");
			Utils.navigateBack();
			return;
		}
		
		view.setUser(model);
	}

}
