package de.kaniba.presenter;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import de.kaniba.model.Email;
import de.kaniba.model.InternalUser;
import de.kaniba.model.User;
import de.kaniba.navigator.NavigatorUI;
import de.kaniba.view.LoginView;
import de.kaniba.view.LoginViewImpl;
import de.kaniba.view.RegisterView;
import de.kaniba.view.UpdateInformationView;

public class UpdateInformationPresenter implements UpdateInformationView.UpdateInformationViewListener {

	InternalUser model;
	UpdateInformationView view;
	VaadinSession session;

	public View getView() {
		return view;
	}

	public UpdateInformationPresenter(UpdateInformationView view) {
		this.model = model;
		this.view = view;

		session = ((NavigatorUI) UI.getCurrent()).getSession();
		Navigator navigator = ((NavigatorUI) UI.getCurrent()).getNavigator();

		Object loggedInObj = session.getAttribute("loggedIn");
		boolean loggedIn = false;
		if (loggedInObj != null) {
			loggedIn = (boolean) loggedInObj;
		}

		if (!loggedIn) {
			Notification.show("Um deine Daten zu ändern, musst du eingeloggt sein.");
			navigator.navigateTo(LoginView.NAME);
		}
		this.model = (InternalUser) session.getAttribute("user");

		if(model != null) {
			view.setUser(model);
		}
		view.addListener(this);
	}

	@Override
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
			
			//geänderte Daten Speichern
			session.setAttribute("user", model);
			Notification.show("Daten geändert.");
		} catch (Exception e) {
			view.getSubmit().setComponentError(new UserError("Fehler beim speichern"));
			e.printStackTrace();
		}
	}
	
	public void enter() {
		this.model = (InternalUser) session.getAttribute("user");
		
		if(model == null) {
			((NavigatorUI) UI.getCurrent()).getNavigator().navigateTo(LoginView.NAME);
		}
		
		view.setUser(model);
	}

}
