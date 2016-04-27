package de.kaniba.presenter;

import com.vaadin.navigator.View;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import de.kaniba.components.LoginPopupImpl;
import de.kaniba.model.InternalUser;
import de.kaniba.model.User;
import de.kaniba.utils.Utils;
import de.kaniba.view.RegisterView;

public class RegisterPresenter implements RegisterView.RegisterViewListener {

	User model;
	RegisterView view;

	public RegisterPresenter(User model, RegisterView view) {
		this.model = model;
		this.view = view;

		view.addListener(this);
	}
	
	public View getView() {
		return view;
	}

	@Override
	public void registerClickListener(ClickEvent event) {
		model = view.getUser();
		try {
			((InternalUser) model).saveUser();
			view.getSubmit().setComponentError(null);
			
			UI.getCurrent().getNavigator().navigateTo("");
			
			Window loginWindow = new Window();
			LoginPopupImpl popup = new LoginPopupImpl(loginWindow);
			loginWindow.setContent(popup);
			popup.setLoginName(((InternalUser) model).getEmail().getMail());
			UI.getCurrent().addWindow(loginWindow);
			
		} catch (Exception e) {
			view.getSubmit().setComponentError(new UserError("Fehler beim speichern"));
			Utils.exception(e);
		}
	}

}
