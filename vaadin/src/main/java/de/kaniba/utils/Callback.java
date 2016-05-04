package de.kaniba.utils;

/**
 * To trigger an event. Can be used if a client has to wait another client to
 * finish some action
 * 
 * @author Philipp
 *
 */
public interface Callback {

	/**
	 * The method to be called on success.
	 */
	public void success();
}
