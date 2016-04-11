package de.kaniba.presenter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.designertest.SearchElement;
import com.example.designertest.SearchView;
import com.example.designertest.SearchViewImpl;

import de.kaniba.model.Bar;
import de.kaniba.model.Database;

public class SearchPresenter {
	
	private SearchViewImpl view;

	public SearchPresenter(SearchViewImpl view) {
		this.view = view;
		view.registerPresenter(this);
	}
	
	public SearchViewImpl getView() {
		return view;
	}

	public void updateSearchView(String searchValue) {
		// TODO: Liste aller bars bekommen
		List<Bar> resultList = null;
		try {
			resultList = Database.searchForBar(searchValue);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(resultList == null) {
			System.out.println("Error while accesing database, will not show search results.");
			return;
		}
		
		// TODO: Alle Results in ein ResultElement gießen
		List<SearchElement> searchElementList = new ArrayList<>();
		for(Bar bar : resultList) {
			// TODO: Make this work!!!
			// Vllt in den View auslagern? Eigentlich muss der ja wissen, wie er sachen darstellen soll...
			// searchElementList.add(new SearchElement(bar));
		}
		
		// TODO: ResultList Updaten
		view.setSearchResults(searchElementList);
		
		// TODO: Map Updaten
		view.displayBarsOnMap(resultList);
		
	}	
}
