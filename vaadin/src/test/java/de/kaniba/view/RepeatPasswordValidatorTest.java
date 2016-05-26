package de.kaniba.view;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.PasswordField;

public class RepeatPasswordValidatorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test(expected=InvalidValueException.class)
	public void testValidateUnequal() {
		PasswordField field = new PasswordField();
		field.setValue("test");
		
		PasswordField repeat = new PasswordField();
		repeat.addValidator(new RepeatPasswordValidator(field, repeat));
		repeat.setValue("test1");
		repeat.validate();
	}
	
	@Test
	public void testValidateEqual() {
		PasswordField field = new PasswordField();
		field.setValue("test");
		
		PasswordField repeat = new PasswordField();
		repeat.addValidator(new RepeatPasswordValidator(field, repeat));
		repeat.setValue("test");
		repeat.validate();
		
		assertNull("There was an error.", repeat.getErrorMessage());
	}

}
