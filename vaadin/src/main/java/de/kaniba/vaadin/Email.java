package de.kaniba.vaadin;

public class Email {
	private String mail;
	
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
}
