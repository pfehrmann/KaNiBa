package de.kaniba.components;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.kaniba.utils.Utils;
import de.kaniba.view.EditBarView;
import de.kaniba.view.MyBarsView;

public class BarAdminMenuImpl extends BarAdminMenu {
	
	public BarAdminMenuImpl() {
		administrateBarsButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Utils.navigateTo(MyBarsView.NAME);
			}
		});
	}
	
}
