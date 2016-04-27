package de.kaniba.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends InternalUser {
	private List<Bar> ownedBars;
	
	public void createBar(Bar bar) throws SQLException {
		Database.saveBar(bar);
	}
	
	public void createSpecial(Special special) throws SQLException {
		Database.saveSpecial(special);
	}
	
	public List<Bar> getOwnedBars() {
		List<Bar> bars = new ArrayList<>();
		for(Bar bar : ownedBars) {
			bars.add(bar);
		}
		return bars;
	}
	
	public void setOwnedBars(List<Bar> bars) {
		while(!ownedBars.isEmpty()) {
			ownedBars.remove(0);
		}
		for(Bar bar : bars) {
			ownedBars.add(bar);
		}
	}

	public Admin() {
		super();
	}
	public Admin(InternalUser user) {
		super();
	}
}
