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
import de.kaniba.uiInterfaces.SurveyPresenterInterface;
import de.kaniba.uiInterfaces.SurveyViewInterface;

/**
 * This class represents the view of a survey.
 * @author Philipp
 *
 */
public class SurveyView extends SurveyDesign implements SurveyViewInterface {
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

	/* (non-Javadoc)
	 * @see de.kaniba.view.SurveyViewInterface#getAnswers()
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see de.kaniba.view.SurveyViewInterface#setPresenter(de.kaniba.presenter.SurveyPresenterInterface)
	 */
	@Override
	public void setPresenter(SurveyPresenterInterface presenter) {
		this.presenter = presenter;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.SurveyViewInterface#displayQuestions(java.util.List)
	 */
	@Override
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
