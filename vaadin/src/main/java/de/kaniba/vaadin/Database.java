package de.kaniba.vaadin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Eine Klasse, die Zugriff auf die Datenbak abstrahiert. Die Zugriffe sind alle
 * thread safe.
 * 
 * @author philipp
 *
 */
public class Database {

	public static Bar readBar(int barID) {
		Bar ret = new Bar();

		// Alle Werte aus der Datenbank auslesen und die Setter Methoden
		// aufrufen.
		// Das auslesen der Liste muss / sollte nicht erfolgen, nur die
		// Informationen, die schnell gefunden werden können.
		ret.setCountRating(0);

		return ret;
	}

	/**
	 * Speichert eine Bar und gibt zurück, ob das Speichern erfolgreich war.
	 * 
	 * @param bar
	 *            Die zu speichernde Bar
	 * @return Gibt true zurück, wenn die Bar problemlos gespeichert wurde.
	 */
	public static boolean saveBar(Bar bar) {
		return true;
	}

	/**
	 * Methode, um die kontinuierliche Zählung der Ratings zu überprüfn und
	 * gegebenenfalls zu aktualisieren und zu berichtigen.
	 * 
	 * @param barID
	 *            Die Bar, die aktualisiert werden soll.
	 * @return Gibt true zurück, wenn es beim ausführen der Aktion keine
	 *         Probleme gab.
	 */
	public static boolean checkRatings(int barID) {
		// Wenn alles andere Richtig ist wird diese Methode eigentlich nie
		// aufgerufen und hat deshalb defintiv keine Priorität. :D
		return true;
	}

	/**
	 * Methode, um ein Rating zu speichern
	 * 
	 * @param bar
	 *            Die Bar, der bewertet werden soll.
	 * @param rating
	 *            Das abgebenene Rating
	 * @return Gibt true zurück, wenn es beim Speichern keine Probleme gab.
	 */
	public static boolean saveBarRating(Bar bar, Rating rating) {
		// Die Bar wird hier als Parameter mit übergeben, da hier die jeweils
		// die gesamtSumme und das Count Rating noch enthalten ist.
		// Das Rating muss entweder in die Datanbank geschrieben werden, oder
		// geupdated werden.
		return true;
	}

	/**
	 * Gibt die aktuell laufenden Specials einer Bar zurück (Also alle, wo das
	 * End datum noch in der Zukunft liegt)
	 * 
	 * @param barID
	 *            Die Bar, zu der die SPecials gesucht werden
	 * @return Eine Liste mit allen aktuellen Specials.
	 */
	public static List<Special> readCurrentSpecials(int barID) {
		return new ArrayList<Special>();
	}

	/**
	 * Gibt die alle Specials einer Bar zurück
	 * 
	 * @param barID
	 *            Die Bar, zu der die Specials gesucht werden
	 * @return Eine Liste mit allen Specials.
	 */
	public static List<Special> readAllSpecials(int barID) {
		return new ArrayList<Special>();
	}

	/**
	 * Methode um ein Special aus der Datenbank auszulesen.
	 * 
	 * @param specialID
	 *            Das Special, as ausgelesen werden soll
	 * @return Gibt das ausgelesene Special zurück.
	 */
	public static Special readSpecial(int specialID) {
		// Zum special alle Informationen auslesen und ausgeben
		return new Special();
	}

	/**
	 * Methode, um ein Special zu speichern oder upzudaten.
	 * 
	 * @param special
	 *            Das Special, das gespeichert werden soll.
	 * @return Gibt true zurück, wenn das Special problemlos in Datenbank
	 *         gespeichert wurde.
	 */
	public static boolean saveSpecial(Special special) {
		return true;
	}

	/**
	 * Methode, um einen User einzuloggen.
	 * 
	 * @param username
	 *            Der Username, mit dem sich der User einloggt.
	 * @param password
	 *            Das Passwort, das der User benutzt hat um sich ein zu loggen.
	 * @return Gibt null zurück, wenn die ANmeldung fehlgeschlagen ist. Gibt
	 *         einen Admin zurück, wenn der User ein Admin ist. Gibt andernfalls
	 *         einen internaluser zurück.
	 */
	public static InternalUser logUserIn(String username, String password) {
		return new InternalUser();
	}
	
	/**
	 * Methode, um ein Rating von einem User zu einer Bar zu finden
	 * @param userID Die UserID des anfragenden Users
	 * @param barID Die BarID, für die das Rating verlngt wird
	 * @return Wenn das Rating gefunden wird, wird es zurückgegeben, sonst null.
	 */
	public static Rating getRating(int userID, int barID) {
		return new Rating(1, 1, 1, 1, 1, 1, 1, 1, new Timestamp(2015, 12, 24, 12, 0, 0, 0));
	}
	
	/**
	 * Methode, um zu bestimmen, ob ein User eine Bar bereits bewertet hat.
	 * @param userID Die Id des Users
	 * @param barID Die ID der Bar
	 * @return Gibt true zurück, wenn die Bar bereits bewertet wurde, andernfalls false.
	 */
	public static boolean hasUserRatedBar(int userID, int barID) {
		return false;
	}
}
