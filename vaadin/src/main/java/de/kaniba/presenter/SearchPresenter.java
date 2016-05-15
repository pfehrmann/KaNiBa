package de.kaniba.presenter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import de.kaniba.components.SearchElement;
import de.kaniba.components.SearchElementImpl;
import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.uiInterfaces.SearchPresenterInterface;
import de.kaniba.uiInterfaces.SearchViewInterface;
import de.kaniba.utils.LoggingUtils;

/**
 * Logic for the search
 * @author Philipp
 *
 */
public class SearchPresenter implements SearchPresenterInterface {
	private static final long serialVersionUID = 1L;
	
	private SearchViewInterface view;

	public SearchPresenter(SearchViewInterface view) {
		this.view = view;
		view.setPresenter(this);
	}
	
	public SearchViewInterface getView() {
		return view;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.presenter.SearchInterface#updateSearchView(java.lang.String)
	 */
	@Override
	public void updateSearchView(String searchValue) {
		List<Bar> resultList = getResultList(searchValue);
		
		if(resultList == null) {
			LoggingUtils.log("Error while accesing database, will not show search results.");
			return;
		}
		
		List<SearchElement> searchElementList = new ArrayList<>();
		for(Bar bar : resultList) {
			searchElementList.add(new SearchElementImpl(bar));
		}
		
		view.setSearchResults(searchElementList);
		
		view.displayBarsOnMap(resultList);
	}

	private List<Bar> getResultList(String searchValue) {
		List<Bar> resultList = null;
		
		// Search for a tag?
		if(searchValue.startsWith("tag=")) {
			try {
				resultList = Database.getBarsForTag(searchValue.replaceFirst("tag=\\s*", ""));
				return resultList;
			} catch (SQLException e) {
				LoggingUtils.exception(e);
			}
		}
		
		try {
			resultList = Database.searchForBar(searchValue);
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}
		return resultList;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		String tag = event.getParameters();
		if(tag != null && !"".equals(tag)) {
			updateSearchView("tag=" + tag);
		}
	}	
}
