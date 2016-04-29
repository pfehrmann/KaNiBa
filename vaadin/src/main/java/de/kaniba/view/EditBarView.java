package de.kaniba.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Upload.Receiver;

import de.kaniba.designs.EditBarDesign;
import de.kaniba.model.Address;
import de.kaniba.model.Bar;
import de.kaniba.presenter.EditBarPresenter;
import de.kaniba.utils.BarUtils;
import de.kaniba.utils.Utils;

public class EditBarView extends EditBarDesign implements View {
	public static final String NAME = "editBar";

	private EditBarPresenter presenter;

	public EditBarView() {
		submitButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				presenter.saveBar();
			}
		});
	}
	
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
	
	public void setBar(final Bar bar) {
		if(bar == null) {
			return;
		}
		
		nameField.setValue(bar.getName());
		streetField.setValue(bar.getAddress().getStreet());
		numberField.setValue(bar.getAddress().getNumber());
		zipField.setValue(bar.getAddress().getZip());
		cityField.setValue(bar.getAddress().getCity());
		descriptionArea.setValue(bar.getDescription());
		
		logoUpload.setReceiver(new Receiver() {

			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				FileOutputStream fos = null;
				try {
					File file = new File(Utils.getBarLogoBasePath() + bar.getBarID() + ".png");
					fos = new FileOutputStream(file);
				} catch (final java.io.FileNotFoundException e) {
					new Notification("Could not open file<br/>", e.getMessage(), Notification.Type.ERROR_MESSAGE)
							.show(Page.getCurrent());
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

	public void setPresenter(EditBarPresenter presenter) {
		this.presenter = presenter;
	}

}
