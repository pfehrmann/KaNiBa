package de.kaniba.model;

import java.io.Serializable;
import java.sql.SQLException;

public class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	private int questionID;
	private int barID;
	private boolean isText;
	private String message;
	
	public Question() {
		// nothing to do here
	};
	
	public Question(int questionID) throws SQLException{
		Question temp = Database.readQuestion(questionID);
		this.questionID = temp.getQuestionID();
		this.barID = temp.getBarID();
		this.isText = temp.isText();
		this.message = temp.getMessage();
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public int getBarID() {
		return barID;
	}

	public void setBarID(int barID) {
		this.barID = barID;
	}

	public boolean isText() {
		return isText;
	}

	public void setText(boolean isText) {
		this.isText = isText;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
