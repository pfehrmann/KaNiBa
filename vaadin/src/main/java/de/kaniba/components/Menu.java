package de.kaniba.components;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import de.kaniba.model.User;
import de.kaniba.navigator.NavigatorUI;
import de.kaniba.presenter.BarFinderPresenter;
import de.kaniba.presenter.LoginPresenter;
import de.kaniba.presenter.QuestionPresenter;
import de.kaniba.view.LoginViewImpl;

@SuppressWarnings("serial")
public class Menu extends CustomComponent {

	public Menu(final Navigator navigator) {
		MenuBar menuBar = new MenuBar();
		menuBar.setId("menu-bar");

		MenuItem findBar = menuBar.addItem("BarFinder", null, new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo(BarFinderPresenter.NAME);
			}
		});

		MenuItem questionair = menuBar.addItem("Questionair 1", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				QuestionPresenter qp = new QuestionPresenter(1);
				navigator.addView("questions", qp.getView());
				navigator.navigateTo("questions");
			}
		});

		MenuItem register = menuBar.addItem("Register", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo("register");
			}
		});

		MenuItem updateInfos = menuBar.addItem("UpdateInfos", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				VaadinSession session = ((NavigatorUI) UI.getCurrent()).getSession();
				Object loggedInObj = session.getAttribute("loggedIn");
				boolean loggedIn = false;
				if (loggedInObj != null) {
					loggedIn = (boolean) loggedInObj;
				}

				if (!loggedIn) {
					Notification.show("Zum ändern der deiner Daten musst du eingeloggt sein.",
							Notification.Type.WARNING_MESSAGE);
					showLogin();
				} else {
					navigator.navigateTo("updateInformation");
				}
			}
		});

		MenuItem login = menuBar.addItem("Login", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				showLogin();
			}
		});

		setCompositionRoot(menuBar);
	}

	public static void showLogin() {
		Window loginWindow = new Window("Login");
		LoginPresenter lp = new LoginPresenter(new User(), new LoginViewImpl());
		loginWindow.setContent((CustomComponent) lp.getView());
		loginWindow.setModal(true);
		loginWindow.setDraggable(false);
		loginWindow.setResizable(false);
		UI.getCurrent().addWindow(loginWindow);
	}
}
