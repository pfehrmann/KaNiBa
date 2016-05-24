package de.kaniba.components;

import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the search field
 * 
 * @author phili
 *
 */
public class SearchField extends CustomComponent {
	private static final long serialVersionUID = 1L;
	private TextField searchTextField;
	private Button searchButton;
	private GridLayout layout;
	private List<Listener> listeners;

	/**
	 * Initializes the search field and sets up all the listeners
	 */
	public SearchField() {
		listeners = new ArrayList<>();

		layout = new GridLayout(2, 1);
		layout.setWidth("100%");
		layout.setId("search-bar-layout");

		searchTextField = new TextField();
		searchTextField.setWidth("100%");
		searchTextField.addStyleName("searchField-textField");

		searchTextField.addShortcutListener(new ShortcutListener(null, KeyCode.ENTER, null) {

			private static final long serialVersionUID = -8536048045745120836L;

			@Override
			public void handleAction(Object sender, Object target) {
				if (target instanceof TextField) {
					TextField t = (TextField) target;
					if (t.equals(searchTextField)) {
						fireSearchEvent(null);
					}
				}
			}
		});
		layout.addComponent(searchTextField);
		layout.setColumnExpandRatio(0, 1.0f);

		searchButton = new Button();
		searchButton.setIcon(FontAwesome.SEARCH);
		searchButton.addStyleName("searchField-Button");
		searchButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -7434207392769531174L;

			@Override
			public void buttonClick(ClickEvent event) {
				fireSearchEvent(event);
			}
		});
		layout.addComponent(searchButton);

		setCompositionRoot(layout);
	}

	private void fireSearchEvent(Event event) {
		for (Listener listener : listeners) {
			listener.componentEvent(event);
		}
	}

	/**
	 * Returns the entered search vale
	 * 
	 * @return
	 */
	public String getSearchValue() {
		return searchTextField.getValue();
	}

	/**
	 * Add a listener. This listener will be fired when either be button is
	 * clicked or enter is pressed
	 * 
	 * @param listener
	 */
	public void addSearchListener(Listener listener) {
		super.addListener(listener);
		listeners.add(listener);
	}
}
