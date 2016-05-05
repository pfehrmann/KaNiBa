package de.kaniba.view;

import java.io.Serializable;

import com.vaadin.ui.Button.ClickEvent;

public interface UpdateInformationInterface extends Serializable {

	void updateClickListener(ClickEvent event);

	void enter();

}