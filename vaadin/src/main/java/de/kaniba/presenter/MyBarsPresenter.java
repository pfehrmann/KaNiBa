/**
 * 
 */
package de.kaniba.presenter;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

import de.kaniba.components.SearchElementImpl;
import de.kaniba.model.Admin;
import de.kaniba.model.Bar;
import de.kaniba.model.InternalUser;
import de.kaniba.model.User;
import de.kaniba.uiInterfaces.MyBarsPresenterInterface;
import de.kaniba.uiInterfaces.MyBarsViewInterface;
import de.kaniba.utils.NotificationUtils;
import de.kaniba.view.EditBarView;

/**
 * Class to control the MyBarsView
 * @author Philipp
 *
 */
public class MyBarsPresenter implements MyBarsPresenterInterface {
	private static final long serialVersionUID = 1L;
	
	private MyBarsViewInterface view;
	
	/**
	 * Initialize with the view of this presenter.
	 * @param view
	 */
	public MyBarsPresenter(MyBarsViewInterface view) {
		this.view = view;
		view.setPresenter(this);
	}
	
	@Override
	public boolean checkRights(String parameters) {
		if(!User.isAdmin()) {
			NotificationUtils.showNotification("Um eine Bar zu administrieren musst du ein Administrator sein.");
			return false;
		}
		return true;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Admin admin = (Admin) InternalUser.getUser();

		List<Bar> ownedBars = admin.getOwnedBars();
		List<Component> components = new ArrayList<>();
		for (Bar bar : ownedBars) {
			SearchElementImpl result = new SearchElementImpl(bar.getName(), bar.getOneLineAddress(),
					EditBarView.NAME + "/" + bar.getBarID());
			components.add(result);
		}
		
		if(ownedBars.isEmpty()) {
			components.add(new Label(
					"Du kannst leider keine Bars administrieren, weil die keine zugwiesen wurden. "
					+ "Wenn das ein Fehler ist, schreib uns bitte einfach an, wir kümmern uns dann darum."));
		}
		
		view.setResults(components);
	}

	/**
	 * get the view of this presenter
	 * @return
	 */
	public View getView() {
		return view;
	}

}
