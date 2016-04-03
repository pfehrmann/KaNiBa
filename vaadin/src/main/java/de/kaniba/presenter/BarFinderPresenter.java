package de.kaniba.presenter;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.navigator.View;

import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.view.BarFinderView;
import de.kaniba.view.BarFinderView.BarFinderViewListener;

public class BarFinderPresenter implements BarFinderViewListener {
	public static final String NAME = "barFinder";
	private BarFinderView view;
	
	public BarFinderPresenter(BarFinderView view) {
		this.view = view;
		view.addListener(this);
	}
	
	public View getView() {
		return view;
	}
	
	@Override
	public void searchBar(String search) {
		try {
			List<Bar> bars = Database.searchForBar(search);
			view.displayBars(bars);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
