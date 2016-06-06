package de.kaniba.components;

import org.vaadin.addon.vol3.OLMap;
import org.vaadin.addon.vol3.OLView;
import org.vaadin.addon.vol3.OLViewOptions;
import org.vaadin.addon.vol3.OLMap.ClickListener;
import org.vaadin.addon.vol3.OLMap.OLClickEvent;
import org.vaadin.addon.vol3.client.Projections;
import org.vaadin.addon.vol3.client.style.OLFillStyle;
import org.vaadin.addon.vol3.client.style.OLIconStyle;
import org.vaadin.addon.vol3.client.style.OLStyle;
import org.vaadin.addon.vol3.client.style.OLTextStyle;
import org.vaadin.addon.vol3.feature.OLFeature;
import org.vaadin.addon.vol3.feature.OLGeometry;
import org.vaadin.addon.vol3.feature.OLPoint;
import org.vaadin.addon.vol3.layer.OLTileLayer;
import org.vaadin.addon.vol3.layer.OLVectorLayer;
import org.vaadin.addon.vol3.source.OLOSMSource;
import org.vaadin.addon.vol3.source.OLSource;
import org.vaadin.addon.vol3.source.OLVectorSource;
import org.vaadin.addon.vol3.util.StyleUtils;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;

import de.kaniba.model.Coordinates;
import de.kaniba.utils.NavigationUtils;

/**
 * Class for abstraction of the map element
 * 
 * @author Philipp
 *
 */
public class Map extends CustomComponent {
	private static final int DEFAULT_ZOOM = 12;
	private static final int MAX_ZOOM = 18;
	private static final int MIN_ZOOM = 4;
	private static final OLStyle style;
	
	static {
		style= StyleUtils.createDefaultStyle();
        style.circleStyle.fill=new OLFillStyle("red");
        style.circleStyle.stroke.color="blue";
        style.circleStyle.radius = 5.0;
        style.textStyle = new OLTextStyle();
	}

	private static final long serialVersionUID = 1L;

	private OLMap openMap;
	private OLTileLayer baseLayer;
	private OLVectorLayer vectorLayer;
	private OLVectorSource source;

	/**
	 * Initialize the map. The center will not be set.
	 */
	public Map() {
		openMap = new OLMap();
		openMap.setView(createView());
		openMap.setSizeFull();
		baseLayer = new OLTileLayer(createTileSource());
		source = new OLVectorSource();
		vectorLayer = new OLVectorLayer(source);
		vectorLayer.setLayerVisible(true);
		openMap.addLayer(baseLayer);
		openMap.addLayer(vectorLayer);
		setSizeFull();
		setCompositionRoot(openMap);
	}

	private OLView createView() {
		OLViewOptions options = new OLViewOptions();
		options.setInputProjection(Projections.EPSG4326);
		options.setMinZoom(MIN_ZOOM);
		options.setMaxZoom(MAX_ZOOM);

		OLView view = new OLView(options);
		view.setZoom(DEFAULT_ZOOM);

		return view;
	}

	private OLSource createTileSource() {
		return new OLOSMSource();
	}

	/**
	 * Set center of the map
	 * 
	 * @param coords
	 */
	public void setCenter(Coordinates coords) {
		if(coords == null) {
			return;
		}
		
		openMap.getView().setCenter(coords.getLon(), coords.getLat());
	}

	/**
	 * Add a marker to the map
	 * 
	 * @param coords
	 *            The coordinates of the marker
	 */
	public void addMarker(Coordinates coords) {
		if(coords == null) {
			return;
		}
		OLFeature feature = new OLFeature();
		feature.setGeometry(new OLPoint(coords.getLon(), coords.getLat()));
		feature.setStyle(style);
		
		source.addFeature(feature);
	}

	/**
	 * Add a marker to the map. The map will navigate to the provided state
	 * 
	 * @param state
	 * @param coords
	 */
	public void addMarker(final String state, Coordinates coords) {
		if(coords == null) {
			return;
		}
		
		final String id = state + new OLFeature().hashCode();
		final OLFeature feature = new OLFeature(id);
		feature.setGeometry(new OLPoint(coords.getLon(), coords.getLat()));
		feature.setId(id);
		feature.setStyle(style);

		source.addFeature(feature);

		openMap.addClickListener(new ClickListener() {

			@Override
			public void onClick(OLClickEvent clickEvent) {
				if (clickEvent.getFeatureIds().contains(id)) {
					NavigationUtils.navigateTo(state);
				}
			}
		});
	}

	/**
	 * Removes all the markers from the map
	 */
	public void removeAllMarkers() {
		while (!source.getFeatures().isEmpty()) {
			source.removeFeatureById(source.getFeatures().get(0).getId());
		}
	}

	/**
	 * Set the zoom to the specified value. The maximum zoom is 18, the minimum
	 * is 4.
	 * 
	 * @param zoom
	 */
	public void setZoom(int zoom) {
		openMap.getView().setZoom(zoom);
	}
}
