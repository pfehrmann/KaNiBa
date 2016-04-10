package com.example.designertest;

import java.io.File;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("mytheme")
public class DesignertestUI extends UI {

	@WebServlet(value = "/VaadinDesigner/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DesignertestUI.class, widgetset = "de.kaniba.vaadin.MyAppWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		TestDesign design = new TestDesign();
		GoogleMap map = new GoogleMap("", null, "german");
		map.setSizeFull();
		design.content.addComponent(map);
		
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/logo.png"));
		design.logo.setSource(resource);
		
		setContent(design);
		
		Navigator navigator = new Navigator(this, design.content);
		navigator.addView("", new BarViewImpl());
		
		navigator.addView("bar", new BarViewImpl());
	}

}