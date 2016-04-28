package de.kaniba.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
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

public class SurveyView extends SurveyDesign implements View {
	public static final String NAME = "survey";

	private List<SurveyPresenter> presenters;
	private List<QuestionElement> questionElements;

	public SurveyView() {
		this.presenters = new ArrayList<>();

		submitButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				for (SurveyPresenter presenter : presenters) {
					presenter.submitForm();
				}
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		for (SurveyPresenter presenter : presenters) {
			presenter.enter(event);
		}
	}

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

		return answers;
	}

	public void addPresenter(SurveyPresenter presenter) {
		this.presenters.add(presenter);
	}

	public void displayQuestions(List<Question> questions) {
		this.questionElements = new ArrayList<>();
		questionContainer.removeAllComponents();

		for (Question question : questions) {
			QuestionElement questionElement;

			AbstractField field = createComponent(question);
			questionElement = new QuestionElement(question, field);
			questionContainer.addComponent(field);
			questionElements.add(questionElement);
		}
	}

	private AbstractField createComponent(Question question) {
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
}
