package de.kaniba.presenter;

import java.sql.SQLException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification.Type;

import de.kaniba.model.Bar;
import de.kaniba.utils.BarUtils;
import de.kaniba.utils.Utils;
import de.kaniba.view.BarView;
import de.kaniba.view.EditBarInterface;
import de.kaniba.view.EditBarView;

/**
 * Logic for editing bars
 * @author Philipp
 *
 */
public class EditBarPresenter implements EditBarInterface {
	private EditBarView view;
	private Bar bar;
	
	/**
	 * @param view The view to manage
	 */
	public EditBarPresenter(EditBarView view) {
		this.view = view;
		view.setPresenter(this);
	}

	public View getView() {
		return view;
	}

	/* (non-Javadoc)
	 * @see de.kaniba.presenter.EditBarInterface#saveBar()
	 */
	@Override
	public void saveBar() {
		Bar read = view.getBar();
		bar.setAddress(read.getAddress());
		bar.setName(read.getName());
		bar.setDescription(read.getDescription());
		try {
			bar.saveBar();
			Utils.navigateTo(BarView.NAME + "/" + bar.getBarID());
			Utils.showNotification("Die Änderungen wurden erfolgreich gespeichert.");
		} catch (SQLException e) {
			Utils.exception(e);
		}
	}

	/* (non-Javadoc)
	 * @see de.kaniba.presenter.EditBarInterface#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public boolean enter(ViewChangeEvent event) {
		bar = BarUtils.getBarFromParams(event.getParameters());
		view.setBar(bar);
		
		if(!Utils.isAdmin()) {
			Utils.navigateBack("Um eine Bar bearbeiten zu können, musst du ein Bar Admin sein.", Type.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
}
