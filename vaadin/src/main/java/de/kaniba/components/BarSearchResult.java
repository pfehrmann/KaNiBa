package de.kaniba.components;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

import de.kaniba.model.Bar;

/**
 * This class represents a result in the search view.
 * 
 * @author Philipp
 */
public class BarSearchResult extends CustomComponent {
	private static final long serialVersionUID = 1L;

	/**
	 * Sets everything up.
	 * @param bar The bar of the link.
	 */
	public BarSearchResult(Bar bar) {
		Layout layout = new VerticalLayout();

		Link name = new Link(bar.getName(), new ExternalResource("#!bar/" + bar.getBarID()));
		layout.addComponent(name);
		
		super.setId("result-" + bar.getBarID());

		super.setCompositionRoot(layout);
	}
}
