package de.kaniba.presenter;

import com.vaadin.navigator.View;

import de.kaniba.model.User;
import de.kaniba.view.BarView;
import de.kaniba.view.LoginView;

public class BarPresenter implements BarView.BarViewListener {
	private BarView view;

	public BarPresenter(BarView view) {
		this.view = view;
	}

	public View getView() {
		return view;
	}

	@Override
	public void buttonClick(char operation) {
		// TODO Auto-generated method stub

	}

}
