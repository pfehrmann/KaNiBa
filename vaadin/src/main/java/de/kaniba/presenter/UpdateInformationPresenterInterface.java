package de.kaniba.presenter;

import com.vaadin.ui.Button.ClickEvent;

public interface UpdateInformationPresenterInterface extends SecuredPresenter {

	void updateClickListener(ClickEvent event);

	void enter();

}