package de.kaniba.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import de.kaniba.model.InternalUser;
import de.kaniba.model.User;

public interface UpdateInformationView extends View{
	public static final String NAME = "updateInformation";
	public InternalUser getUser();
	public void setUser(InternalUser user);
	public void setFormWidth(String width);
	public String getFormWidth();
	
	public TextField getNameField();
	public TextField getFirstNameField();
	public PasswordField getOldPasswordField();
	public PasswordField getPasswordField();
	public PasswordField getRepeatPasswordField();
	public DateField getBirthdateField();
	public TextField getCityField();
	public TextField getStreetField();
	public TextField getNumberField();
	public TextField getZipField();
	public Button getSubmit();
	
	interface UpdateInformationViewListener {
		void updateClickListener(ClickEvent event);
		void enter();
	}

	public void addListener(UpdateInformationViewListener listener);
}
