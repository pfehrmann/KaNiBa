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
 * @author phili
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
	
	private static String getPassword() {
		String password = "";

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