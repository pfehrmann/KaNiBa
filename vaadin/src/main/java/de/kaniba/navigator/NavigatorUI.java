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

		superLayout.addComponent(layout);
		superLayout.setComponentAlignment(layout, Alignment.TOP_CENTER);
		/*
		 * Die Buttons werden genutzt, um zwischen den Views zu navigieren. Um
		 * von außerhalb auf den Navigator zuzugreifen, kann folgendes
		 * aufgerufen werden: Navigator nav = ((NavigatorUI)
		 * UI.getCurrent()).getNavigator();
		 */

		/* Das Layout für das Menü */
		CssLayout menu = new CssLayout() {
			@Override
			protected String getCss(Component c) {
				return "margin: 10px;";
			}
		};
		layout.addComponent(menu);

		Button lpBtn = new Button("Login", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("");
			}
		});
		menu.addComponent(lpBtn);

		Button bpBtn = new Button("Bar Presenter", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("bar");
			}
		});
		menu.addComponent(bpBtn);

		Button qpBtn = new Button("Questionair 1", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				QuestionPresenter qp = new QuestionPresenter(1);
				navigator.addView("questions", qp.getView());
				navigator.navigateTo("questions");
			}
		});
		menu.addComponent(qpBtn);

		Button rpBtn = new Button("Register", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("register");
			}
		});
		menu.addComponent(rpBtn);

		Button upBtn = new Button("UpdateInfos", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("updateInformation");
			}
		});
		menu.addComponent(upBtn);

		sf = new SearchField();
		sf.addListener(new Listener() {

			@Override
			public void componentEvent(Event event) {
				SearchViewImpl searchView = new SearchViewImpl(sf.getSearchValue());
				navigator.addView("search", searchView);
				navigator.navigateTo("search");
			}
		});
		menu.addComponent(sf);

		/*
		 * Dem layout wird in weiterer Container hinzugefügt, in dem die View
		 * ausgetauscht werden.
		 */
		VerticalLayout cont = new VerticalLayout();
		layout.addComponent(cont);

		Button btn = new Button("Get ScreenWidth");
		btn.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label(UI.getCurrent().getPage().getBrowserWindowWidth() + " x "
						+ UI.getCurrent().getPage().getBrowserWindowHeight()));
			}
		});
		layout.addComponent(btn);

		setContent(superLayout);

		// Navigation einrichten
		navigator = new Navigator(this, cont);

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