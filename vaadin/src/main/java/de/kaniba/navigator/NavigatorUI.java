package de.kaniba.navigator;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;

import de.kaniba.components.Menu;
import de.kaniba.components.SearchField;
import de.kaniba.model.Bar;
import de.kaniba.model.User;
import de.kaniba.presenter.BarPresenter;
import de.kaniba.presenter.LoginPresenter;
import de.kaniba.presenter.QuestionPresenter;
import de.kaniba.presenter.RegisterPresenter;
import de.kaniba.presenter.UpdateInformationPresenter;
import de.kaniba.view.BarViewImpl;
import de.kaniba.view.LoginView;
import de.kaniba.view.LoginViewImpl;
import de.kaniba.view.RegisterView;
import de.kaniba.view.RegisterViewImpl;
import de.kaniba.view.SearchViewImpl;
import de.kaniba.view.UpdateInformationVeiwImpl;
import de.kaniba.view.UpdateInformationView;

/**
 *
 */
@Theme("mytheme")
@Widgetset("de.kaniba.vaadin.MyAppWidgetset")
@PreserveOnRefresh
public class NavigatorUI extends UI {
	private Navigator navigator;
	private TextField searchField;
	private SearchField sf;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		Page.getCurrent().setTitle("KaNiBa");

		/**
		 * Das layout beiinhaltet alle Elemente
		 */
		final VerticalLayout superLayout = new VerticalLayout();

		final CssLayout layout = new CssLayout() {
			@Override
			protected String getCss(Component c) {
				return "margin: 10px;";
			}
		};

		layout.setWidth("750px");
		layout.addStyleName("main-wrapper");

		/*
		 * Dem layout wird in weiterer Container hinzugefügt, in dem die View
		 * ausgetauscht werden.
		 */
		VerticalLayout cont = new VerticalLayout();

		setContent(superLayout);

		/*
		 * Navigation einrichten
		 * 
		 * Um von außerhalb auf den Navigator zuzugreifen, kann folgendes
		 * aufgerufen werden: Navigator nav = ((NavigatorUI)
		 * UI.getCurrent()).getNavigator();
		 */
		navigator = new Navigator(this, cont);

		// Die komponenten werden erst hier hinzugefügt, da ansonsten
		// nullpointer exceptions auftreten
		Menu menu = new Menu(navigator);
		superLayout.addComponent(menu);
		superLayout.addComponent(layout);
		superLayout.setComponentAlignment(layout, Alignment.TOP_CENTER);
		layout.addComponent(cont);

		/*
		 * Die Presenter werden initialisiert und Die Views werden zum Navigator
		 * hinzugefügt. Der View mit dem namen "" ist der view, der am Anfang
		 * angezeigt wird. Über die namen kann zwischen den Views navigiert
		 * werden.
		 */
		LoginPresenter lp = new LoginPresenter(new User(), new LoginViewImpl());
		navigator.addView("", lp.getView());
		navigator.addView(LoginView.NAME, lp.getView());

		BarPresenter bp = null;
		try {
			bp = new BarPresenter(new Bar(1), new BarViewImpl());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		navigator.addView("bar", bp.getView());

		RegisterPresenter rp = new RegisterPresenter(new User(), new RegisterViewImpl());
		navigator.addView(RegisterView.NAME, rp.getView());

		UpdateInformationPresenter up = new UpdateInformationPresenter(new UpdateInformationVeiwImpl());
		navigator.addView(UpdateInformationView.NAME, up.getView());
	}

	public Navigator getNavigator() {
		return navigator;
	}
}