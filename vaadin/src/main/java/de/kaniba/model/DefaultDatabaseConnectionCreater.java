/**
 * 
 */
package de.kaniba.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.kaniba.model.ConnectionCreater;
import de.kaniba.utils.LoggingUtils;

/**
 * This class returns a connection to either the database on kaniba or a local
 * database. If used on a local machine, make sure the mysql server is running.
 * 
 * @author Philipp
 *
 */
final class DefaultDatabaseConnectionCreater implements ConnectionCreater {
	@Override
	public Connection verbindung() throws SQLException {
		// TREIBER
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LoggingUtils.log("Fehler bei MySQL-JDBC-Bridge: ");
			LoggingUtils.exception(e);
			return null;
		}
		// VERBINDUNG
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/kaniba", "root", getPassword());
	}

	/**
	 * This method generated / raeds the password.
	 * 
	 * @return Returns the password, if dound on the filesystem or returns ""
	 */
	private static String getPassword() {
		// Default password for local tests
		String password = "";

		// try to find the password file
		try {
			BufferedReader reader = new BufferedReader(new FileReader("/home/kanibaPassword"));
			password = reader.readLine();
			reader.close();
		} catch (IOException e) {
			LoggingUtils.log("Password not available, will use standard password.");
		}

		return password;
	}
}