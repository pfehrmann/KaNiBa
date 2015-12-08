package de.kaniba.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.navigator.NavigatorUI;
import de.kaniba.presenter.BarPresenter;

public class SearchViewImpl extends CustomComponent implements SerachView, Button.ClickListener {
	List<Bar> bars;
	List<SearchViewListener> listeners;
	String searchString;

	public SearchViewImpl(String searchString) {
		listeners = new ArrayList<SearchViewListener>();

		Panel mainPanel = new Panel();
		VerticalLayout cont = new VerticalLayout();

		this.searchString = searchString;
		try {
			bars = Database.searchForBar(searchString);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Bar b : bars) {
			HorizontalLayout barLayout = new HorizontalLayout();
			barLayout.addComponent(new Label(b.getName()));
			Button link = new Button("Bar ansehen");
			link.setData(b);
			link.addClickListener(this);
			barLayout.addComponent(link);
			cont.addComponent(barLayout);
		}

		mainPanel.setContent(cont);
		setCompositionRoot(mainPanel);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		UI.getCurrent().getPage().setTitle("Suche");
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Bar b = ((Bar)((Button) event.getSource()).getData());
		BarPresenter bp = new BarPresenter(b, new BarViewImpl());
		((NavigatorUI) UI.getCurrent()).getNavigator().addView("bar", bp.getView());
		((NavigatorUI) UI.getCurrent()).getNavigator().navigateTo("bar");
	}

	@Override
	public void addListener(SearchViewListener listener) {
		listeners.add(listener);

	}

}
