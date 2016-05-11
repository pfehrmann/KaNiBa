package de.kaniba.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Upload.Receiver;

import de.kaniba.designs.EditBarDesign;
import de.kaniba.model.Address;
import de.kaniba.model.Bar;
import de.kaniba.uiInterfaces.EditBarPresenterInterface;
import de.kaniba.uiInterfaces.EditBarViewInterface;
import de.kaniba.utils.LoggingUtils;
import de.kaniba.utils.Utils;

/**
 * The for editing bars
 * 
 * @author Philipp
 *
 */
public class EditBarView extends EditBarDesign implements EditBarViewInterface {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "editBar";

	private EditBarPresenterInterface presenter;

	/**
	 * Create the view. Adds an click listener
	 */
	public EditBarView() {
		submitButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.saveBar();
			}
		});
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.EditBarViewInterface#getBar()
	 */
	@Override
	public Bar getBar() {
		Bar bar = new Bar();

		String street = streetField.getValue();
		String number = numberField.getValue();
		String zip = zipField.getValue();
		String city = cityField.getValue();
		bar.setAddress(new Address(city, street, number, zip));
		bar.setName(nameField.getValue());
		bar.setDescription(descriptionArea.getValue());

		return bar;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.EditBarViewInterface#setBar(de.kaniba.model.Bar)
	 */
	@Override
	public void setBar(final Bar bar) {
		if (bar == null) {
			return;
		}

		nameField.setValue(bar.getName());
		streetField.setValue(bar.getAddress().getStreet());
		numberField.setValue(bar.getAddress().getNumber());
		zipField.setValue(bar.getAddress().getZip());
		cityField.setValue(bar.getAddress().getCity());
		descriptionArea.setValue(bar.getDescription());

		logoUpload.setReceiver(new Receiver() {
			private static final long serialVersionUID = 1L;

			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				FileOutputStream fos = null;
				try {
					File file = new File(Utils.getBarLogoBasePath() + bar.getBarID() + ".png");
					fos = new FileOutputStream(file);
				} catch (final FileNotFoundException e) {
					new Notification("Could not open file<br/>", e.getMessage(), Notification.Type.ERROR_MESSAGE)
							.show(Page.getCurrent());
					LoggingUtils.exception(e);
					return null;
				}
				return fos;
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		presenter.enter(event);
	}

	/* (non-Javadoc)
	 * @see de.kaniba.view.EditBarViewInterface#setPresenter(de.kaniba.presenter.EditBarPresenterInterface)
	 */
	@Override
	public void setPresenter(EditBarPresenterInterface presenter) {
		this.presenter = presenter;
	}

	@Override
	public boolean checkRights(String parameters) {
		return presenter.checkRights(parameters);
	}

}
