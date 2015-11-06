package de.kaniba.vaadin;

import java.util.List;

public class Admin extends InternalUser {
	List<Bar> ownedBars;
	
	public void createBar(Bar bar) {
		throw new RuntimeException("implement me");
	}
	
	public void createSpecial(Bar bar, Special special) {
		throw new RuntimeException("implement me");
	}
}
