package com.example.designertest;

import java.io.File;

import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

public class BarViewImpl extends BarView implements View {
	
	public BarViewImpl () {
		System.out.println(getComponent(0,0).getId());
		removeComponent(leftGrid);
		addComponent(leftGrid, 0,0,0,1);
		
		leftGrid.removeComponent(barImage);
		leftGrid.addComponent(barImage, 0, 0, 0, 1);
		leftGrid.removeComponent(infoPanel);
		leftGrid.addComponent(infoPanel, 0, 2, 1, 2);
		leftGrid.removeComponent(messageAreaGrid);
		leftGrid.addComponent(messageAreaGrid, 0, 3, 1, 3);
		messageAreaGrid.removeComponent(messagePanel);
		messageAreaGrid.addComponent(messagePanel, 0, 0, 1, 0);
		
		infoPanel.setContent(new Label("Hallo, wir sind das St&ouml;vchen. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."));
		GoogleMap googleMap = new GoogleMap("apiKey", null, "german");
		googleMap.setSizeFull();
		setRowExpandRatio(0, 1.0f);
		addComponent(googleMap, 1, 0);
		
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/stoevchen.png"));
		barImage.setHeightUndefined();
		barImage.setSource(resource);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
