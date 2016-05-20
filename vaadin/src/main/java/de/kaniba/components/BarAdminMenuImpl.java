package de.kaniba.components;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.kaniba.utils.NavigationUtils;
import de.kaniba.view.MyBarsView;

/**
 * Implementation of the BarAdminMenu
 * @author Philipp
 *
 */
public class BarAdminMenuImpl extends BarAdminMenu {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the menu for admins
	 */
	public BarAdminMenuImpl() {
		administrateBarsButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				NavigationUtils.navigateTo(MyBarsView.NAME);
			}
		});
	}
	
}
