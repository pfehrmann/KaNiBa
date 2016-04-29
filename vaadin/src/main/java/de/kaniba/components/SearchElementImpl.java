package de.kaniba.components;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.UI;

import de.kaniba.model.Bar;
import de.kaniba.utils.BarUtils;
import de.kaniba.view.BarView;

public class SearchElementImpl extends SearchElement {

	public SearchElementImpl (final Bar bar) {
		addressLabel.setValue(BarUtils.getOneLineAddress(bar));
		nameLabel.setValue(bar.getName());
		
		addStyleName("search-element");
		addLayoutClickListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(BarView.NAME + "/" + bar.getBarID());
			}
		});
	}
	
	/**
	 * A more generic approach.
	 * @param title The title of the element (like the barname)
	 * @param subtitle The subtile (like the address)
	 * @param navigationState The link to navigate to
	 */
	public SearchElementImpl (final String title, final String subtitle, final String navigationState) {
		addressLabel.setValue(subtitle);
		nameLabel.setValue(title);
		
		addStyleName("search-element");
		addLayoutClickListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(navigationState);
			}
		});
	}
}
