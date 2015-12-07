package de.kaniba.presenter;

import com.vaadin.navigator.View;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import de.kaniba.model.InternalUser;
import de.kaniba.model.User;
import de.kaniba.navigator.NavigatorUI;
import de.kaniba.view.LoginView;
import de.kaniba.view.LoginViewImpl;
import de.kaniba.view.RegisterView;

public class RegisterPresenter implements RegisterView.RegisterViewListener {

	User model;
	RegisterView view;

	public View getView() {
		return view;
	}

	public RegisterPresenter(User model, RegisterView view) {
		this.model = model;
		this.view = view;

		view.addListener(this);
	}

	@Override
	public void registerClickListener(ClickEvent event) {
		model = view.getUser();
		try {
			((InternalUser) model).saveUser();
			view.getSubmit().setComponentError(null);
			
			LoginPresenter loginPresenter = new LoginPresenter(new User(), new LoginViewImpl());
			LoginView loginView = (LoginView) loginPresenter.getView();
			loginView.getUsernameText().setValue(((InternalUser) model).getEmail().getMail());
			
			((NavigatorUI) UI.getCurrent()).getNavigator().addView(LoginView.NAME, loginView);
			((NavigatorUI) UI.getCurrent()).getNavigator().navigateTo(LoginView.NAME);
		} catch (Exception e) {
			view.getSubmit().setComponentError(new UserError("Fehler beim speichern"));
			e.printStackTrace();
		}
	}

}
