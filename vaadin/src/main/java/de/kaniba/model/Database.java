package de.kaniba.model;

import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

/**
 * Eine Klasse, die Zugriff auf die Datenbak abstrahiert. Die Zugriffe sind alle
 * thread safe.
 * 
 * @author philipp, heavily extended by roman
 *
 *Methoden:
 *1. Connection verbindung(): stellt die Verbindung zur Datenbank her.
 *2. Bar readBar(int barID): liest eine Bar aus der DB aus.
 *3. InternalUser giveUser(int UserID): Gibt einen User aus der DB zurück
 *4. givePinboard(int BarID): gibt en Pinboard zurück.
 *5. boolean saveBar(Bar bar): Speichert eine Bar.
 *6. checkRatings(int barID): Überprüft das Bar-Rating.
 *7. saveBarRating(Rating rating): Speichert ein Rating.
 *8. List<Special> readCurrentSpecials(int barID): gibt alle laufenden Specials zurück.
 *9. List<Special> readAllSpecials(int barID): gibt alle Specials zurück.
 *10. Special readSpecial(int specialID): gibt ein Special zurück.
 *11. saveSpecial(Special special): Speichert ein Special.
 *12. InternalUser logUserIn(String useremail, String password): Überprüft Anmeldedaten.
 *13. Rating getRating(int userID, int barID): liest ein Rating aus.
 *14. boolean hasUserRatedBar(int userID, int barID): Überprüft ob ein User schon ein Rating abgegeben hat.
 *15. int saveUser(InternalUser user)
 *16. boolean changeEmail(InternalUser user,String Email)
 *17. Message saveMessage(Message message)
 */

public class Database {

	public static void main(String[] args) throws Exception {
		System.out.println("*****************---------------------------startet hier--------------------------------*************");
		java.util.Date date = new java.util.Date();
		Timestamp lastUpdated = new Timestamp(date.getTime());
		System.out.println(hasUserRatedBar(1, 42));
		
	}
	
