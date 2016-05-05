package de.kaniba.view;

import java.io.Serializable;

import com.vaadin.ui.AbstractField;

import de.kaniba.model.Question;

public class QuestionElement implements Serializable {
	private static final long serialVersionUID = 1L;

	private AbstractField component;
	private Question question;
	
	public QuestionElement(Question question, AbstractField component) {
		this.component = component;
		this.question = question;
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
