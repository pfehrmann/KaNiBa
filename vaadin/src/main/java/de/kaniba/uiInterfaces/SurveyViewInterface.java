package de.kaniba.uiInterfaces;

import java.util.List;

import de.kaniba.model.Answer;
import de.kaniba.model.Question;

public interface SurveyViewInterface extends SecuredView{

	/**
	 * Returns a list of answers supplied by the user. If no answers was given,
	 * the default value is returned.
	 * 
	 * @return Returns a list of answers.
	 */
	List<Answer> getAnswers();

	/**
	 * This adds a presenter to the view. Note that having multiple presenters
	 * is possible, but not suggested. Contradictory actions can lead to
	 * confusing results.
	 * 
	 * @param presenter
	 *            The presenter to add. The presenters are called on enter and
	 *            on button clicks.
	 */
	void setPresenter(SurveyPresenterInterface presenter);

	/**
	 * Shows the list of questions supplied as questions. The questions will be
	 * automatically displayed as text fields or as checkboxes, depending on the
	 * type of question. To retireve the answers, call getAnswers().
	 * 
	 * @param questions
	 *            The questions to display
	 */
	void displayQuestions(List<Question> questions);

}