package de.kaniba.presenter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.kaniba.components.SearchElement;
import de.kaniba.components.SearchElementImpl;
import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.view.SearchView;

public class SearchPresenter {
	
	private SearchView view;

	public SearchPresenter(SearchView view) {
		this.view = view;
		view.registerPresenter(this);
	}
	
	public SearchView getView() {
		return view;
	}

	public void updateSearchView(String searchValue) {

		List<Bar> resultList = null;
		try {
			resultList = Database.searchForBar(searchValue);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(resultList == null) {
			System.out.println("Error while accesing database, will not show search results.");
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
