/**
 * 
 */
package de.kaniba.view;

import com.vaadin.data.Validator;

import de.kaniba.model.Email;

/**
 * @author phili
 *
 */
public class UsedEmailValidator implements Validator {
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.vaadin.data.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(Object value) throws InvalidValueException {
		if(!Email.validateEmail(String.valueOf(value))) {
			throw new InvalidValueException("Die Email wurde schon benutzt.");
		}
	}

}
