package de.kaniba.model;

import java.sql.SQLException;
import java.sql.Timestamp;

import de.kaniba.utils.Utils;

public class Message {
	private int messageID;
	private int userID;
	private int barID;
	private String messageText;
	private Timestamp time;
	
	public Message(int messageID, int userID, int barID, String message, Timestamp time) {
		this.messageID = messageID;
		this.userID = userID;
		this.barID = barID;
		this.messageText = message;
		this.time = time;
	}
	public Message(int userID, int barID, String message) {
		this.userID = userID;
		this.barID = barID;
		this.messageText = message;
	}

	public int getMessageID() {
		return messageID;
	}
	
	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public int getBarID() {
		return barID;
	}
	
	public void setBarID(int barID) {
		this.barID = barID;
	}
	
	public String getMessageText() {
		return messageText;
	}
	
	public void setMessage(String message) {
		this.messageText = message;
	}
	
	public Timestamp getTime() {
		return time;
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public void save() {
		try {
			Database.saveMessage(this);
		} catch (SQLException e) {
			Utils.exception(e);
		}
		
	}
	
}
