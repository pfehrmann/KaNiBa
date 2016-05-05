package de.kaniba.navigator;

import java.io.File;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import de.kaniba.presenter.BarPresenter;
import de.kaniba.presenter.EditBarPresenter;
import de.kaniba.presenter.MyBarsPresenter;
import de.kaniba.presenter.RegisterPresenter;
import de.kaniba.presenter.SearchPresenter;
import de.kaniba.presenter.SurveyPresenter;
import de.kaniba.presenter.UpdateInformationPresenter;
import de.kaniba.utils.NavigationUtils;
import de.kaniba.view.BarView;
import de.kaniba.view.EditBarView;
import de.kaniba.view.MyBarsView;
import de.kaniba.view.RegisterView;
import de.kaniba.view.SearchView;
import de.kaniba.view.SurveyView;
import de.kaniba.view.UpdateInformationView;

/**
 * The main UI
 */
@Theme("mytheme")
@Widgetset("de.kaniba.vaadin.MyAppWidgetset")
@PreserveOnRefresh
public class NavigatorUI extends UI {
	private static final long serialVersionUID = 1L;
	
	private FrameDesign design;
	
	/**
	 * Create the navigator ui
	 */
	public NavigatorUI() {
		super();
	}
	
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		// Use the new design
		design = new FrameDesign();
		
		// Add the logo to the menu
		// TODO: Fix menu logo width and position
		// TODO: Fix the responsive menu behavior
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/logo.png"));
		design.logo.setSource(resource);
		
		// Make sure, that the design is used as the content
		setContent(design);
		
		// Create the Navigator and set it up
		Navigator navigator = new Navigator(this, design.content);
		navigator.addViewChangeListener(NavigationUtils.viewChangeListener);
		
		SearchView searchView = new SearchView();
		SearchPresenter searchPresenter = new SearchPresenter(searchView);
		navigator.addView("", searchView);
		navigator.addView(SearchView.NAME, searchPresenter.getView());
		
		BarView barView = new BarView();
		BarPresenter barPresenter = new BarPresenter(barView);
		navigator.addView(BarView.NAME, barPresenter.getView());

		RegisterPresenter registerPresenter = new RegisterPresenter(new RegisterView());
		navigator.addView(RegisterView.NAME, registerPresenter.getView());

		UpdateInformationPresenter updateInfoPresenter = new UpdateInformationPresenter(new UpdateInformationView());
		navigator.addView(UpdateInformationView.NAME, updateInfoPresenter.getView());
		
		SurveyView surveyView = new SurveyView();
		SurveyPresenter surveyPresenter = new SurveyPresenter(surveyView);
		navigator.addView(SurveyView.NAME, surveyPresenter.getView());
		
		EditBarView editBarView = new EditBarView();
		EditBarPresenter editBarPresenter = new EditBarPresenter(editBarView);
		navigator.addView(EditBarView.NAME, editBarPresenter.getView());
		
		MyBarsView myBarsView = new MyBarsView();
		MyBarsPresenter myBarsPresenter = new MyBarsPresenter(myBarsView);
		navigator.addView(MyBarsView.NAME, myBarsPresenter.getView());
	}
	
	public void setMenu(Component menu) {
		design.menuContainer.removeAllComponents();
		design.menuContainer.addComponent(menu);
	}
}
