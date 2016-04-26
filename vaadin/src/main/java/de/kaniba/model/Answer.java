package de.kaniba.model;

import java.sql.SQLException;

public class Answer {
	private int answerID;
	private int questionID;
	private int userID;
	private String answerString;
	private boolean isText;
	private boolean answerBool;
	
	public Answer() {
		
	};
	
	public Answer(Question question) {
		this.isText = question.isText();
		this.questionID = question.getQuestionID();
	}
	
	public Answer(int answerID) throws SQLException {
		Answer temp = Database.readAnswer(answerID);
		this.answerBool = temp.getAnswerBool();
		this.answerID = answerID;
		this.questionID = temp.getQuestionID();
		this.userID = temp.getUserID();
		this.answerString = temp.getAnswerString();
		this.isText = temp.isText();
	}
	
	public int getAnswerID() {
		return answerID;
	}
	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}
	public int getQuestionID() {
		return questionID;
	}
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	public String getAnswerString() {
		return answerString;
	}
	public void setAnswerString(String answerString) {
		this.answerString = answerString;
	}
	public boolean isText() {
		return isText;
	}
	public void setText(boolean isText) {
		this.isText = isText;
	}
	public boolean getAnswerBool() {
		return answerBool;
	}
	public void setAnswerBool(boolean answerBool) {
		this.answerBool = answerBool;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public void saveAnswer() {
		try {
			Database.saveAnswer(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
