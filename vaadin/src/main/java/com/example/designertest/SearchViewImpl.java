package com.example.designertest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.kaniba.components.BarSearchResult;
import de.kaniba.model.Bar;
import de.kaniba.presenter.SearchPresenter;
import de.kaniba.presenter.Utils;

import com.vaadin.ui.Component.Event;

public class SearchViewImpl extends SearchView implements View{
	public static final String NAME = "search";
	protected GoogleMap map;
	private List<SearchPresenter> presenterList;
	
	public SearchViewImpl() {
		presenterList = new ArrayList<>();
		
		map = createMap();
		mapContainer.addComponent(map);
		UI.getCurrent().getPage().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {

			@Override
			public void browserWindowResized(BrowserWindowResizeEvent event) {
				if(event.getWidth() < 460) {
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
			
			@Override
			public void componentEvent(Event event) {
				firePresenter();
			}
		});
	}
	
	private GoogleMap createMap() {
		GoogleMap map = new GoogleMap("apiKey", null, "german");
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
			
			@Override
			public void markerClicked(GoogleMapMarker clickedMarker) {
				Page.getCurrent().updateLocation("#!bar/" + clickedMarker.getId(), true);
			}
		});
		
		return map;
	}
	
	public void displayBarsOnMap(List<Bar> bars) {
		Collection<GoogleMapMarker> markers = map.getMarkers();
		
		while(!markers.isEmpty()) {
			GoogleMapMarker marker = markers.iterator().next();
			map.removeMarker(marker);
			map.getMarkers();
		}
		
		for(Bar b : bars) {
			LatLon latLon = Utils.getLatLon(b);
			GoogleMapMarker marker = new GoogleMapMarker(b.getName(), latLon, false);
			marker.setId(b.getBarID());
			map.addMarker(marker);
		}
	}
	
	private void firePresenter() {
		for (SearchPresenter presenter : presenterList) {
			presenter.updateSearchView(searchBar.getSearchValue());
		}
	}
	
	public void setSearchResults(List<SearchElement> elements) {
		this.resultList.removeAllComponents();
		resultList.addComponent(searchBar);
		elements.forEach(element -> resultList.addComponent(element));
	}
	
	public void registerPresenter(SearchPresenter presenter) {
		this.presenterList.add(presenter);
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Suchen...");
	}

}
