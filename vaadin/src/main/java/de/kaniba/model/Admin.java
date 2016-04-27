package de.kaniba.model;

import java.sql.SQLException;
import java.util.List;

public class Admin extends InternalUser {
	private List<Bar> ownedBars;
	
	public Admin() {
		super();
	}
	public Admin(InternalUser user) {
		super();
	}
	
	public void createBar(Bar bar) throws SQLException {
		Database.saveBar(bar);
	}
	
	public void createSpecial(Special special) throws SQLException {
		Database.saveSpecial(special);
	}
}
