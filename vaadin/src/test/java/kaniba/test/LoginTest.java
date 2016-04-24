/**
 * 
 */
package kaniba.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.ui.Window;

import de.kaniba.components.LoginPopupImpl;

/**
 * @author phili
 *
 */
public class LoginTest {
	private LoginPopupImpl loginPopup;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Create mocup of the window
		loginPopup = new LoginPopupImpl(new Window());
		fail("Not yet implementsed");
	}

	/**
	 * Test method for {@link de.kaniba.components.LoginPopupImpl#LoginPopupImpl(com.vaadin.ui.Window)}.
	 */
	@Test
	public void testLoginPopupImpl() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.kaniba.components.LoginPopupImpl#setLoginName(java.lang.String)}.
	 */
	@Test
	public void testSetLoginName() {
		fail("Not yet implemented");
	}

}
