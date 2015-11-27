package de.kaniba.model;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import de.kaniba.presenter.BarPresenter;
import de.kaniba.presenter.LoginPresenter;
import de.kaniba.view.BarViewImpl;
import de.kaniba.view.LoginViewImpl;

/**
 *
 */
@Theme("mytheme")
@Widgetset("de.kaniba.vaadin.MyAppWidgetset")
@PreserveOnRefresh
public class MyUI extends UI {
	private Navigator navigator;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		/**
		 * Das layout beiinhaltet alle Elemente
		 */
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);

		/*
		 * Die Buttons werden genutzt, um zwischen den Views zu navigieren.
		 * Um von außerhalb auf den Navigator zuzugreifen, kann folgendes aufgerufen werden:
		 * Navigator nav = ((MyUI) UI.getCurrent()).getNavigator();
		 */
		Button lpBtn = new Button("Login", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("");
			}
		});
		layout.addComponent(lpBtn);

		Button bpBtn = new Button("Bar Presenter", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("bar");
			}
		});
		layout.addComponent(bpBtn);
		
		/*
		 * Dem layout wird in weiterer Container hinzugefügt, in dem die View ausgetauscht werden.
		 */
		VerticalLayout cont = new VerticalLayout();
		layout.addComponent(cont);

		setContent(layout);

		/*
		 * Die Presenter werden initialisiert
		 */
		LoginPresenter lp = new LoginPresenter(new User(), new LoginViewImpl());
		BarPresenter bp = new BarPresenter(new BarViewImpl());

		/*
		 * Die Views werden zum Navigator hinzugefügt.
		 * Der View mit dem namen "" ist der view, der am Anfang angezeigt wird.
		 * Über die namen kann zwischen den Views navigiert werden.
		 */
		navigator = new Navigator(this, cont);
		navigator.addView("", lp.getView());
		navigator.addView("bar", bp.getView());
	}
	
	public Navigator getNavigator() {
		return navigator;
	}

	/*
	 * @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported =
	 * true)
	 * 
	 * @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	 * public static class MyUIServlet extends VaadinServlet { }
	 */
}
