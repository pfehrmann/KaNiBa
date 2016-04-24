package de.kaniba.components;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.UI;

import de.kaniba.model.Bar;
import de.kaniba.presenter.Utils;
import de.kaniba.view.BarView;

public class SearchElementImpl extends SearchElement {

	public SearchElementImpl (final Bar bar) {
		addressLabel.setValue(Utils.getOneLineAddress(bar));
		nameLabel.setValue(bar.getName());
		
		addStyleName("search-element");
		addLayoutClickListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(BarView.NAME + "/" + bar.getBarID());
			}
		});
	}
}
