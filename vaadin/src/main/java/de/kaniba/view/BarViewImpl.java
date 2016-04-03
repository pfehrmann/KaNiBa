package de.kaniba.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.data.Property;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.GoogleMapControl;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;

import de.kaniba.model.Address;
import de.kaniba.model.Bar;
import de.kaniba.model.Message;
import de.kaniba.model.Rating;

public class BarViewImpl extends CustomComponent implements BarView {

	public java.util.List<BarViewListener> listenerList = new ArrayList<BarViewListener>();

	public int generalRating;
	public int pprRating;
	public int musicRating;
	public int atmoRating;
	public int peopleRating;

	private boolean initializing = false;

	public RatingStars ratinggeneral;
	public RatingStars ratingatmo;
	public RatingStars ratingmusic;
	private RatingStars ratingppr;
	private RatingStars ratingpeople;

	private GoogleMap map;

	private TextArea messageArea;
	private Label barInfoText;
	private Label address;
	private String message;
	private TextField messageField;
	private Panel mainPanel;

	public BarViewImpl() {
		mainPanel = createMainPanel();

		Panel barInfo = createBarInfoPanel();
		Panel ratingPanel = createRatingPanel();
		Panel pinboardPanel = createMessagePanel();
		Image barImage = createBarPicture();
		Panel mapPanel = createMap();
		Panel addressPanel = createAddressPanel();

		GridLayout mainLayout = new GridLayout(2, 2);
		mainLayout.setColumnExpandRatio(1, 1.0f);
		mainLayout.setWidth("100%");
		mainLayout.setSpacing(true);
		mainLayout.addComponent(barImage, 0, 0);

		// Left column
		Layout left = new VerticalLayout();
		left.addComponent(addressPanel);
		left.addComponent(mapPanel);
		left.addComponent(ratingPanel);

		// ricght column
		Layout right = new VerticalLayout();
		right.addComponent(barInfo);
		right.addComponent(pinboardPanel);

		mainLayout.addComponent(left, 0, 1);
		mainLayout.addComponent(right, 1, 0, 1, 1);
		mainPanel.setContent(mainLayout);

		setCompositionRoot(mainPanel);
	}

	private Panel createBarInfoPanel() {
		barInfoText = new Label();
		Panel barInfoPanel = new Panel();

		barInfoText.addStyleName("borderless");
		barInfoText.setId("bar-view-textInfo");

		barInfoPanel.setContent(barInfoText);
		barInfoPanel.addStyleName("bar-info-panel");
		barInfoPanel.addStyleName("borderless");

		return barInfoPanel;
	}

	private Panel createMap() {
		Panel mapPanel = new Panel();

		map = new GoogleMap("apiKey", null, "german");
		map.setSizeFull();
		map.setZoom(14);
		map.setMinZoom(4);
		map.setMaxZoom(18);
		map.removeControl(GoogleMapControl.MapType);
		map.removeControl(GoogleMapControl.Pan);
		map.removeControl(GoogleMapControl.Rotate);
		map.removeControl(GoogleMapControl.Zoom);
		map.removeControl(GoogleMapControl.StreetView);
		map.removeControl(GoogleMapControl.Scale);

		mapPanel.setContent(map);
		return mapPanel;
	}

	private Panel createAddressPanel() {
		Panel addressPanel = new Panel();
		addressPanel.addStyleName("borderless");

		address = new Label();
		addressPanel.setContent(address);
		return addressPanel;
	}

	private Panel createMainPanel() {
		Panel mainPanel = new Panel();

		mainPanel.setWidth("100%");

		mainPanel.setId("bar-view-mainPanel");
		return mainPanel;
	}

