package com.example.designertest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.model.DisplayRating;
import de.kaniba.model.InternalUser;
import de.kaniba.model.Message;
import de.kaniba.model.Rating;
import de.kaniba.model.User;
import de.kaniba.presenter.Utils;

public class BarView extends BarDesign implements View {
	protected GoogleMap map;

	private BarPresenter presenter;

	public BarView() {

		// Resize the grid
		removeComponent(leftGrid);
		addComponent(leftGrid, 0, 0, 0, 1);

		leftGrid.removeComponent(barImage);
		leftGrid.addComponent(barImage, 0, 0, 0, 1);
		leftGrid.removeComponent(infoPanel);
		leftGrid.addComponent(infoPanel, 0, 2, 1, 2);
		leftGrid.removeComponent(messageAreaGrid);
		leftGrid.addComponent(messageAreaGrid, 0, 3, 1, 3);
		messageAreaGrid.removeComponent(messagePanel);
		messageAreaGrid.addComponent(messagePanel, 0, 0, 1, 0);

		infoPanel.setContent(new Label(
				"Hallo, wir sind das St&ouml;vchen. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
				ContentMode.HTML));
		map = new GoogleMap("apiKey", null, "german");
		map.setSizeFull();
		setRowExpandRatio(0, 1.0f);
		addComponent(map, 1, 0);

		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/stoevchen.png"));
		barImage.setHeightUndefined();
		barImage.setSource(resource);

		rateButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				saveRatingClick();
			}
		});

		starTotal.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				generalRatingStarClick();
			}
		});

		starPeople.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				peopleRatingStarClick();
			}
		});

		starAtmosphere.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				atmosphereRatingStarClick();
			}
		});

		starMusic.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				musicRatingStarClick();
			}
		});

		starPrice.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				priceRatingStarClick();
			}
		});
		
		sendMessageButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				presenter.sendMessage(messageTextField.getValue());
			}
		});
	}

	protected void priceRatingStarClick() {
		Rating rating = new Rating(-1, -1, -1, 0, 0, 0, 0, 0, null);
		rating.setPprRating(starPrice.getValue().intValue());
		presenter.saveRating(rating);
	}

	protected void musicRatingStarClick() {
		Rating rating = new Rating(-1, -1, -1, 0, 0, 0, 0, 0, null);
		rating.setMusicRating(starMusic.getValue().intValue());
		presenter.saveRating(rating);
	}

	protected void atmosphereRatingStarClick() {
		Rating rating = new Rating(-1, -1, -1, 0, 0, 0, 0, 0, null);
		rating.setAtmosphereRating(starAtmosphere.getValue().intValue());
		presenter.saveRating(rating);
	}

	protected void peopleRatingStarClick() {
		Rating rating = new Rating(-1, -1, -1, 0, 0, 0, 0, 0, null);
		rating.setPeopleRating(starPeople.getValue().intValue());
		presenter.saveRating(rating);
	}

	private void generalRatingStarClick() {
		Rating rating = new Rating(-1, -1, -1, 0, 0, 0, 0, 0, null);
		rating.setGeneralRating(starTotal.getValue().intValue());
		presenter.saveRating(rating);
	}

	private void saveRatingClick() {
		Rating rating = new Rating(-1, -1, -1, 0, 0, 0, 0, 0, null);
		rating.setAtmosphereRating(starAtmosphere.getValue().intValue());
		rating.setPeopleRating(starPeople.getValue().intValue());
		rating.setPprRating(starPrice.getValue().intValue());
		rating.setGeneralRating(starTotal.getValue().intValue());
		rating.setMusicRating(starMusic.getValue().intValue());
		presenter.saveRating(rating);
	}

	public void setPresenter(BarPresenter presenter) {
		this.presenter = presenter;
	}

	public void setUserRating(User user) {

	}

	public void setBarMessageBoard(List<Message> messages) {
		
		List<Component> components = new ArrayList<>();
		messages.forEach(element -> {
			InternalUser user = null;
			try {
				user = Database.giveUser(element.getUserID());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(user != null) {
				String text = "";
				text += element.getTime().getDate() + "." + element.getTime().getMonth() + "." + (element.getTime().getYear()+1900) + "";
				text += " <b>" + user.getName() + " " + user.getFirstname().substring(0, 1) + "." + "</b>: ";
				text += element.getMessage();
				CssLayout layout = new CssLayout();
				Label component = new Label(text, ContentMode.HTML);
				layout.addComponent(component);
				layout.setWidth("100%");
				components.add(component);
			}
		});
		
		for(int i = 0; i < components.size(); i++) {
			if(i % 2 == 1) {
				components.get(i).addStyleName("message-odd");
			} else {
				components.get(i).addStyleName("message-even");
			}
		}
		
		VerticalLayout layout = new VerticalLayout();
		components.forEach(e -> layout.addComponent(e));
		
		messagePanel.setContent(layout);
	}

	public void setBarRating(DisplayRating rating) {
		starTotal.setValue(rating.getGeneralRating());
		starAtmosphere.setValue(rating.getAtmosphereRating());
		starMusic.setValue(rating.getMusicRating());
		starPeople.setValue(rating.getPeopleRating());
		starPrice.setValue(rating.getPriceRating());
	}

	public void setBarLogo(Bar bar) {
		// TODO Auto-generated method stub

	}

	public void setMapCoords(LatLon coords) {
		map.setCenter(coords);
		map.addMarker(new GoogleMapMarker("", coords, false));
	}

	public void setBarName(String name) {
		barNameLabel.setValue(name);
	}

	public void setBarAddress(String address) {
		barAddressLabel.setValue(address);
	}

	public void setBarDescription(String description) {
		infoPanel.setContent(new Label(description, ContentMode.HTML));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		presenter.enter(event);
	}
}
