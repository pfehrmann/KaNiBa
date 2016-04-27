package de.kaniba.presenter;

import java.sql.SQLException;
import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;

import de.kaniba.model.Answer;
import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.model.Question;
import de.kaniba.utils.Utils;
import de.kaniba.view.SurveyView;

public class SurveyPresenter {
	private Bar bar;
	private List<Question> questionsForBar;
	private SurveyView view;

	public SurveyPresenter(SurveyView view) {
		this.view = view;
		view.addPresenter(this);
	}

	public void enter(ViewChangeEvent event) {
		bar = Utils.getBarFromParams(event.getParameters());

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
			Utils.exception(e);
		}
		
		if (questionsForBar != null && questionsForBar.isEmpty()) {
			Utils.navigateBack("Keine Fragebögen für diese Bar gefunden :(", Notification.Type.WARNING_MESSAGE);
			return;
		}

		view.displayQuestions(questionsForBar);
	}

	public void submitForm() {
		List<Answer> answers = view.getAnswers();

		if (answers != null) {
			for (Answer answer : answers) {
				answer.saveAnswer();
			}
		}

		Utils.navigateBack("Danke für deine Abstimmung");
	}

}
