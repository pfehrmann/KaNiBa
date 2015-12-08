package de.kaniba.model;

import java.util.List;

public class Pinboard {
	List<Message> messages;
	int barID;
	
	public Pinboard(int barID){
		this.barID = barID;
		
	}
	
	public List<Message> getMessages() {
		return messages;
	}

	public int getBarID() {
		return barID;
	}

	
	
	
}
