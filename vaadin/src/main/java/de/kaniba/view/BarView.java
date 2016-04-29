package de.kaniba.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.GoogleMapControl;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

import de.kaniba.designs.BarDesign;
import de.kaniba.model.Bar;
import de.kaniba.model.Database;
import de.kaniba.model.DisplayRating;
import de.kaniba.model.InternalUser;
import de.kaniba.model.Message;
import de.kaniba.model.Rating;
import de.kaniba.model.User;
import de.kaniba.presenter.BarPresenter;
import de.kaniba.utils.Utils;

public class BarView extends BarDesign implements View {
	public static final String NAME = "bar";
	
	protected GoogleMap map;

	private BarPresenter presenter;

	public BarView() {

		// Resize the grid
		removeComponent(leftGrid);
		addComponent(leftGrid, 0, 0, 0, 1);

		infoPanel.setContent(new Label("Keine Beschreibung verfügbar", ContentMode.HTML));
		map = new GoogleMap("apiKey", null, "german");
		map.setSizeFull();
		map.setZoom(14);
		map.removeControl(GoogleMapControl.MapType);
		map.removeControl(GoogleMapControl.StreetView);
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
		
		surveyButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				presenter.clickedSurvey();
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

	public void setBarMessageBoard(List<Message> messages) {
		
		final List<Component> components = new ArrayList<>();
		
		for(Message element : messages) {
			InternalUser user = null;
			try {
				user = Database.giveUser(element.getUserID());
			} catch (Exception e) {
				Utils.exception(e);
			}
			
			if(user != null) {
				String text = "";
				text += element.getTime().getDate() + "." + element.getTime().getMonth() + "." + (element.getTime().getYear()+1900) + "";
				text += " <b>" + user.getFirstname() + " " + user.getName().substring(0, 1) + "." + "</b>: ";
				text += element.getMessageText();
				CssLayout layout = new CssLayout();
				Label component = new Label(text, ContentMode.HTML);
				layout.addComponent(component);
				layout.setWidth("100%");
				components.add(component);
			}
		}
		
		for(int i = 0; i < components.size(); i++) {
			if(i % 2 == 1) {
				components.get(i).addStyleName("message-odd");
			} else {
				components.get(i).addStyleName("message-even");
			}
		}
		
		VerticalLayout layout = new VerticalLayout();
		for(Component e : components) {
			layout.addComponent(e);
		}
		
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
		removeComponent(map);
		map = new GoogleMap("apiKey", null, "german");
		map.setSizeFull();
		map.setZoom(15);
		map.removeControl(GoogleMapControl.MapType);
		map.removeControl(GoogleMapControl.StreetView);
		setRowExpandRatio(0, 1.0f);
		addComponent(map, 1, 0);
		
		presenter.enter(event);
	}
}
