package de.kaniba.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;

import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.model.Question;
import de.kaniba.navigator.NavigatorUI;
import de.kaniba.presenter.BarPresenter;

public class QuestionViewImpl extends CustomComponent implements QuestionView, Button.ClickListener {
	private int barID;
	List<Question> questions;
	List<QuestionViewListener> listeners;
	List<QuestionElement> elements;
	String searchString;

	public QuestionViewImpl(int barID) {
		listeners = new ArrayList<QuestionViewListener>();
		elements = new ArrayList<QuestionElement>();

		Panel mainPanel = new Panel();
		VerticalLayout cont = new VerticalLayout();
		cont.setSpacing(true);

		this.barID = barID;
		try {
			questions = Database.getQuestionsForBar(barID);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Question q : questions) {
			HorizontalLayout questionLayout = new HorizontalLayout();
			questionLayout.setSpacing(true);

			questionLayout.addComponent(new Label(q.getMessage()));
			QuestionElement e;
			if (q.isText()) {
				TextField text = new TextField();
				e = new QuestionElement(text, q);
				questionLayout.addComponent(text);
			} else {
				CheckBox check = new CheckBox();
				e = new QuestionElement(check, q);
				questionLayout.addComponent(check);
			}

			elements.add(e);
			cont.addComponent(questionLayout);
		}

		Button submit = new Button("Fertig");
		submit.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				for (QuestionViewListener listener : listeners) {
					listener.submitForm(elements);
				}
			}
		});
		cont.addComponent(submit);
		mainPanel.setContent(cont);
		setCompositionRoot(mainPanel);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		UI.getCurrent().getPage().setTitle("Fragebogen");
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Bar b = ((Bar) ((Button) event.getSource()).getData());
		BarPresenter bp = new BarPresenter(new BarViewImpl());
		((NavigatorUI) UI.getCurrent()).getNavigator().addView("bar", bp.getView());
		((NavigatorUI) UI.getCurrent()).getNavigator().navigateTo("bar/" + b.getBarID());
	}

	@Override
	public void addListener(QuestionViewListener listener) {
		listeners.add(listener);

	}

}
