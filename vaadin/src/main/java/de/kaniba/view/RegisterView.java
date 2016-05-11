package de.kaniba.view;

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
import de.kaniba.uiInterfaces.RegisterPresenterInterface;
import de.kaniba.uiInterfaces.RegisterViewInterface;

/**
 * The view for registering
 * @author Philipp
 *
 */
public class RegisterView extends CustomComponent implements RegisterViewInterface {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "register";
	
	private RegisterPresenterInterface presenter;
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
	
	/**
	 * Set the view up.
	 */
	public RegisterView() {		
		mainPanel = new Panel();
		mainPanel.addStyleName("login-panel");
		mainPanel.setWidth("100%");
		
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
			private static final long serialVersionUID = 1L;

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
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.registerClick();
			}
		});
		form.addComponent(submit);

		for(int i = 0; i < form.getComponentCount(); i++) {
			form.getComponent(i).setWidth("100%");
		}
		
		mainPanel.setContent(form);
		setCompositionRoot(mainPanel);
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#getNameField()
	 */
	@Override
	public TextField getNameField() {
		return nameField;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#getFirstNameField()
	 */
	@Override
	public TextField getFirstNameField() {
		return firstNameField;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#getEmailField()
	 */
	@Override
	public TextField getEmailField() {
		return emailField;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#getPasswordField()
	 */
	@Override
	public PasswordField getPasswordField() {
		return passwordField;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#getRepeatPasswordField()
	 */
	@Override
	public PasswordField getRepeatPasswordField() {
		return repeatPasswordField;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#getBirthdateField()
	 */
	@Override
	public DateField getBirthdateField() {
		return birthdateField;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#getCityField()
	 */
	@Override
	public TextField getCityField() {
		return cityField;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#getStreetField()
	 */
	@Override
	public TextField getStreetField() {
		return streetField;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#getNumberField()
	 */
	@Override
	public TextField getNumberField() {
		return numberField;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#getZipField()
	 */
	@Override
	public TextField getZipField() {
		return zipField;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#getSubmit()
	 */
	@Override
	public Button getSubmit() {
		return submit;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Registrieren");
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#getUser()
	 */
	@Override
	public InternalUser getUser() {
		InternalUser user = new InternalUser();

		Address address = new Address(cityField.getValue(), streetField.getValue(), numberField.getValue(),
				zipField.getValue());
		Email email = new Email(emailField.getValue());
		java.util.Date utilDate = birthdateField.getValue();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    
		user.setFirstname(firstNameField.getValue());
		user.setName(nameField.getValue());
		user.setPassword(passwordField.getValue());
		user.setAddress(address);
		user.setEmail(email);
		user.setBirthdate(sqlDate);

		return user;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#setPresenter(de.kaniba.presenter.RegisterPresenterInterface)
	 */
	@Override
	public void setPresenter(RegisterPresenterInterface presenter) {
		this.presenter = presenter;
	}

	@Override
	public boolean checkRights(String parameters) {
		return presenter.checkRights(parameters);
	}

}
