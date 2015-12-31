package de.kaniba.presenter;

import java.sql.SQLException;
import java.util.List;

import com.google.gwt.dom.client.ModElement;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import de.kaniba.model.Answer;
import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.model.InternalUser;
import de.kaniba.model.Message;
import de.kaniba.model.Rating;
import de.kaniba.navigator.NavigatorUI;
import de.kaniba.view.BarView;
import de.kaniba.view.LoginView;
import de.kaniba.view.QuestionElement;
import de.kaniba.view.QuestionView;
import de.kaniba.view.QuestionViewImpl;

public class QuestionPresenter implements QuestionView.QuestionViewListener {

	QuestionView view;
	InternalUser userModel;
	VaadinSession session;

	public View getView() {
		return view;
	}

	public QuestionPresenter(int barID) {
		this.view = new QuestionViewImpl(barID);
		view.addListener(this);
	}

	@Override
	public void submitForm(List<QuestionElement> questions) {
		session = ((NavigatorUI) UI.getCurrent()).getSession();

		Object loggedInObj = session.getAttribute("loggedIn");
		boolean loggedIn = false;
		if (loggedInObj != null) {
			loggedIn = (boolean) loggedInObj;
		}
		if (!loggedIn) {
			Notification.show("Um an einer Umfrage teil zu nehmen musst du eingeloggt sein.");
			return;
		}
		this.userModel = (InternalUser) session.getAttribute("user");
		
		for(QuestionElement qe: questions) {
			Answer a = new Answer();
			a.setQuestionID(qe.getQuestion().getQuestionID());
			a.setUserID(userModel.getUserID());
			a.setText(qe.getQuestion().isText());
			
			if(a.isText()) {
				a.setAnswerBool(false);
				a.setAnswerString((String) qe.getComponent().getValue());
			} else {
				a.setAnswerBool((boolean) qe.getComponent().getValue());
				a.setAnswerString("");
			}
			a.saveAnswer();
		}
	}
}
