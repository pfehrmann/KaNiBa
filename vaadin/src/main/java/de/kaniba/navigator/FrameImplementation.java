package de.kaniba.navigator;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * Implementation of the frame
 * @author Philipp
 *
 */
public class FrameImplementation extends FrameDesign {
	private static final long serialVersionUID = 1L;

	/**
	 * Initialize all the listeners
	 */
	public FrameImplementation() {
		menuToggle.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
            public void buttonClick(ClickEvent event) {
                if (menuContainer.getStyleName().contains("valo-menu-visible")) {
                	menuContainer.removeStyleName("valo-menu-visible");
                } else {
                	menuContainer.addStyleName("valo-menu-visible");
                }
            }
		});
	}
}
