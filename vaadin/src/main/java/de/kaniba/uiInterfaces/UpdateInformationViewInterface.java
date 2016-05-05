package de.kaniba.uiInterfaces;

import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;

import de.kaniba.model.InternalUser;

public interface UpdateInformationViewInterface extends SecuredView{

	PasswordField getOldPasswordField();

	PasswordField getPasswordField();

	PasswordField getRepeatPasswordField();

	Button getSubmit();

	InternalUser getUser();

	void setUser(InternalUser user);

	/**
	 * Add a presenter to the list of presenters
	 * @param presenter
	 */
	void setPresenter(UpdateInformationPresenterInterface presenter);

}