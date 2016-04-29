package de.kaniba.components;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.UI;

import de.kaniba.model.Bar;
import de.kaniba.utils.BarUtils;
import de.kaniba.view.BarView;

/**
 * An Element of a search.
 * 
 * @author Philipp
 *
 */
public class SearchElementImpl extends SearchElement {
	private static final long serialVersionUID = 1L;

	/**
	 * Setup the element and take all the information from a bar element.
	 * Clicking this element will navigate to that bars page.
	 * 
	 * @param bar
	 */
	public SearchElementImpl(final Bar bar) {
		this(bar.getName(), BarUtils.getOneLineAddress(bar), BarView.NAME + "/" + bar.getBarID());
	}

	/**
	 * A more generic approach.
	 * 
	 * @param title
	 *            The title of the element (like the barname)
	 * @param subtitle
	 *            The subtile (like the address)
	 * @param navigationState
	 *            The link to navigate to
	 */
	public SearchElementImpl(final String title, final String subtitle, final String navigationState) {
		addressLabel.setValue(subtitle);
		nameLabel.setValue(title);

		super.addStyleName("search-element");
		super.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(navigationState);
			}
		});
	}
}
