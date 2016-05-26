package de.kaniba.uiInterfaces;

import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import de.kaniba.model.InternalUser;

public interface RegisterViewInterface extends SecuredView {

	TextField getNameField();

	TextField getFirstNameField();

	TextField getEmailField();

	PasswordField getPasswordField();

	PasswordField getRepeatPasswordField();

	DateField getBirthdateField();

	TextField getCityField();

	TextField getStreetField();

	TextField getNumberField();

	TextField getZipField();

	Button getSubmitButton();

	InternalUser getUser();

	void setPresenter(RegisterPresenterInterface presenter);

}