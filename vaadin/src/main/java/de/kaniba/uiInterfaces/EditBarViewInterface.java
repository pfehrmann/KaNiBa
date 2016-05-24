package de.kaniba.uiInterfaces;

import de.kaniba.model.Bar;

public interface EditBarViewInterface extends SecuredView {

	/**
	 * Reads the bar from the UI.
	 * 
	 * @return Returns the read bar. Note that only the address, name and
	 *         description are set.
	 */
	Bar getBar();

	/**
	 * Set the bar for this view
	 * 
	 * @param bar
	 */
	void setBar(Bar bar);

	/**
	 * Set the presenter for this view
	 * 
	 * @param presenter
	 */
	void setPresenter(EditBarPresenterInterface presenter);

}