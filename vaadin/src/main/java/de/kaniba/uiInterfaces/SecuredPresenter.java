package de.kaniba.uiInterfaces;

public interface SecuredPresenter {
	/**
	 * Checks if a user is allowed to enter the view
	 * @param parameters The parameters for the view
	 * @return Returns true if the user is allowed to access the page and false otherwise.
	 */
	boolean checkRights(String parameters);
}
