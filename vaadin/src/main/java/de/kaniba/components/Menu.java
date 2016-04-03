package de.kaniba.components;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import de.kaniba.model.User;
import de.kaniba.presenter.FindBarPresenter;
import de.kaniba.presenter.LoginPresenter;
import de.kaniba.presenter.QuestionPresenter;
import de.kaniba.view.FindBarImpl;
import de.kaniba.view.LoginView;
import de.kaniba.view.LoginViewImpl;
import de.kaniba.view.SearchViewImpl;

@SuppressWarnings("serial")
public class Menu extends CustomComponent {

	public Menu(final Navigator navigator) {

		/* Das Layout für das Menü */
		Layout menuLayout = new HorizontalLayout();
		menuLayout.addStyleName("menu-bar");

		MenuBar mb = new MenuBar();
		//mb.setWidth(100.0f, Unit.PERCENTAGE);
		menuLayout.addComponent(mb);
		
		MenuItem findBar = mb.addItem("BarFinder", null, new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo(FindBarPresenter.NAME);
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
		
		MenuItem home = mb.addItem("Login", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				showLogin();
			}
		});

		setCompositionRoot(menuLayout);
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
