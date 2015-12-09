package de.kaniba.view;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.concurrent.BackgroundInitializer;
import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import de.kaniba.model.Database;
import de.kaniba.model.Message;
import de.kaniba.model.Pinboard;
import de.kaniba.model.Rating;

public class BarViewImpl extends CustomComponent implements BarView {

	public java.util.List<BarViewListener> listenerList = new ArrayList<BarViewListener>();

	public int generalRating;
	public int pprRating;
	public int musicRating;
	public int atmoRating;
	public int peopleRating = 0;
	public RatingStars ratinggeneral = new RatingStars();
	public RatingStars ratingatmo = new RatingStars();
	public RatingStars ratingmusic = new RatingStars();
	private RatingStars ratingppr = new RatingStars();
	private RatingStars ratingpeople = new RatingStars();
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
		mainLayout.addComponent(ratingStars, 0, 1);
		mainLayout.addComponent(barImage,0,0);
		mainLayout.addComponent(barInfoText,1,0,1,1);
		mainLayout.addComponent(pinboardPanel,0,2,1,2);
		
		mainPanel.setContent(mainLayout);

		setCompositionRoot(mainPanel);
	}

	private TextArea createBarInfoPanel() {
		
		/*Hier fehlt noch eine Anbindung zur Datenbank deswegen Loreipsum Text*/
		
		
		textInfo = new TextArea();
		textInfo.setCaption("Ueberblick");
		
		
		textInfo.setWidth("400px");
		textInfo.setHeight("350px");
		textInfo.addStyleName("ratingpanel");
		textInfo.setReadOnly(true);
		
		
		
		return textInfo;
	}

	private Panel createMainPanel() {
		Panel mainPanel = new Panel();

		mainPanel.setWidth("1024px");
		mainPanel.setHeight("600px");
		return mainPanel;
	}

	private Panel createMessagePanel(){
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
				 for(BarViewListener listener : listenerList){
					 listener.sendMessage(message);
				 }
				
			}
		});
		
		messageArea.setCaption("Messageboard");

		messageHLayout.addComponent(messageField);
		messageHLayout.addComponent(messageSendButton);
		messageVLayout.addComponent(messageArea);
		messageVLayout.addComponent(messageHLayout);
		messagePanel.setContent(messageVLayout);
		
		return messagePanel ;
	}

	private Panel createRatingStars() {
		// Diese Methode legt die Anzeigeelemente für die Sterne an
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

		ratinggeneral.setCaption("Gesamtbewertung Bar: ");
		ratingatmo.setCaption("Bewertung Atmosphäre: ");
		ratingmusic.setCaption("Bewertung der Musik: ");
		ratingppr.setCaption("Bewertung Preis-/Leistung: ");
		ratingpeople.setCaption("Bewertung Leute:");
		
		ratinggeneral.setImmediate(true);
		ratingatmo.setImmediate(true);
		ratingmusic.setImmediate(true);
		ratingppr.setImmediate(true);
		ratingpeople.setImmediate(true);

		
		starLayout.addComponent(ratinggeneral);
		starLayout.addComponent(ratingatmo);
		starLayout.addComponent(ratingmusic);
		starLayout.addComponent(ratingppr);
		starLayout.addComponent(ratingpeople);
		starLayout.addComponent(ratingButton);

		ratingpanel.setContent(starLayout);

		ratingButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				// TODO Auto-generated method stub
				generalRating = ratinggeneral.getValue().intValue();
				pprRating = ratingppr.getValue().intValue();
				atmoRating = ratingatmo.getValue().intValue();
				musicRating = ratingmusic.getValue().intValue();
				peopleRating = ratingpeople.getValue().intValue();
				/* Platzhalter Integer */
				int barID = 0;
				int ratingID = 0;
				int userID = 0;

				Rating rating = new Rating(ratingID, userID, barID, generalRating, pprRating, musicRating, peopleRating,
						atmoRating, null);
				for (BarViewListener listener : listenerList) {
					listener.ratingButtonClick(rating);
				}

			}
		});

		return ratingpanel;
	}

	@Override
	public void addRatingButtonClickListener(BarViewListener listener) {
		// TODO Auto-generated method stub
		listenerList.add(listener);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	public Image createBarPicture(){
	
		
		String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath +"/WEB-INF/images/logo.png"));

		//Show the image in the application
		Image barimage = new Image(null, resource);
	
		barimage.setHeight("150px");
	
		
		return barimage;
		
	}

	@Override
	public void setMessageBoardStrings(List<Message> messages) {
		String buffer = ""; 
		for(Message message : messages){
			 buffer+=message.getMessage()+"\n";
		 }
		//TODO CustomComponet für Messages
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
	
}
