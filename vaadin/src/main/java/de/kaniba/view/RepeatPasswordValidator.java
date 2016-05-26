package de.kaniba.view;

import com.vaadin.data.Validator;
import com.vaadin.ui.PasswordField;

/**
 * This class validates that password are equal.
 * @author Philipp
 *
 */
public class RepeatPasswordValidator implements Validator {
	private static final long serialVersionUID = 1L;

	private PasswordField field;
	private PasswordField repeat;

	public RepeatPasswordValidator(PasswordField field, PasswordField repeat) {
		this.field = field;
		this.repeat = repeat;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if(!field.getValue().equals(repeat.getValue())) {
			throw new InvalidValueException("Die beiden Passwörter müssen gleich sein.");
		}
	}
}
