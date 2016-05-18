package de.kaniba.view;

import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.ui.UI;

import de.kaniba.components.Map;
import de.kaniba.components.Map.Coordinates;
import de.kaniba.components.SearchElement;
import de.kaniba.designs.SearchDesign;
import de.kaniba.model.Bar;
import de.kaniba.uiInterfaces.SearchPresenterInterface;
import de.kaniba.uiInterfaces.SearchViewInterface;

/**
 * The View for Searching
 * @author Philipp
 *
 */
public class SearchView extends SearchDesign implements SearchViewInterface {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "search";
	//protected GoogleMap map;
	protected Map map;
	private SearchPresenterInterface presenter;

	public SearchView() {
		//map = createMap();
		map = new Map();
		map.setCenter(new Coordinates(49.0068901, 8.4036527));
		mapContainer.addComponent(map);
		UI.getCurrent().getPage().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void browserWindowResized(BrowserWindowResizeEvent event) {
				checkMapVisibility();
			}

		});

		searchBar.addSearchListener(new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void componentEvent(Event event) {
				firePresenter();
			}
		});
	}
	
	private void checkMapVisibility() {
		if(Page.getCurrent().getBrowserWindowWidth() <= 800) {
			mapContainer.removeComponent(map);
			resultList.setWidth("100%");
			mapContainer.setWidth("0%");
		} else {
			mapContainer.addComponent(map);
			resultList.setWidth("20%");
			mapContainer.setWidth("80%");
		}
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.SearchViewInterface#displayBarsOnMap(java.util.List)
	 */
	@Override
	public void displayBarsOnMap(List<Bar> bars) {
		map.removeAllMarkers();

		for (Bar b : bars) {
			Coordinates coords = b.getLatLon();
			map.addMarker(BarView.NAME + "/" + b.getBarID(), coords);
		}
	}

	private void firePresenter() {
		presenter.updateSearchView(searchBar.getSearchValue());
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.SearchViewInterface#setSearchResults(java.util.List)
	 */
	@Override
	public void setSearchResults(List<SearchElement> elements) {
		this.resultList.removeAllComponents();
		resultList.addComponent(searchBar);
		for(SearchElement element : elements) {
			resultList.addComponent(element);
		}
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.SearchViewInterface#setPresenter(de.kaniba.presenter.SearchPresenterInterface)
	 */
	@Override
	public void setPresenter(SearchPresenterInterface presenter) {
		this.presenter = presenter;

	}

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Suchen...");
		presenter.enter(event);
		checkMapVisibility();
	}

}
