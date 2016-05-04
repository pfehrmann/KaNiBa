package de.kaniba.model;

import java.sql.Date;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.kaniba.utils.Utils;
import de.kaniba.view.Callback;
import de.kaniba.view.SearchView;

/**
 * Class to represent a tag.
 * @author Philipp
 *
 */
public class Tag {
	public static final int INVALID_TAG_ID = -1;
	private int tagID;
	private int userID;
	private int barID;
	private String name;
	private Date created;
	
	/**
	 * Create a tag and initialize it to an unknown id.
	 */
	public Tag() {
		this.tagID = INVALID_TAG_ID;
	}
	
	public int getBarID() {
		return barID;
	}

	public void setBarID(int barID) {
		this.barID = barID;
	}

	public void setTagID(int tagID) {
		this.tagID = tagID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getTagID() {
		return tagID;
	}

	public int getUserID() {
		return userID;
	}

	public String getName() {
		return name;
	}

	public Date getCreated() {
		return created;
	}
	
	public void saveTag() {
		Database.saveTag(this);
	}

	public Component getComponent() {
		return new Link(getName(), new ExternalResource("#!" + SearchView.NAME + "/" + getName()));
	}
	
	public static void createNewTag(final int barID, final Callback callback) {
		if(!User.isLoggedIn()) {
			Utils.showNotification("Um Tags setzen zu können, musst du eingeloggt sein.");
			return;
		}
		final Window window = new Window("Neuen Tag erstellen");
		
		HorizontalLayout layout = new HorizontalLayout();
		
		final TextField textField = new TextField();
		layout.addComponent(textField);
		layout.setExpandRatio(textField, 1.0F);
		
		Button button = new Button("Speichern");
		button.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Tag tag = new Tag();
				tag.setBarID(barID);
				tag.setName(textField.getValue());
				tag.setUserID(InternalUser.getUser().getUserID());
				tag.saveTag();
				window.close();
				callback.success();
			}
		});
		layout.addComponent(button);
		window.setContent(layout);
		window.setModal(true);
		window.setWidth("300px");
		window.setResizable(false);
		UI.getCurrent().addWindow(window);
	}
}
