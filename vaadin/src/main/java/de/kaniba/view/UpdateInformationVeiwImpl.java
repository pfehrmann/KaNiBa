package de.kaniba.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

import de.kaniba.model.Address;
import de.kaniba.model.Email;
import de.kaniba.model.InternalUser;

public class UpdateInformationVeiwImpl extends CustomComponent implements UpdateInformationView {

	private List<UpdateInformationViewListener> listeners;
	private Panel mainPanel;
	private TextField nameField;
	private TextField firstNameField;
	private PasswordField oldPasswordField;
	private PasswordField passwordField;
	private PasswordField repeatPasswordField;
	private DateField birthdateField;
	private TextField cityField;
	private TextField streetField;
	private TextField numberField;
	private TextField zipField;
	private Button submit;
	private String width = "500px";
	
	public UpdateInformationVeiwImpl() {
		Page.getCurrent().setTitle("Informationen aktualisieren");
		
		listeners = new ArrayList<>();
		mainPanel = new Panel();
		mainPanel.setWidth(width);
		mainPanel.addStyleName("login-panel");
		
		FormLayout form = new FormLayout();
		
		nameField = new TextField("Nachname");
		form.addComponent(nameField);

		firstNameField = new TextField("Vorname");
		form.addComponent(firstNameField);

		oldPasswordField = new PasswordField("Altes Passwort");
		form.addComponent(oldPasswordField);
		
		passwordField = new PasswordField("Passwort");
		form.addComponent(passwordField);

		repeatPasswordField = new PasswordField("Passwort wiederholen");
		repeatPasswordField.addTextChangeListener(new FieldEvents.TextChangeListener() {

			@Override
			public void textChange(TextChangeEvent event) {
				if (passwordField.getValue().equals(repeatPasswordField.getValue())) {
					repeatPasswordField.setComponentError(null);
					passwordField.setComponentError(null);
				} else {
					repeatPasswordField.setComponentError(new UserError("Die beiden Passwörter müssen gleich sein!"));
					passwordField.setComponentError(new UserError("Die beiden Passwörter müssen gleich sein!"));
				}
			}
		});
		form.addComponent(repeatPasswordField);

		birthdateField = new DateField("Geburtsdatum");
		form.addComponent(birthdateField);

		cityField = new TextField("Stadt");
		form.addComponent(cityField);

		zipField = new TextField("PLZ");
		form.addComponent(zipField);

		streetField = new TextField("Straße");
		form.addComponent(streetField);

		numberField = new TextField("Hausnummer");
		form.addComponent(numberField);

		submit = new Button("Aktualisieren");
		submit.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				for (UpdateInformationView.UpdateInformationViewListener listener : listeners) {
					listener.updateClickListener(event);
				}
			}
		});
		form.addComponent(submit);

		for(int i = 0; i < form.getComponentCount(); i++) {
			form.getComponent(i).setWidth("100%");
		}
		
		mainPanel.setContent(form);
		setCompositionRoot(mainPanel);
	}

	public TextField getNameField() {
		return nameField;
	}

	public TextField getFirstNameField() {
		return firstNameField;
	}

	public PasswordField getOldPasswordField() {
		return oldPasswordField;
	}
	
	public PasswordField getPasswordField() {
		return passwordField;
	}

	public PasswordField getRepeatPasswordField() {
		return repeatPasswordField;
	}

	public DateField getBirthdateField() {
		return birthdateField;
	}

	public TextField getCityField() {
		return cityField;
	}

	public TextField getStreetField() {
		return streetField;
	}

	public TextField getNumberField() {
		return numberField;
	}

	public TextField getZipField() {
		return zipField;
	}

	public Button getSubmit() {
		return submit;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		for(UpdateInformationViewListener listener: listeners) {
			listener.enter();
		}

	}

	@Override
	public InternalUser getUser() {
		InternalUser user = new InternalUser();
		
		Address address = new Address(cityField.getValue(), streetField.getValue(), numberField.getValue(),
				zipField.getValue());
		java.util.Date utilDate = birthdateField.getValue();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    
		user.setFirstname(firstNameField.getValue());
		user.setName(nameField.getValue());
		user.setPassword(passwordField.getValue());
		user.setAddress(address);
		user.setBirthdate(sqlDate);

		return user;
	}
	
	public void setUser(InternalUser user) {
		nameField.setValue(user.getName());
		firstNameField.setValue(user.getFirstname());
		birthdateField.setValue(user.getBirthdate());
		cityField.setValue(user.getAddress().getCity());
		streetField.setValue(user.getAddress().getStreet());
		numberField.setValue(user.getAddress().getNumber());
		zipField.setValue(user.getAddress().getZip());
	}

	@Override
	public void setFormWidth(String width) {
		this.width = width;
		mainPanel.setWidth(width);
	}

	@Override
	public String getFormWidth() {
		return width;
	}

	@Override
	public void addListener(UpdateInformationViewListener listener) {
		listeners.add(listener);
	}

}
