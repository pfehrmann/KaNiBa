package kaniba.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.kaniba.model.ConnectionCreater;
import de.kaniba.model.Database;
import de.kaniba.utils.LoggingUtils;

public class Utils {
	public static void prepareDatabaseForTests() {
		ConnectionCreater connectionCreater = new ConnectionCreater() {

			@Override
			public Connection verbindung() throws SQLException {
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					LoggingUtils.log("Fehler bei MySQL-JDBC-Bridge: ");
					LoggingUtils.exception(e);
					return null;
				}
				// VERBINDUNG
				return DriverManager.getConnection("jdbc:mysql://www.db4free.net:3306/kaniba", "kaniba", "kanibatest");
			}
		};

		Database.setConnectionCreater(connectionCreater);
	}
}
