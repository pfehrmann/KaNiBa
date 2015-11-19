package de.kaniba.vaadin;

import java.util.List;

public class Admin extends InternalUser {
	List<Bar> ownedBars;
	
	public void createBar(Bar bar) {
		Database.saveBar(bar);
	}
	
	public void createSpecial(Special special) {
		Database.saveSpecial(special);
	}
}
