package de.kaniba.components;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.kaniba.utils.Utils;
import de.kaniba.view.MyBarsView;

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
				Utils.navigateTo(MyBarsView.NAME);
			}
		});
	}
	
}
