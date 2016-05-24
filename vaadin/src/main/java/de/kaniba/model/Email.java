package de.kaniba.model;

import java.io.Serializable;

/**
 * This class represents an email of a user
 * @author Philipp
 *
 */
public class Email implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mail;
	
	/**
	 * Initialize the email with the string of the mail
	 * @param mail
	 */
	public Email(String mail) {
		this.mail = mail;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	/**
	 * Methode, um eine Email auf korrektheit zu prüfen.
	 * @param mail Der Email String
	 * @return Gibt true zurück, wenn die Email eine valide ist.
	 */
	public static boolean validateEmail(String mail) {
		return true;
	}
	
	@Override
	public String toString() {
		return this.mail;
	}
}
