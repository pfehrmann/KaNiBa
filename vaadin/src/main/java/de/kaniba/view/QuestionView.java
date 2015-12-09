package de.kaniba.view;

import java.util.List;

import com.vaadin.navigator.View;

import de.kaniba.model.Bar;

public interface QuestionView extends View {
	interface QuestionViewListener {
		public void submitForm(List<QuestionElement> questions);
	}
	
	public void addListener(QuestionViewListener listener);
}
