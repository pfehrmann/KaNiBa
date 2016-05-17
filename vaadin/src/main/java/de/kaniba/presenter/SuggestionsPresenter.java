/**
 * 
 */
package de.kaniba.presenter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;

import de.kaniba.components.SearchElementImpl;
import de.kaniba.model.Bar;
import de.kaniba.model.InternalUser;
import de.kaniba.uiInterfaces.SuggestionsPresenterInterface;
import de.kaniba.uiInterfaces.SuggestionsViewInterface;
import de.kaniba.utils.LoggingUtils;
import de.kaniba.view.EditBarView;

/**
 * Class to control the MyBarsView
 * @author Philipp
 *
 */
public class SuggestionsPresenter implements SuggestionsPresenterInterface {
	private static final long serialVersionUID = 1L;
	
	private SuggestionsViewInterface view;
	
	/**
	 * Initialize with the view of this presenter.
	 * @param view
	 */
	public SuggestionsPresenter(SuggestionsViewInterface view) {
		this.view = view;
		view.setPresenter(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		InternalUser user = InternalUser.getUser();

		List<Bar> suggestions = null;
		try {
			suggestions = user.getSuggestions();
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}
		
		List<Component> components = new ArrayList<>();
		for (Bar bar : suggestions) {
			SearchElementImpl result = new SearchElementImpl(bar.getName(), bar.getOneLineAddress(),
					EditBarView.NAME + "/" + bar.getBarID());
			components.add(result);
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
