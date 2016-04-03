package de.kaniba.components;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

import de.kaniba.model.Bar;

public class BarSearchResult extends CustomComponent {
	public BarSearchResult(Bar b) {
		Layout layout = new VerticalLayout();

		Link name = new Link(b.getName(), new ExternalResource("#!bar/" + b.getBarID()));
		layout.addComponent(name);

		setCompositionRoot(layout);
	}
}
