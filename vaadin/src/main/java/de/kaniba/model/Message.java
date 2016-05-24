package de.kaniba.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;

import de.kaniba.utils.LoggingUtils;

/**
 * Represents a message on the messageboard
 * 
 * @author Philipp
 *
 */
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int messageID;
	private int userID;
	private int barID;
	private String messageText;
	private Timestamp time;

	/**
	 * Create a message with all the information available. Used for read
	 * actions from database.
	 * 
	 * @param messageID
	 * @param userID
	 * @param barID
	 * @param message
	 * @param time
	 */
	public Message(int messageID, int userID, int barID, String message, Timestamp time) {
		this.messageID = messageID;
		this.userID = userID;
		this.barID = barID;
		this.messageText = message;
		this.time = new Timestamp(time.getTime());
	}

	/**
	 * Create a message with the fewest information possible. Everythng else
	 * will be filled when saving it.
	 * 
	 * @param userID
	 * @param barID
	 * @param message
	 */
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
		return new Timestamp(time.getTime());
	}

	public void setTime(Timestamp time) {
		this.time = new Timestamp(time.getTime());
	}

	/**
	 * Save the message to the database.
	 */
	public void save() {
		try {
			Database.saveMessage(this);
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}

	}

}
