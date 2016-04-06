package com.example.designertest;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.ui.UI;

public class SearchViewImpl extends SearchView implements View{
	
	public SearchViewImpl() {
		GoogleMap map = new GoogleMap("apiKey", null, "german");
		map.setSizeFull();
		mapContainer.addComponent(map);
		UI.getCurrent().getPage().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {

			@Override
			public void browserWindowResized(BrowserWindowResizeEvent event) {
				if(event.getWidth() < 460) {
					mapContainer.removeComponent(map);
				} else {
					mapContainer.addComponent(map);
				}
				
			}

		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
