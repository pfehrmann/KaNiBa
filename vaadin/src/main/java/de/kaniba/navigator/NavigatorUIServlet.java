package de.kaniba.navigator;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import javax.servlet.ServletException;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;

@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = NavigatorUI.class, productionMode = false)
public class NavigatorUIServlet extends VaadinServlet {

	@Override
	protected void servletInitialized() throws ServletException {
		getService().addSessionInitListener(new SessionInitListener() {
			@Override
			public void sessionInit(final SessionInitEvent event) {
				event.getSession().addBootstrapListener(new BootstrapListener() {

					@Override
					public void modifyBootstrapPage(BootstrapPageResponse response) {

						// Add viewport meta tag
						Attributes attr = new Attributes();
						attr.put("name", "viewport");
						attr.put("content", "width=device-width, initial-scale=1");
						response.getDocument().head().appendChild(new Element(Tag.valueOf("meta"), "", attr));

					}

					@Override
					public void modifyBootstrapFragment(BootstrapFragmentResponse response) {
					}
				});
			}
		});
		super.servletInitialized();
	}
}