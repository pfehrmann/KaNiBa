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
import de.kaniba.utils.LoggingUtils;
import de.kaniba.utils.Utils;
import de.kaniba.view.SurveyInterface;
import de.kaniba.view.SurveyView;

public class SurveyPresenter implements SurveyInterface {
	private Bar bar;
	private List<Question> questionsForBar;
	private SurveyView view;

	public SurveyPresenter(SurveyView view) {
		this.view = view;
		view.addPresenter(this);
	}

	/* (non-Javadoc)
	 * @see de.kaniba.presenter.SurveyInterface#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		bar = Bar.getBarFromParams(event.getParameters());

		if (!Utils.isLoggedIn()) {
			Utils.navigateBack("Du musst eingeloggt sein, um abstimmen zu können.", Notification.Type.WARNING_MESSAGE);
			return;
		}

		if (bar == null) {
			Utils.navigateBack();
			return;
		}

		questionsForBar = null;
		try {
			questionsForBar = Database.getQuestionsForBar(bar.getBarID());
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}
		
		if (questionsForBar != null && questionsForBar.isEmpty()) {
			Utils.navigateBack("Keine Fragebögen für diese Bar gefunden :(", Notification.Type.WARNING_MESSAGE);
			return;
		}

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

		Utils.navigateBack("Danke für deine Abstimmung");
	}

	public View getView() {
		return view;
	}

}
