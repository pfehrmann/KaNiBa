package de.kaniba.uiInterfaces;

import com.vaadin.navigator.View;

public interface SecuredView extends View {
	public boolean checkRights(String parameters);
}
