package de.kaniba.model;

import de.kaniba.utils.LoggingUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mysql.jdbc.MysqlDataTruncation;



/**
 * Eine Klasse, die Zugriff auf die Datenbak abstrahiert. Die Zugriffe sind alle
 * thread safe.
 * 
 * @author philipp, roman
 *
 *         Methoden: 1. Connection verbindung(): stellt die Verbindung zur
 *         Datenbank her. 2. Bar readBar(int barID): liest eine Bar aus der DB
 *         aus. 3. InternalUser giveUser(int UserID): Gibt einen User aus der DB
 *         zurück 4. givePinboard(int BarID): gibt en Pinboard zurück. 5.
 *         boolean saveBar(Bar bar): Speichert eine Bar. 6. checkRatings(int
 *         barID): Überprüft das Bar-Rating. 7. saveBarRating(Rating rating):
 *         Speichert ein Rating. 8. List<Special> readCurrentSpecials(int
 *         barID): gibt alle laufenden Specials zurück. 9. List
 *         <Special> readAllSpecials(int barID): gibt alle Specials zurück. 10.
 *         Special readSpecial(int specialID): gibt ein Special zurück. 11.
 *         saveSpecial(Special special): Speichert ein Special. 12. InternalUser
 *         logUserIn(String useremail, String password): Überprüft Anmeldedaten.
 *         13. Rating getRating(int userID, int barID): liest ein Rating aus.
 *         14. boolean hasUserRatedBar(int userID, int barID): Überprüft ob ein
 *         User schon ein Rating abgegeben hat. 15. int saveUser(InternalUser
 *         user) 16. boolean changeEmail(InternalUser user,String Email) 17.
 *         Message saveMessage(Message message)
 */

public final class Database {

	private static final String CREATED_STRING = "created";
	private static final String BEGIN_STRING = "begin";
	private static final String EMAIL_STRING = "email";
	/*
	 * Fieldnames of the database
	 */
	private static final String BAR_ID_STRING = "barID";
	private static final String MESSAGE_STRING = "message";
	private static final String USER_ID_STRING = "userID";
	private static final String ATMOSPHERE_RATING_STRING = "atmosphereRating";
	private static final String PEOPLE_RATING_STRING = "peopleRating";
	private static final String MUSIC_RATING_STRING = "musicRating";
	private static final String PPR_RATING_STRING = "pprRating";
	private static final String GENERAL_RATING_STRING = "generalRating";

	private static ConnectionCreater connectionCreater = new DefaultDatabaseConnectionCreater();

	
	private Database() {
		// May not be instanciated
	}

	public static void main(String[] args) throws SQLException {
		System.out.println(readAllSpecials(1).size());
	}

	/**
	 * 
	 * Startet Treiber und öffnet eine Verbindung zur Datenbank
	 * 
	 * @return statement
	 * @throws SQLException
	 */
	public static Connection verbindung() throws SQLException {
		if (connectionCreater != null) {
			return connectionCreater.verbindung();
		}
		throw new IllegalStateException("No connection creater available");
	}

	public static void setConnectionCreater(ConnectionCreater connectionCreater) {
		Database.connectionCreater = connectionCreater;
	}

