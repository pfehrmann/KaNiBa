package de.kaniba.presenter;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;

import de.kaniba.model.Answer;
import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.model.Question;
import de.kaniba.model.User;
import de.kaniba.utils.LoggingUtils;
import de.kaniba.utils.NavigationUtils;
import de.kaniba.view.SurveyView;

/**
 * Presenter to control the  SurveyView
 * @author Philipp
 *
 */
public class SurveyPresenter implements SurveyPresenterInterface {
	private static final long serialVersionUID = 1L;
	
	private Bar bar;
	private List<Question> questionsForBar;
	private SurveyView view;

	public SurveyPresenter(SurveyView view) {
		this.view = view;
		view.setPresenter(this);
	}

	/* (non-Javadoc)
	 * @see de.kaniba.presenter.SurveyInterface#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		view.displayQuestions(questionsForBar);
	}

	/* (non-Javadoc)
	 * @see de.kaniba.presenter.SurveyInterface#submitForm()
	 */
	@Override
	public void submitForm() {
		List<Answer> answers = view.getAnswers();

		if (answers != null) {
			for (Answer answer : answers) {
				answer.saveAnswer();
			}
		}

		NavigationUtils.navigateBack("Danke für deine Abstimmung");
	}

	public View getView() {
		return view;
	}

	@Override
	public boolean checkRights(String parameters) {
		bar = Bar.getBarFromParams(parameters);

		if (!User.isLoggedIn()) {
			NavigationUtils.navigateBack("Du musst eingeloggt sein, um abstimmen zu können.",
					Notification.Type.WARNING_MESSAGE);
			return false;
		}

		if (bar == null) {
			NavigationUtils.navigateBack("Bar nicht gefunden.");
			return false;
		}

		questionsForBar = null;
		try {
			questionsForBar = Database.getQuestionsForBar(bar.getBarID());
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}
		
		if (questionsForBar == null || questionsForBar.isEmpty()) {
			NavigationUtils.navigateBack("Keine Fragebögen für diese Bar gefunden :(", 
					Notification.Type.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

}
