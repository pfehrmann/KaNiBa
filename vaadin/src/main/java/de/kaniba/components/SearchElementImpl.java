package de.kaniba.components;

import de.kaniba.model.Bar;
import de.kaniba.presenter.Utils;

public class SearchElementImpl extends SearchElement {

	public SearchElementImpl (Bar bar) {
		addressLabel.setValue(Utils.getOneLineAddress(bar));
		nameLabel.setValue(bar.getName());
	}
}
