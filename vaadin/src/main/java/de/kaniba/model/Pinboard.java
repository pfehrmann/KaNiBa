package de.kaniba.model;

import java.io.Serializable;
import java.util.List;

import de.kaniba.utils.Utils;

/**
 * This class represents the pinboard
 * @author Philipp
 *
 */
public class Pinboard implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Message> messages;
	private int barID;

	/**
	 * Create a pinboard for a bar. Note that this does not load the messages to
	 * the pinboard.
	 * 
	 * @param barID
	 */
	public Pinboard(int barID) {
		this.barID = barID;
	}

	public void setMessages(List<Message> messages) {
		this.messages = Utils.copyList(messages);
	}

	public List<Message> getMessages() {
		return Utils.copyList(messages);
	}

	public int getBarID() {
		return barID;
	}

}
