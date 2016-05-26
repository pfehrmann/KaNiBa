package de.kaniba.uiInterfaces;

import com.vaadin.ui.Button;

import de.kaniba.model.InternalUser;

/**
 * This interface declares the methods for the register view.
 * @author Philipp
 *
 */
public interface RegisterViewInterface extends SecuredView {

	/**
	 * returns the submit button
	 * @return
	 */
	Button getSubmitButton();

	/**
	 * returns a user
	 * @return
	 */
	InternalUser getUser();

	void setPresenter(RegisterPresenterInterface presenter);

}
