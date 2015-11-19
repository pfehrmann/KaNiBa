package de.kaniba.vaadin;

public class User {
	
	public User login(String username, String password) {
		return Database.logUserIn(username, password);
	}
	
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
