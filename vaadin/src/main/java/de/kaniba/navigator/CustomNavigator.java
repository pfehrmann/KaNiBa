package de.kaniba.navigator;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

import de.kaniba.presenter.BarPresenter;
import de.kaniba.presenter.EditBarPresenter;
import de.kaniba.presenter.MyBarsPresenter;
import de.kaniba.presenter.RegisterPresenter;
import de.kaniba.presenter.SearchPresenter;
import de.kaniba.presenter.SuggestionsPresenter;
import de.kaniba.presenter.SurveyPresenter;
import de.kaniba.presenter.UpdateInformationPresenter;
import de.kaniba.view.BarView;
import de.kaniba.view.EditBarView;
import de.kaniba.view.MyBarsView;
import de.kaniba.view.RegisterView;
import de.kaniba.view.SearchView;
import de.kaniba.view.SuggestionsView;
import de.kaniba.view.SurveyView;
import de.kaniba.view.UpdateInformationView;

/**
 * Custom Navigator that sets up everything
 * @author Philipp
 *
 */
public class CustomNavigator extends Navigator {
	private static final long serialVersionUID = 1L;
	
	public CustomNavigator(UI ui, ComponentContainer container) {
		super(ui, container);
		setupNavigator();
	}
	
	private void setupNavigator() {
		SearchView searchView = new SearchView();
		SearchPresenter searchPresenter = new SearchPresenter(searchView);
		super.addView("", searchView);
		super.addView(SearchView.NAME, searchPresenter.getView());

		BarView barView = new BarView();
		BarPresenter barPresenter = new BarPresenter(barView);
		super.addView(BarView.NAME, barPresenter.getView());

		RegisterPresenter registerPresenter = new RegisterPresenter(new RegisterView());
		super.addView(RegisterView.NAME, registerPresenter.getView());

		UpdateInformationPresenter updateInfoPresenter = new UpdateInformationPresenter(new UpdateInformationView());
		super.addView(UpdateInformationView.NAME, updateInfoPresenter.getView());

		SurveyView surveyView = new SurveyView();
		SurveyPresenter surveyPresenter = new SurveyPresenter(surveyView);
		super.addView(SurveyView.NAME, surveyPresenter.getView());

		EditBarView editBarView = new EditBarView();
		EditBarPresenter editBarPresenter = new EditBarPresenter(editBarView);
		super.addView(EditBarView.NAME, editBarPresenter.getView());

		MyBarsView myBarsView = new MyBarsView();
		MyBarsPresenter myBarsPresenter = new MyBarsPresenter(myBarsView);
		super.addView(MyBarsView.NAME, myBarsPresenter.getView());

		SuggestionsView suggestionsView = new SuggestionsView();
		SuggestionsPresenter suggestionsPresenter = new SuggestionsPresenter(suggestionsView);
		super.addView(SuggestionsView.NAME, suggestionsPresenter.getView());
	}

}
