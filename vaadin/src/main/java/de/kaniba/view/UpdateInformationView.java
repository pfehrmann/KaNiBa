package de.kaniba.view;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

import de.kaniba.designs.UpdateInformationDesign;
import de.kaniba.model.Address;
import de.kaniba.model.InternalUser;
import de.kaniba.uiInterfaces.SecuredView;
import de.kaniba.uiInterfaces.UpdateInformationPresenterInterface;
import de.kaniba.uiInterfaces.UpdateInformationViewInterface;

/**
 * The view for updating personal informations
 * @author Philipp
 *
 */
public class UpdateInformationView extends UpdateInformationDesign implements UpdateInformationViewInterface {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "updateInformation";
	
	private UpdateInformationPresenterInterface presenter;
	
	/**
	 * Setup the view.
	 */
	public UpdateInformationView() {
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
		
		submit.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.updateClickListener(event);
			}
		});
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.UpdateInformationViewInterface#getOldPasswordField()
	 */
	@Override
	public PasswordField getOldPasswordField() {
		return oldPasswordField;
	}
	
	/* (non-Javadoc)
	 * @see de.kaniba.view.UpdateInformationViewInterface#getPasswordField()
	 */
	@Override
	public PasswordField getPasswordField() {
		return passwordField;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.UpdateInformationViewInterface#getRepeatPasswordField()
	 */
	@Override
	public PasswordField getRepeatPasswordField() {
		return repeatPasswordField;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.UpdateInformationViewInterface#getSubmit()
	 */
	@Override
	public Button getSubmit() {
		return submit;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Informationen aktualisieren");
		
		UI.getCurrent().getPage().setTitle("Informationen aktualisieren");
		presenter.enter();

	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.UpdateInformationViewInterface#getUser()
	 */
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
	
	/* (non-Javadoc)
	 * @see de.kaniba.view.UpdateInformationViewInterface#setUser(de.kaniba.model.InternalUser)
	 */
	@Override
	public void setUser(InternalUser user) {
		nameField.setValue(user.getName());
		firstNameField.setValue(user.getFirstname());
		birthdateField.setValue(user.getBirthdate());
		cityField.setValue(user.getAddress().getCity());
		streetField.setValue(user.getAddress().getStreet());
		numberField.setValue(user.getAddress().getNumber());
		zipField.setValue(user.getAddress().getZip());
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.UpdateInformationViewInterface#setPresenter(de.kaniba.presenter.UpdateInformationPresenterInterface)
	 */
	@Override
	public void setPresenter(UpdateInformationPresenterInterface presenter) {
		this.presenter = presenter;
	}

	@Override
	public boolean checkRights(String parameters) {
		return presenter.checkRights(parameters);
	}

}
