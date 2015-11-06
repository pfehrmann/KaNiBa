package de.kaniba.vaadin;

import java.sql.Date;

public class InternalUser extends User {
	private String sessionID;
	private int userID;
	private Email email;
	private String password;
	private String name;
	private String firstname;
	private Date birthdate;
	
	InternalUser() {
		throw new RuntimeException("implement me");
	}
	
	public void rateBar(Bar bar, int rating) {
		throw new RuntimeException("implement me");
	}
	
	public boolean raredBar(Bar bar) {
		return false;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
		throw new RuntimeException("implement me");
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		throw new RuntimeException("implement me");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		throw new RuntimeException("implement me");
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
		throw new RuntimeException("implement me");
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
		throw new RuntimeException("implement me");
	}

	public String getSessionID() {
		return sessionID;
	}
	
}
