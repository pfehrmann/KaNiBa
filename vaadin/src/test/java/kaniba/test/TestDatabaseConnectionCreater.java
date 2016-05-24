/**
 * 
 */
package kaniba.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.kaniba.model.ConnectionCreater;
import de.kaniba.utils.LoggingUtils;

/**
 * @author phili
 *
 */
final public class TestDatabaseConnectionCreater implements ConnectionCreater {
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
}