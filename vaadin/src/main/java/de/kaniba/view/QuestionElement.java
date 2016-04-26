package de.kaniba.view;

import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;

import de.kaniba.model.Question;

public class QuestionElement extends CustomComponent {
	private AbstractField component;
	private Question question;
	
	public QuestionElement(Question question, AbstractField component) {
		this.component = component;
		this.question = question;
		setCompositionRoot(component);
	}

	public AbstractField getComponent() {
		return component;
	}

	public void setComponent(AbstractField component) {
		this.component = component;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
}