	/**
	 * 
	 * Startet Treiber und öffnet eine Verbindung zur Datenbank
	 * 
	 * @return statement
	 * @throws SQLException
	 */
	public static Connection verbindung() throws SQLException
	{
		//TREIBER
		try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Fehler bei MySQL-JDBC-Bridge" + e);
            return(null);
        } 
		//VERBINDUNG
		Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/kaniba","root","" );         
		return con;
	}
	
	public static Bar readBar(int barID) throws SQLException{
		Bar ret = new Bar();
		// Alle Werte aus der Datenbank auslesen und die Setter Methoden
		// aufrufen.
		// Das auslesen der Liste muss / sollte nicht erfolgen, nur die
		// Informationen, die schnell gefunden werden können.
			int generalRating=0;
			int pprRating=0;
			int musicRating=0;
			int peopleRating =0;
			int atmosphereRating=0;
			int ratingCount = 0;
			int userID=0;
			String city=null;
			String street=null;
			String number=null;
			String zip=null;
			String description = null;
			String name = null;
			Connection con = verbindung();  
			Statement st = con.createStatement(); 
			ResultSet rs = st.executeQuery("SELECT * FROM bars WHERE barID="+barID);

		while (rs.next()) {
			generalRating = rs.getInt("generalRating");
			pprRating = rs.getInt("pprRating");
			musicRating = rs.getInt("musicRating");
			peopleRating = rs.getInt("peopleRating");
			atmosphereRating = rs.getInt("atmosphereRating");
			ratingCount = rs.getInt("ratingCount");
			userID = rs.getInt("userID");
			city = rs.getString("city");
			street = rs.getString("street");
			number = rs.getString("number");
			zip = rs.getString("zip");
			description = rs.getString("description");
			name = rs.getString("name");

			ret.setSumGeneralRating(generalRating);
			ret.setSumPprRating(pprRating);
			ret.setSumMusicRating(musicRating);
			ret.setSumPeopleRating(peopleRating);
			ret.setSumAtmosphereRating(atmosphereRating);
			ret.setCountRating(ratingCount);
			Admin admin = new Admin(giveUser(userID));
			ret.setBarOwner(admin);
			Address address = new Address(city, street, number, zip);
			ret.setAddress(address);
			ret.setPinboard(givePinboard(barID));
			ret.setName(name);
			ret.setDescription(description);
			ret.setBarID(barID);
			rs.close();
			st.close();
			con.close();
			return ret;

		}
		rs.close();
		st.close();
		con.close();
		return null;
	}

	/**
	 * Nimmt eine UserID und gibt den entsprechenden User zurück
	 * 
	 * @param UserID
	 *            Die UserID
	 * @return Gibt den User zurück.
	 */
	public static InternalUser giveUser(int userID) throws SQLException {
		String name = null;
		String firstname = null;
		String email = null;
		String password = null;
		Date birthdate = null;
		String city = null;
		String street = null;
		String number = null;
		String zip = null;
		Connection con = verbindung();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM User WHERE userID = " + userID + ";");

		while (rs.next()) {
			name = rs.getString("name");
			firstname = rs.getString("firstname");
			email = rs.getString("email");
			password = rs.getString("password");
			birthdate = rs.getDate("birthdate");
			city = rs.getString("city");
			street = rs.getString("street");
			number = rs.getString("number");
			zip = rs.getString("zip");

			InternalUser user = new InternalUser();
			user.setBirthdate(birthdate);
			user.setFirstname(firstname);
			user.setName(name);
			user.setPassword(password);
			Address address = new Address(city, street, number, zip);
			user.setAddress(address);
			Email emailemail = new Email(email);
			user.setEmail(emailemail);
			user.setUserID(UserID);
			st.close();
			rs.close();
			con.close();
			return user;
		}
		rs.close();
		st.close();
		con.close();
		return null;	
	}
	
	/**
	 * Nimmt eine barID und gibt das entsprechende Pinboard zurück
	 * 
	 * @param barID
	 *            Die barID
	 * @return Gibt das Pinboard zurück.
	 */
	public static Pinboard givePinboard(int barID) throws SQLException 
	{
		System.out.println();
		System.out.println("[DATABASE:givePinboard] barID: " + barID);
		Connection con = verbindung();  
		Statement st = con.createStatement(); 
		String message=null;
		Timestamp time=null;
		int userID=0;
		int messageID=0;
		int barIDdb=0;
		List<Message> messages = new ArrayList<Message>();
		ResultSet rs = st.executeQuery("SELECT * FROM message");
		
		int fehler=1;
		while ( rs.next() )
		{
			message = rs.getString("message");
			time = rs.getTimestamp("time");
			userID=rs.getInt("userID");
			messageID=rs.getInt("messageID");
			barIDdb=rs.getInt("barID");
			if(barID==barIDdb)
			{
				fehler=0;
				Message tempMessage = new Message(messageID,userID, barID,message,time);
				messages.add(tempMessage);				
			}
		}
		rs.close();
		st.close();
		con.close();
		
		/* BarID == barIDdb ???? */
		Pinboard pinboard = new Pinboard(barID);
		pinboard.messages=messages;
		return pinboard;
	}
	
	/**
	 * Speichert eine Bar und gibt zurück, ob das Speichern erfolgreich war.
	 * 
	 * @param bar
	 *            Die zu speichernde Bar
	 * @return Gibt true zurück, wenn die Bar problemlos gespeichert wurde.
	 * @throws SQLException 
	 */
	public static boolean saveBar(Bar bar) throws SQLException 
	{		
		Connection con = verbindung();  
		Statement st = con.createStatement(); 
		Address address = bar.getAddress();
		java.util.Date date = new java.util.Date();
		Timestamp lastUpdated = new Timestamp(date.getTime());
		st.executeUpdate("INSERT INTO bars (userID,city,street,number,zip,generalRating,pprRating,musicRating,"
				+ "peopleRating,atmosphereRating,ratingCount,lastUpdated,description,name) VALUES ('"
				+ bar.getBarOwner().getUserID() + "','" + address.getCity() + "','" + address.getStreet() + "','"
				+ address.getNumber() + "','" + address.getZip() + "','" + bar.getSumGeneralRating() + "','"
				+ bar.getSumPprRating() + "','" + bar.getSumMusicRating() + "','" + bar.getSumPeopleRating() + "','"
				+ bar.getSumAtmosphereRating() + "','" + bar.getCountRating() + "','" + lastUpdated + "', '"
				+ bar.getDescription() + "', '" + bar.getName() + "');");
		st.close();
		con.close();
		return true;
	}

	/**
	 * Methode, um die kontinuierliche Zählung der Ratings zu überprüfen und
	 * gegebenenfalls zu aktualisieren und zu berichtigen.
	 * 
	 * @param barID
	 *            Die Bar, die aktualisiert werden soll.
	 * @return Gibt true zurück, wenn es beim ausführen der Aktion keine
	 *         Probleme gab.
	 * @throws SQLException 
	 */
	public static boolean checkRatings(int barID) throws SQLException {
		// Wenn alles andere Richtig ist wird diese Methode eigentlich nie
		// aufgerufen und hat deshalb defintiv keine Priorität. :D
		
		Connection con = verbindung();  
		Statement st = con.createStatement(); 
		int barIDdb=0;
		int generalRating=0;
		int pprRating=0;
		int musicRating=0;
		int peopleRating=0;
		int atmosphereRating=0;
		int generalRatingdb=0;
		int pprRatingdb=0;
		int musicRatingdb=0;
		int peopleRatingdb=0;
		int atmosphereRatingdb=0;

		ResultSet rs = st.executeQuery("SELECT * FROM ratings");
		while ( rs.next() )
		{
			barIDdb = rs.getInt("barID");
			if(barIDdb==barID)
			{
				generalRating += rs.getInt("generalRating");
				pprRating += rs.getInt("pprRating");
				musicRating += rs.getInt("musicRating");
				peopleRating += rs.getInt("peopleRating");
				atmosphereRating += rs.getInt("atmosphereRating");
				
			}
		}
		rs = st.executeQuery("SELECT * FROM bars");
		while ( rs.next() )
		{
			barIDdb = rs.getInt("barID");
			if(barIDdb==barID)
			{
				generalRatingdb = rs.getInt("generalRating");
				pprRatingdb = rs.getInt("pprRating");
				musicRatingdb = rs.getInt("musicRating");
				peopleRatingdb = rs.getInt("peopleRating");
				atmosphereRatingdb = rs.getInt("atmosphereRating");
			}
		}
		if((generalRating!=generalRatingdb)||(pprRating!=pprRatingdb)||(musicRating!=musicRatingdb)||(peopleRating!=peopleRatingdb)||(atmosphereRating!=atmosphereRatingdb))
		{
			st.executeUpdate("UPDATE bars SET generalRating = "+generalRating+", pprRating = "+pprRatingdb+", musicRating = "+musicRating+", peoplerating = "+peopleRating+", atmosphereRating = "+atmosphereRating+" WHERE barID= "+barID+";");
		}
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
	 * @throws SQLException
	 * @todo time, timestamp, lastupdated
	 */
	public static boolean saveBarRating(Rating rating) throws SQLException {
		// Die Bar wird hier als Parameter mit übergeben, da hier die jeweils
		// die gesamtSumme und das Count Rating noch enthalten ist.
		// Das Rating muss entweder in die Datanbank geschrieben werden, oder
		// geupdated werden.
		int userID = -1;
		int barID = -1;
		int update = -1;
		int ratingID = -1;
		int generalRatingBar = -1;
		int pprRatingBar = -1;
		int musicRatingBar = -1;
		int peopleRatingBar = -1;
		int atmosphereRatingBar = -1;
		int generalRating = -1;
		int pprRating = -1;
		int musicRating = -1;
		int peopleRating = -1;
		int atmosphereRating = -1;
		int ratingCount = -1;
		Connection con = verbindung();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM ratings");
		while (rs.next()) {
			userID = rs.getInt("userID");
			barID = rs.getInt("barID");

			if (userID == rating.getUserID() && barID == rating.getBarID()) {
				update = 1;
				// Holt die Ratings des zu aktualisierenden Ratings sowie die ID
				// aus der DB
				generalRating = rs.getInt("generalRating");
				pprRating = rs.getInt("pprRating");
				musicRating = rs.getInt("musicRating");
				peopleRating = rs.getInt("peopleRating");
				atmosphereRating = rs.getInt("atmosphereRating");
				ratingID = rs.getInt("ratingID");

				break;
			}
		}
		if (update == 1) {
			rs = st.executeQuery("SELECT * FROM bars WHERE barID=" + rating.getBarID());
			if (rs.next()) {
				// Holt die alten Ratings der Bar aus der Datenbank
				generalRatingBar = rs.getInt("generalRating");
				pprRatingBar = rs.getInt("pprRating");
				musicRatingBar = rs.getInt("musicRating");
				peopleRatingBar = rs.getInt("peopleRating");
				atmosphereRatingBar = rs.getInt("atmosphereRating");
			}

			// Entfernt das alte Rating vom Bar-Rating, damit das neue addiert
			// werden kann
			generalRatingBar -= generalRating;
			pprRatingBar -= pprRating;
			musicRatingBar -= musicRating;
			peopleRatingBar -= peopleRating;
			atmosphereRatingBar -= atmosphereRating;

			// Addiert das neue Rating zum Bar-Rating
			generalRatingBar += rating.getGeneralRating();
			pprRatingBar += rating.getPprRating();
			musicRatingBar += rating.getMusicRating();
			peopleRatingBar += rating.getPeopleRating();
			atmosphereRatingBar += rating.getAtmosphereRating();

			// Aktualisiert das Bar-Rating
			st.executeUpdate("UPDATE bars SET generalRating = '" + generalRatingBar + "', pprRating = '" + pprRatingBar
					+ "', musicRating = '" + musicRatingBar + "', peopleRating = '" + peopleRatingBar
					+ "', atmosphereRating = '" + atmosphereRatingBar + "' WHERE barID= '" + rating.getBarID() + "';");
			
			//Aktualisiert das Rating
			st.executeUpdate("UPDATE ratings SET generalRating = '" + rating.getGeneralRating() + "', pprRating = '"
					+  rating.getPprRating() + "', musicRating = '" +  rating.getMusicRating() + "', peopleRating = '"
					+  rating.getPeopleRating() + "', atmosphereRating = '" +  rating.getAtmosphereRating() + "' WHERE ratingID= '" + ratingID + "';");
		} else {

			st.executeUpdate(
					"INSERT INTO ratings (userID,barID,generalRating,pprRating,musicRating,peopleRating,atmosphereRating,time) VALUES ('"
							+ rating.getUserID() + "','" + rating.getBarID() + "','" + rating.getGeneralRating() + "','"
							+ rating.getPprRating() + "','" + rating.getMusicRating() + "','" + rating.getPeopleRating()
							+ "','" + rating.getAtmosphereRating() + "','" + rating.getTimestamp() + "');");

			// Liest das RatingCount aus und erhöht um 1
			ResultSet rs2 = st.executeQuery("SELECT * FROM ratings");
			rs2 = st.executeQuery("SELECT ratingCount FROM bars WHERE barID=" + rating.getBarID());
			if (rs2.next()) {
				ratingCount = rs2.getInt("ratingCount") + 1;
			}
			rs2.close();
			
			// Speichert das neue RatingCount
			st.executeUpdate("UPDATE bars SET ratingCount = " + ratingCount);
		}
		
		rs.close();
		st.close();
		con.close();
		return true;
	}

	/**
	 * Gibt die aktuell laufenden Specials einer Bar zurück (Also alle, wo das
	 * End datum noch in der Zukunft liegt)
	 * 
	 * @param barID
	 *            Die Bar, zu der die SPecials gesucht werden
	 * @return Eine Liste mit allen aktuellen Specials.
	 * @throws SQLException 
	 */
	public static List<Special> readCurrentSpecials(int barID) throws SQLException 
	{
		int userID=0;
		String message=null;
		Timestamp begin=null;
		Timestamp end=null;
		Timestamp created=null;
		int barIDdb=0;
		ArrayList <Special> list=new ArrayList <Special>();
		Connection con = verbindung();  
		Statement st = con.createStatement();  
		
		ResultSet rs = st.executeQuery("SELECT * FROM specials");

		int fehler=1;
		while ( rs.next() )
		{
			userID = rs.getInt("userID");
			barIDdb = rs.getInt("barID");
			message = rs.getString("message");
			begin = rs.getTimestamp("begin");
			end = rs.getTimestamp("end");
			created = rs.getTimestamp("created");
			java.util.Date date= new java.util.Date();
			
			if(barID==barIDdb)
			{
				if(date.after(end) && end.before(date))
				{
					fehler=0;

					Special special = new Special();
					special.setBegin(begin);
					special.setCreated(created);
					special.setEnd(end);
					special.setMessage(message);
					special.setUserID(userID);
					special.setBarID(barID);
					
					list.add(special);
				}
			}
		}
		con.close();
		if(fehler==1)
		{
			return(null);
		}		
		
		return new ArrayList<Special>();
	}

	/**
	 * Gibt die alle Specials einer Bar zurück
	 * 
	 * @param barID
	 *            Die Bar, zu der die Specials gesucht werden
	 * @return Eine Liste mit allen Specials.
	 * @throws SQLException 
	 */
	public static List<Special> readAllSpecials(int barID) throws SQLException 
	{
		int userID=0;
		String message=null;
		Timestamp begin=null;
		Timestamp end=null;
		Timestamp created=null;
		int barIDdb=0;
		ArrayList <Special> list = new ArrayList <Special>();
		Connection con = verbindung();  
		Statement st = con.createStatement();   
		
		ResultSet rs = st.executeQuery("SELECT * FROM specials");

		int fehler=1;
		while ( rs.next() )
		{
			userID = rs.getInt("userID");
			barIDdb = rs.getInt("barID");
			message = rs.getString("message");
			begin = rs.getTimestamp("begin");
			end = rs.getTimestamp("end");
			created = rs.getTimestamp("created");
			if(barID==barIDdb)
			{
				fehler=0;

				Special special = new Special();
				special.setBegin(begin);
				special.setCreated(created);
				special.setEnd(end);
				special.setMessage(message);
				special.setUserID(userID);
				special.setBarID(barID);
				
				list.add(special);
			}
		}
		rs.close();
		st.close();
		con.close();
		if(fehler==1)
		{
			return(null);
		}		
		
		return new ArrayList<Special>();
	}

	/**
	 * Methode um ein Special aus der Datenbank auszulesen.
	 * 
	 * @param specialID
	 *            Das Special, as ausgelesen werden soll
	 * @return Gibt das ausgelesene Special zurück.
	 */
	public static Special readSpecial(int specialID) throws Exception{
		// Zum special alle Informationen auslesen und ausgeben
		int barID=0;
		int userID=0;
		String message=null;
		Timestamp begin=null;
		Timestamp end=null;
		Timestamp created=null;
		Connection con = verbindung();  
		Statement st = con.createStatement();   
		
		ResultSet rs = st.executeQuery("SELECT * FROM specials WHERE specialID="+specialID);
		while ( rs.next() )
		{
			userID = rs.getInt("userID");
			message = rs.getString("message");
			begin = rs.getTimestamp("begin");
			end = rs.getTimestamp("end");
			created = rs.getTimestamp("created");

			Special special = new Special();
			special.setBegin(begin);
			special.setCreated(created);
			special.setEnd(end);
			special.setMessage(message);
			special.setUserID(userID);
			special.setBarID(barID);
			rs.close();
			st.close();
			con.close();
			return special;

		}
		rs.close();
		st.close();
		
		return null;
	}

	/**
	 * Methode, um ein Special zu speichern oder upzudaten.
	 * 
	 * @param special
	 *            Das Special, das gespeichert werden soll.
	 * @return Gibt true zurück, wenn das Special problemlos in Datenbank
	 *         gespeichert wurde.
	 * @throws SQLException 
	 */
	public static boolean saveSpecial(Special special) throws SQLException {
		Connection con = verbindung();  
		Statement st = con.createStatement(); 
		st.executeUpdate("INSERT INTO specials (barID,userID,message,begin,end,created) VALUES ('"+special.getBarID()+"','"+special.getUserID()+"','"+special.getMessage()+"','"+special.getBegin()+"','"+special.getEnd()+"','"+special.getCreated()+"');");
		st.close();
		con.close();
		return true;
	}

	/**
	 * Methode, um einen User einzuloggen.
	 * 
	 * @param useremail
	 *            Die Useremail, mit der sich der User einloggt.
	 * @param password
	 *            Das Passwort, das der User benutzt hat um sich ein zu loggen.
	 * @return Gibt null zurück, wenn die ANmeldung fehlgeschlagen ist. Gibt
	 *         einen Admin zurück, wenn der User ein Admin ist. Gibt andernfalls
	 *         einen internaluser zurück.
	 * @throws SQLException 
	 */
	public static InternalUser logUserIn(String useremail, String password) throws SQLException {

		String useremaildb = null;
		String userpassworddb = null;
		int isadmin = 0;
		int userid = 0;
		Connection con = verbindung();
		Statement st = con.createStatement();

		ResultSet rs = st.executeQuery("SELECT * FROM user");

		while (rs.next()) {
			useremaildb = rs.getString("email");
			userpassworddb = rs.getString("password");
			isadmin = rs.getInt("isAdmin");
			userid = rs.getInt("userID");
			if (useremaildb.equalsIgnoreCase(useremail)) {
				if (userpassworddb.equals(password)) {
					if (isadmin == 0) {
						InternalUser user = giveUser(userid);
						rs.close();
						st.close();
						con.close();
						return user;
					} else {
						Admin user = (Admin) giveUser(userid);
						rs.close();
						st.close();
						con.close();
						return user;
					}
				}
			}
		}
		rs.close();
		st.close();
		con.close();
		return null;
	}
	
	/**
	 * Methode, um ein Rating von einem User zu einer Bar zu finden
	 * @param userID Die UserID des anfragenden Users
	 * @param barID Die BarID, für die das Rating verlngt wird
	 * @return Wenn das Rating gefunden wird, wird es zurückgegeben, sonst null.
	 * @throws SQLException 
	 */
	public static Rating getRating(int userID, int barID) throws SQLException {
		int generalRating=0;
		int pprRating=0;
		int musicRating=0;
		int peopleRating=0;
		int atmosphereRating=0;
		int userIDdb = 0;
		int barIDdb = 0;
		int ratingID=0;
		Timestamp time=null;
		Connection con = verbindung();  
		Statement st = con.createStatement(); 
		
		ResultSet rs = st.executeQuery("SELECT * FROM Ratings");

		while ( rs.next() )
		{
			ratingID = rs.getInt("ratingID");
			generalRating = rs.getInt("generalRating");
			pprRating = rs.getInt("pprRating");
			musicRating = rs.getInt("musicRating");
			peopleRating = rs.getInt("peopleRating");
			atmosphereRating = rs.getInt("atmosphereRating");
			userIDdb = rs.getInt("userID");
			barIDdb = rs.getInt("barID");
			time = rs.getTimestamp("time");
			if(barID==barIDdb && userID==userIDdb)
			{
				rs.close();
				st.close();
				con.close();
				return new Rating(ratingID, userIDdb, barIDdb, generalRating, pprRating, musicRating, peopleRating, atmosphereRating, time);
			}
		}
		rs.close();
		st.close();
		con.close();
		return null;
	}
	
	/**
	 * Methode, um zu bestimmen, ob ein User eine Bar bereits bewertet hat.
	 * @param userID Die Id des Users
	 * @param barID Die ID der Bar
	 * @return Gibt true zurück, wenn die Bar bereits bewertet wurde, andernfalls false.
	 * @throws SQLException 
	 */
	public static boolean hasUserRatedBar(int userID, int barID) throws SQLException {

		int userIDdb = 0;
		int barIDdb = 0;

		Connection con = verbindung();  
		Statement st = con.createStatement(); 
		
		ResultSet rs = st.executeQuery("SELECT * FROM Ratings");

		while ( rs.next() )
		{
			userIDdb = rs.getInt("userID");
			barIDdb = rs.getInt("barID");
			if(barID==barIDdb && userID==userIDdb)
			{
				rs.close();
				st.close();
				con.close();
				return true;
			}
		}
		rs.close();
		st.close();
		con.close();
		return false;
	}

	/**
	 * Methode, um einen User zu speichern
	 * 
	 * @param User
	 *            Der User, der erstellt oder geändert werden soll.
	 *            
	 * @return Gibt die UserID zurück
	 * @throws SQLException 
	 */
	public static int saveUser(InternalUser user) throws SQLException {

		Connection con = verbindung();
		Statement st = con.createStatement();
		
		String query = "SELECT userID FROM user WHERE email='" + user.getEmail().getMail() + "'";
		ResultSet rs = st.executeQuery(query);
		
		int userID = -1;
		while (rs.next()) {
			userID = rs.getInt("userID");
		}
		
		//Update user
		if(userID != -1) {
			query = "UPDATE user SET name = '" + user.getName() + "', firstname = '" + user.getFirstname()
					+ "', email = '" + user.getEmail() + "',  password = '" + user.getPassword() + "', sessionID = '"
					+ user.getSessionID() + "', birthdate = '" + user.getBirthdate() + "', city = '"
					+ user.getAddress().getCity() + "', street = '" + user.getAddress().getStreet() + "', number = '"
					+ user.getAddress().getNumber() + "', zip = '" + user.getAddress().getZip() + "' WHERE userID = "
					+ userID + ";";
			System.out.println(query);
			st.executeUpdate(query);
		} else {
			query = "INSERT INTO user (name,firstname,email,password,sessionID,birthdate,city,street,number,zip) VALUES ('"
					+ user.getName() + "','" + user.getFirstname() + "','" + user.getEmail().getMail() + "','"
					+ user.getPassword() + "','" + user.getSessionID() + "','" + user.getBirthdate() + "','"
					+ user.getAddress().getCity() + "','" + user.getAddress().getStreet() + "','"
					+ user.getAddress().getNumber() + "','" + user.getAddress().getZip() + "');";
			st.executeUpdate(query);

		}
		
		st.close();
		rs.close();
		con.close();
		
		return userID;
	}
	
	/**
	 * Methode, um eine Email zu ändern
	 * 
	 * @param User
	 *            Der User, dessen Email geändert werden soll.
	 *            
	 *@param Email
	 *            Die neue Email.
	 *            
	 * @return true: geändert, false: user nicht vorhanden
	 * @throws SQLException 
	 */
	public static boolean changeEmail(InternalUser user,String Email) throws SQLException {
		int userID=0;
		int update=0;
		String UserEmail=user.getEmail().getMail();
		Connection con = verbindung();  
		Statement st = con.createStatement(); 
		ResultSet rs = st.executeQuery("SELECT * FROM user");
		while ( rs.next() )
		{
			UserEmail = rs.getString("email");
			userID = rs.getInt("userID");
			if(UserEmail.equals(Email))
			{
				update=1;
				break;
			}
		}
		if(update==1)
		{
			st.executeUpdate("UPDATE user SET email = '"+Email+"' WHERE userID= '"+userID+"';");
			st.close();
			con.close();
			return true;
		}
		st.close();
		con.close();		
		return false;
	}
	
	/**
	 * Speichert eine Message und gibt sie zurück,wenn das Speichern erfolgreich war.
	 * 
	 * @param message
	 *            Die zu speichernde Message
	 * @return Gibt die Message zurück, wenn die Message problemlos gespeichert wurde.
	 * @throws SQLException 
	 */
	public static Message saveMessage(Message message) throws SQLException 
	{		
		Connection con = verbindung();  
		Statement st = con.createStatement(); 
		Integer messageID =-1;
		st.executeUpdate("INSERT INTO message (userID,barID,message,time)"
				+ " VALUES ('"
				+ message.getUserID() + "','" + message.getBarID() + "','" + message.getMessage() + "','" + message.getTime() 
				+ "');");
		ResultSet rs = st.executeQuery("select last_insert_id() as last_id from message");
		if(rs.next()){
		messageID = rs.getInt("last_id");		
		message.setMessageID(messageID);
		rs.close();
		st.close();
		con.close();
		return message;		
	}

	public static List<Bar> searchForBar(String bar) throws SQLException{
		Connection con = verbindung();
		Statement st = con.createStatement();
		String query = "SELECT barID FROM bars WHERE name LIKE '%" + bar + "%';";
		ResultSet rs = st.executeQuery(query);
		List<Bar> ret = new ArrayList<Bar>();
		
		while(rs.next()) {
			ret.add(readBar(rs.getInt("barID")));
		}
		rs.close();
		st.close();
		con.close();
		
		return ret;
	}
}
