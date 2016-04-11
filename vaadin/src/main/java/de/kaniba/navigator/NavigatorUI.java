package de.kaniba.navigator;

import java.io.File;

import com.example.designertest.BarView;
import com.example.designertest.SearchViewImpl;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import de.kaniba.components.LoginPopup;
import de.kaniba.model.User;
import de.kaniba.presenter.BarPresenter;
import de.kaniba.presenter.BarFinderPresenter;
import de.kaniba.presenter.RegisterPresenter;
import de.kaniba.presenter.SearchPresenter;
import de.kaniba.presenter.UpdateInformationPresenter;
import de.kaniba.view.BarViewImpl;
import de.kaniba.view.BarFinderViewImpl;
import de.kaniba.view.RegisterView;
import de.kaniba.view.RegisterViewImpl;
import de.kaniba.view.UpdateInformationVeiwImpl;
import de.kaniba.view.UpdateInformationView;

/**
 *
 */
@Theme("mytheme")
@Widgetset("de.kaniba.vaadin.MyAppWidgetset")
@PreserveOnRefresh
public class NavigatorUI extends UI {
	private TestDesign design;
	
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		// Use the new design
		design = new TestDesign();
		
		// Add the logo to the menu
		// TODO: Fix menu logo width and position
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/logo.png"));
		design.logo.setSource(resource);
		
		// Make sure, that the design is used as the content
		setContent(design);
		
		// Create the Navigator and set it up
		Navigator navigator = new Navigator(this, design.content);
		
		SearchViewImpl searchView = new SearchViewImpl();
		SearchPresenter searchPresenter = new SearchPresenter(searchView);
		navigator.addView("", searchView);
		navigator.addView(searchView.NAME, searchView);
		
		com.example.designertest.BarPresenter barPresenter = new com.example.designertest.BarPresenter();
		navigator.addView("bar", barPresenter.getView());
		
		/*
		 * Die Presenter werden initialisiert und Die Views werden zum Navigator
		 * hinzugefügt. Der View mit dem namen "" ist der view, der am Anfang
		 * angezeigt wird. Über die namen kann zwischen den Views navigiert
		 * werden.
		 */
		
		// TODO: Die Presenter updatene, müssen mit den neuen versionen zusammenspielen!!!
		//BarPresenter bp = null;
		//bp = new BarPresenter(new BarViewImpl());
		//navigator.addView("bar", bp.getView());

		RegisterPresenter rp = new RegisterPresenter(new User(), new RegisterViewImpl());
		navigator.addView(RegisterView.NAME, rp.getView());

		UpdateInformationPresenter up = new UpdateInformationPresenter(new UpdateInformationVeiwImpl());
		navigator.addView(UpdateInformationView.NAME, up.getView());
	}
	
	public void setMenu(Component menu) {
		design.menuContainer.removeAllComponents();
		design.menuContainer.addComponent(menu);
	}
}