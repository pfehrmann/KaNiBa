package de.kaniba.model;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * In interface to create connections. Useful when connecting to external databases.
 * @author Philipp
 *
 */
public interface ConnectionCreater {
	
	/**
	 * Create a connection.
	 * @return Returns a connection
	 * @throws SQLException
	 */
	public Connection verbindung() throws SQLException;
}
