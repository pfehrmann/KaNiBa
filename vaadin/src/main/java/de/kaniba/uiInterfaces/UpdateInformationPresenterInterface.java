package de.kaniba.uiInterfaces;

import java.io.Serializable;

import com.vaadin.ui.Button.ClickEvent;

public interface UpdateInformationPresenterInterface extends SecuredPresenter, Serializable {

	void updateClickListener(ClickEvent event);

	void enter();

}