package de.kaniba.view;

import java.io.File;
import java.sql.SQLException;
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
import com.vaadin.ui.Button;
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
import de.kaniba.model.Tag;
import de.kaniba.utils.LoggingUtils;
import de.kaniba.utils.Utils;

/**
 * The view of a bar.
 * @author Philipp
 *
 */
public class BarView extends BarDesign implements View {
	private static final int DEFAULT_ZOOM = 15;

	private static final long serialVersionUID = 1L;

	public static final String NAME = "bar";
	
	protected GoogleMap map;

	private BarInterface presenter;

	/**
	 * Sets up the basic layout and tries to fix it.
	 */
	public BarView() {

		// Resize the grid
		super.removeComponent(leftGrid);
		super.addComponent(leftGrid, 0, 0, 0, 1);

		infoPanel.setContent(new Label("Keine Beschreibung verfügbar", ContentMode.HTML));
		map = new GoogleMap("apiKey", null, "german");
		map.setSizeFull();
		map.setZoom(DEFAULT_ZOOM);
		map.removeControl(GoogleMapControl.MapType);
		map.removeControl(GoogleMapControl.StreetView);
		super.setRowExpandRatio(0, 1.0F);
		super.addComponent(map, 1, 0);

		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/stoevchen.png"));
		barImage.setHeightUndefined();
		barImage.setSource(resource);

		rateButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				saveRatingClick();
			}
		});

		starTotal.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				generalRatingStarClick();
			}
		});

		starPeople.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				peopleRatingStarClick();
			}
		});

		starAtmosphere.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				atmosphereRatingStarClick();
			}
		});

		starMusic.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				musicRatingStarClick();
			}
		});

		starPrice.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				priceRatingStarClick();
			}
		});
		
		sendMessageButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				presenter.sendMessage(messageTextField.getValue());
			}
		});
		
		surveyButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				presenter.clickedSurvey();
			}
		});
	}

	private void priceRatingStarClick() {
		Rating rating = new Rating(-1, -1, -1, 0, 0, 0, 0, 0, null);
		rating.setPprRating(starPrice.getValue().intValue());
		presenter.saveRating(rating);
	}

	private void musicRatingStarClick() {
		Rating rating = new Rating(-1, -1, -1, 0, 0, 0, 0, 0, null);
		rating.setMusicRating(starMusic.getValue().intValue());
		presenter.saveRating(rating);
	}

	private void atmosphereRatingStarClick() {
		Rating rating = new Rating(-1, -1, -1, 0, 0, 0, 0, 0, null);
		rating.setAtmosphereRating(starAtmosphere.getValue().intValue());
		presenter.saveRating(rating);
	}

	private void peopleRatingStarClick() {
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

	public void setPresenter(BarInterface presenter) {
		this.presenter = presenter;
	}

	public void setBarMessageBoard(List<Message> messages) {
		if(messages == null) {
			return;
		}
		
		final List<Component> components = new ArrayList<>();
		for(Message element : messages) {
			InternalUser user = null;
			try {
				user = Database.giveUser(element.getUserID());
			} catch (SQLException e) {
				LoggingUtils.exception(e);
			}
			
			if(user != null) {
				StringBuilder text = new StringBuilder("");
				text.append(element.getTime().getDate() + "." + element.getTime().getMonth() + "." + (element.getTime().getYear()+1900) + "");
				text.append(" <b>" + user.getFirstname() + " " + user.getName().substring(0, 1) + "." + "</b>: ");
				text.append(element.getMessageText());
				CssLayout layout = new CssLayout();
				Label component = new Label(text.toString(), ContentMode.HTML);
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
		File image = new File(Utils.getBarLogoBasePath() + bar.getBarID() + ".png");
		
		if(!image.exists()) {
			image = new File(Utils.getBarLogoBasePath() + "logo.png");
		}
		
		FileResource resource = new FileResource(image);
		barImage.setSource(resource);
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
	
	public void setTags(List<Tag> tags, final int barID) {
		tagLayout.removeAllComponents();
		for(Tag tag : tags) {
			tagLayout.addComponent(tag.getComponent());
		}
		Button button = new Button("+");
		button.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Tag.createNewTag(barID, new Callback() {
					
					@Override
					public void success() {
						presenter.updateTagList();
					}
				});
			}
		});
		tagLayout.addComponent(button);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeComponent(map);
		map = new GoogleMap("apiKey", null, "german");
		map.setSizeFull();
		map.setZoom(DEFAULT_ZOOM);
		map.removeControl(GoogleMapControl.MapType);
		map.removeControl(GoogleMapControl.StreetView);
		setRowExpandRatio(0, 1.0F);
		addComponent(map, 1, 0);
		
		presenter.enter(event);
	}
}
