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

public class RegisterViewImpl extends CustomComponent implements RegisterView {
	private List<RegisterViewListener> listeners;
	private Panel mainPanel;
	private TextField nameField;
	private TextField firstNameField;
	private TextField emailField;
	private PasswordField passwordField;
	private PasswordField repeatPasswordField;
	private DateField birthdateField;
	private TextField cityField;
	private TextField streetField;
	private TextField numberField;
	private TextField zipField;
	private Button submit;
	private String width = "100%";
	
	public RegisterViewImpl() {		
		listeners = new ArrayList<>();
		mainPanel = new Panel();
		mainPanel.setWidth(width);
		mainPanel.addStyleName("login-panel");
		
		FormLayout form = new FormLayout();
		
		nameField = new TextField("Nachname");
		form.addComponent(nameField);

		firstNameField = new TextField("Vorname");
		form.addComponent(firstNameField);

		emailField = new TextField("E-Mail");
		form.addComponent(emailField);

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

		submit = new Button("Registrieren");
		submit.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				for (RegisterView.RegisterViewListener listener : listeners) {
					listener.registerClickListener(event);
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

	public TextField getEmailField() {
		return emailField;
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
		Page.getCurrent().setTitle("Registrieren");
	}

	@Override
	public InternalUser getUser() {
		InternalUser user = new InternalUser();

		Address address = new Address(cityField.getValue(), streetField.getValue(), numberField.getValue(),
				zipField.getValue());
		Email email = new Email(emailField.getValue());
		java.util.Date utilDate = birthdateField.getValue();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

	    System.out.println("Vaadin:   " + birthdateField.getValue());
	    System.out.println("UtilDate: " + utilDate.getTime());
	    System.out.println("SQLDate:  " + sqlDate.getTime());
	    System.out.println();
	    
		user.setFirstname(firstNameField.getValue());
		user.setName(nameField.getValue());
		user.setPassword(passwordField.getValue());
		user.setAddress(address);
		user.setEmail(email);
		user.setBirthdate(sqlDate);

		return user;
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
	public void addListener(RegisterViewListener listener) {
		listeners.add(listener);
	}

}
