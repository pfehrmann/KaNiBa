package de.kaniba.presenter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.kaniba.components.SearchElement;
import de.kaniba.components.SearchElementImpl;
import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.utils.Utils;
import de.kaniba.view.SearchInterface;
import de.kaniba.view.SearchView;

/**
 * Logic for the search
 * @author Philipp
 *
 */
public class SearchPresenter implements SearchInterface {
	
	private SearchView view;

	public SearchPresenter(SearchView view) {
		this.view = view;
		view.registerPresenter(this);
	}
	
	public SearchView getView() {
		return view;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.presenter.SearchInterface#updateSearchView(java.lang.String)
	 */
	@Override
	public void updateSearchView(String searchValue) {

		List<Bar> resultList = null;
		try {
			resultList = Database.searchForBar(searchValue);
		} catch (SQLException e) {
			Utils.exception(e);
		}
		
		if(resultList == null) {
			Utils.log("Error while accesing database, will not show search results.");
			return;
		}
		
		List<SearchElement> searchElementList = new ArrayList<>();
		for(Bar bar : resultList) {
			searchElementList.add(new SearchElementImpl(bar));
		}
		
		view.setSearchResults(searchElementList);
		
		view.displayBarsOnMap(resultList);
		
	}	
}
