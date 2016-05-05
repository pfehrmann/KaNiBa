package de.kaniba.view;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;

import de.kaniba.components.SearchElementImpl;
import de.kaniba.designs.MyBarsDesign;
import de.kaniba.model.Admin;
import de.kaniba.model.Bar;
import de.kaniba.model.InternalUser;
import de.kaniba.model.User;
import de.kaniba.utils.Utils;

/**
 * This view is used to display all the bars, one admin can administrate.
 * @author Philipp
 *
 */
public class MyBarsView extends MyBarsDesign implements View {
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "myBars";
	private Admin admin;
	
	/**
	 * Does nothig, setting the view up is done when entering is
	 */
	public MyBarsView() {
		super();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if(!User.isAdmin()) {
			Utils.navigateBack();
			return;
		}
		
		admin = (Admin) InternalUser.getUser();

		barResultContainer.removeAllComponents();
		List<Bar> ownedBars = admin.getOwnedBars();
		for (Bar bar : ownedBars) {
			SearchElementImpl result = new SearchElementImpl(bar.getName(), bar.getOneLineAddress(),
					EditBarView.NAME + "/" + bar.getBarID());
			barResultContainer.addComponent(result);
		}
		
		if(ownedBars.isEmpty()) {
			barResultContainer.addComponent(new Label("Du kannst leider keine Bars administrieren, weil die keine zugwiesen wurden. Wenn das ein Fehler ist, schreib uns bitte einfach an, wir kümmern uns dann darum."));
		}
	}

}
