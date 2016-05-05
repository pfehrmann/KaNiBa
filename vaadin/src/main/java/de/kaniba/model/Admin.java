package de.kaniba.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.kaniba.utils.LoggingUtils;
import de.kaniba.utils.Utils;

/**
 * This class represents an admin.
 * 
 * @author Philipp
 *
 */
public class Admin extends InternalUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new Admin from an existing user.
	 * 
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
	}

	/**
	 * Creates a bar for this Admin. Note that all the attributes, including the
	 * userID have to be set.
	 * 
	 * @param bar
	 *            The bar to save.
	 * @throws SQLException
	 */
	public void createBar(Bar bar) throws SQLException {
		Database.saveBar(bar);
	}

	/**
	 * Create a special. Note that all attributes have to be set.
	 * 
	 * @param special
	 * @throws SQLException
	 */
	public void createSpecial(Special special) throws SQLException {
		Database.saveSpecial(special);
	}

	/**
	 * Reads the list of bars owned by this admin from the database and returns
	 * it.
	 * 
	 * @return The list of bars, an admin may edit.
	 */
	public List<Bar> getOwnedBars() {
		try {
			return Utils.copyList(Database.getBarsOfAdmin(getUserID()));
		} catch (SQLException e) {
			LoggingUtils.exception(e);
		}
		return new ArrayList<>();
	}
}
