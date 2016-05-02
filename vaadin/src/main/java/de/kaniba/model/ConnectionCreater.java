package de.kaniba.model;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionCreater {
	public Connection verbindung() throws SQLException;
}
