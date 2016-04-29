package de.kaniba.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import de.kaniba.components.SearchElementImpl;
import de.kaniba.designs.MyBarsDesign;
import de.kaniba.model.Admin;
import de.kaniba.model.Bar;
import de.kaniba.model.InternalUser;
import de.kaniba.utils.BarUtils;
import de.kaniba.utils.Utils;

public class MyBarsView extends MyBarsDesign implements View {

	public static final String NAME = "myBars";
	private Admin admin;

	@Override
	public void enter(ViewChangeEvent event) {
		if(!Utils.isAdmin()) {
			Utils.navigateBack();
			return;
		}
		
		admin = (Admin) InternalUser.getUser();

		barResultContainer.removeAllComponents();
		for (Bar bar : admin.getOwnedBars()) {
			SearchElementImpl result = new SearchElementImpl(bar.getName(), BarUtils.getOneLineAddress(bar),
					EditBarView.NAME + "/" + bar.getBarID());
			barResultContainer.addComponent(result);
		}
	}

}
