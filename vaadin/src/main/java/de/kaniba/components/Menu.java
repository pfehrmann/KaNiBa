package de.kaniba.components;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import de.kaniba.presenter.QuestionPresenter;
import de.kaniba.view.SearchViewImpl;

@SuppressWarnings("serial")
public class Menu extends CustomComponent {

	public Menu(final Navigator navigator) {

		/* Das Layout für das Menü */
		Layout menuLayout = new HorizontalLayout();

		MenuBar mb = new MenuBar();
		//mb.setWidth(100.0f, Unit.PERCENTAGE);
		menuLayout.addComponent(mb);

		MenuItem home = mb.addItem("Login", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo("");
			}
		});
		
		MenuItem barPresenter = mb.addItem("Bar Presenter", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo("bar");
			}
		});
		
		MenuItem questionair = mb.addItem("Questionair 1", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				QuestionPresenter qp = new QuestionPresenter(1);
				navigator.addView("questions", qp.getView());
				navigator.navigateTo("questions");
			}
		});

		MenuItem register = mb.addItem("Register", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo("register");
			}
		});

		MenuItem updateInfos = mb.addItem("UpdateInfos", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo("updateInformation");
			}
		});

		final SearchField sf = new SearchField();
		sf.addListener(new Listener() {

			@Override
			public void componentEvent(Event event) {
				SearchViewImpl searchView = new SearchViewImpl(sf.getSearchValue());
				navigator.addView("search", searchView);
				navigator.navigateTo("search");
			}
		});
		menuLayout.addComponent(sf);

		setCompositionRoot(menuLayout);
	}
}