	/**
	 * This method will read a bar from the database based on the id of the bar.
	 * 
	 * @param barID
	 *            The ID of the bar to read
	 * @return Returns the read bar.
	 * @throws SQLException
	 *             In case something goes wrong...
	 */
	public static Bar readBar(int barID) throws SQLException {
		if (barID < 0) {
			throw new IllegalArgumentException();
		}

		// Alle Werte aus der Datenbank auslesen und die Setter Methoden
		// aufrufen.
		// Das auslesen der Liste muss / sollte nicht erfolgen, nur die
		// Informationen, die schnell gefunden werden können.

		String sql = "SELECT * FROM bars WHERE barID=?";
		ResultSet rs = null;
		try (Connection con = verbindung(); PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, barID);
			rs = st.executeQuery();

			if (rs.next()) {
				Bar ret = getBarFromRS(rs);

				rs.close();
				return ret;
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

		return null;
	}

	/**
	 * @param barID
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static Bar getBarFromRS(ResultSet rs) throws SQLException {
		int generalRating = rs.getInt(GENERAL_RATING_STRING);
		int pprRating = rs.getInt(PPR_RATING_STRING);
		int musicRating = rs.getInt(MUSIC_RATING_STRING);
		int peopleRating = rs.getInt(PEOPLE_RATING_STRING);
		int atmosphereRating = rs.getInt(ATMOSPHERE_RATING_STRING);
		int ratingCount = rs.getInt("ratingCount");
		int userID = rs.getInt(USER_ID_STRING);
		int barID = rs.getInt(BAR_ID_STRING);
		String city = rs.getString("city");
		String street = rs.getString("street");
		String number = rs.getString("number");
		String zip = rs.getString("zip");
		String description = rs.getString("description");
		String name = rs.getString("name");

		Bar ret = new Bar();
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
		return ret;
	}

	/**
	 * Get the suggestions for a user
	 * 
	 * @param userID
	 *            The user ID
	 * @return
	 * @throws SQLException
	 */
	public static List<Bar> getSuggestions(int userID) throws SQLException {
		List<Bar> bars = new ArrayList<>();

		ResultSet rs = null;
		String sql = "SELECT barID, SUM(generalRating) " + "FROM ratings WHERE userID IN "
				+ "(SELECT userID FROM ratings WHERE barID IN "
				+ "(SELECT barID FROM ratings WHERE userID = ? AND generalRating > 3) AND userID != ?) "
				+ "GROUP BY barID ORDER BY SUM(generalRating) DESC LIMIT 5";

		try (Connection con = verbindung(); PreparedStatement statement = con.prepareStatement(sql);) {
			statement.setInt(1, userID);
			statement.setInt(2, userID);
			rs = statement.executeQuery();
			while (rs.next()) {
				int barID = rs.getInt(BAR_ID_STRING);
				bars.add(readBar(barID));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return bars;
	}

	/**
	 * Get all the tags that are set for a specific bar.
	 * 
	 * @param barID
	 *            The barID
	 * @return Retuns a list with all tags
	 * @throws SQLException
	 *             IF something goes wrong...
	 */
	public static List<Tag> getTagsForBar(int barID) throws SQLException {
		List<Tag> tags = new ArrayList<>();

		String sql = "SELECT * FROM tags WHERE barID=?";

		try (Connection con = verbindung(); PreparedStatement prepareStatement = con.prepareStatement(sql);) {
			prepareStatement.setInt(1, barID);
			ResultSet rs = prepareStatement.executeQuery();

			while (rs.next()) {
				tags.add(getTagFromRs(rs));
			}
			rs.close();
		}

		return tags;
	}

	/**
	 * Get a tag from an ResultSet. The set is not closed here.
	 * 
	 * @param rs
	 * @return
	 */
	private static Tag getTagFromRs(ResultSet rs) {
		Tag tag = new Tag();
		try {
			tag.setTagID(rs.getInt("tagID"));
			tag.setUserID(rs.getInt("userID"));
			tag.setBarID(rs.getInt("barID"));
			tag.setName(rs.getString("name"));
			tag.setCreated(rs.getDate(CREATED_STRING));
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}
		return tag;
	}

	/**
	 * @param tag
	 */
	public static boolean saveTag(Tag tag) {
		if (tag.getTagID() != Tag.INVALID_TAG_ID) {
			updateTag(tag);
			return false;
		}

		String sql = "INSERT INTO tags SET userID=?, barID=?, name=?";

		try (Connection con = verbindung(); PreparedStatement prepareStatement = con.prepareStatement(sql);) {
			prepareStatement.setInt(1, tag.getUserID());
			prepareStatement.setInt(2, tag.getBarID());
			prepareStatement.setString(3, tag.getName());
			prepareStatement.executeUpdate();
			return true;
		} catch (MysqlDataTruncation e) {
			return false;
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}
		return false;
	}

	/**
	 * 
	 * @param tag
	 */
	private static void updateTag(Tag tag) {
		String sql = "UPDATE tags SET userID=?, barID=?, name=? WHERE tagID=?";

		try (Connection con = verbindung(); PreparedStatement prepareStatement = con.prepareStatement(sql);) {
			prepareStatement.setInt(1, tag.getUserID());
			prepareStatement.setInt(2, tag.getBarID());
			prepareStatement.setString(3, tag.getName());
			prepareStatement.setInt(4, tag.getTagID());
			prepareStatement.executeQuery();
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}
	}

	/**
	 * Nimmt eine UserID und gibt den entsprechenden User zurück
	 * 
	 * @param userID
	 *            Die UserID
	 * @return Gibt den User zurück.
	 */
	public static InternalUser giveUser(int userID) throws SQLException {
		if (userID < 0) {
			throw new IllegalArgumentException("Invalid user id");
		}

		ResultSet rs = null;
		String sql = "SELECT * FROM user WHERE userID=?";
		try (Connection con = verbindung(); PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, userID);
			rs = st.executeQuery();

			if (rs.next()) {
				String name = rs.getString("name");
				String firstname = rs.getString("firstname");
				String email = rs.getString(EMAIL_STRING);
				String password = rs.getString("password");
				Date birthdate = rs.getDate("birthdate");
				String city = rs.getString("city");
				String street = rs.getString("street");
				String number = rs.getString("number");
				String zip = rs.getString("zip");

				InternalUser user = new InternalUser();
				user.setBirthdate(birthdate);
				user.setFirstname(firstname);
				user.setName(name);
				user.setPassword(password);

				Address address = new Address(city, street, number, zip);
				user.setAddress(address);

				Email emailemail = new Email(email);
				user.setEmail(emailemail);
				user.setUserID(userID);
				return user;
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return null;
	}

	/**
	 * Nimmt eine BarID und gibt das entsprechende Pinboard zurück
	 * 
	 * @param barID
	 *            Die BarID
	 * @return Gibt das Pinboard zurück.
	 */
	public static Pinboard givePinboard(int barID) throws SQLException {
		Pinboard pinboard = new Pinboard(barID);
		List<Message> messages = new ArrayList<>();

		ResultSet rs = null;
		String sql = "SELECT * FROM message WHERE barID=?";
		try (Connection con = verbindung(); PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, barID);
			rs = st.executeQuery();

			while (rs.next()) {
				String messageString = rs.getString(MESSAGE_STRING);
				Timestamp time = rs.getTimestamp("time");
				int userID = rs.getInt(USER_ID_STRING);
				int messageID = rs.getInt("messageID");
				Message message = new Message(messageID, userID, barID, messageString, time);
				messages.add(message);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

		if (messages.isEmpty()) {
			return pinboard;
		}

		pinboard.setMessages(messages);
		return pinboard;
	}

	/**
	 * Speichert eine Bar und gibt zurück, ob das Speichern erfolgreich war.
	 * 
	 * @param bar
	 *            Die zu speichernde Bar
	 * @return Returns the id of the bar. Returns -1 if the saving failed.
	 * @throws SQLException
	 */
	public static int saveBar(Bar bar) throws SQLException {

		if (bar.getBarID() != Bar.UNKNOWNBARID) {
			String sql = "UPDATE bars SET city=?, street=?, number=?, zip=?, description=?, name=?, lastUpdated=? WHERE barID=?";
			try (Connection con = verbindung(); PreparedStatement statement = con.prepareStatement(sql);) {
				statement.setString(1, bar.getAddress().getCity());
				statement.setString(2, bar.getAddress().getStreet());
				statement.setString(3, bar.getAddress().getNumber());
				statement.setString(4, bar.getAddress().getZip());
				statement.setString(5, bar.getDescription());
				statement.setString(6, bar.getName());
				statement.setDate(7, new Date(Calendar.getInstance().getTimeInMillis()));
				statement.setInt(8, bar.getBarID());
				statement.executeUpdate();
			}
			return bar.getBarID();
		}
		try (Connection con = verbindung(); Statement st = con.createStatement();) {
			Address address = bar.getAddress();
			java.util.Date date = new java.util.Date();
			Timestamp lastUpdated = new Timestamp(date.getTime());
			st.executeUpdate("INSERT INTO bars (userID,city,street,number,zip,generalRating,pprRating,musicRating,"
					+ "peopleRating,atmosphereRating,ratingCount,lastUpdated,description,name) VALUES ('"
					+ bar.getBarOwner().getUserID() + "','" + address.getCity() + "','" + address.getStreet() + "','"
					+ address.getNumber() + "','" + address.getZip() + "','" + bar.getSumGeneralRating() + "','"
					+ bar.getSumPprRating() + "','" + bar.getSumMusicRating() + "','" + bar.getSumPeopleRating() + "','"
					+ bar.getSumAtmosphereRating() + "','" + bar.getCountRating() + "','" + lastUpdated + "', '"
					+ bar.getDescription() + "', '" + bar.getName() + "');", Statement.RETURN_GENERATED_KEYS);

			ResultSet keys = st.getGeneratedKeys();

			if (keys.next()) {
				int returnValue = keys.getInt(1);
				keys.close();
				return returnValue;
			}
			keys.close();
		}
		return -1;
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

		int generalRating = 0;
		int pprRating = 0;
		int musicRating = 0;
		int peopleRating = 0;
		int atmosphereRating = 0;

		ResultSet rs = null;
		String sql = "SELECT * FROM ratings WHERE barID=?";
		try (Connection con = verbindung(); PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, barID);
			rs = st.executeQuery();

			while (rs.next()) {
				generalRating += rs.getInt(GENERAL_RATING_STRING);
				pprRating += rs.getInt(PPR_RATING_STRING);
				musicRating += rs.getInt(MUSIC_RATING_STRING);
				peopleRating += rs.getInt(PEOPLE_RATING_STRING);
				atmosphereRating += rs.getInt(ATMOSPHERE_RATING_STRING);
			}

			st.executeUpdate("UPDATE bars SET generalRating = " + generalRating + ", pprRating = " + pprRating
					+ ", musicRating = " + musicRating + ", peoplerating = " + peopleRating + ", atmosphereRating = "
					+ atmosphereRating + " WHERE barID= " + barID + ";");
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return true;
	}

	/**
	 * Methode, um ein Rating zu speichern
	 * 
	 * @param rating
	 *            Das abgebenene Rating
	 * @return Gibt true zurück, wenn es beim Speichern keine Probleme gab.
	 * @throws SQLException
	 */
	public static boolean saveBarRating(Rating rating) throws SQLException {
		// Die Bar wird hier als Parameter mit übergeben, da hier die jeweils
		// die gesamtSumme und das Count Rating noch enthalten ist.
		// Das Rating muss entweder in die Datanbank geschrieben werden, oder
		// geupdated werden.
		int userID;
		int barID;
		int update = -1;
		int ratingID = -1;
		int generalRatingBar = 0;
		int pprRatingBar = 0;
		int musicRatingBar = 0;
		int peopleRatingBar = 0;
		int atmosphereRatingBar = 0;
		int generalRating = 0;
		int pprRating = 0;
		int musicRating = 0;
		int peopleRating = 0;
		int atmosphereRating = 0;
		int ratingCount = 0;
		Connection con = verbindung();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(
				"SELECT * FROM ratings WHERE userID=" + rating.getUserID() + " AND barID = " + rating.getBarID());

		while (rs.next()) {
			userID = rs.getInt(USER_ID_STRING);
			barID = rs.getInt(BAR_ID_STRING);

			if (userID == rating.getUserID() && barID == rating.getBarID()) {
				update = 1;
				// Holt die Ratings des zu aktualisierenden Ratings sowie die ID
				// aus der DB
				generalRating = rs.getInt(GENERAL_RATING_STRING);
				pprRating = rs.getInt(PPR_RATING_STRING);
				musicRating = rs.getInt(MUSIC_RATING_STRING);
				peopleRating = rs.getInt(PEOPLE_RATING_STRING);
				atmosphereRating = rs.getInt(ATMOSPHERE_RATING_STRING);
				ratingID = rs.getInt("ratingID");

				break;
			}
		}
		rs.close();

		if (update == 1) {
			rs = st.executeQuery("SELECT * FROM bars WHERE barID=" + rating.getBarID());
			if (rs.next()) {
				// Holt die alten Ratings der Bar aus der Datenbank
				generalRatingBar = rs.getInt(GENERAL_RATING_STRING);
				pprRatingBar = rs.getInt(PPR_RATING_STRING);
				musicRatingBar = rs.getInt(MUSIC_RATING_STRING);
				peopleRatingBar = rs.getInt(PEOPLE_RATING_STRING);
				atmosphereRatingBar = rs.getInt(ATMOSPHERE_RATING_STRING);
			}
			rs.close();

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

			// Aktualisiert das Rating
			st.executeUpdate("UPDATE ratings SET generalRating = '" + rating.getGeneralRating() + "', pprRating = '"
					+ rating.getPprRating() + "', musicRating = '" + rating.getMusicRating() + "', peopleRating = '"
					+ rating.getPeopleRating() + "', atmosphereRating = '" + rating.getAtmosphereRating()
					+ "' WHERE ratingID= '" + ratingID + "';");
		} else {

			st.executeUpdate(
					"INSERT INTO ratings (userID,barID,generalRating,pprRating,musicRating,peopleRating,atmosphereRating) VALUES ('"
							+ rating.getUserID() + "','" + rating.getBarID() + "','" + rating.getGeneralRating() + "','"
							+ rating.getPprRating() + "','" + rating.getMusicRating() + "','" + rating.getPeopleRating()
							+ "','" + rating.getAtmosphereRating() + "');");

			// Liest das RatingCount aus und erhöht um 1
			ResultSet rs2 = st.executeQuery("SELECT ratingCount FROM bars WHERE barID=" + rating.getBarID());
			if (rs2.next()) {
				ratingCount = rs2.getInt("ratingCount") + 1;
			}
			rs2.close();

			// Speichert das neue RatingCount
			st.executeUpdate("UPDATE bars SET ratingCount = " + ratingCount + " WHERE barID=" + rating.getBarID());

			rs = st.executeQuery("SELECT * FROM bars WHERE barID=" + rating.getBarID());
			if (rs.next()) {
				// Holt die alten Ratings der Bar aus der Datenbank
				generalRatingBar = rs.getInt(GENERAL_RATING_STRING);
				pprRatingBar = rs.getInt(PPR_RATING_STRING);
				musicRatingBar = rs.getInt(MUSIC_RATING_STRING);
				peopleRatingBar = rs.getInt(PEOPLE_RATING_STRING);
				atmosphereRatingBar = rs.getInt(ATMOSPHERE_RATING_STRING);
			}
			rs.close();

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
	public static List<Special> readCurrentSpecials(int barID) throws SQLException {
		int userID;
		String message;
		Timestamp begin;
		Timestamp end;
		Timestamp created;

		ArrayList<Special> list = new ArrayList<>();

		String sql = "SELECT * FROM specials WHERE barID =?";
		try (Connection con = verbindung(); PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, barID);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				userID = rs.getInt(USER_ID_STRING);
				message = rs.getString(MESSAGE_STRING);
				begin = rs.getTimestamp(BEGIN_STRING);
				end = rs.getTimestamp("end");
				created = rs.getTimestamp(CREATED_STRING);
				java.util.Date date = new java.util.Date();

				if (date.after(end) && end.before(date)) {
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
			con.close();
			st.close();
			// FIX
		}
		return list;
	}

	/**
	 * Gibt die alle Specials einer Bar zurück
	 * 
	 * @param barID
	 *            Die Bar, zu der die Specials gesucht werden
	 * @return Eine Liste mit allen Specials.
	 * @throws SQLException
	 */
	public static List<Special> readAllSpecials(int barID) throws SQLException {
		ArrayList<Special> list = new ArrayList<>();

		String sql = "SELECT * FROM specials WHERE barID =?";
		try (Connection con = verbindung(); PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, barID);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int userID = rs.getInt(USER_ID_STRING);
				String message = rs.getString(MESSAGE_STRING);
				Timestamp begin = rs.getTimestamp(BEGIN_STRING);
				Timestamp end = rs.getTimestamp("end");
				Timestamp created = rs.getTimestamp(CREATED_STRING);

				Special special = new Special();
				special.setBegin(begin);
				special.setCreated(created);
				special.setEnd(end);
				special.setMessage(message);
				special.setUserID(userID);
				special.setBarID(barID);

				list.add(special);
			}
			rs.close();
			st.close();
			con.close();
		}
		return list;
	}

	/**
	 * Methode um ein Special aus der Datenbank auszulesen.
	 * 
	 * @param specialID
	 *            Das Special, as ausgelesen werden soll
	 * @return Gibt das ausgelesene Special zurück.
	 */
	public static Special readSpecial(int specialID) throws SQLException {
		if (specialID < 0) {
			throw new IllegalArgumentException("Invalid special ID");
		}
		// Zum special alle Informationen auslesen und ausgeben
		int barID = 0;
		int userID;
		String message;
		Timestamp begin;
		Timestamp end;
		Timestamp created;

		String sql = "SELECT * FROM specials WHERE specialID=?";
		try (Connection con = verbindung(); PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, specialID);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				userID = rs.getInt(USER_ID_STRING);
				message = rs.getString(MESSAGE_STRING);
				begin = rs.getTimestamp(BEGIN_STRING);
				end = rs.getTimestamp("end");
				created = rs.getTimestamp(CREATED_STRING);

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
		}
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
		st.executeUpdate("INSERT INTO specials (barID,userID,message,begin,end,created) VALUES ('" + special.getBarID()
				+ "','" + special.getUserID() + "','" + special.getMessage() + "','" + special.getBegin() + "','"
				+ special.getEnd() + "','" + special.getCreated() + "');");
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
		int isadmin;
		int userid;

		String sql = "SELECT * FROM user WHERE email=? AND password=? ";
		try (Connection con = verbindung(); PreparedStatement st = con.prepareStatement(sql);) {
			st.setString(1, useremail);
			st.setString(2, password);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				isadmin = rs.getInt("isAdmin");
				userid = rs.getInt(USER_ID_STRING);
				if (isadmin == 0) {
					InternalUser user = giveUser(userid);
					rs.close();
					st.close();
					con.close();
					return user;
				} else {
					Admin user = new Admin(giveUser(userid));
					rs.close();
					st.close();
					con.close();
					return user;
				}
			}
			rs.close();
		}
		return null;

	}

	/**
	 * Get all the bars that an admin may administrate
	 * 
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public static List<Bar> getBarsOfAdmin(int userID) throws SQLException {
		List<Bar> bars = new ArrayList<>();

		String sql = "SELECT barID FROM bars WHERE userID=?";

		ResultSet rs = null;
		try (Connection con = verbindung(); PreparedStatement statement = con.prepareStatement(sql);) {
			statement.setInt(1, userID);
			rs = statement.executeQuery();

			while (rs.next()) {
				bars.add(readBar(rs.getInt("barID")));
			}

			rs.close();
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

		return bars;
	}

	/**
	 * Methode, um ein Rating von einem User zu einer Bar zu finden
	 * 
	 * @param userID
	 *            Die UserID des anfragenden Users
	 * @param barID
	 *            Die BarID, für die das Rating verlngt wird
	 * @return Wenn das Rating gefunden wird, wird es zurückgegeben, sonst null.
	 * @throws SQLException
	 */
	public static Rating getRating(int userID, int barID) throws SQLException {
		String sql = "SELECT * FROM ratings WHERE userID=? AND barID=?";
		ResultSet rs = null;
		try (Connection con = verbindung(); PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, userID);
			st.setInt(2, barID);

			rs = st.executeQuery();

			while (rs.next()) {
				int ratingID = rs.getInt("ratingID");
				int generalRating = rs.getInt(GENERAL_RATING_STRING);
				int pprRating = rs.getInt(PPR_RATING_STRING);
				int musicRating = rs.getInt(MUSIC_RATING_STRING);
				int peopleRating = rs.getInt(PEOPLE_RATING_STRING);
				int atmosphereRating = rs.getInt(ATMOSPHERE_RATING_STRING);
				int userIDdb = rs.getInt(USER_ID_STRING);
				int barIDdb = rs.getInt(BAR_ID_STRING);
				Timestamp time = rs.getTimestamp("time");
				return new Rating(ratingID, userIDdb, barIDdb, generalRating, pprRating, musicRating, peopleRating,
						atmosphereRating, time);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return null;
	}

	/**
	 * Methode, um zu bestimmen, ob ein User eine Bar bereits bewertet hat.
	 * 
	 * @param userID
	 *            Die Id des Users
	 * @param barID
	 *            Die ID der Bar
	 * @return Gibt true zurück, wenn die Bar bereits bewertet wurde,
	 *         andernfalls false.
	 * @throws SQLException
	 */
	public static boolean hasUserRatedBar(int userID, int barID) throws SQLException {
		Connection con = verbindung();

		String sql = "SELECT * FROM ratings WHERE userID=? AND barID=?";
		PreparedStatement prepareStatement = con.prepareStatement(sql);
		prepareStatement.setInt(1, userID);
		prepareStatement.setInt(2, barID);

		ResultSet rs = prepareStatement.executeQuery();
		boolean rated = rs.next();

		rs.close();
		prepareStatement.close();
		con.close();
		return rated;
	}

	/**
	 * Methode, um einen User zu speichern
	 * 
	 * @param user
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
			userID = rs.getInt(USER_ID_STRING);
		}

		// Update user
		if (userID != -1) {
			query = "UPDATE user ";
		} else {
			query = "INSERT INTO user ";

		}

		query += "SET name = '" + user.getName() + "', firstname = '" + user.getFirstname() + "', email = '"
				+ user.getEmail() + "',  password = '" + user.getPassword() + "', sessionID = '" + user.getSessionID()
				+ "', birthdate = '" + user.getBirthdate() + "', city = '" + user.getAddress().getCity()
				+ "', street = '" + user.getAddress().getStreet() + "', number = '" + user.getAddress().getNumber()
				+ "', zip = '" + user.getAddress().getZip() + "'";
		if (userID != -1) {
			query += " WHERE userID = " + userID + ";";
		} else {
			query += ";";
		}
		st.executeUpdate(query);

		st.close();
		rs.close();
		con.close();

		return userID;
	}

	/**
	 * Methode, um eine Email zu ändern
	 * 
	 * @param user
	 *            Der User, dessen Email geändert werden soll.
	 * 
	 * @param email
	 *            Die neue Email.
	 * 
	 * @return true: geändert, false: user nicht vorhanden
	 * @throws SQLException
	 */
	public static boolean changeEmail(String email) throws SQLException {
		// TODO: fix this method!!!
		int userID = 0;
		int update = 0;
		String userEmail;
		Connection con = verbindung();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM user");
		while (rs.next()) {
			userEmail = rs.getString(EMAIL_STRING);
			userID = rs.getInt(USER_ID_STRING);
			if (userEmail.equals(email)) {
				update = 1;
				break;
			}
		}
		rs.close();

		if (update == 1) {
			st.executeUpdate("UPDATE user SET email = '" + email + "' WHERE userID= '" + userID + "';");
			st.close();
			con.close();
			return true;
		}
		st.close();
		con.close();
		return false;
	}

	/**
	 * Speichert eine Message und gibt sie zurück,wenn das Speichern erfolgreich
	 * war.
	 * 
	 * @param message
	 *            Die zu speichernde Message
	 * @return Gibt die Message zurück, wenn die Message problemlos gespeichert
	 *         wurde.
	 * @throws SQLException
	 */
	public static Message saveMessage(Message message) throws SQLException {
		Connection con = verbindung();
		Statement st = con.createStatement();
		Integer messageID = -1;
		st.executeUpdate("INSERT INTO message (userID,barID,message)" + " VALUES ('" + message.getUserID() + "','"
				+ message.getBarID() + "','" + message.getMessageText() + "');");
		ResultSet rs = st.executeQuery("select last_insert_id() as last_id from message");
		if (rs.next()) {
			messageID = rs.getInt("last_id");
		}
		rs.close();
		st.close();
		con.close();
		message.setMessageID(messageID);
		return message;
	}

	/**
	 * Get a question by it's ID
	 * 
	 * @param questionID
	 *            The ID of the question to read
	 * @return
	 * @throws SQLException
	 */
	public static Question readQuestion(int questionID) throws SQLException {
		Question ret = null;
		String query = "SELECT * FROM questions WHERE questionID = ?;";
		ResultSet rs = null;
		
		try (Connection con = verbindung(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, questionID);
			rs = st.executeQuery();
		
		while (rs.next()) {
			ret = new Question();
			ret.setBarID(rs.getInt(BAR_ID_STRING));
			ret.setMessage(rs.getString(MESSAGE_STRING));
			ret.setQuestionID(rs.getInt("questionID"));
			ret.setText(rs.getBoolean("text"));

		}
		rs.close();
		st.close();
		con.close();
		}
		return ret;
	}

	/**
	 * Get all the Questions of a bar
	 * 
	 * @param barID
	 * @return
	 * @throws SQLException
	 */
	public static List<Question> getQuestionsForBar(int barID) throws SQLException {

		ResultSet rs = null;
		List<Question> questions = new ArrayList<>();
		String sql = "SELECT questionID FROM questions WHERE barID=?";
		try (Connection con = verbindung(); PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, barID);
			rs = st.executeQuery();

			while (rs.next()) {
				questions.add(readQuestion(rs.getInt("questionID")));
			}

		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return questions;
	}

	/**
	 * Get an answer by it's ID
	 * 
	 * @param answerID
	 * @return
	 * @throws SQLException
	 */
	public static Answer readAnswer(int answerID) throws SQLException {
		Answer ret = null;

		ResultSet rs = null;
		String sql = "SELECT * FROM answers WHERE answerID=?";
		try (Connection con = verbindung(); PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, answerID);
			rs = st.executeQuery();
		while (rs.next()) {
			ret = new Answer();
			ret.setAnswerID(rs.getInt("answerID"));
			ret.setQuestionID(rs.getInt("questionID"));
			ret.setUserID(rs.getInt(USER_ID_STRING));
			ret.setAnswerString(rs.getString("answerString"));
			ret.setText(rs.getBoolean("isText"));
			ret.setAnswerBool(rs.getBoolean("answerBool"));

		}
		rs.close();
		st.close();
		con.close();
		}
		return ret;
	}

	/**
	 * Save an answer
	 * 
	 * @param answer
	 * @throws SQLException
	 */
	public static void saveAnswer(Answer answer) throws SQLException {
		Connection con = verbindung();
		Statement st = con.createStatement();
		st.executeUpdate("INSERT INTO answers (questionID,userID,answerString,isText,answerBool) VALUES ('"
				+ answer.getQuestionID() + "','" + answer.getUserID() + "','" + answer.getAnswerString() + "','"
				+ (answer.isText() ? 1 : 0) + "','" + (answer.getAnswerBool() ? 1 : 0) + "');");
		st.close();
		con.close();
	}

	/**
	 * Search a bar by a string
	 * 
	 * @param bar
	 * @return
	 * @throws SQLException
	 */
	public static List<Bar> searchForBar(String bar) throws SQLException {

		Connection con = verbindung();
		String query = "SELECT barID FROM bars WHERE name LIKE ?";
		PreparedStatement prepareStatement = con.prepareStatement(query);
		prepareStatement.setString(1, "%" + bar + "%");
		ResultSet rs = prepareStatement.executeQuery();
		List<Bar> ret = new ArrayList<>();

		while (rs.next()) {
			ret.add(readBar(rs.getInt(BAR_ID_STRING)));
		}
		rs.close();
		prepareStatement.close();
		con.close();

		return ret;
	}

	/**
	 * Get all the Tags from a bar
	 * 
	 * @param tag
	 * @return
	 * @throws SQLException
	 */
	public static List<Bar> getBarsForTag(String tag) throws SQLException {
		String sql = "SELECT barID FROM tags WHERE name=?";
		List<Bar> bars = new ArrayList<>();

		try (Connection con = verbindung(); PreparedStatement statement = con.prepareStatement(sql);) {
			statement.setString(1, tag);

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				bars.add(readBar(rs.getInt(BAR_ID_STRING)));
			}
			rs.close();
		}

		return bars;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public static boolean exitsEmail(String email) throws SQLException {
		ResultSet resultSet = null;
		String sql = "SELECT id FROM users WHERE email = ?";
		try (Connection con = verbindung(); PreparedStatement statement = con.prepareStatement(sql);) {
			statement.setString(1, email);
			resultSet = statement.executeQuery();
			boolean returnValue = resultSet.next();
			resultSet.close();
			return returnValue;
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
		}
	}
}
