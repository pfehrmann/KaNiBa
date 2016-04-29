package de.kaniba.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.kaniba.utils.Utils;

/**
 * This class represents an admin.
 * @author Philipp
 *
 */
public class Admin extends InternalUser {
	private List<Bar> ownedBars;
	
	/**
	 * Create a new Admin from an existing user.
	 * @param user
	 */
	public Admin(InternalUser user) {
		super.setAddress(user.getAddress());
		super.setBirthdate(user.getBirthdate());
		super.setEmail(user.getEmail());
		super.setFirstname(user.getFirstname());
		super.setName(user.getName());
		super.setPassword(user.getPassword());
		super.setUserID(user.getUserID());
		
		// TODO: Die Datenbank muss angepasst werden, es gibt keine Verknüpfungen zwischen Admins und Bars...
	}
	
	public void createBar(Bar bar) throws SQLException {
		Database.saveBar(bar);
	}
	
	public void createSpecial(Special special) throws SQLException {
		Database.saveSpecial(special);
	}
	
	public List<Bar> getOwnedBars() {
		// FIX: Die Daten müssen schon drinstehen...
		try {
			return Utils.copyList(Database.searchForBar(""));
		} catch (SQLException e) {
			Utils.exception(e);
		}
		return new ArrayList<>();
	}
	
	public void setOwnedBars(List<Bar> bars) {
		ownedBars = Utils.copyList(bars);
	}
}
