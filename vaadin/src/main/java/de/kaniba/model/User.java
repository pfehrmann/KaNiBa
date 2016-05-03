package de.kaniba.model;

import java.sql.SQLException;

public class User {
	
	public User login(String username, String password) throws SQLException {
		return Database.logUserIn(username, password);
	}
	
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
