package de.kaniba.navigator;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = NavigatorUI.class, productionMode = false)
public class NavigatorUIServlet extends VaadinServlet {
}