package de.kaniba.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import de.kaniba.model.InternalUser;

public interface RegisterView extends View{
	public static final String NAME = "register";
	public InternalUser getUser();
	public void setFormWidth(String width);
	public String getFormWidth();
	
	public TextField getNameField();
	public TextField getFirstNameField();
	public TextField getEmailField();
	public PasswordField getPasswordField();
	public PasswordField getRepeatPasswordField();
	public DateField getBirthdateField();
	public TextField getCityField();
	public TextField getStreetField();
	public TextField getNumberField();
	public TextField getZipField();
	public Button getSubmit();
	
	interface RegisterViewListener {
		void registerClickListener(ClickEvent event);
	}

	public void addListener(RegisterViewListener listener);
}
