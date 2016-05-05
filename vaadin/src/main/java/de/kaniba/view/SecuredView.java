package de.kaniba.view;

import com.vaadin.navigator.View;

public interface SecuredView extends View {
	public boolean checkRights(String parameters);
}
