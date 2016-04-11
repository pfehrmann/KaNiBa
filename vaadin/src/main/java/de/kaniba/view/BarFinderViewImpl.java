package de.kaniba.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.thirdparty.guava.common.collect.MapMaker;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.GoogleMapControl;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MarkerClickListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.Component.Listener;

import de.kaniba.components.BarSearchResult;
import de.kaniba.components.SearchField;
import de.kaniba.model.Bar;
import de.kaniba.navigator.NavigatorUI;
import de.kaniba.presenter.BarPresenter;
import de.kaniba.presenter.Utils;

public class BarFinderViewImpl extends CustomComponent implements BarFinderView {
	private List<BarFinderViewListener> listeners;
	
	private GoogleMap map;
	private Panel searchResultsPanel;
	private List<GoogleMapMarker> markers;
	private SearchField searchField;
	private Panel mapPanel;
	
	public BarFinderViewImpl() {
		markers = new ArrayList<>();
		listeners = new ArrayList<>();
		
		GridLayout layout = new GridLayout(2,1);
		layout.setColumnExpandRatio(1, 1.0f);
		layout.setWidth("100%");
		layout.setHeight("100%");
		layout.setSpacing(true);
		
		Layout left = new VerticalLayout();
		left.addComponent(createSearchBar());
		left.addComponent(createSearchResultPanel());
		left.setWidth(searchField.getWidth(), searchField.getWidthUnits());
		layout.addComponent(left);
		
		mapPanel = createMapPanel();
		layout.addComponent(mapPanel);
		
		Panel panel = new Panel();
		panel.setContent(layout);
		setCompositionRoot(panel);
	}
	
	private SearchField createSearchBar() {
		searchField = new SearchField();
		searchField.addListener(new Listener() {
			
			@Override
			public void componentEvent(Event event) {
				for(BarFinderViewListener listener : listeners) {
					listener.searchBar(searchField.getSearchValue());
				}
			}
		});

		return searchField;
	}
	
	private Panel createSearchResultPanel() {
		searchResultsPanel = new Panel();
		searchResultsPanel.setId("find-bar-view-search-results-panel");
		searchResultsPanel.addStyleName("borderless");
		
		return searchResultsPanel;
	}
	
	private Panel createMapPanel() {
		Panel panel = new Panel();

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
		map.setHeight("600px");
		
		Collection<GoogleMapMarker> markers = map.getMarkers();
		for (GoogleMapMarker marker : markers) {
			map.removeMarker(marker);
		}
		
		map.addMarkerClickListener(new MarkerClickListener() {
			
			@Override
			public void markerClicked(GoogleMapMarker clickedMarker) {
				Page.getCurrent().updateLocation("#!bar/" + clickedMarker.getId(), true);
			}
		});

		panel.setContent(map);
		return panel;
	}
	
	@Override
	public void displayBars(List<Bar> bars) {
		Layout layout = new VerticalLayout();
		for (GoogleMapMarker m : markers) {
			map.removeMarker(m);
		}
		
		for(Bar b : bars) {
			layout.addComponent(new BarSearchResult(b));
			LatLon latLon = Utils.getLatLon(b);
			GoogleMapMarker marker = new GoogleMapMarker(b.getName(), latLon, false);
			marker.setId(b.getBarID());
			markers.add(marker);
			map.addMarker(marker);
		}
		searchResultsPanel.setContent(layout);
	}

	@Override
	public void addListener(BarFinderViewListener listener) {
		listeners.add(listener);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		UI.getCurrent().getPage().setTitle("BarFinder");
	}
}
