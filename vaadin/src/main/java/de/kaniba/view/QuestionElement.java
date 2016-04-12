package de.kaniba.view;

import com.vaadin.ui.AbstractField;

import de.kaniba.model.Question;

public class QuestionElement {
	private AbstractField component;
	private Question question;
	
	public QuestionElement(AbstractField component, Question question) {
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
