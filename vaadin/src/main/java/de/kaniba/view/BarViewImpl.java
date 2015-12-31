package de.kaniba.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;

import de.kaniba.model.Bar;
import de.kaniba.model.Message;
import de.kaniba.model.Rating;

public class BarViewImpl extends CustomComponent implements BarView {

	public java.util.List<BarViewListener> listenerList = new ArrayList<BarViewListener>();

	public int generalRating;
	public int pprRating;
	public int musicRating;
	public int atmoRating;
	public int peopleRating = 0;
	public RatingStars ratinggeneral;
	public RatingStars ratingatmo;
	public RatingStars ratingmusic;
	
	private RatingStars ratingppr;
	private RatingStars ratingpeople;
	private TextArea messageArea;
	private TextArea textInfo;
	private String message;
	private TextField messageField;

	public BarViewImpl() {
		Panel mainPanel = createMainPanel();
		
		TextArea barInfoText = createBarInfoPanel();
		Panel ratingStars = createRatingStars();
		Panel pinboardPanel = createMessagePanel();
		Image barImage = createBarPicture();
		
		GridLayout mainLayout = new GridLayout(2, 3);
		mainLayout.setWidth("100%");
		mainLayout.setSpacing(true);
		
		mainLayout.setColumnExpandRatio(1, 1.0f);
		mainLayout.addComponent(ratingStars, 0, 1);
		mainLayout.addComponent(barImage, 0, 0);
		mainLayout.addComponent(barInfoText, 1, 0, 1, 1);
		mainLayout.addComponent(pinboardPanel, 0, 2, 1, 2);

		mainPanel.setContent(mainLayout);
		
		setCompositionRoot(mainPanel);
	}

	private TextArea createBarInfoPanel() {
		textInfo = new TextArea();
		textInfo.setWidth("100%");
		textInfo.setHeight("100%");
		textInfo.setCaption("Ueberblick");

		textInfo.addStyleName("ratingpanel");
		textInfo.setReadOnly(true);
		textInfo.setId("bar-view-textInfo");

		return textInfo;
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

	private Panel createRatingStars() {
		// Diese Methode legt die Anzeigeelemente für die Sterne an
		ratinggeneral = new RatingStars();
		ratingatmo = new RatingStars();
		ratingmusic = new RatingStars();
		ratingppr = new RatingStars();
		ratingpeople = new RatingStars();

		Panel ratingpanel = new Panel();
		Button ratingButton = new Button("Speichern");

		ratingpanel.addStyleName("ratingpanel");
		VerticalLayout starLayout = new VerticalLayout();
		starLayout.setSizeFull();

		ratinggeneral.setMaxValue(5);
		ratingatmo.setMaxValue(5);
		ratingmusic.setMaxValue(5);
		ratingppr.setMaxValue(5);
		ratingpeople.setMaxValue(5);

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

		ratinggeneral.setCaption("Gesamtbewertung Bar: ");
		ratingatmo.setCaption("Bewertung Atmosphäre: ");
		ratingmusic.setCaption("Bewertung der Musik: ");
		ratingppr.setCaption("Bewertung Preis-/Leistung: ");
		ratingpeople.setCaption("Bewertung Leute:");

		starLayout.addComponent(ratinggeneral);
		starLayout.addComponent(ratingatmo);
		starLayout.addComponent(ratingmusic);
		starLayout.addComponent(ratingppr);
		starLayout.addComponent(ratingpeople);
		starLayout.addComponent(ratingButton);

		ratingpanel.setContent(starLayout);
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

	@Override
	public void addRatingButtonClickListener(BarViewListener listener) {
		listenerList.add(listener);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		UI.getCurrent().getPage().setTitle("Bar");
		for (BarViewListener listener : listenerList) {
			listener.enter();
		}
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
		textInfo.setReadOnly(false);
		textInfo.setValue(barDescription);
	}

	@Override
	public void updateBarMessageBoard() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setRating(Bar bar) {
		ratinggeneral.setValue(bar.getGeneralRating());
		ratingatmo.setValue(bar.getAtmosphereRating());
		ratingmusic.setValue(bar.getMusicRating());
		ratingpeople.setValue(bar.getPeopleRating());
		ratingppr.setValue(bar.getPprRating());
	}
}
