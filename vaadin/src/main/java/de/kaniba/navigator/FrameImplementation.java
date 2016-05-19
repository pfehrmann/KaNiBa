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
                if (menuPart.getStyleName().contains("valo-menu-visible")) {
                	menuPart.removeStyleName("valo-menu-visible");
                } else {
                	menuPart.addStyleName("valo-menu-visible");
                }
            }
		});
	}
}
