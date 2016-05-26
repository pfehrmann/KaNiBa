package de.kaniba.navigator;

import java.io.File;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import de.kaniba.utils.NavigationUtils;

/**
 * The main UI
 */
@Theme("mytheme")
@Widgetset("de.kaniba.vaadin.MyAppWidgetset")
@PreserveOnRefresh
public class NavigatorUI extends UI implements ViewChangeListener {
	private static final long serialVersionUID = 1L;

	private FrameImplementation design;

	/**
	 * Create the navigator ui
	 */
	public NavigatorUI() {
		super();
	}

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		// Use the new design
		design = new FrameImplementation();

		// Add the logo to the menu
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/logo.png"));
		design.logo.setSource(resource);

		// Make sure, that the design is used as the content
		setContent(design);

		// Create the Navigator
		Navigator navigator = new CustomNavigator(this, design.content);
		
		// Register the view change listener for 
		navigator.addViewChangeListener(NavigationUtils.viewChangeListener);
		
		// Register the listener for the menu visibility
		navigator.addViewChangeListener(this);
	}

	public void setMenu(Component menu) {
		design.menuContainer.removeAllComponents();
		design.menuContainer.addComponent(menu);
	}

	/**
	 * Set the visibility of the menu
	 * 
	 * @param visible
	 */
	public void setMenuVisibility(boolean visible) {
		if (visible) {
			design.menuPart.addStyleName("valo-menu-visible");
			return;
		}
		design.menuPart.removeStyleName("valo-menu-visible");
	}

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		return true;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {
		setMenuVisibility(false);
	}
}
