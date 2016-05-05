package de.kaniba.view;

import java.util.Collection;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.GoogleMapControl;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MarkerClickListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.UI;

import de.kaniba.components.SearchElement;
import de.kaniba.designs.SearchDesign;
import de.kaniba.model.Bar;
import de.kaniba.presenter.SearchPresenterInterface;

/**
 * The View for Searching
 * @author Philipp
 *
 */
public class SearchView extends SearchDesign implements View {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "search";
	protected GoogleMap map;
	private SearchPresenterInterface presenter;

	public SearchView() {
		map = createMap();
		mapContainer.addComponent(map);
		UI.getCurrent().getPage().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void browserWindowResized(BrowserWindowResizeEvent event) {
				if (event.getWidth() < 460) {
					mapContainer.removeComponent(map);
					resultList.setWidth("100%");
					mapContainer.setWidth("0%");
				} else {
					mapContainer.addComponent(map);
					resultList.setWidth("20%");
					mapContainer.setWidth("80%");
				}

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

	private GoogleMap createMap() {
		map = new GoogleMap("apiKey", null, "german");
		map.setCenter(new LatLon(49.0068901, 8.4036527));
		map.setSizeFull();
		map.setZoom(12);
		map.setMinZoom(4);
		map.setMaxZoom(18);
		map.removeControl(GoogleMapControl.MapType);
		map.removeControl(GoogleMapControl.Pan);
		map.removeControl(GoogleMapControl.Rotate);
		map.removeControl(GoogleMapControl.Zoom);
		map.removeControl(GoogleMapControl.StreetView);
		map.removeControl(GoogleMapControl.Scale);

		Collection<GoogleMapMarker> markers = map.getMarkers();
		for (GoogleMapMarker marker : markers) {
			map.removeMarker(marker);
		}

		map.addMarkerClickListener(new MarkerClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void markerClicked(GoogleMapMarker clickedMarker) {
				Page.getCurrent().updateLocation("#!bar/" + clickedMarker.getId(), true);
			}
		});

		return map;
	}

	/**
	 * Sets the markers for the bars on the map
	 * @param bars
	 */
	public void displayBarsOnMap(List<Bar> bars) {
		Collection<GoogleMapMarker> markers = map.getMarkers();

		while (!markers.isEmpty()) {
			GoogleMapMarker marker = markers.iterator().next();
			map.removeMarker(marker);
			map.getMarkers();
		}

		for (Bar b : bars) {
			LatLon latLon = b.getLatLon();
			GoogleMapMarker marker = new GoogleMapMarker(b.getName(), latLon, false);
			marker.setId(b.getBarID());
			map.addMarker(marker);
		}
	}

	private void firePresenter() {
		presenter.updateSearchView(searchBar.getSearchValue());
	}

	public void setSearchResults(List<SearchElement> elements) {
		this.resultList.removeAllComponents();
		resultList.addComponent(searchBar);
		for(SearchElement element : elements) {
			resultList.addComponent(element);
		}
	}

	/**
	 * Add an presenter to the list of presenters. It will be called on various events.
	 * @param presenter
	 */
	public void setPresenter(SearchPresenterInterface presenter) {
		this.presenter = presenter;

	}

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Suchen...");
		presenter.enter(event);
	}

}
