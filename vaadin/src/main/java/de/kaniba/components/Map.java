package de.kaniba.components;

import org.vaadin.addon.vol3.OLMap;
import org.vaadin.addon.vol3.OLView;
import org.vaadin.addon.vol3.OLViewOptions;
import org.vaadin.addon.vol3.OLMap.ClickListener;
import org.vaadin.addon.vol3.OLMap.OLClickEvent;
import org.vaadin.addon.vol3.client.Projections;
import org.vaadin.addon.vol3.feature.OLFeature;
import org.vaadin.addon.vol3.feature.OLPoint;
import org.vaadin.addon.vol3.layer.OLTileLayer;
import org.vaadin.addon.vol3.layer.OLVectorLayer;
import org.vaadin.addon.vol3.source.OLOSMSource;
import org.vaadin.addon.vol3.source.OLSource;
import org.vaadin.addon.vol3.source.OLVectorSource;

import com.vaadin.ui.CustomComponent;

import de.kaniba.utils.NavigationUtils;

/**
 * Class for abstraction of the map element
 * @author Philipp
 *
 */
public class Map extends CustomComponent {
	private static final long serialVersionUID = 1L;
	
	private OLMap map;
	private OLTileLayer baseLayer;
	private OLVectorLayer vectorLayer;
	private OLVectorSource source;

	public Map() {
		map = new OLMap();
		map.setView(createView());
		map.setSizeFull();
		baseLayer = new OLTileLayer(createTileSource());
		source = new OLVectorSource();
		vectorLayer = new OLVectorLayer(source);
		vectorLayer.setLayerVisible(true);
		map.addLayer(baseLayer);
		map.addLayer(vectorLayer);
		setSizeFull();
		setCompositionRoot(map);
	}

	private OLView createView() {
		OLViewOptions options = new OLViewOptions();
		options.setInputProjection(Projections.EPSG4326);
		options.setMinZoom(4);
		options.setMaxZoom(18);
		
		OLView view = new OLView(options);
		view.setZoom(12);
		
		return view;
	}

	private OLSource createTileSource() {
		return new OLOSMSource();
	}

	/**
	 * Set senter of the map
	 * @param lat
	 * @param lon
	 */
	public void setCenter(Coordinates coords) {
		map.getView().setCenter(coords.getLon(), coords.getLat());
	}

	/**
	 * Add a marker to the map
	 * @param lat
	 * @param lon
	 */
	public void addMarker(Coordinates coords) {
		OLFeature feature = new OLFeature();
		feature.setGeometry(new OLPoint(coords.getLon(), coords.getLat()));
		
		source.addFeature(feature);
	}
	
	/**
	 * Add a marker to the map. The map will navigate to the provided state
	 * @param state
	 * @param lat
	 * @param lon
	 */
	public void addMarker(final String state, Coordinates coords) {
		final String id = state + new OLFeature().hashCode();
		final OLFeature feature = new OLFeature(id);
		feature.setGeometry(new OLPoint(coords.getLon(), coords.getLat()));
		feature.setId(id);

		source.addFeature(feature);
		//source.updateFeature(feature);
		
		map.addClickListener(new ClickListener() {
			
			@Override
			public void onClick(OLClickEvent clickEvent) {
				if(clickEvent.getFeatureIds().contains(id)) {
					NavigationUtils.navigateTo(state);
				}
			}
		});
	}
	
	/**
	 * Removes all the markers from the map
	 */
	public void removeAllMarkers() {
		while(!source.getFeatures().isEmpty()) {
			source.removeFeatureById(source.getFeatures().get(0).getId());
		}
	}
	
	public static class Coordinates {
		private double lat;
		private double lon;
		
		public Coordinates(double lat, double lon) {
			this.lat = lat;
			this.lon = lon;
		}
		
		public double getLat() {
			return lat;
		}
		
		public double getLon() {
			return lon;
		}
	}
}
