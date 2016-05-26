package de.kaniba.view;

import com.vaadin.data.Validator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import de.kaniba.designs.RegisterDesign;
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
public class RegisterView extends RegisterDesign implements RegisterViewInterface {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "register";
	
	private RegisterPresenterInterface presenter;
	
	/**
	 * Set the view up.
	 */
	public RegisterView() {		
		Validator passwordValidator = new RepeatPasswordValidator(passwordField, repeatPasswordField);
		
		repeatPasswordField.addValidator(passwordValidator);
		
		submitButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.registerClick();
			}
		});
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.RegisterViewInterface#getSubmit()
	 */
	@Override
	public Button getSubmitButton() {
		return submitButton;
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
