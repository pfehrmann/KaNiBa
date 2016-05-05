package de.kaniba.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

import de.kaniba.designs.SurveyDesign;
import de.kaniba.model.Answer;
import de.kaniba.model.InternalUser;
import de.kaniba.model.Question;
import de.kaniba.presenter.SurveyPresenter;
import de.kaniba.presenter.SurveyPresenterInterface;

/**
 * This class represents the view of a survey.
 * @author Philipp
 *
 */
public class SurveyView extends SurveyDesign implements SecuredView {
	private static final long serialVersionUID = 1L;

	/**
	 * The name of the view. The view should be registered in the navigator with
	 * this name.
	 */
	public static final String NAME = "survey";

	private SurveyPresenterInterface presenter;
	private List<QuestionElement> questionElements;

	/**
	 * Creates the view. All the clicklisteners are created.
	 */
	public SurveyView() {
		submitButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.submitForm();
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		presenter.enter(event);
	}

	/**
	 * Returns a list of answers supplied by the user. If no answers was given,
	 * the default value is returned.
	 * 
	 * @return Returns a list of answers.
	 */
	public List<Answer> getAnswers() {
		List<Answer> answers = new ArrayList<>();
		InternalUser user = InternalUser.getUser();

		if (user == null) {
			return answers;
		}

		for (QuestionElement questionElement : questionElements) {
			Question question = questionElement.getQuestion();
			Object value = questionElement.getComponent().getValue();

			Answer answer = new Answer(question);
			answer.setUserID(user.getUserID());

			if (answer.isText()) {
				answer.setAnswerBool(false);
				answer.setAnswerString((String) value);
			} else {
				answer.setAnswerBool((boolean) value);
				answer.setAnswerString("");
			}
			answers.add(answer);
		}
		displayQuestions(null);
		return answers;
	}

	/**
	 * This adds a presenter to the view. Note that having multiple presenters
	 * is possible, but not suggested. Contradictory actions can lead to
	 * confusing results.
	 * 
	 * @param presenter
	 *            The presenter to add. The presenters are called on enter and
	 *            on button clicks.
	 */
	public void setPresenter(SurveyPresenterInterface presenter) {
		this.presenter = presenter;
	}

	/**
	 * Shows the list of questions supplied as questions. The questions will be
	 * automatically displayed as text fields or as checkboxes, depending on the
	 * type of question. To retireve the answers, call getAnswers().
	 * 
	 * @param questions
	 *            The questions to display
	 */
	public void displayQuestions(List<Question> questions) {
		// Check for null
		if (questions == null) {
			return;
		}

		this.questionElements = new ArrayList<>();
		questionContainer.removeAllComponents();

		for (Question question : questions) {
			// check for null
			if (question == null) {
				continue;
			}

			QuestionElement questionElement;

			AbstractField field = createComponent(question);
			questionElement = new QuestionElement(question, field);
			questionContainer.addComponent(field);
			questionElements.add(questionElement);
		}
	}

	/**
	 * Creates a components for the question.
	 * 
	 * @param question
	 *            The question to display
	 * @return Returns the needed component.
	 */
	private static AbstractField createComponent(Question question) {
		String caption = question.getMessage();

		AbstractField field;
		if (question.isText()) {
			field = new TextField(caption);
		} else {
			field = new CheckBox(caption);
		}

		field.setResponsive(true);
		field.setWidth("100%");

		return field;
	}

	@Override
	public boolean checkRights(String parameters) {
		return presenter.checkRights(parameters);
	}
}
