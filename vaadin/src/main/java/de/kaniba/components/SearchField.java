package de.kaniba.components;

import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

import java.util.ArrayList;
import java.util.List;

public class SearchField extends CustomComponent {
	private TextField searchField;
	private Button searchButton;
	private HorizontalLayout layout;
	private List<Listener> listeners;

	public SearchField() {
		listeners = new ArrayList<Listener>();

		layout = new HorizontalLayout();

		searchField = new TextField();
		searchField.addStyleName("searchField-textField");
		searchField.addShortcutListener(new ShortcutListener(null, KeyCode.ENTER, null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				if (target instanceof TextField) {
					TextField t = (TextField) target;
					if(t.equals(searchField)) {
						fireSearchEvent(null);
					}
				}
			}
		});
		layout.addComponent(searchField);

		searchButton = new Button();
		searchButton.setIcon(FontAwesome.SEARCH);
		searchButton.addStyleName("searchField-Button");
		searchButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				fireSearchEvent(event);
			}
		});
		layout.addComponent(searchButton);
		
		setCompositionRoot(layout);
	}
	
	private void fireSearchEvent(Event event) {
		for(Listener listener : listeners) {
			listener.componentEvent(event);
		}
	}
	
	public String getSearchValue() {
		return searchField.getValue();
	}

	@Override
	public void addListener(Listener listener) {
		super.addListener(listener);
		listeners.add(listener);
	}

	@Override
	public void removeListener(Listener listener) {
		super.removeListener(listener);
		listeners.remove(listener);
	}
}