	private Panel createMessagePanel() {
		Panel messagePanel = new Panel();
		messageArea = new TextArea();
		messageField = new TextField();

		HorizontalLayout messageHLayout = new HorizontalLayout();
		VerticalLayout messageVLayout = new VerticalLayout();
		Button messageSendButton = new Button("Senden");

		messageSendButton.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1111413494665766271L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				message = messageField.getValue();
				for (BarViewListener listener : listenerList) {
					listener.sendMessage(message);
				}
			}
		});

		messageArea.setCaption("Messageboard");
		messageArea.setSizeFull();
		messageField.setSizeFull();
		messageField.setWidth("100%");
		messageSendButton.setSizeUndefined();

		/* ID's für Selenium */
		messageArea.setId("bar-view-messageArea");
		messageField.setId("bar-view-messageField");
		messageSendButton.setId("bar-view-SendButton");

		messageField.setStyleName("messageField");
		messageHLayout.addComponent(messageField);
		messageHLayout.setExpandRatio(messageField, 1.0f);
		messageHLayout.addComponent(messageSendButton);
		messageVLayout.addComponent(messageArea);
		messageVLayout.addComponent(messageHLayout);
		messageHLayout.setSizeFull();
		messageVLayout.setSizeFull();
		messagePanel.setContent(messageVLayout);

		return messagePanel;
	}

	/**
	 * Create UI for the ratingstars
	 * 
	 * @return
	 */
	private Panel createRatingPanel() {
		// Diese Methode legt die Anzeigeelemente für die Sterne an
		ratinggeneral = new RatingStars();
		ratingatmo = new RatingStars();
		ratingmusic = new RatingStars();
		ratingppr = new RatingStars();
		ratingpeople = new RatingStars();

		Panel ratingpanel = new Panel();
		Button ratingButton = new Button("Speichern");

		ratingpanel.addStyleName("ratingpanel");
		Layout layout = new VerticalLayout();
		layout.setSizeFull();

		ratinggeneral.setMaxValue(5);
		ratingatmo.setMaxValue(5);
		ratingmusic.setMaxValue(5);
		ratingppr.setMaxValue(5);
		ratingpeople.setMaxValue(5);

		Property.ValueChangeListener ratingValueChangeListener = new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				if (!initializing) {
					saveRating();
				}
			}
		};

		/*
		 * This can only be done, if it is possible to save the rating if not all categorys are rated.
		ratinggeneral.addValueChangeListener(ratingValueChangeListener);
		ratingatmo.addValueChangeListener(ratingValueChangeListener);
		ratingmusic.addValueChangeListener(ratingValueChangeListener);
		ratingppr.addValueChangeListener(ratingValueChangeListener);
		ratingpeople.addValueChangeListener(ratingValueChangeListener);
		 */

		ratinggeneral.setImmediate(true);
		ratingatmo.setImmediate(true);
		ratingmusic.setImmediate(true);
		ratingppr.setImmediate(true);
		ratingpeople.setImmediate(true);

		/* Benennung für Selenium */
		ratinggeneral.setId("bar-view-ratinggeneral");
		ratingatmo.setId("bar-view-ratingatmo");
		ratingmusic.setId("bar-view-ratingmusic");
		ratingppr.setId("bar-view-ratingppr");
		ratingpeople.setId("bar-view-ratingpeople");

		ratinggeneral.addStyleName("borderless");
		ratingatmo.addStyleName("borderless");
		ratingmusic.addStyleName("borderless");
		ratingppr.addStyleName("borderless");
		ratingpeople.addStyleName("borderless");

		ratinggeneral.setCaption("Gesamtbewertung");
		ratingatmo.setCaption("Atmosphäre");
		ratingmusic.setCaption("Musik");
		ratingppr.setCaption("Preis-/Leistung");
		ratingpeople.setCaption("Leute");

		layout.addComponent(ratinggeneral);
		layout.addComponent(ratingatmo);
		layout.addComponent(ratingmusic);
		layout.addComponent(ratingppr);
		layout.addComponent(ratingpeople);
		layout.addComponent(ratingButton);

		ratingpanel.setContent(layout);
		ratingpanel.setId("bar-view-ratingpanel");

		ratingButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				generalRating = ratinggeneral.getValue().intValue();
				pprRating = ratingppr.getValue().intValue();
				atmoRating = ratingatmo.getValue().intValue();
				musicRating = ratingmusic.getValue().intValue();
				peopleRating = ratingpeople.getValue().intValue();

				/* Platzhalter Integer */

				Rating rating = new Rating(-1, -1, -1, generalRating, pprRating, musicRating, peopleRating, atmoRating,
						null);
				for (BarViewListener listener : listenerList) {
					listener.ratingButtonClick(rating);
				}

			}
		});

		return ratingpanel;
	}

	private void saveRating() {
		generalRating = ratinggeneral.getValue().intValue();
		pprRating = ratingppr.getValue().intValue();
		atmoRating = ratingatmo.getValue().intValue();
		musicRating = ratingmusic.getValue().intValue();
		peopleRating = ratingpeople.getValue().intValue();

		/* Platzhalter Integer */

		Rating rating = new Rating(-1, -1, -1, generalRating, pprRating, musicRating, peopleRating, atmoRating, null);
		for (BarViewListener listener : listenerList) {
			listener.ratingButtonClick(rating);
		}
	}

	@Override
	public void addRatingButtonClickListener(BarViewListener listener) {
		listenerList.add(listener);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		initializing = true;
		UI.getCurrent().getPage().setTitle("Bar");
		for (BarViewListener listener : listenerList) {
			listener.enter(event);
		}
		initializing = false;
	}

	public Image createBarPicture() {

		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/logo.png"));

		// Show the image in the application
		Image barimage = new Image(null, resource);

		barimage.setHeight("150px");

		barimage.setId("bar-view-barimage");
		return barimage;

	}

	@Override
	public void setMessageBoardStrings(List<Message> messages) {
		String buffer = "";
		for (Message message : messages) {
			buffer += message.getMessage() + "\n";
		}
		// TODO CustomComponet für Messages
		messageArea.setValue(buffer);
	}

	@Override
	public void setBarDescription(String barDescription) {
		barInfoText.setReadOnly(false);
		barInfoText.setValue(barDescription);
	}

	@Override
	public void setBarTitle(String barName) {
		mainPanel.setCaption(barName);
	}

	@Override
	public void setBarAddress(Address address) {
		String addressString = address.getStreet() + " " + address.getNumber();
		addressString += "\n" + address.getZip() + ", " + address.getCity();
		this.address.setValue(addressString);
	}

	@Override
	public void updateBarMessageBoard() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setRating(Bar bar) {
		if (bar == null) {
			return;
		}

		ratinggeneral.setValue(bar.getGeneralRating());
		ratingatmo.setValue(bar.getAtmosphereRating());
		ratingmusic.setValue(bar.getMusicRating());
		ratingpeople.setValue(bar.getPeopleRating());
		ratingppr.setValue(bar.getPprRating());
	}

	@Override
	public void setMapCoords(LatLon coords) {
		map.setCenter(coords);
		map.addMarker(new GoogleMapMarker("", coords, false));
	}
}
