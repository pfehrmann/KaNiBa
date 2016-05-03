package de.kaniba.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.navigator.View;
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
import de.kaniba.presenter.UpdateInformationPresenter;

/**
 * The view for updating personal informations
 * @author Philipp
 *
 */
public class UpdateInformationView extends UpdateInformationDesign implements View {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "updateInformation";
	
	private List<UpdateInformationPresenter> presenters;
	
	/**
	 * Setup the view.
	 */
	public UpdateInformationView() {
		presenters = new ArrayList<>();
		
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
				for (UpdateInformationInterface presenter : presenters) {
					presenter.updateClickListener(event);
				}
			}
		});
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

	public Button getSubmit() {
		return submit;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Informationen aktualisieren");
		
		UI.getCurrent().getPage().setTitle("Informationen aktualisieren");
		for(UpdateInformationInterface presenter: presenters) {
			presenter.enter();
		}

	}

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

	/**
	 * Add a presenter to the list of presenters
	 * @param presenter
	 */
	public void addPresenter(UpdateInformationPresenter presenter) {
		presenters.add(presenter);
	}

}
