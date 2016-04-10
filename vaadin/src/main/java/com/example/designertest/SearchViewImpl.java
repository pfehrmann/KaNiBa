package com.example.designertest;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.ui.UI;

import de.kaniba.presenter.SearchPresenter;

import com.vaadin.ui.Component.Event;

public class SearchViewImpl extends SearchView implements View{
	protected GoogleMap map;
	private List<SearchPresenter> presenterList;
	
	public SearchViewImpl() {
		presenterList = new ArrayList<>();
		
		map = new GoogleMap("apiKey", null, "german");
		map.setSizeFull();
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
